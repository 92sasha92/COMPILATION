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

public class IRcommand_mixedMove extends IRcommand
{
	int imm;
        String src;
        String dst;
        Temp srcTemp;
        Temp dstTemp;
	
	public IRcommand_mixedMove(String dst, Temp src)
	{
            this.dst = dst;
            this.srcTemp = src;
	}
        public IRcommand_mixedMove(Temp dstTemp, String src)
	{
            this.dstTemp = dstTemp;
            this.src = src;
	}
    	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
            if (src != null && dstTemp != null) {
		sir_MIPS_a_lot.getInstance().mixedMove(dstTemp, src);
            }
            else if (srcTemp != null && dst != null) {
		sir_MIPS_a_lot.getInstance().mixedMove(dst, srcTemp);
            }
            else {
                System.out.println("ERROR!!!");
            }
	}
}
