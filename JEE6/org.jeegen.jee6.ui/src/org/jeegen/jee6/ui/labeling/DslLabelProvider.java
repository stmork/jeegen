/*
* generated by Xtext
*/
package org.jeegen.jee6.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

import org.jeegen.jee6.jee6.Attribute;
import org.jeegen.jee6.jee6.Blob;
import org.jeegen.jee6.jee6.BooleanProp;
import org.jeegen.jee6.jee6.Clob;
import org.jeegen.jee6.jee6.Entity;
import org.jeegen.jee6.jee6.EntityRef;
import org.jeegen.jee6.jee6.IntegerProp;
import org.jeegen.jee6.jee6.Locale;
import org.jeegen.jee6.jee6.Mail;
import org.jeegen.jee6.jee6.Model;
import org.jeegen.jee6.jee6.Option;
import org.jeegen.jee6.jee6.Options;
import org.jeegen.jee6.jee6.Persistence;
import org.jeegen.jee6.jee6.Property;
import org.jeegen.jee6.jee6.Reference;
import org.jeegen.jee6.jee6.Security;
import org.jeegen.jee6.jee6.Text;
import org.jeegen.jee6.jee6.TextProp;


/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class DslLabelProvider extends DefaultEObjectLabelProvider {

	@Inject
	public DslLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	String text(Model model)
	{
		return model.getName();
	}
	
	String image(Model model)
	{
		return "jee6_16.gif";
	}
	
	String text(Persistence p)
	{
		return p.getJndi() + " -> " + p.getName();
	}

	String image(Persistence p)
	{
		return "outline/datasource.gif";
	}

	String image (Security s)
	{
		return "outline/security.gif";
	}

	String text(Locale l)
	{
		return l.getLanguage();
	}

	String image(Locale l)
	{
		return "outline/locale.gif";
	}

	String text(Mail m)
	{
		return m.getJndi();
	}

	String image(Mail m)
	{
		return "outline/mail.gif";
	}

	String image(Option o)
	{
		return "outline/option.gif";
	}

	String text(Entity e)
	{
		return e.getName().concat(" : ").concat(e.eClass().getName());
	}

	String image(Entity e)
	{
		return "outline/entity.gif";
	}

	String image(Options o)
	{
		return o.getKeys().size() == 0 ? "outline/entity_options.gif" : "outline/entity_values.gif";
	}

	String text (Attribute a)
	{
		return a.getName() + " : " + a.eClass().getName();
	}

	String image(Attribute a)
	{
		return "outline/element.gif";
	}

	String image(org.jeegen.jee6.jee6.Boolean a)
	{
		return "outline/element_bool.gif";
	}

	String image(Text a)
	{
		return "outline/element_text.gif";
	}

	String image(org.jeegen.jee6.jee6.Integer a)
	{
		return "outline/element_integer.gif";
	}

	String image(org.jeegen.jee6.jee6.Number a)
	{
		return "outline/element_number.gif";
	}

	String image(Blob a)
	{
		return "outline/element_blob.gif";
	}

	String image(Clob a)
	{
		return "outline/element_clob.gif";
	}

	String text (EntityRef er)
	{
		return er.getName() + (er.isMany() ? "[] : " : " : ") + er.getType().eClass().getName();
	}

	String text (Reference r)
	{
		return r.getName() + " : " + r. eClass().getName();
	}

	String image(Reference r)
	{
		return "outline/entity.gif";
	}

	String text(Property p)
	{
		final StringBuffer buffer = new StringBuffer(p.getJndi());
		
		if (p.getOriginal() != null)
		{
			buffer.append(" -> ").append(p.getOriginal());
		}
		return buffer.toString();
	}

	String text(TextProp tp)
	{
		final StringBuffer buffer = new StringBuffer(tp.getJndi());
		
		if (tp.getValue() != null)
		{
			buffer.append(" = ").append(tp.getValue());
		}
		if (tp.getOriginal() != null)
		{
			buffer.append(" -> ").append(tp.getOriginal());
		}
		return buffer.toString();
	}

	String text(IntegerProp ip)
	{
		final StringBuffer buffer = new StringBuffer(ip.getJndi());
		
		if (ip.getValue() != 0)
		{
			buffer.append(" = ").append(ip.getValue());
		}
		if (ip.getOriginal() != null)
		{
			buffer.append(" -> ").append(ip.getOriginal());
		}
		return buffer.toString();
	}

	String text(BooleanProp bp)
	{
		final StringBuffer buffer = new StringBuffer(bp.getJndi());
		
		if (bp.getValue() != null)
		{
			buffer.append(" = ").append(bp.getValue());
		}
		if (bp.getOriginal() != null)
		{
			buffer.append(" -> ").append(bp.getOriginal());
		}
		return buffer.toString();
	}

	String image(Property p)
	{
		return "outline/option.gif";
	}
}
