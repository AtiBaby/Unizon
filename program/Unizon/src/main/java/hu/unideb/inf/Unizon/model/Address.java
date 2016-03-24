package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ADDRESS database table.
 * 
 */
@Entity
@Table(name="ADDRESS")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ADDRESS_ID", unique=true, nullable=false)
	private int addressId;

	@Column(name="CITY", length=100)
	private String city;

	@Column(name="COUNTRY", length=100)
	private String country;

	@Column(name="DOOR")
	private int door;

	@Column(name="FLOOR")
	private int floor;

	@Column(name="STR_NUMBER")
	private int strNumber;

	@Column(name="STREET", length=100)
	private String street;

	@Column(name="ZIP")
	private int zip;

	//bi-directional many-to-one association to AddressesOfUser
	@OneToMany(mappedBy="address", fetch=FetchType.EAGER)
	private List<AddressesOfUser> addressesOfUsers;

	//bi-directional many-to-one association to UniOrder
	@OneToMany(mappedBy="address1", fetch=FetchType.EAGER)
	private List<UniOrder> uniOrders1;

	//bi-directional many-to-one association to UniOrder
	@OneToMany(mappedBy="address2", fetch=FetchType.EAGER)
	private List<UniOrder> uniOrders2;

	//bi-directional many-to-one association to UniUser
	@OneToMany(mappedBy="address", fetch=FetchType.EAGER)
	private List<UniUser> uniUsers;

	public Address() {
	}

	public int getAddressId() {
		return this.addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getDoor() {
		return this.door;
	}

	public void setDoor(int door) {
		this.door = door;
	}

	public int getFloor() {
		return this.floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getStrNumber() {
		return this.strNumber;
	}

	public void setStrNumber(int strNumber) {
		this.strNumber = strNumber;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getZip() {
		return this.zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public List<AddressesOfUser> getAddressesOfUsers() {
		return this.addressesOfUsers;
	}

	public void setAddressesOfUsers(List<AddressesOfUser> addressesOfUsers) {
		this.addressesOfUsers = addressesOfUsers;
	}

	public AddressesOfUser addAddressesOfUser(AddressesOfUser addressesOfUser) {
		getAddressesOfUsers().add(addressesOfUser);
		addressesOfUser.setAddress(this);

		return addressesOfUser;
	}

	public AddressesOfUser removeAddressesOfUser(AddressesOfUser addressesOfUser) {
		getAddressesOfUsers().remove(addressesOfUser);
		addressesOfUser.setAddress(null);

		return addressesOfUser;
	}

	public List<UniOrder> getUniOrders1() {
		return this.uniOrders1;
	}

	public void setUniOrders1(List<UniOrder> uniOrders1) {
		this.uniOrders1 = uniOrders1;
	}

	public UniOrder addUniOrders1(UniOrder uniOrders1) {
		getUniOrders1().add(uniOrders1);
		uniOrders1.setAddress1(this);

		return uniOrders1;
	}

	public UniOrder removeUniOrders1(UniOrder uniOrders1) {
		getUniOrders1().remove(uniOrders1);
		uniOrders1.setAddress1(null);

		return uniOrders1;
	}

	public List<UniOrder> getUniOrders2() {
		return this.uniOrders2;
	}

	public void setUniOrders2(List<UniOrder> uniOrders2) {
		this.uniOrders2 = uniOrders2;
	}

	public UniOrder addUniOrders2(UniOrder uniOrders2) {
		getUniOrders2().add(uniOrders2);
		uniOrders2.setAddress2(this);

		return uniOrders2;
	}

	public UniOrder removeUniOrders2(UniOrder uniOrders2) {
		getUniOrders2().remove(uniOrders2);
		uniOrders2.setAddress2(null);

		return uniOrders2;
	}

	public List<UniUser> getUniUsers() {
		return this.uniUsers;
	}

	public void setUniUsers(List<UniUser> uniUsers) {
		this.uniUsers = uniUsers;
	}

	public UniUser addUniUser(UniUser uniUser) {
		getUniUsers().add(uniUser);
		uniUser.setAddress(this);

		return uniUser;
	}

	public UniUser removeUniUser(UniUser uniUser) {
		getUniUsers().remove(uniUser);
		uniUser.setAddress(null);

		return uniUser;
	}

}