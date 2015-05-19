package de.itemis.jee7.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
 */
public class XmlBeautifier implements PostProcessor
{
	private final static Log log = LogFactory.getLog(XmlBeautifier.class);

	protected String[] fileExtensions = new String[] { ".xml", ".xsl", ".xsd", ".wsdd", ".wsdl", ".xhtml" };
	protected final static String INDENT = "    ";

	@Override
	public void beforeWriteAndClose(FileHandle handle)
	{
		final String filename = handle.getAbsolutePath(); 

		if (isXmlFile(filename))
		{
			try
			{
				final String unformattedXml = handle.getBuffer().toString().trim(); 

				handle.setBuffer(processXml(unformattedXml, filename));
			}
			catch (Exception e)
			{
				log.error(e);
			}
		}
	}

	protected String processXml(final String unformattedXml, final String filename) throws IOException
	{
		return removeEmptyLines(prettyPrintXml(unformattedXml, filename));
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

	protected String prettyPrintXml(
			final String unformattedXml,
			final String filename) throws IOException
	{
		String result = null;

		try
		{		
			// Create an "identity" transformer - copies input to output
			final Document document = parseXmlFile(unformattedXml);
			final TransformerFactory factory = TransformerFactory.newInstance();
			factory.setAttribute("indent-number", Integer.valueOf(INDENT.length()));
			final Transformer transformer = factory.newTransformer();
			setProperties(transformer, filename);

			// Serialize DOM tree
			try(final Writer out = new StringWriter())
			{
				transformer.transform(new DOMSource(document), new StreamResult(out));
				result = out.toString();
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			result = unformattedXml;
		}
		return result.trim();
	}

	protected void setProperties(final Transformer transformer, final String filename)
	{
		transformer.setOutputProperty(OutputKeys.METHOD,   "xml");
		transformer.setOutputProperty(OutputKeys.INDENT,   "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(INDENT.length())); 
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

	protected String removeEmptyLines(final String input) throws IOException
	{
		final StringReader sr = new StringReader(input);
		BufferedReader reader = null;

		try
		{
			final StringBuffer buffer = new StringBuffer(input.length());
			String line;

			reader = new BufferedReader(sr);
			while ((line = reader.readLine()) != null)
			{
				if (line.trim().length() > 0)
				{
					while (line.startsWith(INDENT))
					{
						buffer.append("\t");
						line = line.substring(INDENT.length());
					}
					buffer.append(line).append("\n");
				}
			}
			return buffer.toString();
		}
		finally
		{
			if (reader != null)
			{
				reader.close();
			}
		}
	}
}
