package de.itemis.faces.dao;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import de.itemis.faces.Profiler;

@Stateless
@Interceptors(Profiler.class)
public class AdminDaoBean extends AbstractAdminDaoBean
{
}
