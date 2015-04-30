package org.jeegen.faces.client;

import java.util.List;

import javax.xml.ws.WebServiceRef;

import org.jeegen.faces.ws.Address;
import org.jeegen.faces.ws.FacesWS;
import org.jeegen.faces.ws.FacesWebService;
import org.jeegen.faces.ws.UserInfo;

public class FacesClient
{
	@WebServiceRef(wsdlLocation="http://localhost:8080/faces?wsdl")
	static FacesWebService service;
	
	private FacesClient()
	{
		FacesWS ws = new FacesWS();
		service = ws.getFacesWebServicePort();
	}

	public static void main(String [] args)
	{
		FacesClient client = new FacesClient();
		client.execute();
		client.test();
	}
	
	private void execute()
	{
		service.ping();
		service.message("Test Test");
		
		System.out.println(service.getUser("smork").getName());
		List<UserInfo> users = service.getUsers();
		
		for (UserInfo user : users)
		{
			System.out.println(user.getName());
			for(Address address : user.getAddresses())
			{
				System.out.println("  " + address.getStreet());
			}
		}
	}

	private void test()
	{
		service.ping();
		service.test();
	}
}
