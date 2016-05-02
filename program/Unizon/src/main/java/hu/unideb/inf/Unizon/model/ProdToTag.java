package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;


/**
 * The persistent class for the PROD_TO_TAG database table.
 * 
 */
@Entity
@Table(name="PROD_TO_TAG")
@NamedQuery(name="ProdToTag.findAll", query="SELECT p FROM ProdToTag p")
public class ProdToTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProdToTagPK id;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false, insertable=false, updatable=false)
	private Product product1;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false, insertable=false, updatable=false)
	private Product product2;

	//bi-directional many-to-one association to Tag
	@ManyToOne
	@JoinColumn(name="TAG_ID", nullable=false, insertable=false, updatable=false)
	private Tag tag1;

	//bi-directional many-to-one association to Tag
	@ManyToOne
	@JoinColumn(name="TAG_ID", nullable=false, insertable=false, updatable=false)
	private Tag tag2;

	public ProdToTag() {
	}

	public ProdToTagPK getId() {
		return this.id;
	}

	public void setId(ProdToTagPK id) {
		this.id = id;
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

	public Tag getTag1() {
		return this.tag1;
	}

	public void setTag1(Tag tag1) {
		this.tag1 = tag1;
	}

	public Tag getTag2() {
		return this.tag2;
	}

	public void setTag2(Tag tag2) {
		this.tag2 = tag2;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final ProdToTag other = (ProdToTag) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}