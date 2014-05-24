/*
 * $Id$
 */
package de.itemis.faces.servlet;

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

import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.UserInfo;

/**
 * This servlet simply shows the logged in users avatar.
 */
@WebServlet("/avatar")
public class AvatarServlet extends HttpServlet
{
	private static final long   serialVersionUID = 1L;

	@Inject
	private Logger log;

	@EJB
	private SessionDaoBean dao;

	@Override
	protected void service(
			HttpServletRequest  request,
			HttpServletResponse response) throws ServletException, IOException
	{
		log.log(Level.FINER, ">service()");
		try
		{
			UserInfo user = dao.getUserInfo(request.getRemoteUser());
			
			if (user != null)
			{
				response.setContentType("image/jpeg");
				response.getOutputStream().write(user.getImage());
				response.flushBuffer();
			}
		}
		catch(FileNotFoundException ioe)
		{
			response.sendError(404);
		}
		log.log(Level.FINER, "<service()");
	}
}
