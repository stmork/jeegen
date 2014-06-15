package de.itemis.jee7.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Diese Klasse verschönert XML-Dateien. Die folgenden Dateiendungen werden erkannt:
 * <ul>
 * <li>.xml</li>
 * <li>.xsl</li>
 * <li>.xsd</li>
 * <li>.wsdd</li>
 * <li>.xsdl</li>
 * <li>.xhtml</li>
 * </ul>
 * @author sm
 *
 */
public class XmlBeautifier implements PostProcessor
{
	protected String[] fileExtensions = new String[] { ".xml", ".xsl", ".xsd", ".wsdd", ".wsdl", ".xhtml" };

	@Override
	public void beforeWriteAndClose(FileHandle handle)
	{
		final String filename = handle.getAbsolutePath(); 

		if (isXmlFile(filename))
		{
			try
			{
				final String unformattedXml = handle.getBuffer().toString().trim(); 

				handle.setBuffer(prettyPrintXml(unformattedXml));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void afterClose(FileHandle impl)
	{
		// This method intentionally left blank
	}

	protected boolean isXmlFile(final String absolutePath)
	{
		for (String ext : fileExtensions)
		{
			if (absolutePath.endsWith(ext.trim()))
			{
				return true;
			}
		}
		return false;
	}

	protected String prettyPrintXml(final String unformattedXml) throws ParserConfigurationException, SAXException, IOException
	{
		final Document document = parseXmlFile(unformattedXml);

		return formatXml(document);
	}

	protected Document parseXmlFile(final String in) throws ParserConfigurationException, SAXException, IOException
	{
		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		dbf.setValidating(false);
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

		final DocumentBuilder db = dbf.newDocumentBuilder();
		final InputSource is = new InputSource(new StringReader(in));

		return db.parse(is);
	}

	protected String formatXml(final Document document) throws IOException, ParserConfigurationException, SAXException
	{
		final OutputFormat format = new OutputFormat(document);
		final Writer out = new StringWriter();

		format.setLineWidth(80);
		format.setIndenting(true);
		format.setIndent(4);
		final XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.serialize(document);

		return out.toString();
	}
}
