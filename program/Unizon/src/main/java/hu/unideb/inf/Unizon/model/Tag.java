package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TAG database table.
 * 
 */
@Entity
@Table(name="TAG")
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TAG_ID", unique=true, nullable=false)
	private int tagId;

	@Column(name="NAME", length=100)
	private String name;

	//bi-directional many-to-many association to Category
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="CAT_TO_TAG"
		, joinColumns={
			@JoinColumn(name="TAG_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="CATEGORY_ID", nullable=false)
			}
		)
	private List<Category> categories;

	//bi-directional many-to-many association to Product
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="PROD_TO_TAG"
		, joinColumns={
			@JoinColumn(name="TAG_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="PRODUCT_ID", nullable=false)
			}
		)
	private List<Product> products;

	public Tag() {
	}

	public int getTagId() {
		return this.tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}