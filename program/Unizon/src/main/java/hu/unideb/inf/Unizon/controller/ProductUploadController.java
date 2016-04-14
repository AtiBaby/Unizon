package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Product;

@ManagedBean
@ViewScoped
public class ProductUploadController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@EJB
	private ProductFacade productFacade;

	private Product product;
	private String valami;

	@PostConstruct
	public void init() {
		log.debug("itten**********************************************");
		this.product = new Product();
	}

	public void upload() {
		productFacade.create(product);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getValami() {
		return valami;
	}

	public void setValami(String valami) {
		this.valami = valami;
	}
	
	
}
