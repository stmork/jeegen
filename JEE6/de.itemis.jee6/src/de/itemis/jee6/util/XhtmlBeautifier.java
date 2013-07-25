package de.itemis.jee6.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.xpand2.output.FileHandle;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import de.morknet.oaw.tools.postprocessor.XmlBeautifier;

public class XhtmlBeautifier extends XmlBeautifier
{
	private final static Log log = LogFactory.getLog(XhtmlBeautifier.class);
	private final static String INDENT = "    ";

	@Override
	public void beforeWriteAndClose(FileHandle handle)
	{
		final String filename = handle.getAbsolutePath(); 

		if (isXmlFile(filename))
		{
			try
			{
				final String unformattedXml = handle.getBuffer().toString().trim(); 

				handle.setBuffer(removeEmptyLines(prettyPrintXml(unformattedXml, filename.endsWith("/layout.xhtml"))));
			}
			catch (Exception e)
			{
				log.error(e);
			}
		}
	}

	protected String prettyPrintXml(
			final String  unformattedXml,
			final boolean isLayout) throws IOException
	{
		Writer out    = null;
		String result = null;

		try
		{		
			// Create an "identity" transformer - copies input to output
	        final Document document = parseXmlFile(unformattedXml);
			final TransformerFactory factory = TransformerFactory.newInstance();
			factory.setAttribute("indent-number", new Integer(INDENT.length()));
			final Transformer t = factory.newTransformer();
	
			t.setOutputProperty(OutputKeys.METHOD,               "html");
			t.setOutputProperty(OutputKeys.INDENT,               "yes");
			t.setOutputProperty(OutputKeys.ENCODING,             "UTF-8");
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.VERSION,              "5.0");
			t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(INDENT.length())); 
			if (isLayout)
			{
				t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "html");
			}
	
			// Serialize DOM tree
			out = new StringWriter();
			t.transform(new DOMSource(document), new StreamResult(out));
			result = out.toString();
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			result = unformattedXml;
		}
		finally
		{
			if (out != null)
			{
				out.close();
			}
		}
		return result;
	}

	protected String prettyPrintXmlSax(
			final String  unformattedXml,
			final boolean isLayout) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException
	{
        final Document                  document = parseXmlFile(unformattedXml);
		final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		final DOMImplementationLS       impl     = (DOMImplementationLS)registry.getDOMImplementation("LS");
		final LSSerializer              writer   = impl.createLSSerializer();

		if (isLayout)
		{
			final DocumentType docType = document.getImplementation().createDocumentType("html", "", "");
			document.appendChild(docType);
		}

		writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); 
		writer.getDomConfig().setParameter("xml-declaration", Boolean.FALSE); 
		final String result = writer.writeToString(document);

		if (isLayout)
		{
			log.debug("\n" + unformattedXml);
			log.debug("\n" + result);
			log.debug(unformattedXml.equals(result));
		}
		return result;
	}
	
	protected String prettyPrintXmlNop(final String  unformattedXml, final boolean isLayout)
	{
		return unformattedXml;
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
