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
        Temp srcTemp;
        Temp dstTemp;
	
	public IRcommand_Addi(String dst, String src, int imm)
	{
            this.dst = dst;
            this.src = src;
	    this.imm = imm;
	}
    	public IRcommand_Addi(Temp dst, Temp src, int imm)
	{
            this.dstTemp = dst;
            this.srcTemp = src;
	    this.imm = imm;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
            if (src != null && dst != null) {
		sir_MIPS_a_lot.getInstance().addi(dst, src, imm);
            }
            else if (srcTemp != null && dstTemp != null) {
		sir_MIPS_a_lot.getInstance().addi(dstTemp, srcTemp, imm);
            }
	}
}
