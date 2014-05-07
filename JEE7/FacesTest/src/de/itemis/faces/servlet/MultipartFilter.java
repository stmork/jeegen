/*
 * $Id$
 */
package de.itemis.faces.servlet;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * The multipart filter is necessary to convert a multipart encoded request into a usual {@link HttpServletRequest}
 * using the {@link MultipartRequestWrapper}. The {@link MultipartRequestWrapper} is only used, if the request
 * is indeed multipart encoded.
 */
@WebFilter(urlPatterns = { "/admin/*" })
public class MultipartFilter implements Filter
{
	private final static Logger log = Logger.getLogger(MultipartFilter.class.getName());

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		log.log(Level.FINE, ">init()");
	}

	@Override
	public void destroy()
	{
		log.log(Level.FINE, "<destroy()");
	}

	@Override
	public void doFilter(ServletRequest inputRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.log(Level.FINE, ">doFilter()");
		final ServletRequest request;

		if (inputRequest.getCharacterEncoding() == null)
		{
			inputRequest.setCharacterEncoding("UTF-8");
		}

		log.log(Level.FINE, "  " + inputRequest.getCharacterEncoding());
		log.log(Level.FINE, "  " + response.getCharacterEncoding());
		if (inputRequest instanceof HttpServletRequest)
		{
			HttpServletRequest httpRequest = (HttpServletRequest) inputRequest;
			log.log(Level.FINE, "  Is multipart = " + ServletFileUpload.isMultipartContent(httpRequest));
			
			for (Part part : httpRequest.getParts())
			{
				log.log(Level.FINE, "  " + part.getName());
				log.log(Level.FINE, "  " + part.getSize());
				log.log(Level.FINE, "  " + part.getContentType());
			}
			if (ServletFileUpload.isMultipartContent(httpRequest))
			{
				request = new MultipartRequestWrapper(httpRequest);
			}
			else
			{
				request = new SimpleRequestWrapper(httpRequest);
			}
		}
		else
		{
			request = inputRequest;
		}
		chain.doFilter(request, response);
		log.log(Level.FINE, "<doFilter()");
	}
}
