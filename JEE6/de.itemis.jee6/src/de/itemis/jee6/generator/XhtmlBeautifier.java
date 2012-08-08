package de.itemis.jee6.generator;

import org.eclipse.xpand2.output.FileHandle;
import org.eclipse.xpand2.output.PostProcessor;

public class XhtmlBeautifier implements PostProcessor
{

	@Override
	public void beforeWriteAndClose(FileHandle impl)
	{
		if (impl.getAbsolutePath().endsWith(".xhtml"))
		{
			final CharSequence buffer = impl.getBuffer(); 

			impl.setBuffer(buffer.toString().trim() + "\n");
		}
	}

	@Override
	public void afterClose(FileHandle impl)
	{
		// Intentionally do nothing!
	}
}
