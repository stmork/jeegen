package de.itemis.faces.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.LogUtil;
import de.itemis.faces.dao.SessionDaoBean;
import de.itemis.faces.entities.Address;
import de.itemis.faces.entities.UserInfo;

@WebService(serviceName="FacesWS")
public class FacesWebService {
	private final static Log log = LogFactory.getLog(FacesWebService.class);

	@EJB
	private SessionDaoBean session;

	@WebMethod
	public void ping()
	{
		log.info("=ping()");
	}
	
	@WebMethod
	public void message(final String text)
	{
		log.info(text);
	}
	
	@WebMethod
	public UserInfo getUser(final String login)
	{
		LogUtil.debug(log, ">getUser(%s)", login);
		final UserInfo user = session.getUserInfo(login);
		for (Address address : user.getAddresses())
		{
			address.setUserInfo(null);
		}
		LogUtil.debug(log, "<getUser(%s) = %s", login, user);
		return user;
	}

	@WebMethod
	public List<UserInfo> getUsers()
	{
		log.debug(">getUsers()");
		final List<UserInfo> users = session.getAllUsers();
		
		for (UserInfo user : users)
		{
			log.debug(" " + user);
			for(Address address : user.getAddresses())
			{
				address.setUserInfo(null);
			}
		}
		log.debug("<getUsers() = ...");
		
		return users;
	}
}
