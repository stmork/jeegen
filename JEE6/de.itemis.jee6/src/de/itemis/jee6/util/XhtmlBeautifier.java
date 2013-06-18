package de.itemis.jee6.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import de.morknet.oaw.tools.postprocessor.XmlBeautifier;

public class XhtmlBeautifier extends XmlBeautifier
{
	@Override
	protected String formatXml(final Document document) throws IOException, ParserConfigurationException, SAXException
	{
		final OutputFormat format = new OutputFormat(document);
		final Writer out = new StringWriter();
		
		format.setMethod("html");
		format.setDoctype("http", "html");
		format.setEncoding("UTF-8");
		format.setLineWidth(120);
		format.setIndenting(true);
		format.setIndent(4);
		format.setOmitXMLDeclaration(true);
		format.setOmitDocumentType(false);
		final XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(document);
		
		return out.toString();
	}
}
