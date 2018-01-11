package IR;

import Temp.*;
import MIPS.*;

public class IRcommand_Binop_Integers extends IRcommand{
	public Temp t1;
	public Temp t2;
	public Temp dst;
	public String command;
        private static boolean divInit = false;
	
	public IRcommand_Binop_Integers(String command,Temp dst,Temp t1,Temp t2)
	{
		this.command = command;
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/******************************************************/
		/* [0] Allocate a fresh temporary t4 for the addition */
		/******************************************************/
		Temp t1_bOp_t2 = Temp_FACTORY.getInstance().getFreshTemp();

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
                String illegal_div_by_0 = null;

                /*********************/
		/* [4] t4 := t1 op t2 */
		/*********************/
		switch(command){
			case "add":
				sir_MIPS_a_lot.getInstance().add(t1_bOp_t2,t1,t2);
				break;
			case "sub":
				sir_MIPS_a_lot.getInstance().sub(t1_bOp_t2,t1,t2);
				break;
			case "mul":
				sir_MIPS_a_lot.getInstance().mul(t1_bOp_t2,t1,t2);
				break;
			case "div":
                                if(!divInit){
		                illegal_div_by_0 = getFreshLabel(illegal_div_by_0);
                                }
				sir_MIPS_a_lot.getInstance().beq(t2 ,illegal_div_by_0);
				sir_MIPS_a_lot.getInstance().div(t1_bOp_t2,t1,t2);
				break;
                }
		
		
		/*********************************************************/
		/* [5] if (32767 <  t1_plus_t2) goto label_overflow;    */
        /*      if (t1_plus_t2 < -32768) goto label_overflow;   */
		/*     if (32767 >= t1_plus_t2 && t1_plus_t2 >= -32768)*/ 
		/*			goto label_no_overflow;                      */
		/*********************************************************/
		sir_MIPS_a_lot.getInstance().addBranch("blt", intMax, t1_bOp_t2, label_overflow_pos);
		sir_MIPS_a_lot.getInstance().addBranch("blt", t1_bOp_t2, intMin, label_overflow_neg);
		sir_MIPS_a_lot.getInstance().addBranch("bge", intMax, t1_bOp_t2, label_no_overflow);

               if(command.equals("div") && !divInit){
			sir_MIPS_a_lot.getInstance().label(illegal_div_by_0);
			Temp t = Temp_FACTORY.getInstance().getFreshTemp();
			sir_MIPS_a_lot.getInstance().load_string(t,"Illegal Division By Zero\n");
			sir_MIPS_a_lot.getInstance().print_string(t);
			sir_MIPS_a_lot.getInstance().exitProgram();
			divInit = true;
		}
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
		/*         t3 := t1 op t2    */
		/*         goto end;      */
		/*                        */
		/**************************/
		sir_MIPS_a_lot.getInstance().label(label_no_overflow);
		switch(command){
			case "add":
				sir_MIPS_a_lot.getInstance().add(dst,t1,t2);
				break;
			case "sub":
				sir_MIPS_a_lot.getInstance().sub(dst,t1,t2);
				break;
			case "mul":
				sir_MIPS_a_lot.getInstance().mul(dst,t1,t2);
				break;
			case "div":
				sir_MIPS_a_lot.getInstance().div(dst,t1,t2);
				break;
		}
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [9] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);		
	}
}
