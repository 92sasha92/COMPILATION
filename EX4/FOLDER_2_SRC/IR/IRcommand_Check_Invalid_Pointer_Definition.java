/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/;
import Temp.*;
import MIPS.*;

public class IRcommand_Check_Invalid_Pointer_Definition extends IRcommand
{

    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {

        boolean shouldEnumerate = false;
        String invalidPointer = getFreshLabel("Check_Invalid_Pointer_Definition", shouldEnumerate);
        String validPointer = getFreshLabel("Valid_Pointer", shouldEnumerate);
        Temp varAddress = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().label(invalidPointer);
            
        sir_MIPS_a_lot.getInstance().push("$ra");

        Temp currentTemp;
        for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().push(currentTemp);
        }

        sir_MIPS_a_lot.getInstance().load(varAddress,"$sp",9 * 4);
        sir_MIPS_a_lot.getInstance().bne(varAddress, validPointer);

        Temp t = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().load_string(t,"Invalid Pointer Dereference\n");
        sir_MIPS_a_lot.getInstance().print_string(t);
        sir_MIPS_a_lot.getInstance().exitProgram();
            
        sir_MIPS_a_lot.getInstance().label(validPointer);

        for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().pop(currentTemp);
        }


        sir_MIPS_a_lot.getInstance().pop("$ra");

        sir_MIPS_a_lot.getInstance().addi("$sp","$sp",4);

        sir_MIPS_a_lot.getInstance().jr("$ra");



    }
}
