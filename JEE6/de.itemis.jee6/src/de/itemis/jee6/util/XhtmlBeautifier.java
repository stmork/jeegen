package de.itemis.jee6.util;

import java.io.IOException;
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
import org.xml.sax.SAXException;

import de.morknet.oaw.tools.postprocessor.XmlBeautifier;

public class XhtmlBeautifier extends XmlBeautifier
{
	private final static Log log = LogFactory.getLog(XhtmlBeautifier.class);

	@Override
	public void beforeWriteAndClose(FileHandle handle)
	{
		final String filename = handle.getAbsolutePath(); 

		if (isXmlFile(filename))
		{
			try
			{
				final String unformattedXml = handle.getBuffer().toString().trim(); 

				handle.setBuffer(prettyPrintXml(unformattedXml, filename.endsWith("/layout.xhtml")));
			}
			catch (Exception e)
			{
				log.error(e);
			}
		}
	}

	protected String prettyPrintXml(
			final String  unformattedXml,
			final boolean isLayout) throws ParserConfigurationException, SAXException, IOException
	{
        final Document document = parseXmlFile(unformattedXml);
		Writer out = null;
		String result = null;

		try
		{		
			// Create an "identity" transformer - copies input to output
			Transformer t = TransformerFactory.newInstance().newTransformer();
	
			// For "HTML" serialization, use
			t.setOutputProperty(OutputKeys.METHOD,               "xml");
			t.setOutputProperty(OutputKeys.INDENT,               "4");
			t.setOutputProperty(OutputKeys.ENCODING,             "UTF-8");
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.VERSION,              "5.0");
			if (isLayout)
			{
				t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "html");
//				t.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "html");
			}
	
			// Serialize DOM tree
			out = new StringWriter();
			t.transform(new DOMSource(document), new StreamResult(out));
			result = out.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
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
}
