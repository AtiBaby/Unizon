package hu.unideb.inf.Unizon.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Product;

/**
 * @author Czuczi
 * @author Zsoca
 */
@ManagedBean
@ViewScoped
public class CartController {

	@Inject
	private FacesContext facesContext;

	@EJB
	private ProductFacade productFacade;

	@ManagedProperty("#{cartItemController}")
	private CartItemController cartItemController;

	private Product selectedProduct;
	private int selectedProductAmount;

	@PostConstruct
	public void init() {
		RequestContext.getCurrentInstance().update("cartDataTable");
		selectedProduct = null;
		selectedProductAmount = 1;
	}

	public void addProductToCartListen() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (params.containsKey("productId")) {
			int productId = Integer.parseInt(params.get("productId"));
			selectedProduct = productFacade.findById(productId);
			selectedProductAmount = 1;
		}
	}

	public void addProductToCart() {
		cartItemController.addProductToCart(selectedProduct, selectedProductAmount);
	}

	public void editProductInCartListen() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (params.containsKey("productId")) {
			int productId = Integer.parseInt(params.get("productId"));
			selectedProduct = productFacade.findById(productId);
			selectedProductAmount = Integer.parseInt(params.get("productAmount"));
		}
	}
	
	public void editProductInCart(){
		cartItemController.editProductInCart(selectedProduct, selectedProductAmount);
	}

	public CartItemController getCartItemController() {
		return cartItemController;
	}

	public void setCartItemController(CartItemController cartItemController) {
		this.cartItemController = cartItemController;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public int getSelectedProductAmount() {
		return selectedProductAmount;
	}

	public void setSelectedProductAmount(int selectedProductAmount) {
		this.selectedProductAmount = selectedProductAmount;
	}
}
