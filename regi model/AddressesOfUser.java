package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADDRESSES_OF_USER database table.
 * 
 */
@Entity
@Table(name="ADDRESSES_OF_USER")
@NamedQueries({
	@NamedQuery(name="AddressesOfUser.findAll", query="SELECT a FROM AddressesOfUser a"),
	@NamedQuery(name="AddressesOfUser.countUsersOfAddress",
		query="SELECT count(distinct a.user) FROM AddressesOfUser a WHERE a.address.addressId = :addressId")
})
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

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID", nullable=false, insertable=false, updatable=false)
	private User user;

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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AddressesOfUser)) {
			return false;
		}
		AddressesOfUser other = (AddressesOfUser) obj;
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

}