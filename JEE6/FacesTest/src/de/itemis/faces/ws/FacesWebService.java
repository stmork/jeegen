package de.itemis.faces.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@WebService(serviceName="FacesWS")
public class FacesWebService {
	private final static Log log = LogFactory.getLog(FacesWebService.class);

	@WebMethod
	public void ping()
	{
		log.info("=ping()");
	}
}
