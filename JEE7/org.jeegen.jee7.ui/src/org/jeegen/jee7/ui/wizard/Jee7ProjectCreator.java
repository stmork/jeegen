package org.jeegen.jee7.ui.wizard;

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
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.xtext.ui.util.ProjectFactory;

import com.google.common.collect.ImmutableList;

public class Jee7ProjectCreator extends DslProjectCreator
{
	private final static String BUNDLE_ID        = "org.jeegen.jee7.ui";

	private final static String SRC_ROOT         = "src/main/java";
	private final static String SRC_GEN_ROOT     = "src/generated/java";
	private final static String RES_ROOT         = "src/main/resources";
	private final static String RES_GEN_ROOT     = "src/generated/resources";
	private final static String WEB_CONTENT_ROOT = "src/main/webapp";
	private final static String MODEL_ROOT       = "model";

	private final List<String> PLAIN_SRC_FOLDER_LIST = ImmutableList.of(SRC_ROOT, SRC_GEN_ROOT, RES_ROOT, RES_GEN_ROOT, WEB_CONTENT_ROOT, MODEL_ROOT);
	protected final List<String> SRC_FOLDER_LIST = ImmutableList.of("src","src/main","src/generated",SRC_ROOT, SRC_GEN_ROOT, RES_ROOT, RES_GEN_ROOT, WEB_CONTENT_ROOT, MODEL_ROOT);

	@Override
	protected List<String> getAllFolders()
	{
		return SRC_FOLDER_LIST;
	}

	@Override
	protected void enhanceProject(IProject project, IProgressMonitor monitor) throws CoreException
	{
		try
		{
			copyFile(project, "resources/jee-logo-120.png", RES_ROOT + "/img", monitor);
			copyFile(project, "resources/logo.png",         RES_ROOT + "/img", monitor);
			copyFile(project, "resources/favicon.png",      RES_ROOT + "/img", monitor);
			if(!getProjectInfo().isMavenProject())
			{
				copyFile(project, "resources/jee7-utils.jar", RES_ROOT + "/WEB-INF/lib", monitor);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			Status status = new Status(Status.ERROR, BUNDLE_ID, e.getMessage(), e);
			throw new CoreException(status);
		}

		final IJavaProject               javaProject  = JavaCore.create(project);
		final ArrayList<IClasspathEntry> newEntryList = new ArrayList<IClasspathEntry>();

		System.out.println(getProjectInfo().isMavenProject());
		if(!getProjectInfo().isMavenProject())
		{
			final IPath path = javaProject.getPath().append(RES_ROOT + "/WEB-INF/lib/jee7-utils.jar");
			final IClasspathEntry jeeUtils = JavaCore.newLibraryEntry(path, null, null);

			newEntryList.add(jeeUtils);
		}

		/*
		 * Set the JVM Version to 1.7
		 */
		final IClasspathEntry jvmVersion = JavaCore.newContainerEntry(
				new Path("org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7"));
		newEntryList.add(jvmVersion);

		/*
		 * Add the required plugins from MANIFEST.MF to the classpath
		 */
		final IClasspathEntry requiredPlugins = JavaCore.newContainerEntry(
				new Path("org.eclipse.pde.core.requiredPlugins"));
		newEntryList.add(requiredPlugins);

		for(String folder : PLAIN_SRC_FOLDER_LIST)
		{
			IClasspathEntry srcfolder = JavaCore.newSourceEntry(javaProject.getPath().append(folder), null);
			newEntryList.add(srcfolder);
		}

		IClasspathEntry [] newEntries = new IClasspathEntry[newEntryList.size()];
		newEntryList.toArray(newEntries);
		javaProject.setRawClasspath(newEntries, monitor);

		super.enhanceProject(project, monitor);
	}

	private void createFolder(final IFolder folder, final IProgressMonitor monitor) throws CoreException
	{
		final IContainer parent = folder.getParent();

		if (parent instanceof IFolder)
		{
			createFolder((IFolder)parent, monitor);
		}
		if (!folder.exists())
		{
			folder.create(false, true, monitor);
		}
	}

	private void copyFile(final IProject project, final String src, final String dstDir, final IProgressMonitor monitor) throws CoreException, IOException
	{
		final URL url = Platform.getBundle(BUNDLE_ID).getEntry(src);
		final String urlString = url.toString();
		InputStream stream = null;

		try
		{
			// Create target directory.
			final IFolder folder = project.getFolder(dstDir);
			createFolder(folder, monitor);

			// Copy file
			final String fileName = urlString.substring(urlString.lastIndexOf('/'));
			final String destinationFile = "/" + dstDir + fileName;
			final IFile file = project.getFile(destinationFile);

			stream = url.openStream();
			file.create(stream, true, monitor);
		}
		finally
		{
			if (stream != null)
			{
				stream.close();
			}
		}
	}

	@Override
	protected DslProjectInfo getProjectInfo() {
		return super.getProjectInfo();
	}

	@Override
	protected String getModelFolderName() {
		return super.getModelFolderName();
	}

	@Override
	protected ProjectFactory configureProjectFactory(ProjectFactory factory) {
		if(getProjectInfo().isMavenProject())
		{
			factory.addBuilderIds("org.eclipse.m2e.core.maven2Builder");
			factory.addProjectNatures("org.eclipse.m2e.core.maven2Nature");
		}

		return super.configureProjectFactory(factory);
	}
}
