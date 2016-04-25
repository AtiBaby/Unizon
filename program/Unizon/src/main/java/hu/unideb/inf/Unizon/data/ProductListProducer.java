package hu.unideb.inf.Unizon.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Product;

@RequestScoped
public class ProductListProducer {

	@EJB
	private ProductFacade productFacade;

	private List<Product> products;

	@Produces
	@Named
	public List<Product> getProducts() {
		return products;
	}

	public void onProductListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Product product) {
		retrieveAllProducts();
	}

	@PostConstruct
	public void retrieveAllProducts() {
		products = productFacade.findAll();
	}

}
