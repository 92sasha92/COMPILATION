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

public class IRcommand_Addi extends IRcommand
{
	int imm;
        String src;
        String dst;
	
	public IRcommand_Addi(String dst, String src, int imm)
	{
            this.dst = dst;
            this.src = src;
	    this.imm = imm;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().addi(dst, src, imm);
	}
}
