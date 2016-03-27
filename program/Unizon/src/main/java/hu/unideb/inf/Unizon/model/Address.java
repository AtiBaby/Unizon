package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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
    private Integer strNumber;

    @Column(name = "STREET", length = 100)
    private String street;

    @Column(name = "ZIP", length = 20)
    private String zip;

    //bi-directional many-to-one association to AddressesOfUser
    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER)
    private List<AddressesOfUser> addressesOfUsers;

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "address1", fetch = FetchType.EAGER)
    private List<Order> orders1;

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "address2", fetch = FetchType.EAGER)
    private List<Order> orders2;

    //bi-directional many-to-one association to User
    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER)
    private List<User> users;

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

    public Integer getStrNumber() {
        return strNumber;
    }

    public void setStrNumber(Integer strNumber) {
        this.strNumber = strNumber;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
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

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User addUser(User user) {
        getUsers().add(user);
        user.setAddress(this);

        return user;
    }

    public User removeUser(User user) {
        getUsers().remove(user);
        user.setAddress(null);

        return user;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.city);
        hash = 37 * hash + Objects.hashCode(this.country);
        hash = 37 * hash + this.door;
        hash = 37 * hash + this.floor;
        hash = 37 * hash + this.strNumber;
        hash = 37 * hash + Objects.hashCode(this.street);
        hash = 37 * hash + Objects.hashCode(this.zip);
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
        final Address other = (Address) obj;
        if (this.door != other.door) {
            return false;
        }
        if (this.floor != other.floor) {
            return false;
        }
        if (this.strNumber != other.strNumber) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        return true;
    }

}
