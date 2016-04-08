package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the PROD_TO_ORDER database table.
 * 
 */
@Embeddable
public class ProdToOrderPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ORDER_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int orderId;

	@Column(name="PRODUCT_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int productId;

	public ProdToOrderPK() {
	}
	public int getOrderId() {
		return this.orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return this.productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProdToOrderPK)) {
			return false;
		}
		ProdToOrderPK castOther = (ProdToOrderPK)other;
		return 
			(this.orderId == castOther.orderId)
			&& (this.productId == castOther.productId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orderId;
		hash = hash * prime + this.productId;
		
		return hash;
	}
}