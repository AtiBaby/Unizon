package hu.unideb.inf.Unizon.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.slf4j.Logger;

import hu.unideb.inf.Unizon.facade.ProductFacade;
import hu.unideb.inf.Unizon.facade.UserFacade;
import hu.unideb.inf.Unizon.model.CatToProd;
import hu.unideb.inf.Unizon.model.ProdToOrder;
import hu.unideb.inf.Unizon.model.ProdToTag;
import hu.unideb.inf.Unizon.model.Product;
import hu.unideb.inf.Unizon.model.User;
import java.util.List;

@ManagedBean
@ViewScoped
public class ProductController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@ManagedProperty("#{loginController}")
	private LoginController loginController;

	@EJB
	private ProductFacade productFacade;

	@EJB
	private UserFacade userFacade;

	private Product newProduct;
	private Product originalProduct;
	private User user;
	private ProdToOrder proToOrder;
	private ProdToTag prodToTag;
	private CatToProd catToProd;
    private List<Product> products;

	@PostConstruct
	public void init() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (params.get("productId") != null) {
			int productId = Integer.parseInt(params.get("productId"));
			originalProduct = productFacade.find(productId);
		} else {
			originalProduct = null;
		}

		newProduct = new Product();

		if (originalProduct != null) {
                        newProduct.setTitle(originalProduct.getTitle());
                        newProduct.setAmount(originalProduct.getAmount());
                        newProduct.setPrice(originalProduct.getPrice());
                        newProduct.setDescription(originalProduct.getDescription());
		}

        
        user = loginController.getUser();     
        products = productFacade.findAll();
	}
	
        
    public void showAllProducts(){
        redirect("/edit_products.jsf?faces-redirect=true");
    }
    
    public void editProductListen() {
		init();
	}

	public void editProduct() {
		if (!newProduct.equals(originalProduct)) {
			newProduct = createOrFindProduct(newProduct);

			removeProductFromProdToOrder(originalProduct);
			addProductToProdToOrder(newProduct);
			removeProductFromProdToTag(originalProduct);
			addProductToProdProdToTag(newProduct);
			removeProductFromCatToProd(originalProduct);
			addProductToCatToProd(newProduct);
			
			
		}

		redirect("/edit_products.jsf?faces-redirect=true");
	}

	public Product createOrFindProduct(Product newProduct){
		return newProduct;
	}
	
	public void removeProductFromProdToOrder(Product originalProduct){
		
	}
	
	public void addProductToProdToOrder(Product newProduct){
		
	}
	
	public void removeProductFromProdToTag(Product originalProduct){
		
	}
	
	public void addProductToProdProdToTag(Product newProduct){
		
	}
	
	public void removeProductFromCatToProd(Product originalProduct){
		
	}
	
	public void addProductToCatToProd(Product newProduct){
		
	}
	
	public void removeProduct() {

		redirect("/edit_products.jsf?faces-redirect=true");
	}


	private void redirect(String url) {
		log.info("Redirecting {} to {}.", user, url);
		try {
			ExternalContext ec = facesContext.getExternalContext();
			ec.redirect(ec.getRequestContextPath() + url);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public FacesContext getFacesContext() {
		return facesContext;
	}

	public void setFacesContext(FacesContext facesContext) {
		this.facesContext = facesContext;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public Product getNewProduct() {
		return newProduct;
	}

	public void setNewProduct(Product product) {
		this.newProduct = product;
	}
        
        public Product getOriginalProduct() {
		return originalProduct;
	}

	public void setOriginalProduct(Product product) {
		this.originalProduct = product;
	}
        
        public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public CatToProd getCatToProd() {
		return catToProd;
	}
    
    public void setCatToProd(CatToProd catToProd) {
		this.catToProd = catToProd;
	}
    
    public ProdToTag getProdToTag() {
		return prodToTag;
	}
    
    public void setProdToTag(ProdToTag prodToTag) {
		this.prodToTag = prodToTag;
	}
    
    public ProdToOrder getProToOrder() {
		return proToOrder;
	}
    
    public void setProToOrder(ProdToOrder proToOrder) {
		this.proToOrder = proToOrder;
	}
    
}

