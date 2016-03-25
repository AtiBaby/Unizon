package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROD_TO_TAG database table.
 * 
 */
@Embeddable
public class ProdToTagPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PRODUCT_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int productId;

	@Column(name="TAG_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int tagId;

	public ProdToTagPK() {
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getTagId() {
		return this.tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProdToTagPK)) {
			return false;
		}
		ProdToTagPK castOther = (ProdToTagPK)other;
		return 
			(this.productId == castOther.productId)
			&& (this.tagId == castOther.tagId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productId;
		hash = hash * prime + this.tagId;
		
		return hash;
	}
}