package hu.unideb.inf.Unizon.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.unideb.inf.Unizon.facade.CategoryFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Category;
import hu.unideb.inf.Unizon.model.Product;

@Stateless
@ManagedBean(name = "SearchController")
@SessionScoped
public class SearchController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	@EJB
	private CategoryFacade categoryFacade;

	@EJB
	private ProductFacade productFacade;

	private String category;
	private List<String> categoryNames;
	private String productName;

	private List<Product> products;

	@PostConstruct
	public void init() {
		categoryNames = new ArrayList<>();
		List<Category> categories = categoryFacade.findAll();
		for (Category category : categories) {
			categoryNames.add(category.getName());
		}
	}

	public List<String> autoCompleteProductName(String term) {
		logger.debug("Search category and term: " + category + " - " + term);
		List<String> productNames = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		if (category == null || category.equals("all")) {
			products = productFacade.findByNameStartingWith(term, 10);
		} else {
			Category cat = categoryFacade.findByName(category);
			products = productFacade.findByNameStartingWith(term, cat, 10);
		}
		for (Product prod : products) {
			productNames.add(prod.getTitle());
		}
		return productNames;
	}

	public void handleChange(ValueChangeEvent event) {
		category = (String) event.getNewValue();
	}

	public void search() {
		if (productName != null && !productName.isEmpty()) {
			if (category == null || category.equals("all")) {
				setProducts(productFacade.findByNameContaining(productName));
			} else {
				Category cat = categoryFacade.findByName(category);
				setProducts(productFacade.findByNameContaining(productName, cat));
			}
		} else {
			if (category == null || category.equals("all")) {
				setProducts(productFacade.findAll());
			} else {
				Category cat = categoryFacade.findByName(category);
				setProducts(productFacade.findAll(cat));
			}
		}
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(List<String> categories) {
		this.categoryNames = categories;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}