package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;


/**
 * The persistent class for the CAT_TO_PROD database table.
 * 
 */
@Entity
@Table(name="CAT_TO_PROD")
@NamedQuery(name="CatToProd.findAll", query="SELECT c FROM CatToProd c")
public class CatToProd implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CatToProdPK id;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID", nullable=false, insertable=false, updatable=false)
	private Category category1;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID", nullable=false, insertable=false, updatable=false)
	private Category category2;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false, insertable=false, updatable=false)
	private Product product1;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false, insertable=false, updatable=false)
	private Product product2;

	public CatToProd() {
	}

	public CatToProdPK getId() {
		return this.id;
	}

	public void setId(CatToProdPK id) {
		this.id = id;
	}

	public Category getCategory1() {
		return this.category1;
	}

	public void setCategory1(Category category1) {
		this.category1 = category1;
	}

	public Category getCategory2() {
		return this.category2;
	}

	public void setCategory2(Category category2) {
		this.category2 = category2;
	}

	public Product getProduct1() {
		return this.product1;
	}

	public void setProduct1(Product product1) {
		this.product1 = product1;
	}

	public Product getProduct2() {
		return this.product2;
	}

	public void setProduct2(Product product2) {
		this.product2 = product2;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final CatToProd other = (CatToProd) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}