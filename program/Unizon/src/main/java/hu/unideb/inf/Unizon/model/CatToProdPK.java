package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CAT_TO_PROD database table.
 * 
 */
@Embeddable
public class CatToProdPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PRODUCT_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int productId;

	@Column(name="CATEGORY_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int categoryId;

	public CatToProdPK() {
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CatToProdPK)) {
			return false;
		}
		CatToProdPK castOther = (CatToProdPK)other;
		return 
			(this.productId == castOther.productId)
			&& (this.categoryId == castOther.categoryId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productId;
		hash = hash * prime + this.categoryId;
		
		return hash;
	}
}