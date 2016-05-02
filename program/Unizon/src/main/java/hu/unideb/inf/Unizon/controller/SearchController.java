package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.event.SlideEndEvent;
import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.CategoryFacade;
import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.model.Category;
import hu.unideb.inf.Unizon.model.Product;
import hu.unideb.inf.Unizon.model.Tag;

@Stateless
@ManagedBean(name = "SearchController")
@SessionScoped
public class SearchController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private CategoryFacade categoryFacade;

	@EJB
	private ProductFacade productFacade;

	private String category;
	private List<String> categoryNames;
	private List<Tag> tags;
	private List<String> tags2;

	private String productName;

	private List<Product> products;
	private List<Product> filteredProducts;

	private String selectedSort;
	private List<String> sortingOptions;

	private Integer minPrice;
	private Integer maxPrice;

	@PostConstruct
	public void init() {
		sortingOptions = new ArrayList<>();
		sortingOptions.add("Title - Low to High");
		sortingOptions.add("Title - High to Low");
		sortingOptions.add("Price - Low to High");
		sortingOptions.add("Price - High to Low");
		categoryNames = new ArrayList<>();
		List<Category> categories = categoryFacade.findAll();
		for (Category category : categories) {
			categoryNames.add(category.getName());
		}

		products = productFacade.findAllNotDeleted();
		tags = productFacade.findAllTags();
		tags2 = new ArrayList<>();
		for (Tag tag : tags) {
			tags2.add(tag.getName());
		}
		Collections.sort(products, PriceASCComparator);

		if (products.get(0) != null) {
			minPrice = products.get(0).getPrice();
			maxPrice = products.get(products.size() - 1).getPrice();
		}
		filteredProducts = products;
	}

	public String submitProduct(Integer id) {
		return "/?faces-redirect=true&id=" + id;
	}

	public void catSelectSearch() {
		productName = "";
		search();
	}

	public List<String> autoCompleteProductName(String term) {
		log.debug("Search category and term: " + category + " - " + term);
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

	public void onSlideEndPrice(SlideEndEvent event) {
		tagFilterListener();
	}

	public void tagFilterListener() {
		filteredProducts = new ArrayList<>();
		for (Product prod : products) {
			int o = 0;
			if (prod.getPrice() >= minPrice && prod.getPrice() <= maxPrice) {
				if (prod.getTags().isEmpty()) {
					filteredProducts.add(prod);
					o = 1;
				}
			}
			for (String tag : tags2) {
				for (Tag pTag : prod.getTags()) {
					if (prod.getPrice() >= minPrice && prod.getPrice() <= maxPrice) {
						if (pTag.getName().equals(tag)) {
							filteredProducts.add(prod);
							o = 1;
							break;
						}
					}
				}
				if (o == 1) {
					break;
				}
			}
		}
	}

	public void sortChangeListener(ValueChangeEvent e) {
		String sorting = e.getNewValue().toString();
		if (sorting.equals("Title - Low to High")) {
			Collections.sort(products, ProductNameASCComparator);
		} else if (sorting.equals("Title - High to Low")) {
			Collections.sort(products, ProductNameDESCComparator);
		} else if (sorting.equals("Price - Low to High")) {
			Collections.sort(products, PriceASCComparator);
		} else if (sorting.equals("Price - High to Low")) {
			Collections.sort(products, PriceDESCComparator);
		}
		filteredProducts = products;
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
				setProducts(productFacade.findAllNotDeleted());
			} else {
				Category cat = categoryFacade.findByName(category);
				setProducts(productFacade.findAll(cat));
			}
		}
		if (products.get(0) != null) {
			minPrice = products.get(0).getPrice();
			maxPrice = products.get(0).getPrice();
		} else {
			minPrice = 0;
			maxPrice = 0;
		}
		for (int i = 0; i < products.size(); ++i) {
			if (minPrice > products.get(i).getPrice()) {
				minPrice = products.get(i).getPrice();
			} else if (maxPrice < products.get(i).getPrice()) {
				maxPrice = products.get(i).getPrice();
			}
		}
		tags = new ArrayList<>();
		for (Product prod : products) {
			for (Tag tag : prod.getTags()) {
				int o = 0;
				for (Tag t : tags) {
					if (t.getName().equals(tag.getName())) {
						o = 1;
						break;
					}
				}
				if (o == 0) {
					tags.add(tag);
				}
			}
		}

		tags2 = new ArrayList<>();
		for (Tag tag : tags) {
			tags2.add(tag.getName());
		}
		filteredProducts = products;
	}

	public void catSelectBehindProductDetails() {
		catSelectSearch();
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(ec.getRequestContextPath() + "/products.jsf?faces-redirect=true");
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
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

	public static Comparator<Product> ProductNameASCComparator = new Comparator<Product>() {
		@Override
		public int compare(Product p1, Product p2) {
			String pName1 = p1.getTitle().toUpperCase();
			String pName2 = p2.getTitle().toUpperCase();
			return pName1.compareTo(pName2);
		}
	};
	public static Comparator<Product> ProductNameDESCComparator = new Comparator<Product>() {
		@Override
		public int compare(Product p1, Product p2) {
			String pName1 = p1.getTitle().toUpperCase();
			String pName2 = p2.getTitle().toUpperCase();
			return pName2.compareTo(pName1);
		}
	};
	public static Comparator<Product> PriceDESCComparator = new Comparator<Product>() {
		@Override
		public int compare(Product p1, Product p2) {
			Integer pPrice1 = p1.getPrice();
			Integer pPrice2 = p2.getPrice();
			return pPrice2.compareTo(pPrice1);
		}
	};
	public static Comparator<Product> PriceASCComparator = new Comparator<Product>() {
		@Override
		public int compare(Product p1, Product p2) {
			Integer pPrice1 = p1.getPrice();
			Integer pPrice2 = p2.getPrice();
			return pPrice1.compareTo(pPrice2);
		}
	};

	public String getSelectedSort() {
		return selectedSort;
	}

	public void setSelectedSort(String selectedSort) {
		this.selectedSort = selectedSort;
	}

	public List<String> getSortingOptions() {
		return sortingOptions;
	}

	public void setSortingOptions(List<String> sortingOptions) {
		this.sortingOptions = sortingOptions;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Product> getFilteredProducts() {
		return filteredProducts;
	}

	public void setFilteredProducts(List<Product> filteredProducts) {
		this.filteredProducts = filteredProducts;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<String> getTags2() {
		return tags2;
	}

	public void setTags2(List<String> tags2) {
		this.tags2 = tags2;
	}

}