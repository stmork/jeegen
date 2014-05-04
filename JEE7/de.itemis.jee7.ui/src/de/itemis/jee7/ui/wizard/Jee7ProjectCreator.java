package de.itemis.jee7.ui.wizard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class Jee7ProjectCreator extends DslProjectCreator
{
	private final static String BUNDLE_ID   = "de.itemis.jee7.ui";
	private final static String WEB_CONTENT = "res";

	@Override
	protected List<String> getAllFolders()
	{
		List<String> defaultFolders = new ArrayList<String>(super.getAllFolders());
		defaultFolders.add(WEB_CONTENT);
		defaultFolders.add("WebContent");
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
			copyFile(project, "resources/logo.png",       WEB_CONTENT + "/img");
			copyFile(project, "resources/favicon.ico",    WEB_CONTENT + "/img");
			copyFile(project, "resources/jee7-utils.jar", WEB_CONTENT + "/WEB-INF/lib");
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			Status status = new Status(Status.ERROR, BUNDLE_ID, e.getMessage(), e);
			throw new CoreException(status);
		}
		
		final IJavaProject javaProject = JavaCore.create(project);
		final IPath path = javaProject.getPath().append(WEB_CONTENT + "/WEB-INF/lib/jee7-utils.jar");
		final IClasspathEntry entry = JavaCore.newLibraryEntry(path, null, null);

		IClasspathEntry [] oldEntries = javaProject.getRawClasspath();
		IClasspathEntry [] newEntries = new IClasspathEntry[oldEntries.length + 1];
		newEntries[0] = entry;
		System.arraycopy(oldEntries, 0, newEntries, 1, oldEntries.length);
		
		javaProject.setRawClasspath(newEntries, new NullProgressMonitor());

		super.enhanceProject(project, monitor);
	}

	private void createFolder(IFolder folder) throws CoreException
	{
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder)
		{
			createFolder((IFolder)parent);
		}
		if (!folder.exists())
		{
			folder.create(false, true, new NullProgressMonitor());
		}
	}

	private void copyFile(final IProject project, final String src, final String dstDir) throws CoreException, IOException
	{
		final URL url = Platform.getBundle(BUNDLE_ID).getEntry(src);
		final String urlString = url.toString();
		InputStream stream = null;

		try
		{
			// Create target directory.
			final IFolder folder = project.getFolder(dstDir);
			createFolder(folder);

			// Copy file
			final String fileName = urlString.substring(urlString.lastIndexOf('/'));
			final String destinationFile = "/" + dstDir + fileName;
			final IFile file = project.getFile(destinationFile);

			stream = url.openStream();
			file.create(stream, true, new NullProgressMonitor());
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}
}
