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

public class IRcommand_Malloc extends IRcommand
{
	Temp tempContainingSize;
	Temp mallocAddress;
        boolean shouldAddNullByte;
	
	public IRcommand_Malloc(Temp tempContainingSize, boolean shouldAddNullByte, Temp mallocAddress)
	{
		this.tempContainingSize = tempContainingSize;
                this.shouldAddNullByte = shouldAddNullByte;
                this.mallocAddress = mallocAddress;
	}
	public IRcommand_Malloc(Temp tempContainingSize, Temp mallocAddress)
	{
            this(tempContainingSize, false, mallocAddress);
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
                // allocates memory and moves the address to mallocAddress
		Temp tempWithAddress = sir_MIPS_a_lot.getInstance().malloc(tempContainingSize,shouldAddNullByte);
		sir_MIPS_a_lot.getInstance().move(mallocAddress,tempWithAddress);

                // initialize memory to zero

                String zeroMemLoop = getFreshLabel("zeroMemLoop");

                // create the stopping condition address of the loop (base address + size)
                Temp stoppingAddressTemp = Temp_FACTORY.getInstance().getFreshTemp();
		sir_MIPS_a_lot.getInstance().add(stoppingAddressTemp,mallocAddress, tempContainingSize);

                // create loop label
                sir_MIPS_a_lot.getInstance().label(zeroMemLoop);

                sir_MIPS_a_lot.getInstance().store(tempWithAddress, "$zero", 0);
                sir_MIPS_a_lot.getInstance().addi(tempWithAddress, tempWithAddress, 4);
                sir_MIPS_a_lot.getInstance().addBranch("bne", tempWithAddress, stoppingAddressTemp, zeroMemLoop);

	}
}
