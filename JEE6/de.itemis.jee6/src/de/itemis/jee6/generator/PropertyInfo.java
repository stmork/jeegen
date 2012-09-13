package de.itemis.jee6.generator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;

class PropertyInfo extends Properties {
	private static final long serialVersionUID = 1L;
	private URL url;
	private boolean modified = false;
	private Log log;

	PropertyInfo(final Log log, final String filename) throws IOException {
		this.log = log;
		InputStream is = null;
		try
		{
			url = Thread.currentThread().getContextClassLoader().getResource(filename);

			if (url != null) {
				is = url.openStream();
				log.info("Reading " + url);
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
		Enumeration keysEnum = super.keys();
		Vector keyList = new Vector();
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

	void flush() throws IOException, URISyntaxException {
		if (modified && (url != null)) {
			OutputStream os = null;

			try {
				log.info("Storing updated " + url.getFile());

				os = new FileOutputStream(url.getFile());
				store(os, "Automatically extended");
			} finally {
				if (os != null) {
					os.close();
				}
			}
		}
	}
}
