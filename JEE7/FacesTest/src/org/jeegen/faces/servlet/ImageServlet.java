/*
 * $Id$
 */
package org.jeegen.faces.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.jee7.util.Profiled;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/example.gif")
@Profiled
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_TYPE = "gif";
	private static final Logger  log    = Logger.getLogger(ImageServlet.class.getName());

	@EJB
	private SessionDaoBean session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet()
	{
		super();
	}

	@Override
	public void init() throws ServletException
	{
		log.log(Level.FINE, ">init()");
		super.init();
		log("=init()");
		log.log(Level.FINE, "<init()");
	}
	
	@Override
	public void destroy()
	{
		log.log(Level.FINE, ">destroy()");
		log("=destroy()");
		super.destroy();
		log.log(Level.FINE, "<destroy()");
	}

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		log.log(Level.FINE, ">service()");
		session.ping();

		renderImage(response, IMAGE_TYPE);
		log.log(Level.FINE, "<service()");
	}

	private static final int  width  = 240;
	private static final int  height = 160;
	private static final Font MONOSPACED   = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	private static final Font SERIF        = new Font(Font.SERIF, Font.PLAIN,      36);
	private static final Font SANS_SERIF   = new Font(Font.SANS_SERIF, Font.PLAIN, 36);
	
	public static void renderImage(final HttpServletResponse response, final String type) throws IOException
	{
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D    gfx   = image.createGraphics();

		gfx.setBackground(Color.white);
		gfx.clearRect(0, 0, width, height);
		gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		gfx.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		log.log(Level.FINE, " -draw");

		gfx.setColor(Color.green);
		gfx.drawLine(0, 0, width, height);
		gfx.drawLine(0, height, width, 0);

		gfx.setColor(Color.gray);
		gfx.fillRoundRect(20, 18, 80, 60, 5, 5);
		gfx.setColor(Color.black);
		gfx.drawRoundRect(20, 18, 80, 60, 5, 5);

		gfx.setColor(Color.red);
		gfx.setFont(MONOSPACED);
		for (int y = 0;y < height; y += 16)
		{
			gfx.drawString(String.format("x: %3d", y), 0, y);
		}

		gfx.setColor(Color.magenta);
		gfx.setFont(SERIF);
		gfx.drawString(Font.SERIF, width >> 1, height-101);

		gfx.setColor(Color.blue);
		gfx.setFont(SANS_SERIF);
		gfx.drawString(Font.SANS_SERIF, 47, height-19);
		
		log.log(Level.FINE, " -write");

		response.setContentType("image/" + IMAGE_TYPE);
		ImageIO.write(image, type, response.getOutputStream());
		response.flushBuffer();
		gfx.dispose();
	}
}
