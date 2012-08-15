package de.itemis.example.entities;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "OrderPosition")
public class OrderPosition extends AbstractOrderPosition {
	private static final long serialVersionUID = 1L;

	@Transient
	@Override
	public String getPosition()
	{
		if (getInventoryType() != null)
		{
			return getInventoryType().getLabel();
		}
		else
		{
			return getModel();
		}
	}

	@Override
	public boolean filter(final String pattern, final Locale locale)
	{
		return getPosition().toLowerCase(locale).contains(pattern.toLowerCase(locale));
	}
}
