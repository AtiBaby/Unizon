package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FlowEvent;
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

	private Image image;
	private Image storedImage;
	private Product newProduct;
	private Product originalProduct;
	private List<String> selectedCategoryIds;
	private List<String> selectedTags;
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

		user = loginController.getUser();
		this.image = new Image();
		this.storedImage = new Image();
		this.selectedCategoryIds = new ArrayList<>();
		this.selectedTags = new ArrayList<>();
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
		// case "details":
		// return "images";
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
		log.info("Uploading product: {}, category ids: {}, tags: {}.", newProduct, selectedCategoryIds, selectedTags);

		Image queriedImage = imageFacade.findByImageUrl(image.getImageUrl());
		if (queriedImage == null) {
			imageFacade.create(image);
		} else {
			image = queriedImage;
		}
		newProduct.setImage(image);

		newProduct.setCategories(selectedCategoryIds.stream().mapToInt(Integer::valueOf)
				.mapToObj(categoryId -> categoryFacade.find(categoryId)).collect(Collectors.toSet()));

		newProduct.setTags(selectedTags.stream().map(tagString -> {
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

	private void redirect(String url) {
		log.info("Redirecting {} to {}.", user, url);
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
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

	public List<String> getSelectedCategoryIds() {
		return selectedCategoryIds;
	}

	public void setSelectedCategoryIds(List<String> selectedCategoryIds) {
		this.selectedCategoryIds = selectedCategoryIds;
	}

	public List<String> getSelectedTags() {
		return selectedTags;
	}

	public void setSelectedTags(List<String> selectedTags) {
		this.selectedTags = selectedTags;
	}
}
