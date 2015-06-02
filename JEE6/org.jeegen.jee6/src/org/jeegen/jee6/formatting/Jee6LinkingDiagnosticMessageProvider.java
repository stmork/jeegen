package org.jeegen.jee6.formatting;


import org.eclipse.xtext.diagnostics.DiagnosticMessage;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.impl.LinkingDiagnosticMessageProvider;

import org.jeegen.jee6.jee6.Jee6Package;

public class Jee6LinkingDiagnosticMessageProvider extends LinkingDiagnosticMessageProvider
{
	public final static String ENTITY_MISSING  = "entity missing";
	public final static String OPTION_MISSING  = "option missing";
	public final static String HISTORY_MISSING = "history missing";

	@Override
	public DiagnosticMessage getUnresolvedProxyMessage(ILinkingDiagnosticContext context)
	{
		String error = null;
		DiagnosticMessage message = null;

		if (context.getReference() == Jee6Package.Literals.ENTITY_REF__TYPE)
		{
			error = ENTITY_MISSING;
		}
		else if (context.getReference() == Jee6Package.Literals.OPTION_REF__TYPE)
		{
			error = OPTION_MISSING;
		}
		else if (context.getReference() == Jee6Package.Literals.HISTORY__TYPE)
		{
			error = HISTORY_MISSING;
		}

		if (error != null)
		{
			final String name = context.getLinkText();

			message = new DiagnosticMessage("Entity " + name + " is missing!",
					Severity.ERROR, error, name);
		}
		else
		{
			message = super.getUnresolvedProxyMessage(context);
		}

		return message;
	}
}
