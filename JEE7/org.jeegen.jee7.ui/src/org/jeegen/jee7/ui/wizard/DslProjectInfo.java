package org.jeegen.jee7.ui.wizard;

import org.eclipse.xtext.ui.wizard.DefaultProjectInfo;

public class DslProjectInfo extends DefaultProjectInfo
{
	private final String DEFAULT_PACKAGE      = "org.example.jee7";
	private final String DEFAULT_PROJECT_NAME = "example";

	private boolean isMavenProject;
	private String  basePackage = DEFAULT_PACKAGE;
	private String  simpleName  = DEFAULT_PROJECT_NAME;

	public String getApplicationName()
	{
		return getSimpleName().replaceAll(" ", "").toLowerCase();
	}

	@Override
	public void setProjectName(final String projectName)
	{
		super.setProjectName(projectName);
		final String [] parts = projectName.split("\\.");

		simpleName = parts[parts.length - 1];

		if (parts.length > 1)
		{
			basePackage = projectName.substring(0, projectName.length() - simpleName.length() - 1);
		}
	}

	public String getSimpleName()
	{
		return simpleName;
	}

	public String getBasePackage()
	{
		return basePackage;
	}

	public boolean isMavenProject()
	{
		return isMavenProject;
	}

	public void setMavenProject(boolean isMavenProject)
	{
		this.isMavenProject = isMavenProject;
	}
}
