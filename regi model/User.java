package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * The persistent class for the UNI_USER database table.
 * 
 */
@Entity
@Table(name = "UNI_USER")
@NamedQueries({
    @NamedQuery(name="User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.eMail = :eMail")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID", unique = true, nullable = false)
	private int userId;

	@Column(name = "E_MAIL", length = 100)
	private String eMail;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "PASSWORD", nullable = false, length = 100)
	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name = "REGISTRATION_DATE")
	private Date registrationDate;

	@Column(name = "USERNAME", nullable = false, length = 100)
	private String username;

	// bi-directional many-to-one association to AddressesOfUser
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
        @Fetch(FetchMode.SUBSELECT)
	private List<AddressesOfUser> addressesOfUsers;

	// bi-directional one-to-one association to Administrator
	@OneToOne(mappedBy = "user")
	private Administrator administrator;

	// bi-directional many-to-one association to Order
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Order> orders;

	// bi-directional one-to-one association to UserData
	@OneToOne(mappedBy = "user")
	private UserData userData;

	public User() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		addressesOfUser.setUser(this);

		return addressesOfUser;
	}

	public AddressesOfUser removeAddressesOfUser(AddressesOfUser addressesOfUser) {
		getAddressesOfUsers().remove(addressesOfUser);
		addressesOfUser.setUser(null);

		return addressesOfUser;
	}

	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setUser(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setUser(null);

		return order;
	}

	public UserData getUserData() {
		return this.userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}

}