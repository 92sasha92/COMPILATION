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
        public int leftLength;
        public int rightLength;
	
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
            // I really need to document this shit

            Temp globalLoopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
            Temp loopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
            Temp tempVal = sir_MIPS_a_lot.getInstance().initializeRegToZero();
            Temp addressToRead = Temp_FACTORY.getInstance().getFreshTemp();
            Temp addressToWrite = Temp_FACTORY.getInstance().getFreshTemp();
            String mallocBlock = getFreshLabel("mallocBlock");
            String firstLoop = getFreshLabel("copyFirstStrLoop");
            String firstLoopCounter = getFreshLabel("copyFirstStrLoopCounter");
            String secondLoop = getFreshLabel("copySecondStrLoop");
            String secondLoopCounter = getFreshLabel("copySecondStrLoopCounter");
            String secondLoopInitializer = getFreshLabel("copySecondStrLoopInitializer");
            String outsideLoops = getFreshLabel("outsideLoops");


            sir_MIPS_a_lot.getInstance().label(firstLoopCounter);
            sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
            sir_MIPS_a_lot.getInstance().beq(tempVal,secondLoopCounter);
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().jump(firstLoopCounter);

            sir_MIPS_a_lot.getInstance().label(secondLoopCounter);
            sir_MIPS_a_lot.getInstance().add(addressToRead, t2, loopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
            sir_MIPS_a_lot.getInstance().beq(tempVal,mallocBlock);
            sir_MIPS_a_lot.getInstance().addi(loopCounter, loopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().jump(secondLoopCounter);

            sir_MIPS_a_lot.getInstance().label(mallocBlock);
            Temp mallocAddressReg = sir_MIPS_a_lot.getInstance().malloc(globalLoopCounter,true); // true for null terminator
            sir_MIPS_a_lot.getInstance().move(dst,mallocAddressReg);
            sir_MIPS_a_lot.getInstance().initializeRegToZero(globalLoopCounter);
            sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().jump(firstLoop);




            sir_MIPS_a_lot.getInstance().label(firstLoop);
            sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
            sir_MIPS_a_lot.getInstance().beq(tempVal,secondLoopInitializer);
            sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().store_byte(addressToWrite, tempVal );
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().jump(firstLoop);

            sir_MIPS_a_lot.getInstance().label(secondLoopInitializer);
            sir_MIPS_a_lot.getInstance().initializeRegToZero(loopCounter);
            sir_MIPS_a_lot.getInstance().label(secondLoop);
            sir_MIPS_a_lot.getInstance().add(addressToRead, t2, loopCounter);
            sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
            sir_MIPS_a_lot.getInstance().beq(tempVal,outsideLoops);
            sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().store_byte(addressToWrite,tempVal);
            sir_MIPS_a_lot.getInstance().addi(loopCounter, loopCounter, 1);
            sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
            sir_MIPS_a_lot.getInstance().jump(secondLoop);

            sir_MIPS_a_lot.getInstance().label(outsideLoops);
            sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
            sir_MIPS_a_lot.getInstance().initializeRegToZero(tempVal);
            sir_MIPS_a_lot.getInstance().store_byte(addressToWrite,tempVal);
        }
}
