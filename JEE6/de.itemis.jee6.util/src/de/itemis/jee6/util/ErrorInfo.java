package de.itemis.jee6.util;

public class ErrorInfo
{
	private final String item;
	private final String key;
	
	public ErrorInfo(final String item, final String key)
	{
		this.item = item;
		this.key  = key;
	}
	
	public String getGuiItemId()
	{
		return this.item;
	}
	
	public String getMessageKey()
	{
		return this.key;
	}
}

