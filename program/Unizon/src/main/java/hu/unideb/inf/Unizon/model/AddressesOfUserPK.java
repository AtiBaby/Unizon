package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ADDRESSES_OF_USER database table.
 * 
 */
@Embeddable
public class AddressesOfUserPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int userId;

	@Column(name="ADDRESS_ID", insertable=false, updatable=false, unique=true, nullable=false)
	private int addressId;

	public AddressesOfUserPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAddressId() {
		return this.addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AddressesOfUserPK)) {
			return false;
		}
		AddressesOfUserPK castOther = (AddressesOfUserPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.addressId == castOther.addressId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.addressId;
		
		return hash;
	}
}