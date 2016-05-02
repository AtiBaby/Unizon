package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Image;
import hu.unideb.inf.Unizon.model.Product;

@Stateless
@ManagedBean
@ViewScoped
public class ProductDetailController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private ProductFacade productFacade;

	@ManagedProperty("#{cartItemController}")
	private CartItemController cartItemController;

	private Product actualProduct;
	private Set<Image> images;
	private List<Image> productImages;
	private int size;
	private int productId;
	private String actualImageUrl;

	public void init() {
		actualProduct = productFacade.findById(productId);
		images = actualProduct.getImages();
		size = 0;
		actualImageUrl = actualProduct.getImage().getImageUrl();
		productImages = new ArrayList<>();
		for (Iterator<Image> it = images.iterator(); it.hasNext();) {
			Image f = it.next();
			productImages.add(f);
		}
		size = productImages.size();
	}

	public void pictureClick(String imageUrl) {
		setActualImageUrl(imageUrl);
	}

	public Product getActualProduct() {
		return actualProduct;
	}

	public void setActualProduct(Product actualProduct) {
		this.actualProduct = actualProduct;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public List<Image> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<Image> productImages) {
		this.productImages = productImages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getActualImageUrl() {
		return actualImageUrl;
	}

	public void setActualImageUrl(String actualImageUrl) {
		this.actualImageUrl = actualImageUrl;
	}

	public CartItemController getCartItemController() {
		return cartItemController;
	}

	public void setCartItemController(CartItemController cartItemController) {
		this.cartItemController = cartItemController;
	}
}
