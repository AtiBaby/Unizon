package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the UNI_USER database table.
 * 
 */
@Entity
@Table(name="UNI_USER")
@NamedQuery(name="UniUser.findAll", query="SELECT u FROM UniUser u")
public class UniUser implements Serializable {
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
	@OneToMany(mappedBy="uniUser", fetch=FetchType.EAGER)
	private List<AddressesOfUser> addressesOfUsers;

	//bi-directional many-to-one association to UniOrder
	@OneToMany(mappedBy="uniUser", fetch=FetchType.EAGER)
	private List<UniOrder> uniOrders;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="DEFAULT_ADDRESS_ID", nullable=false)
	private Address address;

	//bi-directional many-to-one association to PhoneNumber
	@ManyToOne
	@JoinColumn(name="PHONE_NUMBER_ID", nullable=false)
	private PhoneNumber phoneNumber;

	public UniUser() {
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
		addressesOfUser.setUniUser(this);

		return addressesOfUser;
	}

	public AddressesOfUser removeAddressesOfUser(AddressesOfUser addressesOfUser) {
		getAddressesOfUsers().remove(addressesOfUser);
		addressesOfUser.setUniUser(null);

		return addressesOfUser;
	}

	public List<UniOrder> getUniOrders() {
		return this.uniOrders;
	}

	public void setUniOrders(List<UniOrder> uniOrders) {
		this.uniOrders = uniOrders;
	}

	public UniOrder addUniOrder(UniOrder uniOrder) {
		getUniOrders().add(uniOrder);
		uniOrder.setUniUser(this);

		return uniOrder;
	}

	public UniOrder removeUniOrder(UniOrder uniOrder) {
		getUniOrders().remove(uniOrder);
		uniOrder.setUniUser(null);

		return uniOrder;
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