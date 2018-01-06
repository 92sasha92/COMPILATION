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

public class IRcommand_Binop_Mul_Integers extends IRcommand
{
	public Temp t1;
	public Temp t2;
	public Temp dst;
	
	public IRcommand_Binop_Mul_Integers(Temp dst,Temp t1,Temp t2)
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
		/************************************************************/
		/* [0] Allocate a fresh temporary t4 for the multiplication */
		/************************************************************/
		Temp t1_mul_t2 = Temp_FACTORY.getInstance().getFreshTemp();

		/******************************************/
		/* [1] Allocate a fresh temporary INT_MAX */
		/******************************************/
		Temp intMax = Temp_FACTORY.getInstance().getFreshTemp();
		Temp intMin = Temp_FACTORY.getInstance().getFreshTemp();
		/************************************/
		/* [2] intMax := 32767 (= 2^15 - 1) */
		/*	   intMin := -32768 (= -2^15)	*/
		/************************************/
		sir_MIPS_a_lot.getInstance().li(intMax,32767);
		sir_MIPS_a_lot.getInstance().li(intMin,-32768);
		/****************************************************/
		/* [3] Allocate a fresh label for possible overflow */
		/****************************************************/
		String label_end = getFreshLabel("end");
		String label_overflow_pos = getFreshLabel("overflow_pos");
		String label_overflow_neg = getFreshLabel("overflow_neg");
		String label_no_overflow = getFreshLabel("no_overflow");

		/*********************/
		/* [4] t4 := t1 * t2 */
		/*********************/
		sir_MIPS_a_lot.getInstance().mul(t1_mul_t2,t1,t2);
		
		/*********************************************************/
		/* [5] if (32767 <  t1_mul_t2) goto label_overflow;    */
        /*      if (t1_mul_t2 < -32768) goto label_overflow;   */
		/*     if (32767 >= t1_mul_t2 && t1_mul_t2 >= -32768)*/ 
		/*			goto label_no_overflow;                      */
		/*********************************************************/
		sir_MIPS_a_lot.getInstance().blt(intMax,t1_mul_t2,label_overflow_pos);
		sir_MIPS_a_lot.getInstance().blt(t1_mul_t2,intMin,label_overflow_neg);
		sir_MIPS_a_lot.getInstance().bge(intMax,t1_mul_t2,label_no_overflow);

		/***********************/
		/* [6] label_overflow: */
		/*                     */
		/*         t3 := 32767 */
		/*         goto end;   */
		/*                     */
		/***********************/
		sir_MIPS_a_lot.getInstance().label(label_overflow_pos);
		sir_MIPS_a_lot.getInstance().li(dst,32767);
		sir_MIPS_a_lot.getInstance().jump(label_end);
		
		/***************************/
		/* [7] label_overflow_neg: */
		/*                         */
		/*         t3 := 32767     */
		/*         goto end;       */
		/*                         */
		/***************************/
		sir_MIPS_a_lot.getInstance().label(label_overflow_neg);
		sir_MIPS_a_lot.getInstance().li(dst,-32768);
		sir_MIPS_a_lot.getInstance().jump(label_end);
		
		/**************************/
		/* [8] label_no_overflow: */
		/*                        */
		/*         t3 := t1*t2    */
		/*         goto end;      */
		/*                        */
		/**************************/
		sir_MIPS_a_lot.getInstance().label(label_no_overflow);
		sir_MIPS_a_lot.getInstance().mul(dst,t1,t2);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [9] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);		
	}
}
