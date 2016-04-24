package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.ImageFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Image;
import hu.unideb.inf.Unizon.model.Product;
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
	private UserFacade userFacade;

	@Inject
	private Event<Product> productEventSrc;

	private Image image;
	private Image storedImage;
	private Product newProduct;
	private Product originalProduct;
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
		}

		user = loginController.getUser();
		this.image = new Image();
		this.storedImage = new Image();
	}

	public void showAllProducts() {
		redirect("/admin.jsf?faces-redirect=true");
	}

	public void editProductListen() {
		System.out.println("Halló world!");
		init();
	}

	public void upload() {
		log.info("Uploading a product!");
		imageFacade.create(image);
		for (Image i : imageFacade.findAll()) {
			if (i.getImageUrl().equals(image.getImageUrl())) {
				storedImage = i;
			}
		}
		newProduct.setImage(storedImage);
		productFacade.create(newProduct);
		log.info("Product uploaded, name: {}.", newProduct.getTitle());
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
}
