/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

import MIPS.*;

public class IRcommand_addLabel extends IRcommand
{
	String label;
	public IRcommand_addLabel(String label)
	{
		this.label = label;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//String label_end = getFreshLabel(label);
        sir_MIPS_a_lot.getInstance().label(label);

	}
}
