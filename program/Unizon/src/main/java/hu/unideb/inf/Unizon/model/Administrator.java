package hu.unideb.inf.Unizon.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


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

}