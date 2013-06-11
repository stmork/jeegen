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

import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.UserInfo;

@WebServlet("/avatar")
public class AvatarServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Log  log    = LogFactory.getLog(AvatarServlet.class);

	@EJB
	private SessionDaoBean dao;

	@Override
	protected void service(
			HttpServletRequest  request,
    		HttpServletResponse response) throws ServletException, IOException
	{
		log.trace(">service()");
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
		log.trace("<service()");
	}
}
