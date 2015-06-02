package org.jeegen.jee6.ui.wizard;

import org.eclipse.xtext.ui.wizard.DefaultProjectInfo;

public class DslProjectInfo extends DefaultProjectInfo {

	private boolean isMavenProject;

	public String getApplicationName()
	{
		return getProjectName().replaceAll(" ", "").toLowerCase();
	}
	
	public String getBasePackage()
	{
		return "org.example.jee6";
	}

	public boolean isMavenProject() {
		return isMavenProject;
	}

	public void setMavenProject(boolean isMavenProject) {
		this.isMavenProject = isMavenProject;
	}
}
