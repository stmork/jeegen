package de.itemis.jee6.minimal.handler;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import de.itemis.jee6.minimal.dao.MinimalDao;
import de.itemis.jee6.minimal.entity.MinimalEntity;

@ManagedBean
@SessionScoped
public class MinimalHandler {
	@EJB
	MinimalDao dao;

	@PostConstruct
	public void init()
	{
		MinimalEntity entity = new MinimalEntity();
		entity.setName("Georg");
		dao.init(entity);
	}

	public String getExampleText()
	{
		StringBuffer buffer = new StringBuffer();

		for (MinimalEntity entity : dao.getEntities())
		{
			buffer.append(entity.getName()).append("\n");
		}
		return buffer.toString();
	}
}
