package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Product;

@ManagedBean
@ViewScoped
public class ProductDetailController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProductFacade productFacade;

	@ManagedProperty("#{cartItemController}")
	private CartItemController cartItemController;

	private int productId;
	private Product actualProduct;

	public void init() {
		actualProduct = productFacade.findById(productId);
	}

	public CartItemController getCartItemController() {
		return cartItemController;
	}

	public void setCartItemController(CartItemController cartItemController) {
		this.cartItemController = cartItemController;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Product getActualProduct() {
		return actualProduct;
	}

	public void setActualProduct(Product actualProduct) {
		this.actualProduct = actualProduct;
	}

}
