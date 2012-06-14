package de.itemis.faces.dao;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import de.itemis.jee6.util.Profiler;

@Stateless
@Interceptors(Profiler.class)
public class InfoDaoBean extends AbstractInfoDaoBean {
}
