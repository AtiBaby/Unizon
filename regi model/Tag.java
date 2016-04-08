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

	//bi-directional many-to-one association to CatToTag
	@OneToMany(mappedBy="tag1", fetch=FetchType.EAGER)
	private List<CatToTag> catToTags1;

	//bi-directional many-to-one association to CatToTag
	@OneToMany(mappedBy="tag2", fetch=FetchType.EAGER)
	private List<CatToTag> catToTags2;

	//bi-directional many-to-one association to ProdToTag
	@OneToMany(mappedBy="tag1", fetch=FetchType.EAGER)
	private List<ProdToTag> prodToTags1;

	//bi-directional many-to-one association to ProdToTag
	@OneToMany(mappedBy="tag2", fetch=FetchType.EAGER)
	private List<ProdToTag> prodToTags2;

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

	public List<CatToTag> getCatToTags1() {
		return this.catToTags1;
	}

	public void setCatToTags1(List<CatToTag> catToTags1) {
		this.catToTags1 = catToTags1;
	}

	public CatToTag addCatToTags1(CatToTag catToTags1) {
		getCatToTags1().add(catToTags1);
		catToTags1.setTag1(this);

		return catToTags1;
	}

	public CatToTag removeCatToTags1(CatToTag catToTags1) {
		getCatToTags1().remove(catToTags1);
		catToTags1.setTag1(null);

		return catToTags1;
	}

	public List<CatToTag> getCatToTags2() {
		return this.catToTags2;
	}

	public void setCatToTags2(List<CatToTag> catToTags2) {
		this.catToTags2 = catToTags2;
	}

	public CatToTag addCatToTags2(CatToTag catToTags2) {
		getCatToTags2().add(catToTags2);
		catToTags2.setTag2(this);

		return catToTags2;
	}

	public CatToTag removeCatToTags2(CatToTag catToTags2) {
		getCatToTags2().remove(catToTags2);
		catToTags2.setTag2(null);

		return catToTags2;
	}

	public List<ProdToTag> getProdToTags1() {
		return this.prodToTags1;
	}

	public void setProdToTags1(List<ProdToTag> prodToTags1) {
		this.prodToTags1 = prodToTags1;
	}

	public ProdToTag addProdToTags1(ProdToTag prodToTags1) {
		getProdToTags1().add(prodToTags1);
		prodToTags1.setTag1(this);

		return prodToTags1;
	}

	public ProdToTag removeProdToTags1(ProdToTag prodToTags1) {
		getProdToTags1().remove(prodToTags1);
		prodToTags1.setTag1(null);

		return prodToTags1;
	}

	public List<ProdToTag> getProdToTags2() {
		return this.prodToTags2;
	}

	public void setProdToTags2(List<ProdToTag> prodToTags2) {
		this.prodToTags2 = prodToTags2;
	}

	public ProdToTag addProdToTags2(ProdToTag prodToTags2) {
		getProdToTags2().add(prodToTags2);
		prodToTags2.setTag2(this);

		return prodToTags2;
	}

	public ProdToTag removeProdToTags2(ProdToTag prodToTags2) {
		getProdToTags2().remove(prodToTags2);
		prodToTags2.setTag2(null);

		return prodToTags2;
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