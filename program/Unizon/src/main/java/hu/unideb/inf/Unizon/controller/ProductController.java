package hu.unideb.inf.Unizon.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.primefaces.context.RequestContext;
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

	private boolean badFileFormat = false;
	private String kepLink;
	private Image image;
	private Image storedImage;
	private Product newProduct;
	private Product originalProduct;
	private List<String> categories;
	private List<String> tags;
	private User user;
	private String url;

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

		user = loginController.getUser();
		this.image = new Image();
		this.storedImage = new Image();
		this.url = null;
		this.categories = new ArrayList<>();
		this.tags = new ArrayList<>();
	}

	public void showAllProducts() {
		redirect("/admin.jsf?faces-redirect=true");
	}

	public void editProductListen() {
		init();
	}

	public String addProductOnFlowProcess(FlowEvent event) {
		return event.getNewStep();
		// switch (event.getOldStep()) {
		// case "catsTags":
		//
		//
		// newProduct.setTags(selectedTags.stream().map(tagString -> {
		// Tag tag = tagFacade.findByName(tagString);
		// if (tag == null) {
		// tag = new Tag();
		// tag.setName(tagString);
		// tagFacade.create(tag);
		// }
		// return tag;
		// }).collect(Collectors.toSet()));
		// return "confirm";
		//
		// default:
		// return event.getNewStep();
		// }
	}

	public List<String> completeTags(String query) {
		Stream<String> tags = tagFacade.findByNameStartingWith(query).stream().map(Tag::getName);

		return Stream.concat(Stream.of(query), tags).sorted().distinct().collect(Collectors.toList());
	}

	public void upload() {
		log.info("Uploading product: {}, categories: {}, tags: {}.", newProduct, categories, tags);

		Image queriedImage = imageFacade.findByImageUrl(image.getImageUrl());
		if (queriedImage == null) {
			imageFacade.create(image);
		} else {
			image = queriedImage;
		}
		newProduct.setImage(image);

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
		UploadedFile kep = (UploadedFile) event.getFile();

		InputStream inputStr = null;

		if (kep.getSize() == 0) {
			return;
		}

		try {
			inputStr = kep.getInputstream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		kepLink = (System.getProperty("user.home") + "/uniPicture/" + kep.getFileName()).replaceAll("\\\\", "/");
		System.out.println(kepLink);
		url = ("images/Consumer electronics/" + kep.getFileName()).replaceAll("\\\\", "/");
		File destFile = new File(kepLink);

		String mimetype = new MimetypesFileTypeMap().getContentType(destFile);
		String type = mimetype.split("/")[0];
		if (!type.equals("image")) {
			badFileFormat = true;
			RequestContext.getCurrentInstance().execute("PF('uzenetDialogWidget').hide()");
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
					"A kép formátuma nem megfelelő!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

		try {
			FileUtils.copyInputStreamToFile(inputStr, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (inputStr != null) {
				inputStr.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		image.setImageUrl(url);
		imageFacade.create(image);
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setStoredImage(Image storedImage) {
		this.storedImage = storedImage;
	}

	public Image getStoredImage() {
		return storedImage;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
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

}
