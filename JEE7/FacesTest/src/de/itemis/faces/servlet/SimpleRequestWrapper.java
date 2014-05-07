/*
 * $Id$
 */
package de.itemis.faces.servlet;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * This class is a request wrapper for usual request. It is like a loopback request for testing purposes.
 */
public class SimpleRequestWrapper extends HttpServletRequestWrapper
{
	private final static Logger log = Logger.getLogger(MultipartRequestWrapper.class.getName());
	private final Hashtable<String, String[]> params = new Hashtable<String, String[]>();

	public SimpleRequestWrapper(HttpServletRequest request)
	{
		super(request);
		log.log(Level.FINE, ">SimpleRequestWrapper()");
		for (Map.Entry<String, String[]> entry : super.getParameterMap().entrySet())
		{
			params.put(entry.getKey(), entry.getValue());
			
			if (log.isLoggable(Level.FINE))
			{
				log.log(Level.FINE, "   " + entry.getKey());
				for (String value : entry.getValue())
				{
					log.log(Level.FINE, "      [" + value + "]");
				}
			}
		}
		log.log(Level.FINE, "<SimpleRequestWrapper()");
	}

	@Override
	public String getParameter(String key)
	{
		final String [] values = getParameterValues(key);
		final String    value  = values != null ? values[0] : null;

		return value;
	}
	
	@Override
	public String[] getParameterValues(String name)
	{
		return params.get(name);
	}

	@Override
	public Enumeration<String> getParameterNames()
	{
		return params.keys();
	}

	@Override
	public Map<String, String[]> getParameterMap()
	{
		return params;
	}
}
