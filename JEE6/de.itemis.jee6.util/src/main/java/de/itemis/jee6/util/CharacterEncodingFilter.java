package de.itemis.jee6.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This web servlet filter ensures that the request encoding is set to UTF-8.
 */
@WebFilter(urlPatterns = {"/*"})
public class CharacterEncodingFilter implements Filter
{
	private final static Log log = LogFactory.getLog(CharacterEncodingFilter.class);

	/**
	 * This init method intentionally do nothing.
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		log.debug("=init()");
	}

	/**
	 * This destroy method intentionally do nothing.
	 */
	@Override
	public void destroy()
	{
		log.debug("=destroy()");
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
			log.trace(" Setting request encoding to UTF-8");
		    request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, response);
	}
}
