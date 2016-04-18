package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the PHONE_NUMBER database table.
 *
 */
@Entity
@Table(name = "PHONE_NUMBER")
@NamedQueries({
    @NamedQuery(name = "PhoneNumber.findAll", query = "SELECT p FROM PhoneNumber p"),
    @NamedQuery(name = "PhoneNumber.findByPhoneNumber", query = "SELECT p FROM PhoneNumber p WHERE p.phoneNumber = :phoneNumber")
})
public class PhoneNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PHONE_NUMBER_ID", unique = true, nullable = false)
    private int phoneNumberId;

    @Column(name = "PHONE_NUMBER", nullable = false, length = 100)
    private String phoneNumber;

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "phoneNumber", fetch = FetchType.EAGER)
    private Set<Order> orders;

    //bi-directional many-to-many association to User
    @ManyToMany(mappedBy = "phoneNumbers", fetch = FetchType.EAGER)
    private Set<User> users;

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

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Order addOrder(Order order) {
        getOrders().add(order);
        order.setPhoneNumber(this);

        return order;
    }

    public Order removeOrder(Order order) {
        getOrders().remove(order);
        order.setPhoneNumber(null);

        return order;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
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
        if (!(obj instanceof PhoneNumber)) {
            return false;
        }
        PhoneNumber other = (PhoneNumber) obj;
        if (phoneNumber == null) {
            if (other.phoneNumber != null) {
                return false;
            }
        } else if (!phoneNumber.equals(other.phoneNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return phoneNumber;
    }

}
