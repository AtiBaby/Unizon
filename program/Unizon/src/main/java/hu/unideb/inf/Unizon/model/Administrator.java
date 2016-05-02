package hu.unideb.inf.Unizon.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ADMINISTRATOR database table.
 * 
 */
@Entity
@Table(name="ADMINISTRATOR")
@NamedQueries({
	@NamedQuery(name="Administrator.findAll", query="SELECT a FROM Administrator a"),
	@NamedQuery(name="Administrator.isAdministrator", query="SELECT a FROM Administrator a WHERE a.userId = :userId")
})
public class Administrator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="USER_ID", unique=true, nullable=false)
	private int userId;

	//bi-directional one-to-one association to User
	@OneToOne
	@JoinColumn(name="USER_ID", nullable=false, insertable=false, updatable=false)
	private User user;

	public Administrator() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.userId;
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
        final Administrator other = (Administrator) obj;
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

}