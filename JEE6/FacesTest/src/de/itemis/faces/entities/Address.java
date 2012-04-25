package de.itemis.faces.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="Address")
public class Address implements Serializable
{
	private static final long serialVersionUID = 1L;

	private int id;
	private int position;
	private String street;
	private String plz;
	private String location;
	private UserInfo user;
	private AddressOption addressOption;

	@Id
	@TableGenerator(
			name="idGenerator", table = "IDs",
			pkColumnName = "id", valueColumnName = "value", pkColumnValue = "Address",
			initialValue = 1, allocationSize = 10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="idGenerator")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(nullable=false)
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Column
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column
	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	@Column
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@ManyToOne
	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}

	@OneToOne
	public AddressOption getAddressOption() {
		return addressOption;
	}

	public void setAddressOption(AddressOption addressOption) {
		this.addressOption = addressOption;
	}

	@Override
	public String toString()
	{
		return getStreet() + ", " + (getPlz() + " " + getLocation()).trim();
	}
}
