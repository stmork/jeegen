/*
 * $Id$
 */
package org.jeegen.jee6.util;

/**
 * This bean class holds information about a bundle key and its UI element id pair.
 */
public class ErrorInfo
{
	/**
	 * This JSF item ID.
	 */
	private final String item;

	/**
	 * The resource bundle error key.
	 */
	private final String key;

	/**
	 * The constructor defines the UI item ID and resource bundle key pair.
	 * 
	 * @param item The JSF UI item ID.
	 * @param key The resource bundle error key.
	 */
	public ErrorInfo(final String item, final String key)
	{
		this.item = item;
		this.key  = key;
	}
	
	/**
	 * The JSF UI item ID getter.
	 * 
	 * @return The JSF UI item ID.
	 */
	public String getGuiItemId()
	{
		return this.item;
	}

	/**
	 * The resource bundle error Key getter.
	 * 
	 * @return The resource bundle error key.
	 */
	public String getMessageKey()
	{
		return this.key;
	}
}
