/*
 * generated by Xtext
 */
package de.itemis.jee6.formatting;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Pair;

import de.itemis.jee6.services.DslGrammarAccess;

/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it 
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an example
 */
public class DslFormatter extends AbstractDeclarativeFormatter {
	
	@Override
	protected void configureFormatting(FormattingConfig c)
	{
		final DslGrammarAccess f = (DslGrammarAccess)getGrammarAccess();

		c.setAutoLinewrap(128);
		for (Pair<Keyword, Keyword> pair : f.findKeywordPairs("{", "}"))
		{
			final Keyword first = pair.getFirst();
			final Keyword second = pair.getSecond();

			c.setIndentation(first, second);
//			c.setNoSpace().before(first);
			c.setLinewrap().before(first);
			c.setLinewrap().after(first);
			c.setLinewrap(2).after(second);
		}

		for (Keyword keyword : f.findKeywords(";"))
		{
			c.setNoSpace().before(keyword);
			c.setLinewrap().after(keyword);
		}

		for (Keyword keyword : f.findKeywords(","))
		{
			c.setNoSpace().before(keyword);
		}
	}
}
