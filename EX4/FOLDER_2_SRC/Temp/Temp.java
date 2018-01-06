/***********/
/* PACKAGE */
/***********/
package Temp;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class Temp
{
	private int serial=0;
	
	public Temp(int serial)
	{
		this.serial = serial;
	}
	public int getSerialNumber()
	{
		return serial;
	}
	public String toString() {
		return "Temp_" + serial;
	}

}
