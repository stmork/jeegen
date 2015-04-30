/*
 * $Id$
 */
package org.jeegen.faces.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.util.HttpResponseCodes;
import org.jeegen.faces.beans.TimerSingleton;

@WebServlet("/proxy/image")
public class ProxyImageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private TimerSingleton singleton;

	@Override
	protected void service(
			HttpServletRequest  request,
			HttpServletResponse response) throws ServletException, IOException
	{
		log.log(Level.FINER, ">service()");
		try
		{
			final String parameter = request.getParameter("cam");

			if (parameter != null)
			{
				singleton.push(response, request.getRequestURI(), parameter);
			}
			else
			{
				response.sendError(HttpResponseCodes.SC_NOT_FOUND);
			}
		}
		catch(FileNotFoundException ioe)
		{
			response.sendError(HttpResponseCodes.SC_NOT_FOUND);
		}
		log.log(Level.FINER, "<service()");
	}
}
