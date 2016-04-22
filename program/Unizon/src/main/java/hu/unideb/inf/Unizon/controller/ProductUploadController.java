package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
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

	@EJB
	private ProductFacade productFacade;
	
	@EJB
	private ImageFacade imageFacade;

	private Product product;
	private Image image;
	private Image storedImage;

	@PostConstruct
	public void init() {
		log.info("Init product upload: ******************************");
		this.product = new Product();
		this.image = new Image();
		this.storedImage = new Image();
	}

	public void upload() {
		log.info("########################uploadban");
		System.out.println(image.getImageUrl());
		imageFacade.create(image);
		for(Image i : imageFacade.findAll()){
			log.info("########################image:{}", image);
			log.info("########################i:{}", i);
			if(i.getImageUrl().equals(image.getImageUrl())){
				log.info("########################match");
				storedImage=i;
			}
		}
		if(image == null){
			log.info("########################null");
		} else {
			log.info("########################" + storedImage.getImageId() + storedImage.getImageUrl());
		}

		product.setImage(storedImage);
		productFacade.create(product);
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
