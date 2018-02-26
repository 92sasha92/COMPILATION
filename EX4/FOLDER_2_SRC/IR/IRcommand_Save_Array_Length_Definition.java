package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;


public class IRcommand_Save_Array_Length_Definition extends IRcommand{



    public void MIPSme() {

        boolean shouldEnumerate = false;
        String saveArrayLength = getFreshLabel("Save_Array_Length_Definition", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().label(saveArrayLength);

        Temp varAddress = Temp_FACTORY.getInstance().getFreshTemp();
        Temp mallocSize = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().push("$ra");

        Temp currentTemp;
        for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().push(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().load(mallocSize,"$sp",9 * 4);
        sir_MIPS_a_lot.getInstance().load(varAddress,"$sp",10 * 4);

        // restore the length of the array
        sir_MIPS_a_lot.getInstance().addi(mallocSize,mallocSize,-4);
        Temp regWithFour = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().li(regWithFour,4);

        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().div(mallocSize,mallocSize,regWithFour);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().store(varAddress,mallocSize);


        // sir_MIPS_a_lot.getInstance().mixedMove("$v0",varAddress);


        for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().pop(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().pop("$ra");

        sir_MIPS_a_lot.getInstance().addi("$sp","$sp",8);

        sir_MIPS_a_lot.getInstance().jr("$ra");

    }
}
