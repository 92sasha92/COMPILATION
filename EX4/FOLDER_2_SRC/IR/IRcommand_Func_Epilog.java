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

public class IRcommand_Func_Epilog extends IRcommand
{

    public String funcLabel;
    public int totalLocalVarSize;

	public IRcommand_Func_Epilog(String funcLabel, int totalLocalVarSize)
	{
		this.funcLabel = funcLabel;
		this.totalLocalVarSize = totalLocalVarSize;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	    sir_MIPS_a_lot.getInstance().fileWriter.print("\t# --- function epilog ---\n");

            if (funcLabel.equals("main")) {
		// sir_MIPS_a_lot.getInstance().fileWriter.format("\taddi $fp,$sp,%d\n",totalLocalVarSize + 9 * 4); // 8 for registers and 1 for saved ra
            }
            else {
                if (totalLocalVarSize > 0) {
                    sir_MIPS_a_lot.getInstance().moveSpEpilog(totalLocalVarSize);
                } 
                Temp currentTemp;
                for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
                    currentTemp = Temp_FACTORY.getTemp(i);
		    sir_MIPS_a_lot.getInstance().pop(currentTemp);
                }
		sir_MIPS_a_lot.getInstance().pop("$ra");
		sir_MIPS_a_lot.getInstance().pop("$fp");
		sir_MIPS_a_lot.getInstance().jr("$ra");
                // sir_MIPS_a_lot.getInstance().moveFpEpilog();
                
            }
	    sir_MIPS_a_lot.getInstance().fileWriter.print("\t# --- function epilog ending ---\n");
	}
}
