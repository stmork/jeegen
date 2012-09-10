/*
 * $Id$
 */
package de.itemis.faces.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.beans.TimerSingleton;

@WebServlet("/proxy/image")
public class ProxyImageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Log  log    = LogFactory.getLog(ImageServlet.class);
	
	@EJB
	private TimerSingleton singleton;

	@Override
	protected void service(
			HttpServletRequest  request,
    		HttpServletResponse response) throws ServletException, IOException
	{
		log.trace(">service()");
		try
		{
			final String parameter = request.getParameter("cam");

			if (parameter != null)
			{
				singleton.push(response, request.getRequestURI(), parameter);
			}
			else
			{
				response.sendError(404);
			}
		}
		catch(FileNotFoundException ioe)
		{
			response.sendError(404);
		}
		log.trace("<service()");
	}
}
