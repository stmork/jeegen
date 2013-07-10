package de.itemis.jee6.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

public class ProxyTest {
	@Test
	public void proxyAuth() throws IOException
	{
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("vm04.itemis.de", 80));
		Authenticator authenticator = new Authenticator()
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("benutzer", "geheim".toCharArray());
			}
		};
		Authenticator.setDefault(authenticator);
		URL url = new URL("http://blogs.itemis.de");
		URLConnection connection = url.openConnection(proxy);
		InputStream stream = connection.getInputStream();
		
		stream.read();
	}
}
