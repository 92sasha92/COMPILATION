package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;


public class IRcommand_Create_New_Array_Definition extends IRcommand{



    public void MIPSme() {

        boolean shouldEnumerate = false;
        String createNewArray = getFreshLabel("Create_New_Array_Definition", shouldEnumerate);
        String saveArrayLength = getFreshLabel("Save_Array_Length_Definition", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().label(createNewArray);

        Temp varAddress = Temp_FACTORY.getInstance().getFreshTemp();
        Temp arraySize = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().push("$ra");

        Temp currentTemp;
        for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().push(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().load(arraySize,"$sp",9 * 4);
        // sir_MIPS_a_lot.getInstance().load(varAddress,"$sp",10 * 4);

        //------------
        Temp regWithFour = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().li(regWithFour,4);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().mul(arraySize,arraySize,regWithFour);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().addi("$zero","$zero",0);
        sir_MIPS_a_lot.getInstance().addi(arraySize,arraySize,4);
        Temp arrayOffset = arraySize;


        // allocates memory and moves the address to mallocAddress
        boolean shouldAddNullByte = false;
        Temp tempWithAddress = sir_MIPS_a_lot.getInstance().malloc(arrayOffset,shouldAddNullByte);
        sir_MIPS_a_lot.getInstance().mixedMove("$v0",tempWithAddress); // maybe we should move this to the end

        // initialize memory to zero

        String zeroMemLoop = getFreshLabel("zeroMemLoop");

        // create the stopping condition address of the loop (base address + size)
        Temp stoppingAddressTemp = Temp_FACTORY.getInstance().getFreshTemp();
        sir_MIPS_a_lot.getInstance().add(stoppingAddressTemp,tempWithAddress, arrayOffset);

        // create loop label
        sir_MIPS_a_lot.getInstance().label(zeroMemLoop);

        sir_MIPS_a_lot.getInstance().store(tempWithAddress, "$zero", 0);
        sir_MIPS_a_lot.getInstance().addi(tempWithAddress, tempWithAddress, 4);
        sir_MIPS_a_lot.getInstance().addBranch("bne", tempWithAddress, stoppingAddressTemp, zeroMemLoop);


        // save length of array
        sir_MIPS_a_lot.getInstance().push("$v0");
        sir_MIPS_a_lot.getInstance().push(arraySize);
        sir_MIPS_a_lot.getInstance().jal(saveArrayLength);



        for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().pop(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().pop("$ra");

        sir_MIPS_a_lot.getInstance().addi("$sp","$sp",4);

        sir_MIPS_a_lot.getInstance().jr("$ra");




    }
}
