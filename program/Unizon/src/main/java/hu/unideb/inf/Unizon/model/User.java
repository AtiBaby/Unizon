package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the UNI_USER database table.
 * 
 */
@Entity
@Table(name="UNI_USER")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.eMail = :eMail")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="USER_ID", unique=true, nullable=false)
	private int userId;

	@Column(name="E_MAIL", nullable=false, length=100)
	private String eMail;

	@Column(name="NAME", nullable=false, length=100)
	private String name;

	@Column(name="PASSWORD", nullable=false, length=100)
	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name="REGISTRATION_DATE", nullable=false)
	private Date registrationDate;

	@Column(name="USERNAME", nullable=false, length=100)
	private String username;

	//bi-directional one-to-one association to Administrator
	@OneToOne(mappedBy="user")
	private Administrator administrator;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Order> orders;

	//bi-directional many-to-many association to Address
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="ADDRESS_TO_USER"
		, joinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="ADDRESS_ID", nullable=false)
			}
		)
	private List<Address> addresses;

	//bi-directional many-to-many association to PhoneNumber
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="USER_TO_PHONE_NUMBER"
		, joinColumns={
			@JoinColumn(name="USER_ID", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="PHONE_NUMBER_ID", nullable=false)
			}
		)
	private List<PhoneNumber> phoneNumbers;

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

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return this.phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
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

	@Override
	public String toString() {
		return String.format("User [id=%s, %s]", userId, username);
	}

}