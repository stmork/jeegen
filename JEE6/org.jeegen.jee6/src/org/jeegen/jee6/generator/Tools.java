package org.jeegen.jee6.generator;

import java.util.Calendar;

public class Tools {
	public static int year()
	{
		Calendar cal = Calendar.getInstance();
		
		return cal.get(Calendar.YEAR);
	}
}
