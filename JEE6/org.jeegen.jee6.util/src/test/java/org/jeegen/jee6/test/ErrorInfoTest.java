package org.jeegen.jee6.test;

import org.jeegen.jee6.util.ErrorInfo;
import org.junit.Assert;
import org.junit.Test;

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
