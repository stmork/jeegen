package de.itemis.faces.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.SessionDaoBean;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/ImageServlet")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log  log    = LogFactory.getLog(ImageServlet.class);
	private static final int  width  = 240;
	private static final int  height = 160;
	private static final Font font   = new Font("Monospaced", Font.PLAIN, 12);

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
    	log.debug(">init()");
    	super.init();
    	log("=init()");
    	log.debug("<init()");
    }
    
    @Override
    public void destroy()
    {
    	log.debug(">destroy()");
    	log("=destroy()");
    	super.destroy();
    	log.debug("<destroy()");
    }

	@Override
	protected void service(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException
	{
		log.debug(">service()");
    	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	Graphics      gfx   = image.getGraphics();

    	session.ping();

    	gfx.setColor(Color.white);
		gfx.fillRect(0, 0, width, height);
    	gfx.setFont(font);

    	log.debug(" -draw");
    	gfx.setColor(Color.green);
		gfx.drawLine(0, 0, width, height);
		gfx.drawLine(0, height, width, 0);

		gfx.setColor(Color.gray);
		gfx.fillRoundRect(20, 20, 80, 60, 5, 5);
		gfx.setColor(Color.black);
		gfx.drawRoundRect(20, 20, 80, 60, 5, 5);

		gfx.setColor(Color.red);
		for (int y = 0;y < height; y += 16)
		{
			gfx.drawString("x: " + y, 0, y);
		}

		log.debug(" -write");
    	OutputStream output = response.getOutputStream();
		response.setContentType("image/gif");
		ImageIO.write(image, "gif", output);
		output.flush();
		gfx.dispose();
		log.debug("<service()");
    }
}
