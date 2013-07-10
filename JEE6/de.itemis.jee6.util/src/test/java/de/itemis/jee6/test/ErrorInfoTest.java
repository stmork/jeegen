package de.itemis.jee6.test;

import org.junit.Assert;
import org.junit.Test;

import de.itemis.jee6.util.ErrorInfo;

public class ErrorInfoTest {
	@Test
	public void info()
	{
		final String guiItem = "a";
		final String key = "b";

		final ErrorInfo info = new ErrorInfo(guiItem, key);
		Assert.assertEquals(key, info.getMessageKey());
		Assert.assertEquals(guiItem, info.getGuiItemId());
	}
}
