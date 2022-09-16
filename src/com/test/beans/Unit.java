package com.test.beans;

public class Unit
{
	private String strName;
	private String strUnitType;
	private String strDefinition;
	private boolean isCopied;
	private int intLevel;
	private int countRepeat;

	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrUnitType() {
		return strUnitType;
	}
	public void setStrUnitType(String strUnitType) {
		this.strUnitType = strUnitType;
	}
	public String getStrDefinition() {
		return strDefinition;
	}
	public void setStrDefinition(String strDefinition) {
		this.strDefinition = strDefinition;
	}
	public boolean isCopied() {
		return isCopied;
	}
	public void setCopied(boolean isCopied) {
		this.isCopied = isCopied;
	}
	public int getIntLevel() {
		return intLevel;
	}
	public void setIntLevel(int intLevel) {
		this.intLevel = intLevel;
	}
	public int getCountRepeat() {
		return countRepeat;
	}
	public void setCountRepeat(int countRepeat) {
		this.countRepeat = countRepeat;
	}
	
	@Override
	public String toString() {
		return "Unit [strName=" + strName + ", strUnitType=" + strUnitType + ", isCopied=" + isCopied +  ", level=" + intLevel + "]\n";
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((strName == null) ? 0 : strName.hashCode());
		result = prime * result + ((strUnitType == null) ? 0 : strUnitType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if (obj == null || !(obj instanceof Unit))
		{
			return false;
		}
		else
		{
			Unit other = (Unit) obj;

			if(other.getStrName().equalsIgnoreCase(this.getStrName())
					&& other.getStrUnitType().equalsIgnoreCase(this.getStrUnitType()))
			{
				return true;	
			}
			else
			{
				return false;
			}
		}
	}
}
