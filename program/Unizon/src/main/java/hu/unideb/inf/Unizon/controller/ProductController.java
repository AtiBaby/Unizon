package hu.unideb.inf.Unizon.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.CategoryFacade;
import hu.unideb.inf.Unizon.facade.ImageFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.facade.TagFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Image;
import hu.unideb.inf.Unizon.model.Product;
import hu.unideb.inf.Unizon.model.Tag;
import hu.unideb.inf.Unizon.model.User;
import hu.unideb.inf.Unizon.util.ImageWrapper;

@ManagedBean
@ViewScoped
public class ProductController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@EJB
	private ProductFacade productFacade;

	@EJB
	private ImageFacade imageFacade;

	@EJB
	private CategoryFacade categoryFacade;

	@EJB
	private TagFacade tagFacade;

	@EJB
	private UserFacade userFacade;

	@Inject
	private Event<Product> productEventSrc;

	private Product newProduct;
	private Product originalProduct;
	private List<String> categories;
	private List<String> tags;
	private List<ImageWrapper> selectedImages;
	private List<ImageWrapper> uploadedImages;
	private User user;

	public void init() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		System.out.println("productId: " + params.get("productId"));
		if (params.get("productId") != null) {
			int productId = Integer.parseInt(params.get("productId"));
			originalProduct = productFacade.find(productId);
		} else {
			originalProduct = null;
		}

		newProduct = new Product();

		if (originalProduct != null) {
			newProduct.setProductId(originalProduct.getProductId());
			newProduct.setTitle(originalProduct.getTitle());
			newProduct.setAmount(originalProduct.getAmount());
			newProduct.setPrice(originalProduct.getPrice());
			newProduct.setDescription(originalProduct.getDescription());
			newProduct.setImage(originalProduct.getImage());
			newProduct.setDeleted(originalProduct.getDeleted());
		}

		this.user = loginController.getUser();
		this.categories = new ArrayList<>();
		this.tags = new ArrayList<>();
		this.uploadedImages = new ArrayList<>();
		this.selectedImages = new ArrayList<>();
	}

	public void showAllProducts() {
		redirect("/admin.jsf?faces-redirect=true");
	}

	public void editProductListen() {
		init();
	}

	public String addProductOnFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	public List<String> completeTags(String query) {
		Stream<String> tags = tagFacade.findByNameStartingWith(query).stream().map(Tag::getName);

		return Stream.concat(Stream.of(query), tags).sorted().distinct().collect(Collectors.toList());
	}

	public void upload() {
		log.info("Uploading product: {}, categories: {}, tags: {}.", newProduct, categories, tags);

		newProduct.setImages(selectedImages.stream().map(ImageWrapper::getImage).collect(Collectors.toSet()));
		newProduct.setImage(new ArrayList<>(newProduct.getImages()).get(0)); // TODO radio buttonnal kellene az ui-n

		newProduct.setCategories(categories.stream().map(categoryFacade::findByName).collect(Collectors.toSet()));

		newProduct.setTags(tags.stream().map(tagString -> {
			Tag tag = tagFacade.findByName(tagString);
			if (tag == null) {
				tag = new Tag();
				tag.setName(tagString);
				tagFacade.create(tag);
			}
			return tag;
		}).collect(Collectors.toSet()));

		productFacade.create(newProduct);
		log.info("Product {} has been uploaded.", newProduct);

		productEventSrc.fire(newProduct);

		redirect("/admin.jsf?faces-redirect=true");
	}

	public void editProduct() {
		if (newProduct.getDeleted() == true) {
			System.out.println("You cannot edit a deleted product!");
		} else {
			if (!newProduct.equals(originalProduct)) {
				newProduct = productFacade.edit(newProduct);
			}
		}

		productEventSrc.fire(newProduct);

		redirect("/admin.jsf?faces-redirect=true");
	}

	public void removeProduct() {
		init();

		if (newProduct.getDeleted() == false) {
			newProduct.setDeleted(true);
			newProduct = productFacade.edit(newProduct);
		}

		productEventSrc.fire(newProduct);

		redirect("/admin.jsf?faces-redirect=true");
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile uploadedFile = (UploadedFile) event.getFile();

		if (uploadedFile.getSize() == 0) {
			return;
		}

		try (InputStream inputStr = uploadedFile.getInputstream()) {
			String imageUrl = (System.getProperty("user.home") + "/Desktop/unizonPictures/"
					+ new Date(System.currentTimeMillis()).getTime() + "/" + uploadedFile.getFileName())
							.replaceAll("\\\\", "/");
			File destFile = new File(imageUrl);

			String mimetype = new MimetypesFileTypeMap().getContentType(destFile);
			String type = mimetype.split("/")[0];
			if (!type.equals("image")) {
				addErrorMessage("The format of the picture is invalid!");
				return;
			}

			FileUtils.copyInputStreamToFile(inputStr, destFile);

			Image image = new Image();
			image.setImageUrl(imageUrl);
			imageFacade.create(image);

			uploadedImages.add(new ImageWrapper(image, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectImage() {
		String imageId = facesContext.getExternalContext().getRequestParameterMap().get("imageId");
		Image image = imageFacade.find(Integer.valueOf(imageId));

		System.out.println("Before doing anything: " + selectedImages.size());
		ImageWrapper imageWrapper = new ImageWrapper(image, true);
		if (selectedImages.contains(imageWrapper)) {
			selectedImages.remove(imageWrapper);
		} else {
			selectedImages.add(imageWrapper);
		}
		System.out.println("After doing anything: " + selectedImages.size());
	}

	private void addErrorMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesMessage msg = new FacesMessage(severity, summary, detail);
		facesContext.addMessage(null, msg);
	}

	private void redirect(String url) {
		log.info("Redirecting {} to {}.", user, url);
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public Product getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Product product) {
		this.newProduct = product;
	}

	public Product getOriginalProduct() {
		return originalProduct;
	}

	public void setOriginalProduct(Product product) {
		this.originalProduct = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<ImageWrapper> getSelectedImages() {
		return selectedImages;
	}

	public void setSelectedImages(List<ImageWrapper> images) {
		this.selectedImages = images;
	}

	public List<ImageWrapper> getUploadedImages() {
		return uploadedImages;
	}

	public void setUploadedImages(List<ImageWrapper> uploadedImages) {
		this.uploadedImages = uploadedImages;
	}

}
