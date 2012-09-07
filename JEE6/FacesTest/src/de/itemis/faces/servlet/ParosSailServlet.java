/*
 * $Id$
 */
package de.itemis.faces.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.beans.TimerSingleton;

@WebServlet("/proxy/ParosSail")
public class ParosSailServlet extends HttpServlet
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
		log.debug(">service()");
		response.setHeader("Refresh", "5; URL=/faces/proxy/ParosSail" ); 
		response.setContentType(singleton.getParosSailMimeType());
		ServletOutputStream os = response.getOutputStream();
		os.write(singleton.getParosSailImage());
		os.flush();
		
		log.debug("<service()");
	}
}
