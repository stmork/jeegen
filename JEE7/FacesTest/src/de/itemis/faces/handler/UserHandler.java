/*
 * $Id$
 */
package de.itemis.faces.handler;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.io.IOUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.itemis.faces.beans.SessionInfo;
import de.itemis.faces.dao.AdminDaoBean;
import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.UserInfo;
import de.itemis.faces.servlet.ImageServlet;
import de.itemis.jee7.util.Profiled;

@Named
@SessionScoped
@Profiled
@RolesAllowed(value="admin")
@Transactional(value = TxType.REQUIRED)
public class UserHandler extends AbstractHandler
{
	private static final long serialVersionUID = 1L;
//	private final static Logger log = Logger.getLogger(UserHandler.class.getName());

	@Inject
	private Logger log;

	@EJB
	private AdminDaoBean dao;

	@Inject
	private SessionInfo sessionInfo;

	@Inject
	private AdminHandler adminHandler;

	private Part image;

	public Part getImage()
	{
		return this.image;
	}
	
	public void setImage(final Part image)
	{
		this.image = image;
	}

	public String change()
	{
		log.fine(">change");
		UserInfo user = sessionInfo.getUser();

		try
		{
			if ((image != null) && (image.getSize() > 0))
			{
				user.setImage(IOUtils.toByteArray(image.getInputStream()));
			}
		}
		catch (IOException e)
		{
			log.fine(e.toString());
		}

		user = dao.updateUserInfo(user);
		sessionInfo.setUser(user);
		log.fine("<change");
		return "/index.xhtml";
	}

	private final static float relativeWidths [] = { 4.0f, 1.0f, 3.0f, 1.0f, 1.5f };
	public void export() throws IOException, DocumentException
	{
		final ExternalContext context = getExternalContext();
		final HttpServletResponse response = (HttpServletResponse) context.getResponse();
		final Document document = new Document();
		final Font font = new Font(FontFamily.HELVETICA, 8.0f);
		final Paragraph paragraph = new Paragraph("Liste", font);
		final PdfPTable table = new PdfPTable(relativeWidths);

		table.setWidthPercentage(100f);
		for (Address address : getAddressList())
		{
			PdfPCell cell;

			cell = new PdfPCell(new Phrase(address.getStreet(), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(address.getPlz(), font));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(address.getLocation(), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(getYesNo(address.isActive()), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(getMessage(address.getAddressOption().getBundleKey()), font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);
		}

		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", "inline; filename=example.pdf");

		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		document.add(paragraph);
		document.add(table);
		document.close();

		response.flushBuffer();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void image() throws IOException
	{
		final ExternalContext context = getExternalContext();
		final HttpServletResponse response = (HttpServletResponse) context.getResponse();
		
		response.setHeader("Content-Disposition", "inline; filename=image.gif");
		ImageServlet.renderImage(response,  "gif");
		FacesContext.getCurrentInstance().responseComplete();
	}

	public List<Address> getAddressList()
	{
		return dao.getAddressList(sessionInfo.getUser());
	}

	public String addAddress()
	{
		log.log(Level.FINE, ">addAddress");
		UserInfo user = sessionInfo.getUser();
		Address address = dao.addToUserInfo(user, new Address());
		adminHandler.setAddress(address);
		log.log(Level.FINE, "<addAddress");
		return "address.xhtml";
	}
	
	public String editAddress(final Address address)
	{
		log.log(Level.FINE, ">editAddress");
		adminHandler.setAddress(address);
		log.log(Level.FINE, "<editAddress");
		return "address.xhtml";
	}

	public String removeAddress(final Address address)
	{
		log.log(Level.FINE, ">removeAddress");
		UserInfo user = dao.deleteFromUserInfo(address);
		sessionInfo.setUser(user);
		log.log(Level.FINE, "<removeAddress");
		return "index.xhtml";
	}
}
