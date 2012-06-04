package de.itemis.faces.entities;

import java.io.Serializable;
import java.text.MessageFormat;
import javax.persistence.*;

@Entity
@Table(name = "AddressOption")
public class AddressOption implements Serializable

{
	private static final long serialVersionUID = 1L;

	public static enum AddressOptionEnum {

		ADDRESS_WORK, ADDRESS_HOME

	}

	public AddressOption() {
	}

	public AddressOption(final AddressOptionEnum type, final String bundleKey) {
		setId(type.ordinal());
		setBundleKey(bundleKey);
	}

	public AddressOption(final int id, final String bundleKey) {
		setId(id);
		setBundleKey(bundleKey);
	}

	private String bundleKey;

	@Column
	@javax.validation.constraints.NotNull
	public String getBundleKey() {
		return this.bundleKey;
	}

	public void setBundleKey(final String bundleKey) {
		this.bundleKey = bundleKey;
	}

	@Transient
	public AddressOptionEnum getAddressOptionEnum() {
		return AddressOptionEnum.values()[getId()];
	}

	private int id;

	@Id
	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object object) {
		if ((object == null) || !(object instanceof AddressOption)) {
			return false;
		}
		AddressOption option = (AddressOption) object;

		return getId() == option.getId();

	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		return MessageFormat.format("[AddressOption:{0}]{1}",

		getId(),

		buffer);
	}
}
