package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
 * The persistent class for the UNI_USER database table.
 * 
 */
@Entity
@Table(name="UNI_USER")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID", unique=true, nullable=false)
	private int userId;

	@Column(name="E_MAIL", length=100)
	private String eMail;

	@Column(name="ENCRYPTED_PASSWORD", length=100)
	private String encryptedPassword;

	@Column(name="NAME", length=100)
	private String name;

	@Temporal(TemporalType.DATE)
	@Column(name="REGISTRATION_DATE")
	private Date registrationDate;

	@Column(name="USERNAME", length=100)
	private String username;

	//bi-directional many-to-one association to AddressesOfUser
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<AddressesOfUser> addressesOfUsers;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Order> orders;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="DEFAULT_ADDRESS_ID", nullable=false)
	private Address address;

	//bi-directional many-to-one association to PhoneNumber
	@ManyToOne
	@JoinColumn(name="PHONE_NUMBER_ID", nullable=false)
	private PhoneNumber phoneNumber;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEMail() {
		return this.eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getEncryptedPassword() {
		return this.encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<AddressesOfUser> getAddressesOfUsers() {
		return this.addressesOfUsers;
	}

	public void setAddressesOfUsers(List<AddressesOfUser> addressesOfUsers) {
		this.addressesOfUsers = addressesOfUsers;
	}

	public AddressesOfUser addAddressesOfUser(AddressesOfUser addressesOfUser) {
		getAddressesOfUsers().add(addressesOfUser);
		addressesOfUser.setUser(this);

		return addressesOfUser;
	}

	public AddressesOfUser removeAddressesOfUser(AddressesOfUser addressesOfUser) {
		getAddressesOfUsers().remove(addressesOfUser);
		addressesOfUser.setUser(null);

		return addressesOfUser;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);

		return order;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PhoneNumber getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}