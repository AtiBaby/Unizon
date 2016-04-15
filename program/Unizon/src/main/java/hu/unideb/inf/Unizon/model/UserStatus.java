package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the USER_STATUS database table.
 * 
 */
@Entity
@Table(name="USER_STATUS")
@NamedQueries({
	@NamedQuery(name="UserStatus.findAll", query="SELECT u FROM UserStatus u"),
	@NamedQuery(name="UserStatus.findByStatusName", query="SELECT u FROM UserStatus u WHERE u.statusName = :statusName")
})
public class UserStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="STATUS_ID", unique=true, nullable=false)
	private int statusId;

	@Column(name="STATUS_NAME", nullable=false, length=100)
	private String statusName;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userStatus", fetch=FetchType.EAGER)
	private List<User> users;

	public UserStatus() {
	}

	public int getStatusId() {
		return this.statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserStatus(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserStatus(null);

		return user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statusName == null) ? 0 : statusName.hashCode());
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
		if (!(obj instanceof UserStatus)) {
			return false;
		}
		UserStatus other = (UserStatus) obj;
		if (statusName == null) {
			if (other.statusName != null) {
				return false;
			}
		} else if (!statusName.equals(other.statusName)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("UserStatus [statusId=%s, statusName=%s]", statusId, statusName);
	}

}