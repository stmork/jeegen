/*
 * $Id$
 */
package de.itemis.jee6.util;

import java.util.Collection;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Profiler
{
	private final static boolean isDebug = LogFactory.getLog(Profiler.class).isDebugEnabled();
	private final static int MAX_LENGTH = 100;

	@AroundInvoke
	public Object profile (InvocationContext invocation) throws Exception
	{
		if (isDebug)
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

	private static String time(final long start, final long end)
	{
		final Double diff = (end - start) / 1000.0;
		return diff.toString();
	}
}
