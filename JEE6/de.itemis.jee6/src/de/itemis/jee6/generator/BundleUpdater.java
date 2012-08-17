package de.itemis.jee6.generator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BundleUpdater
{
	private final static Log log = LogFactory.getLog(BundleUpdater.class);
	private final static HashMap<String, PropertyInfo> bundles = new HashMap<String, PropertyInfo>();

	public static void update(final String name, final String key, final String value) throws IOException
	{
		log.debug(String.format("name: %s key=%s value=%s", name, key, value));
		
		PropertyInfo properties = bundles.get(name);
		if (properties == null)
		{
			properties = new PropertyInfo(log, name);
			bundles.put(name,  properties);
		}
		properties.set(key, value);
	}
	
	public static void flush(final String name) throws IOException, URISyntaxException
	{
		PropertyInfo properties = bundles.get(name);
		if (properties != null)
		{
			properties.flush();
		}
	}
}
