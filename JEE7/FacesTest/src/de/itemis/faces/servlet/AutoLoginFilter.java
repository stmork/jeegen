/*
 * $Id$
 */
package de.itemis.faces.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.Base64;
import de.itemis.jee6.util.LogUtil;

@WebFilter(urlPatterns = {"/*"})
public class AutoLoginFilter implements Filter
{
	private static final Log  log    = LogFactory.getLog(AutoLoginFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(
			ServletRequest  request,
			ServletResponse response,
			FilterChain filter) throws IOException, ServletException {
		log.trace(">doFilter()");

		if (request instanceof HttpServletRequest)
		{
			final HttpServletRequest r = (HttpServletRequest)request;
			final String basic = r.getHeader("authorization"); 

			LogUtil.trace(log, " URI: %s", r.getRequestURI());
	        LogUtil.trace(log, " URL: %s", r.getRequestURL());
	        if ((basic != null) && basic.startsWith("Basic "))
	        {
	        	final String principal = r.getRemoteUser();
	            if (principal == null)
	            {
	            	final byte [] text = Base64.decode(basic.substring(6));
	            	final String encoding = request.getCharacterEncoding();
	                final String [] userdata = new String (text, encoding != null ? encoding : "UTF-8").split(":");
	                final String user = userdata[0];
	
	                LogUtil.trace(log, " Automatically logging in user %s.", user);
	            	r.login(userdata[0], userdata[1]);
	//            	HttpServletResponse resp = (HttpServletResponse)response;
	//            	resp.sendRedirect(r.getRequestURL().toString());
	            }
	        }
        }
//		else
        {
        	filter.doFilter(request, response);
        }
		log.trace("<doFilter()");
	}
}
