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
import Temp.*;
import MIPS.*;

public class IRcommand_If extends IRcommand
{
	Temp t;
	String label;
	public IRcommand_If(Temp t, String label)
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
