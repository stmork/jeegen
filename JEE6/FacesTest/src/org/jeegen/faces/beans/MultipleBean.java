package org.jeegen.faces.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeegen.faces.dao.AbstractDaoBean;
import org.jeegen.faces.entities.AddressOption;
import org.jeegen.jee6.util.LogUtil;
import org.jeegen.jee6.util.Profiler;

/**
 * This class tests the usage of XA data sources.
 */
@Stateless
@Interceptors(Profiler.class)
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
		Connection connection = null;
		Statement  stmt       = null;
		ResultSet  result     = null;

		try
		{
			for (AddressOption type : getAddressOptionList())
			{
				log.debug("    " + type);
			}

			connection = ds.getConnection();
			stmt = connection.createStatement();
			result = stmt.executeQuery("SELECT * FROM minimal");
					
			while (result.next())
			{
				log.debug("    " + result.getString("name"));
			}
		}
		finally
		{
			close(connection, stmt, result);
		}
	}
	
	/**
	 * This method uses two data sources at the same time so XA data sources for the entity
	 * manager is needed. This method uses a second pure data source injected into this bean.
	 * The pure data source does not need to be a XA data source. Additionally the entity
	 * manager mustn't be an extended persistence context so we can use a stateless EJB. 
	 *  
	 * @throws SQLException
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void checkSsl() throws SQLException
	{
		final Query query = em.createNativeQuery("SHOW STATUS WHERE Variable_name in ( 'ssl_cipher', 'ssl_cipher_list', 'ssl_version')");

		for(Object o : query.getResultList())
		{
			final Object [] result = (Object [])o;
			
			if (result.length >= 2)
			{
				LogUtil.info(log, "%s = %s", result[0], result[1]);
			}
		}
		
		Connection connection = null;
		Statement  stmt       = null;
		ResultSet  result     = null;

		try
		{
			for (AddressOption type : getAddressOptionList())
			{
				log.debug("    " + type);
			}

			connection = ds.getConnection();
			stmt = connection.createStatement();
			result = stmt.executeQuery("SHOW STATUS WHERE Variable_name LIKE '%ssl%'");
					
			while (result.next())
			{
				LogUtil.debug(log, "%-20.20s = %s", result.getString("Variable_name"), result.getString("Value"));
			}
		}
		finally
		{
			close(connection, stmt, result);
		}
	}
	
	private void close(final Connection connection, final Statement stmt, final ResultSet result) throws SQLException
	{
		try
		{
			if (result != null)
			{
				result.close();
			}
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			finally
			{
				if (connection != null)
				{
					connection.close();
				}
			}
		}
	}
}
