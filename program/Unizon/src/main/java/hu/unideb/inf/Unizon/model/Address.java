package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the ADDRESS database table.
 *
 */
@Entity
@Table(name = "ADDRESS")
@NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADDRESS_ID", unique = true, nullable = false)
    private int addressId;

    @Column(name = "CITY", length = 100)
    private String city;

    @Column(name = "COUNTRY", length = 100)
    private String country;

    @Column(name = "DOOR")
    private Integer door;

    @Column(name = "FLOOR")
    private Integer floor;

    @Column(name = "STR_NUMBER")
    private int strNumber;

    @Column(name = "STREET", length = 100)
    private String street;

    @Column(name = "ZIP")
    private String zip;

    // bi-directional many-to-one association to AddressesOfUser
    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER)
    private List<AddressesOfUser> addressesOfUsers;

    // bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "address1", fetch = FetchType.EAGER)
    private List<Order> orders1;

    // bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "address2", fetch = FetchType.EAGER)
    private List<Order> orders2;

    // //bi-directional many-to-one association to UserData
    // @OneToMany(mappedBy="address", fetch=FetchType.EAGER)
    // private List<UserData> userData;
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

    public Integer getDoor() {
        return door;
    }

    public void setDoor(Integer door) {
        this.door = door;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
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

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
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

    public List<Order> getOrders1() {
        return this.orders1;
    }

    public void setOrders1(List<Order> orders1) {
        this.orders1 = orders1;
    }

    public Order addOrders1(Order orders1) {
        getOrders1().add(orders1);
        orders1.setAddress1(this);

        return orders1;
    }

    public Order removeOrders1(Order orders1) {
        getOrders1().remove(orders1);
        orders1.setAddress1(null);

        return orders1;
    }

    public List<Order> getOrders2() {
        return this.orders2;
    }

    public void setOrders2(List<Order> orders2) {
        this.orders2 = orders2;
    }

    public Order addOrders2(Order orders2) {
        getOrders2().add(orders2);
        orders2.setAddress2(this);

        return orders2;
    }

    public Order removeOrders2(Order orders2) {
        getOrders2().remove(orders2);
        orders2.setAddress2(null);

        return orders2;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((door == null) ? 0 : door.hashCode());
		result = prime * result + ((floor == null) ? 0 : floor.hashCode());
		result = prime * result + strNumber;
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		if (!(obj instanceof Address)) {
			return false;
		}
		Address other = (Address) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (country == null) {
			if (other.country != null) {
				return false;
			}
		} else if (!country.equals(other.country)) {
			return false;
		}
		if (door == null) {
			if (other.door != null) {
				return false;
			}
		} else if (!door.equals(other.door)) {
			return false;
		}
		if (floor == null) {
			if (other.floor != null) {
				return false;
			}
		} else if (!floor.equals(other.floor)) {
			return false;
		}
		if (strNumber != other.strNumber) {
			return false;
		}
		if (street == null) {
			if (other.street != null) {
				return false;
			}
		} else if (!street.equals(other.street)) {
			return false;
		}
		if (zip == null) {
			if (other.zip != null) {
				return false;
			}
		} else if (!zip.equals(other.zip)) {
			return false;
		}
		return true;
	}

    // public List<UserData> getUserData() {
    // return this.userData;
    // }
    //
    // public void setUserData(List<UserData> userData) {
    // this.userData = userData;
    // }
    // public UserData addUserData(UserData userData) {
    // getUserData().add(userData);
    // userData.setAddress(this);
    //
    // return userData;
    // }
    // public UserData removeUserData(UserData userData) {
    // getUserData().remove(userData);
    // userData.setAddress(null);
    //
    // return userData;
    // }
}
