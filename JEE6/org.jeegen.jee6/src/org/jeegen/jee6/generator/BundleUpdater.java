package org.jeegen.jee6.generator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.xtend.expression.ExecutionContext;
import org.eclipse.xtend.expression.IExecutionContextAware;
import org.eclipse.xtend.expression.Variable;

/**
 * This class is called from Xpand2 templates via extensions. It ensures, that all needed
 * resource bundle values exist in the resource bundle.
 */
public class BundleUpdater implements IExecutionContextAware
{
	private final static Log log = LogFactory.getLog(BundleUpdater.class);
	private final static HashMap<String, PropertyInfo> bundles = new HashMap<String, PropertyInfo>();
	private File basedir;

	public void update(final String filename, final String key, final String value) throws IOException
	{
		final File   file = new File(basedir, filename);
		final String name = file.getAbsolutePath();
		log.debug(String.format("name: %s key=%s value=%s", name, key, value));

		PropertyInfo properties = bundles.get(name);
		if (properties == null)
		{
			properties = new PropertyInfo(log, file);
			bundles.put(name,  properties);
		}
		properties.set(key, value);
	}

	public void flush(final String filename) throws IOException, URISyntaxException
	{
		final File file = new File(basedir, filename);
		final String name = file.getAbsolutePath();
		final PropertyInfo properties = bundles.get(name);

		if (properties != null)
		{
			properties.flush();
		}
	}

	@Override
	public void setExecutionContext(ExecutionContext ctx)
	{
		Variable var = ctx.getGlobalVariables().get("src");

		if (var != null)
		{
			basedir = new File(var.getValue().toString());
			log.debug("Basedir: " + basedir.getAbsolutePath());
		}
		else
		{
			basedir = null;
			log.error("Global variable 'src' is missing!");
		}
	}
}
