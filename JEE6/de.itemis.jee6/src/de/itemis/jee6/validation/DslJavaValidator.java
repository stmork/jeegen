package de.itemis.jee6.validation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import de.itemis.jee6.jee6.Attribute;
import de.itemis.jee6.jee6.Entity;
import de.itemis.jee6.jee6.EntityRef;
import de.itemis.jee6.jee6.History;
import de.itemis.jee6.jee6.Jee6Package;
import de.itemis.jee6.jee6.Model;
import de.itemis.jee6.jee6.OptionRef;
import de.itemis.jee6.jee6.Persistence;
import de.itemis.jee6.jee6.Process;
import de.itemis.jee6.jee6.Reference;
import de.itemis.jee6.jee6.Security;
import de.itemis.jee6.jee6.Text;
import de.itemis.jee6.jee6.Timestamp;
 

public class DslJavaValidator extends AbstractDslJavaValidator
{
	private final static Set<String> invalidNames = new HashSet<String>();

	static
	{
		invalidNames.add("title");
		invalidNames.add("number");
		invalidNames.add("integer");
		invalidNames.add("order");
		invalidNames.add("create");
		invalidNames.add("update");
		invalidNames.add("insert");
		invalidNames.add("order");
	}

	@Check(CheckType.FAST)
	public void checkInvalidName(final Entity attr)
	{
		if (invalidNames.contains(attr.getName()))
		{
			error("Dieser Attributname darf nicht verwendet werden!", Jee6Package.Literals.ENTITY__NAME);
		}
	}

	@Check(CheckType.FAST)
	public void checkInvalidName(final Attribute attr)
	{
		if (invalidNames.contains(attr.getName()))
		{
			error("Dieser Attributname darf nicht verwendet werden!", Jee6Package.Literals.ATTRIBUTE__NAME);
		}
	}
	
	@Check(CheckType.FAST)
	public void checkContextPath(final Model model)
	{
		if (!model.getPath().startsWith("/"))
		{
			error("Der Context path beginnt nicht mit '/'!", Jee6Package.Literals.MODEL__PATH);
		}
	}
	
	@Check(CheckType.FAST)
	public void checkHistory(final Entity entity)
	{
		final List<History> histories = EcoreUtil2.typeSelect(entity.getTypes(), History.class);

		if (histories.size() > 1)
		{
			for(History history : histories)
			{
				error ("Es darf nur eine Historie vorhanden sein!", history, Jee6Package.Literals.HISTORY__TYPE, 0);
			}
		}
		else
		{
			final List<EntityRef> entities = EcoreUtil2.typeSelect(entity.getTypes(), EntityRef.class);

			for(History history : histories)
			{
				final Entity type = history.getType();

				for (EntityRef ref : entities)
				{
					if (ref.isMany())
					{
						if (ref.getType() == type)
						{
							error ("Es darf keine 1:n-Relation eines Typs auch als Type einer Historie vorhanden sein!",
									ref, Jee6Package.Literals.ENTITY_REF__TYPE, 0);
							error ("Es darf nur eine Historie vorhanden sein!",
									history, Jee6Package.Literals.HISTORY__TYPE, 0);
						}
					}
				}
			}			
		}
	}

	@Check(CheckType.FAST)
	public void checkManyEntities(final Entity entity)
	{
		final List<EntityRef> entities = EcoreUtil2.typeSelect(entity.getTypes(), EntityRef.class);

		for (EntityRef outerRef : entities)
		{
			if (outerRef.isMany())
			{
				final Entity type = outerRef.getType();
				int count = 0;

				for (EntityRef innerRef : entities)
				{
					if (innerRef.getType() == type)
					{
						count++;
					}
				}
				if (count > 1)
				{
					error ("Es darf nur eine 1:n-Relation eines Typs vorhanden sein!",
							outerRef, Jee6Package.Literals.ENTITY_REF__TYPE, 0);
				}
			}
		}
	}

	@Check(CheckType.FAST)
	public void checkText(final Text text)
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

	@Check(CheckType.FAST)
	public void checkTimestampAuto(final Timestamp timestamp)
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
	

	@Check(CheckType.FAST)
	public void checkTimestampUpdate(final Timestamp timestamp)
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

	@Check(CheckType.FAST)
	public void checkProcessRoles(final Process process)
	{
		final Model model    = (Model)process.eContainer();
		final int   secCount = EcoreUtil2.typeSelect(model.getOptions(), Security.class).size();
		final int   rolesCount = process.getRoles().size();

		if ((secCount == 0) &&  (rolesCount > 0))
		{
			warning("Rollen machen nur Sinn, wenn JAAS konfiguriert wurde!", Jee6Package.Literals.PROCESS__ROLES);
		}
	}
	
	@Check(CheckType.FAST)
	public void checkPersistenceUnits(final Model model)
	{
		final int persistenceUnitCount   = EcoreUtil2.typeSelect(model.getOptions(), Persistence.class).size();
		final int entityCount = model.getEntities().size();

		if ((persistenceUnitCount <= 0) &&  (entityCount > 0))
		{
			error("Wenn Entities definiert wurden, muss mindestens eine Persistence Unit vorliegen!",
					Jee6Package.Literals.MODEL__OPTIONS);
		}
	}
	
	@Check(CheckType.FAST)
	public void checkEntity(final Entity entity)
	{
		final Persistence persistence = getPersistenceUnit(entity);

		for (EntityRef reference : EcoreUtil2.typeSelect(entity.getTypes(), EntityRef.class))
		{
			checkEntity(entity, reference, reference.getType(), persistence, Jee6Package.Literals.ENTITY_REF__TYPE);
		}

		for (History reference : EcoreUtil2.typeSelect(entity.getTypes(), History.class))
		{
			checkEntity(entity, reference, reference.getType(), persistence, Jee6Package.Literals.HISTORY__TYPE);
		}

		for (OptionRef reference : EcoreUtil2.typeSelect(entity.getTypes(), OptionRef.class))
		{
			checkEntity(entity, reference, reference.getType(), persistence, Jee6Package.Literals.OPTION_REF__TYPE);
		}
	}
	
	private void checkEntity(final Entity parent, final Reference reference, final Entity entity, final Persistence persistence, final EReference literal)
	{
		if (getPersistenceUnit(entity) != persistence)
		{
			error(String.format(
					"Die Persistence Unit der Referenz %s muss der Persistence Unit der umgebenden Entity %s entsprechen!",
					entity.getName(), parent.getName()), reference, literal, 0);
		}
	}
	
	private Persistence getPersistenceUnit(final Entity entity)
	{
		Persistence persistence = entity.getPersistence();
		
		if (persistence == null)
		{
			Model model = (Model)EcoreUtil2.getRootContainer(entity);
			persistence = EcoreUtil2.typeSelect(model.getOptions(), Persistence.class).get(0);
		}
		return persistence;
	}
}
