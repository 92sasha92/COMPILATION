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

public class IRcommand_Func_Prolog extends IRcommand
{

    public String funcLabel;
    public int totalLocalVarSize;

	public IRcommand_Func_Prolog(String funcLabel, int totalLocalVarSize)
	{
		this.funcLabel = funcLabel;
		this.totalLocalVarSize = totalLocalVarSize;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	    sir_MIPS_a_lot.getInstance().fileWriter.print("\t# --- function prolog ---\n");

            if (funcLabel.equals("main")) {
		sir_MIPS_a_lot.getInstance().fileWriter.format("\taddi $fp,$sp,%d\n",totalLocalVarSize + 9 * 4); // 8 for registers and 1 for saved ra
            }
            else {
		sir_MIPS_a_lot.getInstance().push("$fp");
		sir_MIPS_a_lot.getInstance().push("$ra");
                sir_MIPS_a_lot.getInstance().moveFpProlog();
                Temp currentTemp;
                for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
                    currentTemp = Temp_FACTORY.getTemp(i);
		    sir_MIPS_a_lot.getInstance().push(currentTemp);
                }
                if (totalLocalVarSize > 0) {
                    sir_MIPS_a_lot.getInstance().moveSpProlog(totalLocalVarSize);
                }
            }
	    sir_MIPS_a_lot.getInstance().fileWriter.print("\t# --- function prolog ending ---\n");
	}
}
