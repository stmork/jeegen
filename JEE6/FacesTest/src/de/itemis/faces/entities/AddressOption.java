package de.itemis.faces.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="AddressOption")
public class AddressOption implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static enum AddressOptionType
	{
		HOME,
		WORK;
	}
	
	private int type;
	private String description;

	public AddressOption()
	{
	}

	public AddressOption(final AddressOptionType type, final String description)
	{
		setType(type.ordinal());
		setDescription(description);
	}

	@Id
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	@Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString()
	{
		return getDescription() + "/" + getType();
	}
	
	@Override
	public boolean equals(Object object)
	{
		AddressOption option = (AddressOption)object;
		return getType() == option.getType();
	}
}
