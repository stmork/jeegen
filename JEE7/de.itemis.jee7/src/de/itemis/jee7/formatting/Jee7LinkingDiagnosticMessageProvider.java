package de.itemis.jee7.formatting;


import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;

import de.itemis.jee7.jee7.Jee7Package;

public class Jee7LinkingDiagnosticMessageProvider implements ILinkingDiagnosticMessageProvider
{
	public final static String ENTITY_MISSING  = "entity missing";
	public final static String OPTION_MISSING  = "option missing";
	public final static String HISTORY_MISSING = "history missing";

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context)
	{
		String error = null;
		DiagnosticMessage message = null;

		if (context.getReference() == Jee7Package.Literals.ENTITY_REF__TYPE)
		{
			error = ENTITY_MISSING;
		}
		else if (context.getReference() == Jee7Package.Literals.OPTION_REF__TYPE)
		{
			error = OPTION_MISSING;
		}
		else if (context.getReference() == Jee7Package.Literals.HISTORY__TYPE)
		{
			error = HISTORY_MISSING;
		}

		if (error != null)
		{
			final String name = context.getLinkText();

			message = new DiagnosticMessage("Entity " + name + " is missing!",
					Severity.ERROR, error, name);
		}
		return message;
	}
}
