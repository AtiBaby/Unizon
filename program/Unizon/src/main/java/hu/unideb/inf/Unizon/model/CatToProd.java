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
	private Category category;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false, insertable=false, updatable=false)
	private Product product;

	public CatToProd() {
	}

	public CatToProdPK getId() {
		return this.id;
	}

	public void setId(CatToProdPK id) {
		this.id = id;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
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