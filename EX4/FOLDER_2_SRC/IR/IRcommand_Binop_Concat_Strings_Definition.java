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

public class IRcommand_Binop_Concat_Strings_Definition extends IRcommand
{
    
    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {

        boolean shouldEnumerate = false;
        String funcName = getFreshLabel("Concat_Strings", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().label(funcName);


            Temp t1 = Temp_FACTORY.getInstance().getFreshTemp();
            Temp t2 = Temp_FACTORY.getInstance().getFreshTemp();
            Temp dst = Temp_FACTORY.getInstance().getFreshTemp();

            sir_MIPS_a_lot.getInstance().push("$ra");

            Temp currentTemp;
            for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
                currentTemp = Temp_FACTORY.getTemp(i);
                sir_MIPS_a_lot.getInstance().push(currentTemp);
            }
            sir_MIPS_a_lot.getInstance().load(t2,"$sp",9 * 4);
            sir_MIPS_a_lot.getInstance().load(t1,"$sp",10 * 4);

            Temp loopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
            Temp tempVal = sir_MIPS_a_lot.getInstance().initializeRegToZero();
            // Temp addressToRead = Temp_FACTORY.getInstance().getFreshTemp();
            // Temp addressToWrite = Temp_FACTORY.getInstance().getFreshTemp();
            String mallocBlock = getFreshLabel("mallocBlock");
            String firstLoop = getFreshLabel("copyFirstStrLoop");
            String firstLoopCounter = getFreshLabel("copyFirstStrLoopCounter");
            String secondLoop = getFreshLabel("copySecondStrLoop");
            String secondLoopCounter = getFreshLabel("copySecondStrLoopCounter");
            String secondLoopInitializer = getFreshLabel("copySecondStrLoopInitializer");
            String outsideLoops = getFreshLabel("outsideLoops");



            Temp globalLoopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
            sir_MIPS_a_lot.getInstance().label(firstLoopCounter);
            // sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, t1);
            sir_MIPS_a_lot.getInstance().beq(tempVal,secondLoopCounter);
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(t1, t1, 1);
            sir_MIPS_a_lot.getInstance().jump(firstLoopCounter);

            sir_MIPS_a_lot.getInstance().label(secondLoopCounter);
            // sir_MIPS_a_lot.getInstance().add(addressToRead, t2, loopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, t2);
            sir_MIPS_a_lot.getInstance().beq(tempVal,mallocBlock);
            sir_MIPS_a_lot.getInstance().addi(loopCounter, loopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(t2, t2, 1);
            sir_MIPS_a_lot.getInstance().jump(secondLoopCounter);

            sir_MIPS_a_lot.getInstance().label(mallocBlock);
            Temp mallocAddressReg = sir_MIPS_a_lot.getInstance().malloc(globalLoopCounter,true); // true for null terminator
            sir_MIPS_a_lot.getInstance().move(dst,mallocAddressReg);

            sir_MIPS_a_lot.getInstance().sub(t1,t1,globalLoopCounter);
            sir_MIPS_a_lot.getInstance().add(t1,t1,loopCounter);
            sir_MIPS_a_lot.getInstance().sub(t2,t2,loopCounter);

            sir_MIPS_a_lot.getInstance().initializeRegToZero(globalLoopCounter);
            sir_MIPS_a_lot.getInstance().initializeRegToZero(loopCounter);
            // sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
            // sir_MIPS_a_lot.getInstance().jump(firstLoop);

            sir_MIPS_a_lot.getInstance().label(firstLoop);
            // sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, t1);
            sir_MIPS_a_lot.getInstance().beq(tempVal,secondLoopInitializer);
            sir_MIPS_a_lot.getInstance().store_byte(dst, tempVal );
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(t1, t1, 1);
            sir_MIPS_a_lot.getInstance().addi(dst, dst, 1);
            sir_MIPS_a_lot.getInstance().jump(firstLoop);

            sir_MIPS_a_lot.getInstance().label(secondLoopInitializer);
            sir_MIPS_a_lot.getInstance().initializeRegToZero(loopCounter);
            sir_MIPS_a_lot.getInstance().label(secondLoop);
            // sir_MIPS_a_lot.getInstance().add(addressToRead, t2, loopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, t2);
            sir_MIPS_a_lot.getInstance().beq(tempVal,outsideLoops);
            sir_MIPS_a_lot.getInstance().store_byte(dst,tempVal);
            sir_MIPS_a_lot.getInstance().addi(loopCounter, loopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(t2, t2, 1);
            sir_MIPS_a_lot.getInstance().addi(dst, dst, 1);
            sir_MIPS_a_lot.getInstance().jump(secondLoop);

            sir_MIPS_a_lot.getInstance().label(outsideLoops);
            sir_MIPS_a_lot.getInstance().initializeRegToZero(tempVal);
            sir_MIPS_a_lot.getInstance().store_byte(dst,tempVal);
            sir_MIPS_a_lot.getInstance().sub(dst,dst,globalLoopCounter);
            
            sir_MIPS_a_lot.getInstance().mixedMove("$v0",dst);


            for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
                currentTemp = Temp_FACTORY.getTemp(i);
                sir_MIPS_a_lot.getInstance().pop(currentTemp);
            }
            sir_MIPS_a_lot.getInstance().pop("$ra");

            sir_MIPS_a_lot.getInstance().addi("$sp","$sp",8);

            sir_MIPS_a_lot.getInstance().jr("$ra");
        } 


}
