package de.itemis.purchasing.entities;

import javax.persistence.*;

import de.itemis.purchasing.entities.AbstractInventory;
import de.itemis.purchasing.entities.InventoryHistoryEntry;

import java.util.Locale;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "Inventory")
public class Inventory extends AbstractInventory {
	private static final long serialVersionUID = 1L;

	@Override
	public boolean filter(final String pattern, final Locale locale)
	{
        final String filter = pattern.toLowerCase(locale);
        final InventoryHistoryEntry last = getLast();

        if (last.getOwner() != null)
        {
            if (last.getOwner().getName().toLowerCase(locale).contains(filter))
            {
                return true;
            }
        }
        if (getInventoryType().getLabel().toLowerCase(locale).contains(filter))
        {
            return true;
        }
        if (last.getUtilization() != null)
        {
            if (last.getUtilization().toLowerCase(locale).contains(filter))
            {
                return true;
            }
        }
        if (getSerialNumber() != null)
        {
            return getSerialNumber().toLowerCase(locale).contains(filter);
        }
        return false;
	}

}
