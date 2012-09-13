package de.itemis.jee6.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IBuildConfiguration;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;

public class Jee6ProjectCreator extends DslProjectCreator
{
	private final static String BUNDLE_ID   = "de.itemis.jee6.ui";
	private final static String WEB_CONTENT = "WebContent";

	@Override
	protected List<String> getAllFolders()
	{
		List<String> defaultFolders = new ArrayList<String>(super.getAllFolders());
		defaultFolders.add(WEB_CONTENT);
		defaultFolders.add("res-gen");
		defaultFolders.add("model");
        return defaultFolders;
    }

	@Override
	protected void enhanceProject(IProject project, IProgressMonitor monitor)
			throws CoreException
	{
		try
		{
			final String projectDir = project.getLocation().makeAbsolute().toOSString();
			
			System.out.println(projectDir);

			copyFile(projectDir, "/resources/ii.png",         WEB_CONTENT + File.separator + "img");
			copyFile(projectDir, "/resources/favicon.ico",    WEB_CONTENT + File.separator + "img");
			copyFile(projectDir, "/resources/jee6-utils.jar", WEB_CONTENT + File.separator + "WEB-INF/lib");
		}
		catch(Exception e)
		{
			Status status = new Status(0, BUNDLE_ID, e.getMessage(), e);
			throw new CoreException(status);
		}

		IBuildConfiguration config = project.getActiveBuildConfig();
		super.enhanceProject(project, monitor);
	}

	private void copyFile(final String projectDir, final String src, final String dstDir) throws MalformedURLException, IOException
	{
		final URL  url  = FileLocator.resolve(new URL("platform:/plugin/" + BUNDLE_ID + src));
		final File file = new File(url.getFile());

		FileInputStream  fis = null;
		FileOutputStream fos = null;
		
		try
		{
			final byte [] buffer = new byte[(int)file.length()];
			final String destinationDir = projectDir + File.separator + dstDir; 

			File dir  = new File(destinationDir);
			dir.mkdirs();

			fis = new FileInputStream(file);
			fis.read(buffer);
			fos = new FileOutputStream(destinationDir + File.separator + file.getName());
			fos.write(buffer);
		}
		finally
		{
			if (fis != null)
			{
				fis.close();
			}
			if (fos != null)
			{
				fos.close();
			}
		}

	}
}
