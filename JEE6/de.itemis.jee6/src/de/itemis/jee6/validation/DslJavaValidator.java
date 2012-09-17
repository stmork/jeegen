package de.itemis.jee6.validation;

import java.util.List;

import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;

import de.itemis.jee6.jee6.Entity;
import de.itemis.jee6.jee6.History;
import de.itemis.jee6.jee6.Jee6Package;
import de.itemis.jee6.jee6.Model;
import de.itemis.jee6.jee6.Process;
import de.itemis.jee6.jee6.Security;
import de.itemis.jee6.jee6.Text;
import de.itemis.jee6.jee6.Timestamp;
 

public class DslJavaValidator extends AbstractDslJavaValidator
{
	@Check
	public void checkContextPath(Model model)
	{
		if (!model.getPath().startsWith("/"))
		{
			error("Context path does not start wit '/'!", Jee6Package.Literals.MODEL__PATH);
		}
	}
	
	@Check
	public void checkHistory(Entity entity)
	{
		List<History> histories = EcoreUtil2.typeSelect(entity.getTypes(), History.class);

		if (histories.size() > 1)
		{
			for(History history : histories)
			{
				error ("Es darf nur max. eine Historie vorhanden sein!", history, Jee6Package.Literals.HISTORY__TYPE, 0);
			}
		}
	}

	@Check
	public void checkText(Text text)
	{
		guard(text.isId());
		if (text.isId())
		{
			final Entity entity = (Entity)text.eContainer();
			int idCount = 0;
	
			for (Text t : EcoreUtil2.typeSelect(entity.getTypes(), Text.class))
			{
				if (t.isId())
				{
					idCount++;
				}
			}
	
			if (idCount > 1)
			{
				error("Es darf nur max. ein Attribut eine ID sein!",  Jee6Package.Literals.TEXT__ID);
			}
		}
	}

	@Check
	public void checkTimestampAuto(Timestamp timestamp)
	{
		int prePersistCount = 0;

		if (timestamp.isAuto())
		{
			final Entity entity = (Entity)timestamp.eContainer();
	
			for (Timestamp t : EcoreUtil2.typeSelect(entity.getTypes(), Timestamp.class))
			{
				if (t.isAuto())
				{
					prePersistCount++;
				}
			}
		}

		if (prePersistCount > 1)
		{
			warning("Es macht nur eine automatische Datumsmarkierung Sinn!", Jee6Package.Literals.TIMESTAMP__AUTO);
		}
	}
	

	@Check
	public void checkTimestampUpdate(Timestamp timestamp)
	{
		int preUpdateCount = 0;

		if (timestamp.isUpdate())
		{
			final Entity entity = (Entity)timestamp.eContainer();
	
			for (Timestamp t : EcoreUtil2.typeSelect(entity.getTypes(), Timestamp.class))
			{
				if (t.isUpdate())
				{
					preUpdateCount++;
				}
			}
		}

		if (preUpdateCount > 1)
		{
			warning("Es macht nur eine automatische Datumsaktualisierung Sinn!", Jee6Package.Literals.TIMESTAMP__UPDATE);
		}
	}

	@Check
	public void checkProcessRoles(Process process)
	{
		final Model model    = (Model)process.eContainer();
		final int   secCount = EcoreUtil2.typeSelect(model.getOptions(), Security.class).size();
		final int   rolesCount = process.getRoles().size();

		if ((secCount == 0) &&  (rolesCount > 0))
		{
			warning("Rollen machen nur Sinn, wenn JAAS konfiguriert wurde!", Jee6Package.Literals.PROCESS__ROLES);
		}
	}
}
