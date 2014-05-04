/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.io.Serializable;
import java.util.Collection;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This method implements a logging profiler which can be used as and EJB SessionBean interceptor.
 * 
 * @author sm
 *
 */
public class Profiler implements Serializable
{
	private final static long    serialVersionUID = 1L;
	private final static int     MAX_LENGTH = 100;
	private       static boolean verbose = LogFactory.getLog(Profiler.class).isDebugEnabled();

	/**
	 * The profiling method. It prints the call with its objects and the return of the method with
	 * the resulting object. Furthermore it counts the comsumed time of the called method. The method
	 * calling is done in a performant way. If debug logging is not enabled no logging and time stopping
	 * occur.
	 * 
	 * @param invocation The invocation context.
	 * @return The result object of the called method.
	 * @throws Exception The throwed exception of the called method.
	 */
	@AroundInvoke
	public Object profile (InvocationContext invocation) throws Exception
	{
		if (isVerbose())
		{
			final  Log    log    = LogFactory.getLog(invocation.getTarget().getClass());
			final  String method = invocation.getMethod().getName();
			Object        result = null;
	
			LogUtil.debug(log, "  >%s(%s)", method, listParams(invocation.getParameters()));

			final long start  = System.currentTimeMillis();
			try
			{
				result = invocation.proceed();
				return result;
			}
			catch(Exception e)
			{
				LogUtil.error(log, "!  %s() - %s", method, e.getMessage());
				throw e;
			}
			finally
			{
				final long end = System.currentTimeMillis();

				StringBuffer buffer = new StringBuffer();

				buffer.append("  <").append(method).append("(...)");
				if (result != null)
				{
					String resultText = result.toString();

					buffer.
						append(" = ").
						append(resultText.length() > MAX_LENGTH ? "..." : resultText);
					if (result instanceof Collection)
					{
						@SuppressWarnings("rawtypes")
						Collection collection = (Collection)result;
						
						buffer.append(LogUtil.printf(" (%d entries)", collection.size()));
					}
				}
				buffer.append(" took ").append(time(start,end)).append("s");
				log.debug(buffer);
			}
		}
		else
		{
			return invocation.proceed();
		}
	}

	/**
	 * This method builds a String from the method parameter objects.
	 * 
	 * @param params The method parameter object array.
	 * @return The formatted object {@link String}.
	 */
	private static String listParams(final Object [] params)
	{
		if (params == null)
		{
			return "";
		}
		else if (params.length == 0)
		{
			return "";
		}
		else if(params.length == 1)
		{
			return paramToString(params[0]);
		}
		else
		{
			final StringBuffer buffer = new StringBuffer();
			
			for (int i = 0;i < params.length;i++)
			{
				if (i > 0)
				{
					buffer.append(", ");
				}
				buffer.append(paramToString(params[i]));
				if (buffer.length() > MAX_LENGTH)
				{
					return "...";
				}
			}
			
			return buffer.toString();
		}
	}

	/**
	 * This method formats one method parameter object. It checks for null and will
	 * reduce a {@link String} longer than {@value #MAX_LENGTH} chars to a short {@link String}.
	 * 
	 * @param param The method parameter object.
	 * @return The resulting formatted {@link String}.
	 */
	private static String paramToString(Object param)
	{
		String result;
		
		if (param != null)
		{
			result = param.toString();
			if (result.length() > MAX_LENGTH)
			{
				result = "...";
			}
		}
		else
		{
			result = "<null>";
		}
		return result;
	}

	/**
	 * This method computes the comsumed time measured as milliseconds and formats a {@link String} for
	 * logging.
	 * 
	 * @param start The start point just before method calling.
	 * @param end The end point just after returning from method call.
	 * @return The formatted {@link String} of the comsumed time. 
	 */
	private static String time(final long start, final long end)
	{
		final Double diff = (end - start) / 1000.0;
		return diff.toString();
	}

	/**
	 * Returns the debug logging state.
	 * 
	 * @return Returns true if logging is enabled.
	 */
	public static boolean isVerbose()
	{
		return verbose;
	}

	/**
	 * Enables or disables debug logging for profiling purposes.
	 * 
	 * @param isVerbose If true debug logging is enabled. 
	 */
	public static void setVerbose(boolean isVerbose)
	{
		verbose = isVerbose;
	}
}
