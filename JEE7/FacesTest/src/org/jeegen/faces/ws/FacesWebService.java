package org.jeegen.faces.ws;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jeegen.faces.beans.StatefulBean;
import org.jeegen.faces.dao.InfoDaoBean;
import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.faces.entities.Address;
import org.jeegen.faces.entities.AddressOption.AddressOptionEnum;
import org.jeegen.faces.entities.EntityEntry;
import org.jeegen.faces.entities.Startup;
import org.jeegen.faces.entities.UserInfo;
import org.jeegen.jee7.util.LogUtil;

@WebService(serviceName="FacesWS")
public class FacesWebService {
	private final static Logger log = Logger.getLogger(FacesWebService.class.getName());

	@EJB
	private SessionDaoBean session;

	@EJB
	private StatefulBean stateful;

	@EJB
	private InfoDaoBean info;

	@WebMethod
	public void ping()
	{
		log.info(">ping()");
		session.ping();
		stateful.ping();
		log.info("<ping()");
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
		log.log(Level.FINE, ">getUsers()");
		final List<UserInfo> users = session.getAllUsers();
		
		for (UserInfo user : users)
		{
			log.log(Level.FINE, " " + user);
			for(Address address : user.getAddresses())
			{
				address.setUserInfo(null);
			}
		}
		log.log(Level.FINE, "<getUsers() = ...");
		
		return users;
	}
	
	@WebMethod
	public void test()
	{
		log.log(Level.FINE, ">test()");
		Startup startup = new Startup();

		info.addStartup(startup);
		startup = info.updateStartup(startup);
		log.log(Level.FINE, " " + startup);

		for (int i = 0; i < 10; i++)
		{
			EntityEntry entry = new EntityEntry();
			entry.setComment("Persist automatically - " + i);
			entry.setAddressOption(info.findAddressOption(AddressOptionEnum.ADDRESS_HOME));
			entry = info.addToStartup(startup, entry);
			log.log(Level.FINE, " ====== " + entry);
		}

		for (EntityEntry entry : info.getEntityEntryList(startup))
		{
			log.log(Level.FINE, " ====== " + entry);
			info.deleteFromStartup(entry);
		}

		info.deleteStartup(startup);
		log.log(Level.FINE, "<test()");
	}
}
