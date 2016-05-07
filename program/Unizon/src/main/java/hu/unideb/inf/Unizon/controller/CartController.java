package hu.unideb.inf.Unizon.controller;

import java.util.Map;

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
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Product;

@ManagedBean
@ViewScoped
public class CartController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private Logger log;

	@EJB
	private ProductFacade productFacade;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@ManagedProperty("#{cartItemController}")
	private CartItemController cartItemController;

	@ManagedProperty("#{checkOutController}")
	private CheckOutController checkOutController;

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

	public void checkOut() {
		if (!loginController.isLoggedIn()) {
			addInfoMessage("Please, log in first!");
			return;
		}

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('checkOutFromCartDialogVar').show();");
	}

	private void addInfoMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_INFO, "INFO", detail);
	}

	private void addErrorMessage(String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, "ERROR", detail);
	}

	private void addMessage(Severity severity, String summary, String detail) {
		FacesMessage msg = new FacesMessage(severity, summary, detail);
		facesContext.addMessage(null, msg);
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public CartItemController getCartItemController() {
		return cartItemController;
	}

	public void setCartItemController(CartItemController cartItemController) {
		this.cartItemController = cartItemController;
	}

	public CheckOutController getCheckOutController() {
		return checkOutController;
	}

	public void setCheckOutController(CheckOutController checkOutController) {
		this.checkOutController = checkOutController;
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
