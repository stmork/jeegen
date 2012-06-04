package de.itemis.faces.entities;

import java.io.Serializable;
import java.text.MessageFormat;
import javax.persistence.*;

import de.itemis.faces.entities.AddressOption;

@Entity
@Table(name = "Address")
public class Address implements Serializable

{
	private static final long serialVersionUID = 1L;

	private int id;

	@Id
	@TableGenerator(name = "AddressIDs", table = "IDs", pkColumnName = "id", valueColumnName = "value", pkColumnValue = "Address", initialValue = 1, allocationSize = 10)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AddressIDs")
	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	private int position;

	@Column
	public int getPosition() {
		return this.position;
	}

	public void setPosition(final int position) {
		this.position = position;
	}

	private String street;

	@Column
	public String getStreet() {
		return this.street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	private String plz;

	@Column
	public String getPlz() {
		return this.plz;
	}

	public void setPlz(final String plz) {
		this.plz = plz;
	}

	private String location;

	@Column
	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	private boolean active;

	@Column
	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	private AddressOption addressOption;

	@OneToOne
	public AddressOption getAddressOption() {
		return this.addressOption;
	}

	public void setAddressOption(final AddressOption addressOption) {
		this.addressOption = addressOption;
	}

	private UserInfo userInfo;

	@ManyToOne
	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(final UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" position=").append(getPosition());

		buffer.append(" street=").append(getStreet());

		buffer.append(" plz=").append(getPlz());

		buffer.append(" location=").append(getLocation());

		buffer.append(" active=").append(isActive());

		return MessageFormat.format("[Address:{0}]{1}",

		getId(),

		buffer);
	}
}
