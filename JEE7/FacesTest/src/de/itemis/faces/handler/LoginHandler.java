package de.itemis.faces.handler;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.jee7.util.LogUtil;

@ManagedBean
@RequestScoped
public class LoginHandler extends AbstractHandler
{
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(LoginHandler.class);
	private String login;
	private String password;

	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String doLogin()
	{
		String outcome = "auth.xhtml?faces-redirect=true";

		log.debug(">doLogin()");
		HttpServletRequest request = (HttpServletRequest)getExternalContext().getRequest();
		try
		{
			LogUtil.debug(log, " login=%s password=%s", getLogin(), "********");
			request.login(getLogin(), getPassword());
			outcome = "/index.xhtml"; 
		}
		catch(ServletException se)
		{
			LogUtil.error(log, "Error loging in user %s: %s", getLogin(), se.getLocalizedMessage());
		}
		finally
		{
			setPassword(null);
			log.debug(">doLogin() = " + outcome);
		}
		return outcome;
	}
}
