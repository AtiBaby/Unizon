package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.ImageFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Image;
import hu.unideb.inf.Unizon.model.Product;

@ManagedBean(name="productUploadController")
@ViewScoped
public class ProductUploadController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;
	
	@EJB
	private ProductFacade productFacade;
	
	@EJB
	private ImageFacade imageFacade;

	private Product product;
	private Image image;
	private Image storedImage;

	@PostConstruct
	public void init() {
		this.product = new Product();
		this.image = new Image();
		this.storedImage = new Image();
	}

	public void upload() {
		log.info("Uploading a product!");
		imageFacade.create(image);
		for(Image i : imageFacade.findAll()){
			if(i.getImageUrl().equals(image.getImageUrl())){
				storedImage=i;
			}
		}
		product.setImage(storedImage);
		productFacade.create(product);
		log.info("Product uploaded, name:{}.", product.getTitle());
		redirect("/admin.jsf?faces-redirect=true");
	}

	public void showProducts(){
		redirect("/admin.jsf?faces-redirect=true");
	}
	
	private void redirect(String url) {
		log.info("Redirecting to {}.", url);
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
}
