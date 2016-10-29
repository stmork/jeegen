package org.jeegen.jee7.tests;

import org.jeegen.jee7.ui.wizard.DslProjectInfo;
import org.junit.Assert;
import org.junit.Test;

public class InfoTest
{
	@Test
	public void testJavaNaming()
	{
		final DslProjectInfo info = new DslProjectInfo();

		info.setProjectName("de.morknet.jee7.test");

		Assert.assertEquals("test", info.getSimpleName());
		Assert.assertEquals("test", info.getApplicationName());
		Assert.assertEquals("de.morknet.jee7", info.getBasePackage());
		Assert.assertEquals("de.morknet.jee7.test", info.getProjectName());
	}

	@Test
	public void testSinglePart()
	{
		final DslProjectInfo info = new DslProjectInfo();

		info.setProjectName("test");

		Assert.assertEquals("test", info.getSimpleName());
		Assert.assertEquals("test", info.getApplicationName());
		Assert.assertEquals("org.example.jee7", info.getBasePackage());
		Assert.assertEquals("test", info.getProjectName());
	}

	@Test
	public void testClearTest()
	{
		final DslProjectInfo info = new DslProjectInfo();

		info.setProjectName("Test App");

		Assert.assertEquals("Test App", info.getSimpleName());
		Assert.assertEquals("testapp", info.getApplicationName());
		Assert.assertEquals("org.example.jee7", info.getBasePackage());
		Assert.assertEquals("Test App", info.getProjectName());
	}
}
