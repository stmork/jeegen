package de.itemis.jee7.util;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

public class XhtmlBeautifier extends XmlBeautifier
{
	private final static Log log = LogFactory.getLog(XhtmlBeautifier.class);

	@Override
	protected String processXml(final String unformattedXml, final String filename) throws IOException
	{
		return removeEmptyLines(prettyPrintXmlNop(unformattedXml, filename));
	}

	@Override
	protected void setProperties(final Transformer transformer, final String filename)
	{
		transformer.setOutputProperty(OutputKeys.METHOD,               "html");
		transformer.setOutputProperty(OutputKeys.INDENT,               "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING,             "UTF-8");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.VERSION,              "5.0");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(INDENT.length())); 
		if (filename.endsWith("/layout.xhtml"))
		{
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "html");
		}
	}

	protected String prettyPrintXmlSax(
			final String unformattedXml,
			final String filename) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException
	{
		final Document                  document = parseXmlFile(unformattedXml);
		final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		final DOMImplementationLS       impl     = (DOMImplementationLS)registry.getDOMImplementation("LS");
		final LSSerializer              writer   = impl.createLSSerializer();

		if (filename.endsWith("/layout.xhtml"))
		{
			final DocumentType docType = document.getImplementation().createDocumentType("html", "", "");
			document.appendChild(docType);
		}

		writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); 
		writer.getDomConfig().setParameter("xml-declaration", Boolean.FALSE); 
		final String result = writer.writeToString(document);

		if (filename.endsWith("/layout.xhtml"))
		{
			log.debug("\n" + unformattedXml);
			log.debug("\n" + result);
			log.debug(unformattedXml.equals(result));
		}
		return result;
	}
	
	protected String prettyPrintXmlNop(final String  unformattedXml, final String filename)
	{
		return unformattedXml;
	}
}
