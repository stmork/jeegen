/*
 * $Id$
 */
package de.itemis.faces.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;

/**
 * This class implements the {@link Part} interface by wrapping the {@link FileItem} class.
 */
public class BlobPart implements Part
{
	private final FileItem item;

	public BlobPart(final FileItem item)
	{
		this.item = item;
	}
	
	@Override
	public void delete() throws IOException
	{
		item.delete();
	}

	@Override
	public String getContentType()
	{
		return item.getContentType();
	}

	@Override
	public String getHeader(String header)
	{
		final FileItemHeaders headers = item.getHeaders();
		
		return headers.getHeader(header);
	}

	@Override
	public Collection<String> getHeaderNames()
	{
		return null;
	}

	@Override
	public Collection<String> getHeaders(String arg0)
	{
		return null;
	}

	@Override
	public InputStream getInputStream() throws IOException
	{
		return item.getInputStream();
	}

	@Override
	public String getName()
	{
		return item.getName();
	}

	@Override
	public long getSize()
	{
		return item.getSize();
	}

	@Override
	public void write(final String filename) throws IOException
	{
		try
		{
			item.write(new File(filename));
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
