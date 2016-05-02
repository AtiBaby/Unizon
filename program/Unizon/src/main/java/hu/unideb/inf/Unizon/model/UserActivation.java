package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the USER_ACTIVATION database table.
 * 
 */
@Entity
@Table(name="USER_ACTIVATION")
@NamedQueries({
	@NamedQuery(name="UserActivation.findAll", query="SELECT u FROM UserActivation u"),
	@NamedQuery(name="UserActivation.findByActivationKey", query="SELECT u FROM UserActivation u WHERE u.activationKey = :activationKey")
})
public class UserActivation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false)
	private int userId;

	@Column(name="ACTIVATION_KEY", nullable=false, length=200)
	private String activationKey;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="USER_ID", nullable=false, insertable=false, updatable=false)
	private User user;

	public UserActivation() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getActivationKey() {
		return this.activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
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
		result = prime * result + ((activationKey == null) ? 0 : activationKey.hashCode());
		result = prime * result + userId;
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
		if (!(obj instanceof UserActivation)) {
			return false;
		}
		UserActivation other = (UserActivation) obj;
		if (activationKey == null) {
			if (other.activationKey != null) {
				return false;
			}
		} else if (!activationKey.equals(other.activationKey)) {
			return false;
		}
		if (userId != other.userId) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("UserActivation [userId=%s, activationKey=%s]", userId, activationKey);
	}

}