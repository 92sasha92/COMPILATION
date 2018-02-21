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

public class IRcommand_Load_Address extends IRcommand
{
	Temp dst;
        String name;
	
	public IRcommand_Load_Address(Temp dst,String name)
	{
		this.dst = dst;
                this.name = name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().load_address(dst,name);
	}
}
