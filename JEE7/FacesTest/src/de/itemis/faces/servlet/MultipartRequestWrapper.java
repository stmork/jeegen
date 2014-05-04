/*
 * $Id$
 */
package de.itemis.faces.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee6.util.LogUtil;

/**
 * This class wrapps a multipart request in a way that usual request handler can continue working. Especsially
 * the form field parameter are converted into the usual parameter map. The upload fields are converted into
 * a {@link Part} map for usage. This class makes use of the Apache commons file upload API. 
 */
public class MultipartRequestWrapper extends HttpServletRequestWrapper
{
	private final static Log log = LogFactory.getLog(MultipartRequestWrapper.class);
	private final Hashtable<String, String[]> params = new Hashtable<String, String[]>();
	private final Hashtable<String, Part> parts = new Hashtable<String, Part>();

	public MultipartRequestWrapper(HttpServletRequest request) throws UnsupportedEncodingException
	{
		super(request);
		
		log.debug(">MultipartRequestWrapper()");
		final DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		factory.setSizeThreshold(1024000);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try
		{
			for (FileItem item : upload.parseRequest(request))
			{
				if (item.isFormField())
				{
					final String [] value = new String[]
							{
								item.getString(request.getCharacterEncoding())
							};

					params.put(item.getFieldName(), value);
					LogUtil.debug(log, "  %s/%s", item.getFieldName(), value[0]);
				}
				else
				{
					parts.put(item.getFieldName(), new BlobPart(item));
					LogUtil.debug(log, "  %s/%d bytes", item.getFieldName(), item.getSize());
				}
			}
		}
		catch (FileUploadException e)
		{
			log.error(e);
		}
		finally
		{
			log.debug("<MultipartRequestWrapper()");
		}
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

	@Override
	public Part getPart(final String key)
	{
		LogUtil.debug(log, ">getPart(%s)", key);
		return parts.get(key);
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException
	{
		return parts.values();
	}

}
