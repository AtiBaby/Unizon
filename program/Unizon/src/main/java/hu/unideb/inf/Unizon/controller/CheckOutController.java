package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.exceptions.ActivationEmailException;
import hu.unideb.inf.Unizon.facade.AddressFacade;
import hu.unideb.inf.Unizon.facade.OrderFacade;
import hu.unideb.inf.Unizon.facade.PhoneNumberFacade;
import hu.unideb.inf.Unizon.facade.ProdToOrderFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.Order;
import hu.unideb.inf.Unizon.model.ProdToOrder;
import hu.unideb.inf.Unizon.model.ProdToOrderPK;
import hu.unideb.inf.Unizon.model.Product;

@ManagedBean
@ViewScoped
public class CheckOutController implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{orderEmailController}")
	private OrderEmailController orderEmailController;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Logger log;

	@EJB
	private ProductFacade productFacade;

	@EJB
	private UserFacade userFacade;

	@EJB
	private OrderFacade orderFacade;

	@EJB
	private ProdToOrderFacade prodToOrderFacade;

	@EJB
	private AddressFacade addressFacade;

	@EJB
	private PhoneNumberFacade phoneNumberFacade;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@ManagedProperty("#{cartItemController}")
	private CartItemController cartItemController;

	@ManagedProperty("#{searchController}")
	private SearchController searchController;

	private Integer shippingAddressId;
	private Integer billingAddressId;
	private Integer phoneNumberId;

	public void finalizeShopping() {
		log.info("Finalizing shopping-session...");

		// TODO Check ALL newAmounts together and apply only if all conditions
		// fit

		/*
		 * Some multithreading lock operations should be applied before modifying the DB (if the chosen technologies
		 * don't provide)
		 */

		Order newOrder = new Order();
		newOrder.setUser(loginController.getUser());
		newOrder.setAddress1(addressFacade.find(shippingAddressId));
		newOrder.setAddress2(addressFacade.find(billingAddressId));
		newOrder.setPhoneNumber(phoneNumberFacade.find(phoneNumberId));
		newOrder.setOrderDate(new Date());

		orderFacade.create(newOrder);
		log.info("Order {} added to UNI_ORDER.", newOrder.toString());

		for (Map.Entry<Product, Integer> entry : cartItemController.getProducts().entrySet()) {
			Product product = entry.getKey();

			int newAmount = product.getAmount() - entry.getValue();

			if (newAmount >= 0) {
				ProdToOrderPK newProdToOrderPk = new ProdToOrderPK();
				newProdToOrderPk.setOrderId(newOrder.getOrderId());
				newProdToOrderPk.setProductId(product.getProductId());
				ProdToOrder newProdToOrder = new ProdToOrder();
				newProdToOrder.setId(newProdToOrderPk);
				newProdToOrder.setAmount(entry.getValue());
				newProdToOrder.setOrder(newOrder);
				newProdToOrder.setProduct(product);
				System.out.println("ProdToOrder: " + newProdToOrder);
				System.out.println("Order: " + newOrder);
				newOrder.addProdToOrder(newProdToOrder);
				prodToOrderFacade.create(newProdToOrder);

				product.setAmount(newAmount);
				productFacade.edit(product);
				searchController.modifyProduct(product);

			} else {
				addErrorMessage(product.getTitle() + "is out of stock");
				
				ExternalContext ec = facesContext.getExternalContext();
				ec.getFlash().setKeepMessages(true);
				RequestContext.getCurrentInstance().update("messages");
				
				return;
			}
		}

		loginController.setUser(userFacade.findByUsername(loginController.getUser().getUsername()));
		addInfoMessage("You have successfully ordered the selected products.");

		ExternalContext ec = facesContext.getExternalContext();
		ec.getFlash().setKeepMessages(true);
		RequestContext.getCurrentInstance().update("messages");

		log.info("Products' amounts and products page successfully updated.");

		try {
			orderEmailController.sendEmail(newOrder);
		} catch (ActivationEmailException e) {
			log.error(e.getMessage());
		}

		log.info("Email about shopping has been sent.");

		cartItemController.getProducts().clear();
		log.info("Cart has been emptied.");
		log.info("Finalizing shopping-session... Done.");

		redirect("/cart.jsf?faces-redirect=true");
	}

	private void redirect(String url) {
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
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

	public OrderEmailController getOrderEmailController() {
		return orderEmailController;
	}

	public void setOrderEmailController(OrderEmailController orderEmailController) {
		this.orderEmailController = orderEmailController;
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

	public SearchController getSearchController() {
		return searchController;
	}

	public void setSearchController(SearchController searchController) {
		this.searchController = searchController;
	}

	public Integer getPhoneNumberId() {
		return phoneNumberId;
	}

	public void setPhoneNumberId(Integer phoneNumberId) {
		this.phoneNumberId = phoneNumberId;
	}
}
