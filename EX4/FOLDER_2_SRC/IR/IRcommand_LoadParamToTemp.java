package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import Temp.*;
import MIPS.*;

public class IRcommand_LoadParamToTemp extends IRcommand
{
	int paramIndex;
	Temp temp;
	public IRcommand_LoadParamToTemp(int paramIndex, Temp temp)
	{
		this.paramIndex = paramIndex;
		this.temp = temp;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
	    sir_MIPS_a_lot.getInstance().addressParam(paramIndex,temp);
	}
}
