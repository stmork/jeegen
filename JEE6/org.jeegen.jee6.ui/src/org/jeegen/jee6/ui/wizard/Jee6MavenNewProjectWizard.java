package org.jeegen.jee6.ui.wizard;

import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.xtext.ui.wizard.IProjectCreator;
import org.eclipse.xtext.ui.wizard.IProjectInfo;
import org.eclipse.xtext.ui.wizard.XtextNewProjectWizard;

import com.google.inject.Inject;

public class Jee6MavenNewProjectWizard extends XtextNewProjectWizard {

	private WizardNewProjectCreationPage mainPage;
	private String title = "Maven based JEE6 Generator Project";

	@Inject
	public Jee6MavenNewProjectWizard(IProjectCreator projectCreator) {
		super(projectCreator);
		setWindowTitle(title);
	}

	/**
	 * Use this method to add pages to the wizard.
	 * The one-time generated version of this class will add a default new project page to the wizard.
	 */
	public void addPages() {
		mainPage = new WizardNewProjectCreationPage("basicNewProjectPage");
		mainPage.setTitle(title);
		mainPage.setDescription("Create a new JEE6 Generator project.");
		addPage(mainPage);
	}

	/**
	 * Use this method to read the project settings from the wizard pages and feed them into the project info class.
	 */
	@Override
	protected IProjectInfo getProjectInfo() {
		org.jeegen.jee6.ui.wizard.DslProjectInfo projectInfo = new org.jeegen.jee6.ui.wizard.DslProjectInfo();
		projectInfo.setProjectName(mainPage.getProjectName());
		projectInfo.setMavenProject(true);
		return projectInfo;
	}
}
