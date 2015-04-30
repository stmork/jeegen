/*
 * $Id$
 */
package org.jeegen.faces.servlet;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is a request wrapper for usual request. It is like a loopback request for testing purposes.
 */
public class SimpleRequestWrapper extends HttpServletRequestWrapper
{
	private final static Log log = LogFactory.getLog(MultipartRequestWrapper.class);
	private final Hashtable<String, String[]> params = new Hashtable<String, String[]>();

	public SimpleRequestWrapper(HttpServletRequest request)
	{
		super(request);
		log.debug(">SimpleRequestWrapper()");
		for (Map.Entry<String, String[]> entry : super.getParameterMap().entrySet())
		{
			params.put(entry.getKey(), entry.getValue());
			
			if (log.isDebugEnabled())
			{
				log.debug("   " + entry.getKey());
				for (String value : entry.getValue())
				{
					log.debug("      [" + value + "]");
				}
			}
		}
		log.debug("<SimpleRequestWrapper()");
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
