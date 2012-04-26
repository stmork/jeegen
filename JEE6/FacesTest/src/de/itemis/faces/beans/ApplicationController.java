package de.itemis.faces.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.OptionsDaoBean;
import de.itemis.faces.entities.AddressOption;
import de.itemis.faces.entities.AddressOption.AddressOptionType;

@ManagedBean(eager=true)
@ApplicationScoped
public class ApplicationController implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final static Log log = LogFactory.getLog(ApplicationController.class);

	private final TimeZone timezone = TimeZone.getDefault();
	private final Locale locale = Locale.getDefault();

	@EJB
	private OptionsDaoBean dao;

	@PostConstruct
	public void init()
	{
		log.debug(">init()");
		log.info(TimeZone.getDefault());
		initOptions();
		log.debug("<init()");
	}

	public TimeZone getTimezone()
	{
		return timezone;
	}

	public Locale getLocale()
	{
		return locale;
	}

	private void initOptions()
	{
		log.debug("  >initOptions()");
		add(AddressOptionType.HOME, "Wohnadresse");
		add(AddressOptionType.WORK, "Arbeitsstätte");
		log.debug("  <initOptions()");
	}

	private final static Map<String, AddressOption> addressOptions = new LinkedHashMap<String, AddressOption>();

	private void add(final AddressOptionType type, final String description)
	{
		final AddressOption option = dao.ensure(type, description);

		addressOptions.put(Integer.toString(type.ordinal()), option);
		
	}

	public Collection<AddressOption> getAddressOptionList()
	{
		return addressOptions.values();
	}
	
	public static AddressOption getAddressOption(final String type)
	{
		return addressOptions.get(type);
	}
}
