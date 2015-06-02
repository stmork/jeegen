package org.jeegen.jee7.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;

class PropertyInfo extends Properties {
	private static final long serialVersionUID = 1L;
	private final File file;
	private boolean modified = false;
	private Log log;
	

	PropertyInfo(final Log log, final File file) throws IOException {
		this.log = log;
		this.file = file;
		InputStream is = null;

		try
		{
			is = new FileInputStream(file);
			if (is != null)
			{
				log.info("Reading " + file.getName());
				load(is);
			}
		}
		finally
		{
			if (is != null)
			{
				is.close();
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized Enumeration keys()
	{
		final Enumeration keysEnum = super.keys();
		final Vector keyList = new Vector();

		while (keysEnum.hasMoreElements())
		{
			keyList.add(keysEnum.nextElement());
		}
		Collections.sort(keyList);
		return keyList.elements();
	}

	void set(final String key, final String value) {
		if (get(key) == null) {
			log.info(String.format(" * Adding %s=%s", key, value));
			setProperty(key, value);
			modified = true;
		}
	}

	void flush() throws IOException
	{
		if (modified)
		{
			if (file != null)
			{
				OutputStream os = null;
	
				try {
					log.info("Storing updated " + file.getName());
	
					os = new FileOutputStream(file);
					store(os, "Automatically extended");
				}
				finally
				{
					if (os != null)
					{
						os.close();
					}
				}
			}
			else
			{
				log.warn("No properties file available!");
			}
		}
	}
}
