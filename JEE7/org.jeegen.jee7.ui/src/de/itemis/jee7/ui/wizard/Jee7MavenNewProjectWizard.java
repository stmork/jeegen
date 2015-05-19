package de.itemis.jee7.ui.wizard;

import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.xtext.ui.wizard.IProjectCreator;
import org.eclipse.xtext.ui.wizard.IProjectInfo;
import org.eclipse.xtext.ui.wizard.XtextNewProjectWizard;

import com.google.inject.Inject;

public class Jee7MavenNewProjectWizard extends XtextNewProjectWizard {

	private WizardNewProjectCreationPage mainPage;
	private String title = "Maven based JEE7 Generator Project";

	@Inject
	public Jee7MavenNewProjectWizard(IProjectCreator projectCreator) {
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
		mainPage.setDescription("Create a new JEE7 Generator project.");
		addPage(mainPage);
	}

	/**
	 * Use this method to read the project settings from the wizard pages and feed them into the project info class.
	 */
	@Override
	protected IProjectInfo getProjectInfo() {
		de.itemis.jee7.ui.wizard.DslProjectInfo projectInfo = new de.itemis.jee7.ui.wizard.DslProjectInfo();
		projectInfo.setProjectName(mainPage.getProjectName());
		projectInfo.setMavenProject(true);
		return projectInfo;
	}
}
