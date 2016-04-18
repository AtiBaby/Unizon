package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;


/**
 * The persistent class for the PROD_TO_ORDER database table.
 * 
 */
@Entity
@Table(name="PROD_TO_ORDER")
@NamedQuery(name="ProdToOrder.findAll", query="SELECT p FROM ProdToOrder p")
public class ProdToOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProdToOrderPK id;

	@Column(name="AMOUNT", nullable=false)
	private int amount;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID", nullable=false, insertable=false, updatable=false)
	private Product product;

	//bi-directional many-to-one association to Order
	@ManyToOne
	@JoinColumn(name="ORDER_ID", nullable=false, insertable=false, updatable=false)
	private Order order;

	public ProdToOrder() {
	}

	public ProdToOrderPK getId() {
		return this.id;
	}

	public void setId(ProdToOrderPK id) {
		this.id = id;
	}

	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + this.amount;
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
        final ProdToOrder other = (ProdToOrder) obj;
        if (this.amount != other.amount) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}