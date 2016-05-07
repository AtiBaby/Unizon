package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.OrderFacade;
import hu.unideb.inf.Unizon.facade.ProdToOrderFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Order;
import hu.unideb.inf.Unizon.model.Product;

@ManagedBean
@ViewScoped
public class CheckOutController implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Logger log;

	@EJB
	private ProductFacade productFacade;

	@EJB
	private OrderFacade orderFacade;

	@EJB
	private ProdToOrderFacade prodToOrderFacade;

	@ManagedProperty("#{cartItemController}")
	private CartItemController cartItemController;

	private Integer shippingAddressId;
	private Integer billingAddressId;

	public void finalizeShopping() {
		log.info("Finalizing shopping-session...");

		// Updating DB: modify amounts in stock
		// TODO Check ALL newAmounts together and apply only if all conditions
		// fit
		// Some multithreading lock operations should be applied before
		// modifying the DB (if the chosen technologies don't provide)

		for (Map.Entry<Product, Integer> entry : cartItemController.getProducts().entrySet()) {
			Product product = entry.getKey();
			int newAmount = product.getAmount() - entry.getValue();
			// TODO Check newAmount first (individually)
			product.setAmount(newAmount);
			productFacade.edit(product);
		}

		log.info("Products' amount successfully updated.");

		// TODO Update DB: insert new orders
		// Two tables are involved: UNI_ORDER and PROD_TO_ORDER

		log.info("Order successfully saved.");

		// TODO Send an email about the shopping

		log.info("Email about shopping has been sent.");

		// TODO Empty cart

		cartItemController.getProducts().clear();

		log.info("Cart has been emptied.");
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

	public CartItemController getCartItemController() {
		return cartItemController;
	}

	public void setCartItemController(CartItemController cartItemController) {
		this.cartItemController = cartItemController;
	}

	public Integer getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Integer shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public Integer getBillingAddressId() {
		return billingAddressId;
	}

	public void setBillingAddressId(Integer billingAddressId) {
		this.billingAddressId = billingAddressId;
	}
}
