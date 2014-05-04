package de.itemis.jee7.ui.wizard;

import org.eclipse.xtext.ui.wizard.DefaultProjectInfo;

public class DslProjectInfo extends DefaultProjectInfo {
	public String getApplicationName()
	{
		return getProjectName().replaceAll(" ", "").toLowerCase();
	}
	
	public String getBasePackage()
	{
		return "org.example.jee7";
	}
}
