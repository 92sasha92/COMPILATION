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
import TEMP.*;
import MIPS.*;

public class IRcommand_Binop_Concat_Strings extends IRcommand
{
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
        public int leftLength;
        public int rightLength;
	
	public IRcommand_Binop_Concat_Strings(TEMP dst,TEMP t1,TEMP t2, int leftLength, int rightLength)
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
		TEMP mallocAddressReg = sir_MIPS_a_lot.getInstance().malloc(leftLength + rightLength + 1); // +1 for null terminator
                sir_MIPS_a_lot.getInstance().move(dst,mallocAddressReg);

    TEMP globalLoopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
    TEMP loopCounter = sir_MIPS_a_lot.getInstance().initializeRegToZero();
    TEMP tempVal = sir_MIPS_a_lot.getInstance().initializeRegToZero();
    TEMP addressToRead = TEMP_FACTORY.getInstance().getFreshTEMP();
    TEMP addressToWrite = TEMP_FACTORY.getInstance().getFreshTEMP();
    String firstLoop = getFreshLabel("copyFirstStrLoop");
    String secondLoop = getFreshLabel("copySecondStrLoop");
    String outsideLoops = getFreshLabel("outsideLoops");


sir_MIPS_a_lot.getInstance().label(firstLoop);
    sir_MIPS_a_lot.getInstance().add(addressToRead, t1, globalLoopCounter);
    sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
    sir_MIPS_a_lot.getInstance().beq(tempVal, secondLoop);
    sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
    sir_MIPS_a_lot.getInstance().store_byte(tempVal, addressToWrite);
    sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
    sir_MIPS_a_lot.getInstance().jump(firstLoop);
    


    sir_MIPS_a_lot.getInstance().label(secondLoop);
        sir_MIPS_a_lot.getInstance().add(addressToRead, t2, loopCounter);
        sir_MIPS_a_lot.getInstance().load_byte(tempVal, addressToRead);
        sir_MIPS_a_lot.getInstance().beq(tempVal, outsideLoops);
        sir_MIPS_a_lot.getInstance().add(addressToWrite, dst, globalLoopCounter);
        sir_MIPS_a_lot.getInstance().store_byte(tempVal, addressToWrite);
        sir_MIPS_a_lot.getInstance().addi(loopCounter, loopCounter, 1);
        sir_MIPS_a_lot.getInstance().addi(globalLoopCounter, globalLoopCounter, 1);
        sir_MIPS_a_lot.getInstance().jump(secondLoop);
    
    sir_MIPS_a_lot.getInstance().label(outsideLoops);
        }
}
