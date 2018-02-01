package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import Temp.*;
import MIPS.*;

public class IRcommand_AdressStackAlloc extends IRcommand
{
	int localVariableIndex;
	Temp temp;
	public IRcommand_AdressStackAlloc(int localVariableIndex, Temp temp)
	{
		this.localVariableIndex = localVariableIndex;
		this.temp = temp;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	    sir_MIPS_a_lot.getInstance().addressLocalVar(localVariableIndex + Temp_FACTORY.numOfMipsRegs + 1,temp); 
	}
}
