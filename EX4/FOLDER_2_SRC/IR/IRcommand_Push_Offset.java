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

public class IRcommand_Push_Offset extends IRcommand
{
	Temp toPush;
        int offset;
	
	public IRcommand_Push_Offset(Temp t, int offset)
	{
		this.toPush = t;
                this.offset = offset;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		// sir_MIPS_a_lot.getInstance().push(toPush);
		sir_MIPS_a_lot.getInstance().push_offset(toPush, offset);
	}
}
