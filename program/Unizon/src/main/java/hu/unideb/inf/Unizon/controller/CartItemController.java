package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.model.Product;

@ManagedBean
@SessionScoped
public class CartItemController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Logger log;

	private HashMap<Product, Integer> products;

	@PostConstruct
	public void init() {
		products = new HashMap<>();
	}

	public HashMap<Product, Integer> getProducts() {
		return products;
	}

	public void setProducts(HashMap<Product, Integer> products) {
		this.products = products;
	}

	public void addProductToCart(Product product, int amount) {
		log.info("Selected product: {}, amount: {}.", product, amount);

		if (!products.containsKey(product)) {
			products.put(product, amount);
			log.info("Product ({}) has been added to the cart.", product);
			addInfoMessage(String.format("Product has been added to the cart with an amount of %d.", amount));
		} else {
			int newAmount = products.get(product) + amount;

			if (product.getAmount() < newAmount) {
				addErrorMessage(String.format(
						"Amount of product in cart has not been changed: amount %d greater than stock amount (%d).",
						newAmount, product.getAmount()));
				log.info("Amount of ({}) has not been changed: amount ({}) greater than allowed ({}).", product,
						newAmount, product.getAmount());
				return;
			}

			products.put(product, newAmount);
			log.info("Amount of ({}) has been changed to ({}).", product, newAmount);
			addInfoMessage(String.format("Amount of product in cart has been increased to %d.", newAmount));
		}
	}

	public void editProductInCart(Product product, int amount) {

		if (products.containsKey(product)) {
			products.put(product, amount);
			redirect("/cart.jsf?faces-redirect=true");
		}
	}
	public void deleteProductFromCart(Product product) {

		if (products.containsKey(product)) {
			products.remove(product);
			
		}
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
}
