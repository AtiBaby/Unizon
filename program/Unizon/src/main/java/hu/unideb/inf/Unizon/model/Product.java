package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PRODUCT database table.
 * 
 */
@Entity
@Table(name="PRODUCT")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PRODUCT_ID", unique=true, nullable=false)
	private int productId;

	@Column(name="AMOUNT")
	private int amount;

	@Column(name="DESCRIPTION", length=100)
	private String description;

	@Column(name="PRICE")
	private int price;

	@Column(name="TITLE", length=100)
	private String title;

	//bi-directional many-to-many association to Category
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="CAT_TO_PROD"
		, joinColumns={
			@JoinColumn(name="PRODUCT_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="CATEGORY_ID", nullable=false)
			}
		)
	private List<Category> categories;

	//bi-directional many-to-one association to ProdToOrder
	@OneToMany(mappedBy="product", fetch=FetchType.EAGER)
	private List<ProdToOrder> prodToOrders;

	//bi-directional many-to-many association to Tag
	@ManyToMany(mappedBy="products", fetch=FetchType.EAGER)
	private List<Tag> tags;

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

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<ProdToOrder> getProdToOrders() {
		return this.prodToOrders;
	}

	public void setProdToOrders(List<ProdToOrder> prodToOrders) {
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

	public List<Tag> getTags() {
		return this.tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

}