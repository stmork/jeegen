package de.itemis.faces.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="UserInfo")
public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String name;
	private Date birth;
	private Date changed;
	
	@Id
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	public Date getChanged() {
		return changed;
	}
	public void setChanged(Date changed) {
		this.changed = changed;
	}

	@PreUpdate
	@PrePersist
	protected void update()
	{
		setChanged(new Date());
	}

	@Override
	public String toString()
	{
		return getClass().getName() + ": " + getName() + " # " + getBirth() + " # " + getChanged();
	}
}
