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
	
	public IRcommand_Binop_Concat_Strings(Temp dst,Temp t1,Temp t2, int leftLength, int rightLength)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
                this.leftLength = leftLength;
                this.rightLength = rightLength;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
            // I really need to document this shit
		Temp mallocAddressReg = sir_MIPS_a_lot.getInstance().malloc(leftLength + rightLength + 1); // +1 for null terminator
                sir_MIPS_a_lot.getInstance().move(dst,mallocAddressReg);

    Temp globalLoopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
    Temp loopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
    Temp tempVal = sir_MIPS_a_lot.getInstance().initializeRegToZero();
    Temp addressToRead = Temp_FACTORY.getInstance().getFreshTemp();
    Temp addressToWrite = Temp_FACTORY.getInstance().getFreshTemp();
    String firstLoop = getFreshLabel("copyFirstStrLoop");
    String secondLoop = getFreshLabel("copySecondStrLoop");
    String outsideLoops = getFreshLabel("outsideLoops");

sir_MIPS_a_lot.getInstance().label(firstLoop);
    sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
    sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
    sir_MIPS_a_lot.getInstance().beq(tempVal,secondLoop);
    sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
    sir_MIPS_a_lot.getInstance().store_byte(tempVal, addressToWrite);
    sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
    sir_MIPS_a_lot.getInstance().jump(firstLoop);
    


    sir_MIPS_a_lot.getInstance().label(secondLoop);
        sir_MIPS_a_lot.getInstance().add(addressToRead, t2, loopCounter);
        sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
        sir_MIPS_a_lot.getInstance().beq(tempVal,outsideLoops);
        sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
        sir_MIPS_a_lot.getInstance().store_byte(tempVal, addressToWrite);
        sir_MIPS_a_lot.getInstance().addi(loopCounter, loopCounter, 1);
        sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
        sir_MIPS_a_lot.getInstance().jump(secondLoop);
    
    sir_MIPS_a_lot.getInstance().label(outsideLoops);
        }
}
