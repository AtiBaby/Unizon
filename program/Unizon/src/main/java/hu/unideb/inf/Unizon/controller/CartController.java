package hu.unideb.inf.Unizon.controller;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
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

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

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

	public void removeProductFromCart() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (params.containsKey("productId")) {
			int productId = Integer.parseInt(params.get("productId"));
			selectedProduct = productFacade.findById(productId);
			cartItemController.deleteProductFromCart(selectedProduct);
		}
	}

	public void editProductInCart() {
		cartItemController.editProductInCart(selectedProduct, selectedProductAmount);
	}

	public boolean isCartNotEmpty() {
		return !cartItemController.getProducts().isEmpty();
	}

	public void checkOut() {
		if (!loginController.isLoggedIn()) {
			addErrorMessage("You must be logged in!");
		} else {
			getAndDeleteProducts();
		}

	}

	public synchronized void getAndDeleteProducts() {
		for(Entry<Product, Integer> entry : cartItemController.getProducts().entrySet()) {
		    Integer id = entry.getKey().getProductId();
		    int amount = entry.getValue();
		    Product p= productFacade.findById(id);
		    if(p.getAmount()>=amount){
		    	
		    }
		}

	}

	private void addErrorMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesMessage msg = new FacesMessage(severity, summary, detail);
		facesContext.addMessage(null, msg);
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

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

}
