package de.itemis.faces;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil
{
	public final static String printf(final String fmt, final Object ... args)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter  pw = new PrintWriter(sw);
		
		pw.printf(fmt, args);
		final String result = sw.toString();
		
		pw.close();
		return result;
	}
}
