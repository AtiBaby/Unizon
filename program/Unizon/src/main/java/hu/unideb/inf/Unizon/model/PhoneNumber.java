package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

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

    @Column(name = "PHONE_NUMBER", length = 100)
    private String phoneNumber;

    //bi-directional many-to-one association to Order
    @OneToMany(mappedBy = "phoneNumber", fetch = FetchType.EAGER)
    private List<Order> orders;

    //bi-directional many-to-one association to User
    @OneToMany(mappedBy = "phoneNumber", fetch = FetchType.EAGER)
    private List<User> users;

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

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
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

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User addUser(User user) {
        getUsers().add(user);
        user.setPhoneNumber(this);

        return user;
    }

    public User removeUser(User user) {
        getUsers().remove(user);
        user.setPhoneNumber(null);

        return user;
    }

}
