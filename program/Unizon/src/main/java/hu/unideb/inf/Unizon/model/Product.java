package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the PRODUCT database table.
 *
 */
@Entity
@Table(name = "PRODUCT")
@NamedQueries({
		@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
		@NamedQuery(name = "Product.findAllTags", query = "SELECT DISTINCT t FROM Product p INNER JOIN p.tags AS t"),
		@NamedQuery(name = "Product.findAllNotDeleted", query = "SELECT p FROM Product p WHERE p.deleted=false"),
		@NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.productId = :productId"),
		@NamedQuery(name = "Product.findAllByCat", query = "SELECT p FROM Product p INNER JOIN p.categories cats WHERE p.deleted=false AND cats.categoryId = :catId"),
		@NamedQuery(name = "Product.findAllContain", query = "SELECT p FROM Product p WHERE p.deleted=false AND p.title LIKE :term"),
		@NamedQuery(name = "Product.findAllByCatIdContain", query = "SELECT p FROM Product p INNER JOIN p.categories cats WHERE  p.deleted=false AND cats.categoryId = :catId AND p.title LIKE :term")

})
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRODUCT_ID", unique = true, nullable = false)
	private int productId;

	@Column(name = "AMOUNT")
	private int amount;

	@Column(name = "DESCRIPTION", length = 1000)
	private String description;

	@Column(name = "PRICE")
	private int price;

	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;

	@Column(name = "DELETED", nullable = false)
	private boolean deleted;

	// bi-directional many-to-many association to Category
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "CAT_TO_PROD", joinColumns = {
			@JoinColumn(name = "PRODUCT_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "CATEGORY_ID", nullable = false) })
	private Set<Category> categories;

	// bi-directional many-to-one association to Image
	@ManyToOne
	@JoinColumn(name = "DEFAULT_IMAGE_ID", nullable = false)
	private Image image;

	// bi-directional many-to-many association to Image
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PRODUCT_TO_IMAGE", joinColumns = {
			@JoinColumn(name = "PRODUCT_ID", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "IMAGE_ID", nullable = false) })
	private Set<Image> images;

	// bi-directional many-to-one association to ProdToOrder
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Set<ProdToOrder> prodToOrders;

	// bi-directional many-to-many association to Tag
	@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
	private Set<Tag> tags;

	public Product() {
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public Set<ProdToOrder> getProdToOrders() {
		return this.prodToOrders;
	}

	public void setProdToOrders(Set<ProdToOrder> prodToOrders) {
		this.prodToOrders = prodToOrders;
	}

	public ProdToOrder addProdToOrder(ProdToOrder prodToOrder) {
		getProdToOrders().add(prodToOrder);
		prodToOrder.setProduct(this);

		return prodToOrder;
	}

	public ProdToOrder removeProdToOrder(ProdToOrder prodToOrder) {
		getProdToOrders().remove(prodToOrder);
		prodToOrder.setProduct(null);

		return prodToOrder;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 73 * hash + this.productId;
		hash = 73 * hash + this.amount;
		hash = 73 * hash + Objects.hashCode(this.description);
		hash = 73 * hash + this.price;
		hash = 73 * hash + Objects.hashCode(this.title);
		hash = 73 * hash + Objects.hashCode(this.deleted);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Product other = (Product) obj;
		if (this.productId != other.productId) {
			return false;
		}
		if (this.amount != other.amount) {
			return false;
		}
		if (this.price != other.price) {
			return false;
		}
		if (!Objects.equals(this.deleted, other.deleted)) {
			return false;
		}
		if (!Objects.equals(this.description, other.description)) {
			return false;
		}
		if (!Objects.equals(this.title, other.title)) {
			return false;
		}
		return true;
	}

}
