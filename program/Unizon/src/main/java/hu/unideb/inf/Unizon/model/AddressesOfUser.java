package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADDRESSES_OF_USER database table.
 * 
 */
@Entity
@Table(name="ADDRESSES_OF_USER")
@NamedQuery(name="AddressesOfUser.findAll", query="SELECT a FROM AddressesOfUser a")
public class AddressesOfUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AddressesOfUserPK id;

	@Column(name="DESCRIPTION", length=100)
	private String description;

	//bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name="ADDRESS_ID", nullable=false, insertable=false, updatable=false)
	private Address address;

	//bi-directional many-to-one association to UniUser
	@ManyToOne
	@JoinColumn(name="USER_ID", nullable=false, insertable=false, updatable=false)
	private UniUser uniUser;

	public AddressesOfUser() {
	}

	public AddressesOfUserPK getId() {
		return this.id;
	}

	public void setId(AddressesOfUserPK id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public UniUser getUniUser() {
		return this.uniUser;
	}

	public void setUniUser(UniUser uniUser) {
		this.uniUser = uniUser;
	}

}