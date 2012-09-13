package de.itemis.jee6.ui.wizard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

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
		
		final IJavaProject javaProject = JavaCore.create(project);
		final IPath path = javaProject.getPath().append(WEB_CONTENT + File.separator + "WEB-INF/lib/jee6-utils.jar");
		final IClasspathEntry entry = JavaCore.newLibraryEntry(path, null, null);

		IClasspathEntry [] oldEntries = javaProject.getRawClasspath();
		IClasspathEntry [] newEntries = new IClasspathEntry[oldEntries.length + 1];
		newEntries[0] = entry;
		System.arraycopy(oldEntries, 0, newEntries, 1, oldEntries.length);
		
		javaProject.setRawClasspath(newEntries, new NullProgressMonitor());

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
