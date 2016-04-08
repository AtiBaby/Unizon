package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the UNI_USER_DATA database table.
 * 
 */
@Entity
@Table(name = "UNI_USER_DATA")
@NamedQuery(name = "UserData.findAll", query = "SELECT u FROM UserData u")
public class UserData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	private int userId;

	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "DEFAULT_ADDRESS_ID", nullable = false)
	private Address defaultAddress;

	// bi-directional many-to-one association to PhoneNumber
	@ManyToOne
	@JoinColumn(name = "PHONE_NUMBER_ID", nullable = false)
	private PhoneNumber phoneNumber;

	// bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)
	private User user;

	public UserData() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Address getDefaultAddress() {
		return this.defaultAddress;
	}

	public void setDefaultAddress(Address address) {
		this.defaultAddress = address;
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

}