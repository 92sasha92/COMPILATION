package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;


public class IRcommand_CalculateAdress_Definition extends IRcommand{



    public void MIPSme() {

        boolean shouldEnumerate = false;
        String calculateAdress = getFreshLabel("Calculate_Address", shouldEnumerate);
        String Access_Violation = getFreshLabel("Access_Violation", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().label(calculateAdress);

        Temp varAddress = Temp_FACTORY.getInstance().getFreshTemp();
        Temp arrayOffset = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().push("$ra");

        Temp currentTemp;
        for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().push(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().load(arrayOffset,"$sp",9 * 4);
        sir_MIPS_a_lot.getInstance().load(varAddress,"$sp",10 * 4);


        // check for access violations
        Temp storedArrayLength = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().load(storedArrayLength,varAddress);
        sir_MIPS_a_lot.getInstance().addBranch("bge", arrayOffset, storedArrayLength, Access_Violation);

        //Temp regWithFour = sir_MIPS_a_lot.getInstance().initializeRegToZero();
        Temp regWithFour = Temp_FACTORY.getInstance().getFreshTemp();
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        sir_MIPS_a_lot.getInstance().li(regWithFour, 4);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        sir_MIPS_a_lot.getInstance().mul(arrayOffset, arrayOffset, regWithFour);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        sir_MIPS_a_lot.getInstance().add(varAddress, varAddress, arrayOffset);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        sir_MIPS_a_lot.getInstance().addi(varAddress, varAddress, 4); // skip the first element (length of array)
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);
        // sir_MIPS_a_lot.getInstance().addi("$zero", "$zero", 0);


        sir_MIPS_a_lot.getInstance().mixedMove("$v0",varAddress);


        for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().pop(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().pop("$ra");

        sir_MIPS_a_lot.getInstance().addi("$sp","$sp",8);

        sir_MIPS_a_lot.getInstance().jr("$ra");

        sir_MIPS_a_lot.getInstance().label(Access_Violation);

        Temp t = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().load_string(t,"Access Violation\n");
        sir_MIPS_a_lot.getInstance().print_string(t);
        sir_MIPS_a_lot.getInstance().exitProgram();
        for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().pop(currentTemp);
        }


        sir_MIPS_a_lot.getInstance().jr("$ra");





    }
}
