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
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The multipart filter is necessary to convert a multipart encoded request into a usual {@link HttpServletRequest}
 * using the {@link MultipartRequestWrapper}. The {@link MultipartRequestWrapper} is only used, if the request
 * is indeed multipart encoded.
 */
@WebFilter(urlPatterns = { "/admin/*" })
public class MultipartFilter implements Filter
{
	private final static Log log = LogFactory.getLog(MultipartFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		log.debug(">init()");
	}

	@Override
	public void destroy()
	{
		log.debug("<destroy()");
	}

	@Override
	public void doFilter(ServletRequest inputRequest, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		log.debug(">doFilter()");
		final ServletRequest request;

		if (inputRequest.getCharacterEncoding() == null)
		{
			inputRequest.setCharacterEncoding("UTF-8");
		}

		log.debug("  " + inputRequest.getCharacterEncoding());
		log.debug("  " + response.getCharacterEncoding());
		if (inputRequest instanceof HttpServletRequest)
		{
			HttpServletRequest httpRequest = (HttpServletRequest) inputRequest;
			log.debug("  Is multipart = " + ServletFileUpload.isMultipartContent(httpRequest));
			
			for (Part part : httpRequest.getParts())
			{
				log.debug("  " + part.getName());
				log.debug("  " + part.getSize());
				log.debug("  " + part.getContentType());
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
		log.debug("<doFilter()");
	}
}
