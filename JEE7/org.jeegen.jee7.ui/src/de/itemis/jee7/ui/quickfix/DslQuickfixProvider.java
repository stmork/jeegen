
package de.itemis.jee7.ui.quickfix;

import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.itemis.jee7.formatting.EntityModification;
import de.itemis.jee7.formatting.Jee7LinkingDiagnosticMessageProvider;
import de.itemis.jee7.jee7.Jee7Package;

public class DslQuickfixProvider extends DefaultQuickfixProvider
{
	@Inject
	private Injector injector;

	@Fix(Jee7LinkingDiagnosticMessageProvider.ENTITY_MISSING)
	public void fixForMissingEntity(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		quickFixEntity(issue, acceptor);
	}

	@Fix(Jee7LinkingDiagnosticMessageProvider.OPTION_MISSING)
	public void fixForMissingOption(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		quickFixOptions(issue, acceptor);
	}

	@Fix(Jee7LinkingDiagnosticMessageProvider.HISTORY_MISSING)
	public void fixForMissingHistory(final Issue issue, IssueResolutionAcceptor acceptor)
	{
		quickFixEntity(issue, acceptor);
	}

	private void quickFixEntity(final Issue issue, final IssueResolutionAcceptor acceptor)
	{
		final String data [] = issue.getData();
		final ISemanticModification modification = new EntityModification(Jee7Package.Literals.TABLE, data[0]);

		accept(issue, acceptor, "Create entity " + data[0], modification);
	}

	private void quickFixOptions(final Issue issue, final IssueResolutionAcceptor acceptor)
	{
		final String data [] = issue.getData();
		final ISemanticModification editableOptionModification    = new EntityModification(true,  data[0]);
		final ISemanticModification enumerationOptionModification = new EntityModification(false, data[0]);

		injector.injectMembers(editableOptionModification);
		injector.injectMembers(enumerationOptionModification);

		accept(issue, acceptor, "Create editable options", editableOptionModification);
		accept(issue, acceptor, "Create enumeration options", enumerationOptionModification);
	}

	private void accept(final Issue issue, final IssueResolutionAcceptor acceptor, final String label, final ISemanticModification modification)
	{
		acceptor.accept(issue, label, label, null, modification);
	}
}
