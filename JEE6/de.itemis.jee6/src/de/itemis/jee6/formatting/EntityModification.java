package de.itemis.jee6.formatting;

import java.util.Locale;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.model.edit.ISemanticModification;

import com.google.inject.Inject;

import de.itemis.jee6.jee6.Entity;
import de.itemis.jee6.jee6.Jee6Factory;
import de.itemis.jee6.jee6.Jee6Package;
import de.itemis.jee6.jee6.Model;
import de.itemis.jee6.jee6.Options;
import de.itemis.jee6.jee6.Text;

public class EntityModification implements ISemanticModification
{
	private final EClass eClass;
	private final String name;
	private final boolean editable;
	
	@Inject
	private ILinker linker;

	public EntityModification(final EClass eClass, final String name)
	{
		this(eClass, name, true);
	}
	
	public EntityModification(boolean editable, final String name)
	{
		this(Jee6Package.Literals.OPTIONS, name, editable);
	}
	
	private EntityModification(final EClass eClass, final String name, final boolean editable)
	{
		this.eClass = eClass;
		this.name   = name;
		this.editable = editable;
	}

	@Override
	public void apply(final EObject element, final IModificationContext context)
			throws Exception
	{
		final Entity entity = (Entity)Jee6Factory.eINSTANCE.create(eClass);
		entity.setName(name);
		
		if (editable)
		{
			final Text text = Jee6Factory.eINSTANCE.createText();
			text.setName("example");
			entity.getTypes().add(text);
		}
		else
		{
			final Options options = (Options)entity;
			options.getKeys().add(name.toLowerCase(Locale.ROOT) + ".example");
		}
		final Entity parent = (Entity)element.eContainer();
		final Model model = (Model)parent.eContainer();
		final int index = model.getEntities().indexOf(parent);
		model.getEntities().add(index, entity);
		
		linker.linkModel(model, new ListBasedDiagnosticConsumer());
	}
}
