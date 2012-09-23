/*
 * $Id$
 */
package de.itemis.faces.handler;

import java.io.IOException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

@ManagedBean
@SessionScoped
@RolesAllowed(value="admin")
public class UserHandler extends AbstractHandler
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(UserHandler.class);

	@EJB
	private AdminDaoBean dao;

	@ManagedProperty(value="#{sessionInfo}")
	private SessionInfo sessionInfo;

	@ManagedProperty(value="#{adminHandler}")
	private AdminHandler adminHandler;

	public SessionInfo getSessionInfo() {
		return sessionInfo;
	}
	public void setSessionInfo(SessionInfo sessionInfo) {
		this.sessionInfo = sessionInfo;
	}

	
	public AdminHandler getAdminHandler() {
		return adminHandler;
	}
	public void setAdminHandler(AdminHandler adminHandler) {
		this.adminHandler = adminHandler;
	}

	public String change()
	{
		log.debug(">change");
		UserInfo user = getSessionInfo().getUser();
		user = dao.updateUserInfo(user);
		getSessionInfo().setUser(user);
		log.debug("<change");
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
		return dao.getAddressList(getSessionInfo().getUser());
	}
	
	public String addAddress()
	{
		log.debug(">addAddress");
		UserInfo user = getSessionInfo().getUser();
		Address address = dao.addToUserInfo(user, new Address());
		getAdminHandler().setAddress(address);
		log.debug("<addAddress");
		return "address.xhtml";
	}
	
	public String editAddress(final Address address)
	{
		log.debug(">editAddress");
		getAdminHandler().setAddress(address);
		log.debug("<editAddress");
		return "address.xhtml";
	}
	
	public String removeAddress(final Address address)
	{
		log.debug(">removeAddress");
		UserInfo user = dao.deleteFromUserInfo(address);
		getSessionInfo().setUser(user);
		log.debug("<removeAddress");
		return "index.xhtml";
	}
}
