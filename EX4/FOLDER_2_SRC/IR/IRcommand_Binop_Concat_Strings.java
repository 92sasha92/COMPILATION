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

public class IRcommand_Binop_Concat_Strings extends IRcommand
{
    public Temp t1;
    public Temp t2;
    public Temp dst;

    public IRcommand_Binop_Concat_Strings(Temp dst,Temp t1,Temp t2)
    {
        this.dst = dst;
        this.t1 = t1;
        this.t2 = t2;
    }
    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {

        boolean shouldEnumerate = false;
        String funcName = getFreshLabel("Concat_Strings", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().push(t1);
        sir_MIPS_a_lot.getInstance().push(t2);
        sir_MIPS_a_lot.getInstance().jal(funcName);
        sir_MIPS_a_lot.getInstance().mixedMove(dst, "$v0");
    }
}
