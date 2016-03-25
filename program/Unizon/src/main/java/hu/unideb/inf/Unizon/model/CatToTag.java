package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CAT_TO_TAG database table.
 * 
 */
@Entity
@Table(name="CAT_TO_TAG")
@NamedQuery(name="CatToTag.findAll", query="SELECT c FROM CatToTag c")
public class CatToTag implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CatToTagPK id;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID", nullable=false, insertable=false, updatable=false)
	private Category category1;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID", nullable=false, insertable=false, updatable=false)
	private Category category2;

	//bi-directional many-to-one association to Tag
	@ManyToOne
	@JoinColumn(name="TAG_ID", nullable=false, insertable=false, updatable=false)
	private Tag tag1;

	//bi-directional many-to-one association to Tag
	@ManyToOne
	@JoinColumn(name="TAG_ID", nullable=false, insertable=false, updatable=false)
	private Tag tag2;

	public CatToTag() {
	}

	public CatToTagPK getId() {
		return this.id;
	}

	public void setId(CatToTagPK id) {
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

}