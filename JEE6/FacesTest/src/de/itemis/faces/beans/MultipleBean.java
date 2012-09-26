package de.itemis.faces.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.itemis.faces.dao.AbstractDaoBean;
import de.itemis.faces.entities.AddressOption;

/**
 * This class tests the usage of XA data sources.
 */
@Stateless
public class MultipleBean extends AbstractDaoBean
{
	private final static Log log = LogFactory.getLog(MultipleBean.class);
	
	@Resource(mappedName="java:/jdbc/minimalDS")
	private DataSource ds; 

	/**
	 * This method uses two data sources at the same time so XA data sources for the entity
	 * manager is needed. This method uses a second pure data source injected into this bean.
	 * The pure data source does not need to be a XA data source. Additionally the entity
	 * manager mustn't be an extended persistence context so we can use a stateless EJB. 
	 *  
	 * @throws SQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void xaAccess() throws SQLException
	{
		log.debug(">test()");
		
		try
		{
			for (AddressOption type : getAddressOptionList())
			{
				log.debug("    " + type);
			}

			final Connection connection = ds.getConnection();
			final Statement stmt = connection.createStatement();
			final ResultSet result = stmt.executeQuery("SELECT * FROM minimal");
			
			while (result.next())
			{
				log.debug("    " + result.getString("name"));
			}
			connection.close();
		}
		finally
		{
			log.debug("<test()");
		}
	}
}
