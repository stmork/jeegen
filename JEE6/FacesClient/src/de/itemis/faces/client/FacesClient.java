package de.itemis.faces.client;

import java.util.List;

import javax.xml.ws.WebServiceRef;

import de.itemis.faces.ws.Address;
import de.itemis.faces.ws.FacesWS;
import de.itemis.faces.ws.FacesWebService;
import de.itemis.faces.ws.UserInfo;

public class FacesClient
{
	@WebServiceRef(wsdlLocation="http://localhost:8080/calculator?wsdl")
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
}
