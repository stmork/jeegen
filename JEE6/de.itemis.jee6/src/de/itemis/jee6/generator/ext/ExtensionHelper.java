package de.itemis.jee6.generator.ext;

public class ExtensionHelper
{
	public static String asType(final String jndiName)
	{
		final String [] elements = jndiName.split("/");
		final StringBuffer buffer = new StringBuffer(elements[0]);
		
		for (int i = 1;i < elements.length;i++)
		{
			final char [] array = elements[i].toCharArray();
			array[0] = Character.toUpperCase(array[0]);
					
			buffer.append(array);
		}
		return buffer.toString();
	}
}
