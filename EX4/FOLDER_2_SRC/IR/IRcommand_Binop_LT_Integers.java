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

public class IRcommand_Binop_LT_Integers extends IRcommand
{
	public Temp t1;
	public Temp t2;
	public Temp dst;

	public IRcommand_Binop_LT_Integers(Temp dst,Temp t1,Temp t2)
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
		/*******************************/
		/* [1] Allocate 2 fresh labels */
		/*******************************/
		String label_end        = getFreshLabel("end");
		String label_AssignOne  = getFreshLabel("AssignOne");
		String label_AssignZero = getFreshLabel("AssignZero");
		
		/******************************************/
		/* [2] if (t1< t2) goto label_AssignOne;  */
		/*     if (t1>=t2) goto label_AssignZero; */
		/******************************************/
		sir_MIPS_a_lot.getInstance().addBranch("blt", t1, t2, label_AssignOne);
		sir_MIPS_a_lot.getInstance().addBranch("bge", t1, t2, label_AssignZero);

		/************************/
		/* [3] label_AssignOne: */
		/*                      */
		/*         t3 := 1      */
		/*         goto end;    */
		/*                      */
		/************************/
		sir_MIPS_a_lot.getInstance().label(label_AssignOne);
		sir_MIPS_a_lot.getInstance().li(dst,1);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/*************************/
		/* [4] label_AssignZero: */
		/*                       */
		/*         t3 := 1       */
		/*         goto end;     */
		/*                       */
		/*************************/
		sir_MIPS_a_lot.getInstance().label(label_AssignZero);
		sir_MIPS_a_lot.getInstance().li(dst,0);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [5] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);
	}
}
