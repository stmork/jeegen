package org.jeegen.faces.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.faces.beans.StatefulBean;
import org.jeegen.faces.dao.InfoDaoBean;
import org.jeegen.faces.dao.SessionDaoBean;
import org.jeegen.faces.entities.Address;
import org.jeegen.faces.entities.AddressOption.AddressOptionEnum;
import org.jeegen.faces.entities.EntityEntry;
import org.jeegen.faces.entities.Startup;
import org.jeegen.faces.entities.UserInfo;
import org.jeegen.jee6.util.LogUtil;

@WebService(serviceName="FacesWS")
public class FacesWebService {
	private final static Log log = LogFactory.getLog(FacesWebService.class);

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
	
	@WebMethod
	public void test()
	{
		log.debug(">test()");
		Startup startup = new Startup();
		
		info.addStartup(startup);
		startup = info.updateStartup(startup);
		log.debug(" " + startup);

		for (int i = 0; i < 10; i++)
		{
			EntityEntry entry = new EntityEntry();
			entry.setComment("Persist automatically - " + i);
			entry.setAddressOption(info.findAddressOption(AddressOptionEnum.ADDRESS_HOME));
			entry = info.addToStartup(startup, entry);
			log.debug(" ====== " + entry);
		}

		for (EntityEntry entry : info.getEntityEntryList(startup))
		{
			log.debug(" ====== " + entry);
			info.deleteFromStartup(entry);
		}

		info.deleteStartup(startup);
		log.debug("<test()");
	}
}
