package de.itemis.faces.handler;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import de.itemis.jee7.util.LogUtil;

@ManagedBean
@RequestScoped
public class LoginHandler extends AbstractHandler
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LoginHandler.class.getName());
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

		log.log(Level.FINE, ">doLogin()");
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
			log.log(Level.FINE, ">doLogin() = " + outcome);
		}
		return outcome;
	}
}