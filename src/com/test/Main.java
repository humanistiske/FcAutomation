package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.test.beans.Constants;
import com.test.beans.Unit;

public class Main 
{
	static List<Unit> arrAllUnits = new ArrayList<Unit>();
	static List<Unit> arrCompiledUnits = new ArrayList<Unit>();
	
	public static void main(String args[])
	{
		checkUnit("STPKS_STDCIF_MAIN");
	}
	
	public static void checkUnit(final String strUnitName)
	{
		String strDbUrl = "", strUserName = "", strUserPassword = "", strUnitType = "";	
		
		try
		{
			strDbUrl = "jdbc:oracle:thin:@(description=(address=(host=localhost)(protocol=tcp)(port=1521))(connect_data=(service_name=DB19C)(server=DEDICATED)))";
			strUserName = "FCUB146";
			strUserPassword = "FCUB146";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(strDbUrl, strUserName, strUserPassword); 
			String strQuery = "select * from all_dependencies where name like ?";
			PreparedStatement stmt = con.prepareStatement(strQuery);
			stmt.setString(1, strUnitName);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Unit u1 = new Unit();
				
				String strSubUnitName = "";
				strUnitType = rs.getString("REFERENCED_TYPE");
				u1.setStrUnitType(strUnitType);
				strSubUnitName = rs.getString("REFERENCED_NAME");
				u1.setStrName(strSubUnitName);
				u1.setIntLevel(Constants.getLevel(strUnitType));
				
				if(!arrAllUnits.contains(u1))
				{
					arrAllUnits.add(u1); // Table, View, Package Declaration, Synonym, Package Body
					System.out.println(arrAllUnits);
					
					if(!(strSubUnitName.equalsIgnoreCase(Constants.STANDARD)) 
							&& !(rs.getString("REFERENCED_TYPE").equalsIgnoreCase(Constants.PACKAGE_BODY))
							&& !(strSubUnitName.equals(strUnitName))
							&& (strUnitType.equalsIgnoreCase(Constants.SYNONYM) || strUnitType.equalsIgnoreCase(Constants.PACKAGE)))
					{
						if(strUnitType.equalsIgnoreCase(Constants.SYNONYM))
						{
							checkSynonim(strSubUnitName);
						}
						else if(strUnitType.equalsIgnoreCase(Constants.PACKAGE) || strUnitType.equalsIgnoreCase(Constants.VIEW))
						{
							checkUnit(strSubUnitName);
						}
					}	
				}
			}
			if(rs != null){ rs.close(); } 
			con.close();
			
			System.out.println(arrAllUnits);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void checkSynonim(final String strSubUnitName)
	{
		String strDbUrl = "", strUserName = "", strUserPassword = "";	
		
		try
		{
			strDbUrl = "jdbc:oracle:thin:@(description=(address=(host=localhost)(protocol=tcp)(port=1521))(connect_data=(service_name=DB19C)(server=DEDICATED)))";
			strUserName = "FCUB146";
			strUserPassword = "FCUB146";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(strDbUrl, strUserName, strUserPassword);

			String strSynonymObjName = "";
			String strQuery = "select * from ALL_SYNONYMS where synonym_name=?";
			PreparedStatement stmt = con.prepareStatement(strQuery);
			stmt.setString(1, strSubUnitName);
			ResultSet rs1 = stmt.executeQuery();
			if(rs1.next())
			{
				strSynonymObjName = rs1.getString("TABLE_NAME");	
			}
			if(rs1 != null){ rs1.close(); } 			
			
			strQuery = "select * from user_objects where object_name=?";
			stmt = con.prepareStatement(strQuery);
			stmt.setString(1, strSynonymObjName);
			rs1 = stmt.executeQuery();
			if(rs1.next())
			{
				Unit u2 = new Unit();
				u2.setStrName(strSubUnitName);
				u2.setStrUnitType(rs1.getString("OBJECT_TYPE"));
				u2.setIntLevel(Constants.getLevel(rs1.getString("OBJECT_TYPE")));
				
				if(u2.getStrUnitType().equals(Constants.PACKAGE) && !arrAllUnits.contains(u2))
				{
					checkUnit(strSubUnitName);		
				}
				else
				{
					arrAllUnits.add(u2); // Table, View, Package Declaration, Synonym, Package Body
					System.out.println(arrAllUnits);				
				}	
			}
			if(rs1 != null){ rs1.close(); }
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
