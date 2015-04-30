/*
 * $Id$
 */
package org.jeegen.jee7.test.sorter;

import java.text.Collator;
import java.util.Locale;

import org.jeegen.jee7.util.CollatingComparator;

public class GreekSorter extends CollatingComparator<String>
{
	private static final long serialVersionUID = 1L;

	public GreekSorter()
	{
		super(ASC, new Locale("el", "GR"), Collator.PRIMARY);
	}

	@Override
	protected int compareLocale(String v1, String v2)
	{
		return collator.compare(v1, v2);
	}
}
