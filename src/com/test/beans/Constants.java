package com.test.beans;

public class Constants
{
	public static final String TABLE = "TABLE";
	public static final String VIEW = "VIEW";
	public static final String PACKAGE = "PACKAGE";
	public static final String SYNONYM = "SYNONYM";
	public static final String PACKAGE_BODY = "PACKAGE_BODY";
	public static final String STANDARD = "STANDARD";
	
	public static int getLevel(final String strType)
	{
		int intLevel = 0;
		
		switch(strType)
		{
			case TABLE : intLevel = 1; break;
			case VIEW : intLevel = 2; break;
			case PACKAGE : intLevel = 3; break;
			case SYNONYM : intLevel = 4; break;
			case PACKAGE_BODY : intLevel = 5; break;
		}
				
		return intLevel;
	}
}
