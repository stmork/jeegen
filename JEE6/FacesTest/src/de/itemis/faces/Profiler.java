package de.itemis.faces;

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
	
			log.debug(LogUtil.printf(">%s(%s)", method, listParams(invocation.getParameters())));
			final long start  = System.currentTimeMillis();
			try
			{
				result = invocation.proceed();
				return result;
			}
			catch(Exception e)
			{
				log.error(LogUtil.printf("!%s() - %s", method, e.getMessage()));
				throw e;
			}
			finally
			{
				final long end = System.currentTimeMillis();

				StringBuffer buffer = new StringBuffer("<" + method + "(...)");

				if (result != null)
				{
					String resultText = result.toString();

					buffer.
						append(" = ").
						append(resultText.length() > MAX_LENGTH ? "..." : resultText);
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
		return param != null ? param.toString() : "<null>";
	}

	private static String time(final long start, final long end)
	{
		final Double diff = (end - start) / 1000.0;
		return diff.toString();
	}
}
