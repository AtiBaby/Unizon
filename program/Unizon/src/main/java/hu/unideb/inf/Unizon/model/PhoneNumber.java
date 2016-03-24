package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PHONE_NUMBER database table.
 * 
 */
@Entity
@Table(name="PHONE_NUMBER")
@NamedQuery(name="PhoneNumber.findAll", query="SELECT p FROM PhoneNumber p")
public class PhoneNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="PHONE_NUMBER_ID", unique=true, nullable=false)
	private int phoneNumberId;

	@Column(name="PHONE_NUMBER", length=100)
	private String phoneNumber;

	//bi-directional many-to-one association to UniOrder
	@OneToMany(mappedBy="phoneNumber", fetch=FetchType.EAGER)
	private List<UniOrder> uniOrders;

	//bi-directional many-to-one association to UniUser
	@OneToMany(mappedBy="phoneNumber", fetch=FetchType.EAGER)
	private List<UniUser> uniUsers;

	public PhoneNumber() {
	}

	public int getPhoneNumberId() {
		return this.phoneNumberId;
	}

	public void setPhoneNumberId(int phoneNumberId) {
		this.phoneNumberId = phoneNumberId;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<UniOrder> getUniOrders() {
		return this.uniOrders;
	}

	public void setUniOrders(List<UniOrder> uniOrders) {
		this.uniOrders = uniOrders;
	}

	public UniOrder addUniOrder(UniOrder uniOrder) {
		getUniOrders().add(uniOrder);
		uniOrder.setPhoneNumber(this);

		return uniOrder;
	}

	public UniOrder removeUniOrder(UniOrder uniOrder) {
		getUniOrders().remove(uniOrder);
		uniOrder.setPhoneNumber(null);

		return uniOrder;
	}

	public List<UniUser> getUniUsers() {
		return this.uniUsers;
	}

	public void setUniUsers(List<UniUser> uniUsers) {
		this.uniUsers = uniUsers;
	}

	public UniUser addUniUser(UniUser uniUser) {
		getUniUsers().add(uniUser);
		uniUser.setPhoneNumber(this);

		return uniUser;
	}

	public UniUser removeUniUser(UniUser uniUser) {
		getUniUsers().remove(uniUser);
		uniUser.setPhoneNumber(null);

		return uniUser;
	}

}