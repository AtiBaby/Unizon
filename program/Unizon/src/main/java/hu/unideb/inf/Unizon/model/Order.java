package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the UNI_ORDER database table.
 * 
 */
@Entity
@Table(name = "UNI_ORDER")
@NamedQuery(name = "Order.findAll", query = "SELECT o FROM Order o")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDER_ID", unique = true, nullable = false)
	private int orderId;

	@Temporal(TemporalType.DATE)
	@Column(name = "ORDER_DATE", nullable = false)
	private Date orderDate;

	// bi-directional many-to-one association to ProdToOrder
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	private Set<ProdToOrder> prodToOrders;

	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "SHIPPING_ADDRESS_ID", nullable = false)
	private Address address1;

	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "BILLING_ADDRESS_ID", nullable = false)
	private Address address2;

	// bi-directional many-to-one association to PhoneNumber
	@ManyToOne
	@JoinColumn(name = "PHONE_NUMBER_ID", nullable = false)
	private PhoneNumber phoneNumber;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	public Order() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Set<ProdToOrder> getProdToOrders() {
		return this.prodToOrders;
	}

	public void setProdToOrders(Set<ProdToOrder> prodToOrders) {
		this.prodToOrders = prodToOrders;
	}

	public ProdToOrder addProdToOrder(ProdToOrder prodToOrder) {
		getProdToOrders().add(prodToOrder);
		prodToOrder.setOrder(this);

		return prodToOrder;
	}

	public ProdToOrder removeProdToOrder(ProdToOrder prodToOrder) {
		getProdToOrders().remove(prodToOrder);
		prodToOrder.setOrder(null);

		return prodToOrder;
	}

	public Address getAddress1() {
		return this.address1;
	}

	public void setAddress1(Address address1) {
		this.address1 = address1;
	}

	public Address getAddress2() {
		return this.address2;
	}

	public void setAddress2(Address address2) {
		this.address2 = address2;
	}

	public PhoneNumber getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + this.orderId;
		hash = 17 * hash + Objects.hashCode(this.orderDate);
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
		final Order other = (Order) obj;
		if (this.orderId != other.orderId) {
			return false;
		}
		if (!Objects.equals(this.orderDate, other.orderDate)) {
			return false;
		}
		return true;
	}

}