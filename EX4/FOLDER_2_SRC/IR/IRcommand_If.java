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
import TEMP.*;
import MIPS.*;

public class IRcommand_If extends IRcommand
{
	TEMP t;
	String label;
	public IRcommand_If(TEMP t, String label)
	{
		this.t = t;
		this.label = getFreshLabel(label);
	}
	public String getLabel(){
		return label;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().beq(t,label);

	}
}
