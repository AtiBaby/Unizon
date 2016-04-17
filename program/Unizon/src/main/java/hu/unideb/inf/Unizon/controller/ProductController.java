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
        
        private List<Product> products;

	@PostConstruct
	public void init() {
		/*Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		int productId = Integer.parseInt(params.get("productId"));

		originalProduct = productFacade.find(productId);*/

		newProduct = new Product();

		if (originalProduct != null) {
                        newProduct.setTitle(originalProduct.getTitle());
                        newProduct.setAmount(originalProduct.getAmount());
                        newProduct.setPrice(originalProduct.getPrice());
                        newProduct.setDescription(originalProduct.getDescription());
		}

                originalProduct = new Product();
                user = loginController.getUser();
                
                products = productFacade.findAll();
	}
        
    public List<Product> listAll(){
        return productFacade.findAll();
    }
        
    public void showAllProducts(){
        redirect("/administrator/edit_products.jsf?faces-redirect=true");
    }

	public void editProduct() {
		if (!newProduct.equals(originalProduct)) {
			//newProduct = productFacade.find(newProduct);

			originalProduct.setDescription(newProduct.getDescription());
                        originalProduct.setTitle(newProduct.getTitle());
                        originalProduct.setAmount(newProduct.getAmount());
                        originalProduct.setPrice(newProduct.getPrice());
                        
                        newProduct = productFacade.edit(newProduct);
		}

		redirect("/administrator/edit_products.jsf?faces-redirect=true");
	}

	public void removeProduct() {

		redirect("/administrator/edit_products.jsf?faces-redirect=true");
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
}

