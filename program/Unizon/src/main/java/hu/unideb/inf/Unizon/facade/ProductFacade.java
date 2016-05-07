package hu.unideb.inf.Unizon.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import hu.unideb.inf.Unizon.model.Category;
import hu.unideb.inf.Unizon.model.Product;
import hu.unideb.inf.Unizon.model.Tag;

@Stateless
public class ProductFacade extends AbstractFacade<Product> {

	@PersistenceContext(unitName = "primary")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public ProductFacade() {
		super(Product.class);
	}

	public List<Product> findAllNotDeleted() {
		return em.createNamedQuery("Product.findAllNotDeleted", Product.class).getResultList();
	}

	public List<Product> findByNameStartingWith(String term, Integer num) {
		return em.createNamedQuery("Product.findAllContain", Product.class).setParameter("term", term + "%")
				.setMaxResults(num).getResultList();

	}

	public List<Product> findByNameStartingWith(String term, Category cat, Integer num) {
		return em.createNamedQuery("Product.findAllByCatIdContain", Product.class).setParameter("term", term + "%")
				.setParameter("catId", cat.getCategoryId()).setMaxResults(num).getResultList();
	}

	public List<Product> findByNameContaining(String productName, Category cat) {
		return em.createNamedQuery("Product.findAllByCatIdContain", Product.class)
				.setParameter("term", "%" + productName + "%").setParameter("catId", cat.getCategoryId())
				.getResultList();
	}

	public List<Product> findByNameContaining(String productName) {
		return em.createNamedQuery("Product.findAllContain", Product.class)
				.setParameter("term", "%" + productName + "%").getResultList();
	}

	public List<Product> findAll(Category cat) {
		return em.createNamedQuery("Product.findAllByCat", Product.class).setParameter("catId", cat.getCategoryId())
				.getResultList();
	}

	public Product findById(int productId) {
		return em.createNamedQuery("Product.findById", Product.class).setParameter("productId", productId)
				.getSingleResult();
	}

	public List<Tag> findAllTags() {
		return em.createNamedQuery("Product.findAllTags", Tag.class).getResultList();
	}
}
