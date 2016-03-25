package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CAT_TO_TAG database table.
 * 
 */
@Embeddable
public class CatToTagPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CATEGORY_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int categoryId;

	@Column(name="TAG_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int tagId;

	public CatToTagPK() {
	}
	public int getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
		if (!(other instanceof CatToTagPK)) {
			return false;
		}
		CatToTagPK castOther = (CatToTagPK)other;
		return 
			(this.categoryId == castOther.categoryId)
			&& (this.tagId == castOther.tagId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.categoryId;
		hash = hash * prime + this.tagId;
		
		return hash;
	}
}