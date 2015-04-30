/*
 * $Id$
 */
package org.jeegen.jee7.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * This web servlet filter ensures that the request encoding is set to UTF-8.
 */
@WebFilter(urlPatterns = {"/*"})
public class CharacterEncodingFilter implements Filter
{
	private final static Logger log = Logger.getLogger(CharacterEncodingFilter.class.getName());

	/**
	 * This init method intentionally do nothing.
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		log.log(Level.FINE, "=init()");
	}
	
	/**
	 * This destroy method intentionally do nothing.
	 */
	@Override
	public void destroy()
	{
		log.log(Level.FINE, "=destroy()");
	}

	/**
	 * This filter ensures the request encoding of UTF-8.
	 */
	@Override
	public void doFilter(
			final ServletRequest request,
			final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException
	{
		if (request.getCharacterEncoding() == null)
		{
			log.log(Level.FINER, " Setting request encoding to UTF-8");
		    request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, response);
	}
}
