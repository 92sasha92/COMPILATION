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

public class IRcommand_Binop_Illegal_Div_Definition extends IRcommand
{

    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {

        boolean shouldEnumerate = false;
        String illegalDiv = getFreshLabel("Div_By_Zero", shouldEnumerate);
        sir_MIPS_a_lot.getInstance().label(illegalDiv);
            
        sir_MIPS_a_lot.getInstance().push("$ra");

        Temp currentTemp;
        for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().push(currentTemp);
        }

        Temp t = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().load_string(t,"Division By Zero\n");
        sir_MIPS_a_lot.getInstance().print_string(t);
        sir_MIPS_a_lot.getInstance().exitProgram();
            for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
                currentTemp = Temp_FACTORY.getTemp(i);
                sir_MIPS_a_lot.getInstance().pop(currentTemp);
            }


        sir_MIPS_a_lot.getInstance().jr("$ra");



    }
}
