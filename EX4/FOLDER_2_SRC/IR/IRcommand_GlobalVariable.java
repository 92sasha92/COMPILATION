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

public class IRcommand_GlobalVariable extends IRcommand
{
	Temp t;
	String name;
        boolean shouldCreate;
	
	public IRcommand_GlobalVariable(Temp t,String name, boolean shouldCreate)
	{
		this.t = t;
		this.name = name;
                this.shouldCreate = shouldCreate;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
            if (this.shouldCreate) {
		sir_MIPS_a_lot.getInstance().create_global(t,name);
            }
            else {
		sir_MIPS_a_lot.getInstance().load_global(t,name);
            }
	}
}
