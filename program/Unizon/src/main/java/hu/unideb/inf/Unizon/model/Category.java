package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CATEGORY database table.
 * 
 */
@Entity
@Table(name="CATEGORY")
@NamedQueries({
	@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c"),
	@NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :catName")
})
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CATEGORY_ID", unique=true, nullable=false)
	private int categoryId;

	@Column(name="NAME", nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to CatToProd
	@OneToMany(mappedBy="category1", fetch=FetchType.EAGER)
	private List<CatToProd> catToProds1;

	//bi-directional many-to-one association to CatToProd
	@OneToMany(mappedBy="category2", fetch=FetchType.EAGER)
	private List<CatToProd> catToProds2;

	//bi-directional many-to-many association to Product
	@ManyToMany(mappedBy="categories", fetch=FetchType.EAGER)
	private List<Product> products;

	public Category() {
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CatToProd> getCatToProds1() {
		return this.catToProds1;
	}

	public void setCatToProds1(List<CatToProd> catToProds1) {
		this.catToProds1 = catToProds1;
	}

	public CatToProd addCatToProds1(CatToProd catToProds1) {
		getCatToProds1().add(catToProds1);
		catToProds1.setCategory1(this);

		return catToProds1;
	}

	public CatToProd removeCatToProds1(CatToProd catToProds1) {
		getCatToProds1().remove(catToProds1);
		catToProds1.setCategory1(null);

		return catToProds1;
	}

	public List<CatToProd> getCatToProds2() {
		return this.catToProds2;
	}

	public void setCatToProds2(List<CatToProd> catToProds2) {
		this.catToProds2 = catToProds2;
	}

	public CatToProd addCatToProds2(CatToProd catToProds2) {
		getCatToProds2().add(catToProds2);
		catToProds2.setCategory2(this);

		return catToProds2;
	}

	public CatToProd removeCatToProds2(CatToProd catToProds2) {
		getCatToProds2().remove(catToProds2);
		catToProds2.setCategory2(null);

		return catToProds2;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}