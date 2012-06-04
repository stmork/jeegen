package de.itemis.faces.entities;

import java.io.Serializable;
import java.text.MessageFormat;
import javax.persistence.*;

import java.util.List;

import de.itemis.faces.entities.Address;

import java.util.Date;

@Entity
@Table(name = "UserInfo")
public class UserInfo implements Serializable

{
	private static final long serialVersionUID = 1L;

	private String login;

	@Id
	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	private List<Address> addresses;

	@OneToMany(mappedBy = "userInfo")
	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(final List<Address> addresses) {
		this.addresses = addresses;
	}

	private String name;

	@Column
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	private String mail;

	@Column
	public String getMail() {
		return this.mail;
	}

	public void setMail(final String mail) {
		this.mail = mail;
	}

	private Date birth;

	@Column
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(final Date birth) {
		this.birth = birth;
	}

	private Date changed;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	public Date getChanged() {
		return this.changed;
	}

	public void setChanged(final Date changed) {
		this.changed = changed;
	}

	@PreUpdate
	public void preUpdate() {

		setChanged(new Date());

	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if ((object == null) || !(object instanceof UserInfo)) {
			return false;
		}
		UserInfo option = (UserInfo) object;

		return getLogin().equals(option.getLogin());

	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" name=").append(getName());

		buffer.append(" mail=").append(getMail());

		buffer.append(" birth=").append(getBirth());

		buffer.append(" changed=").append(getChanged());

		return MessageFormat.format("[UserInfo:{0}]{1}",

		getLogin(),

		buffer);
	}
}
