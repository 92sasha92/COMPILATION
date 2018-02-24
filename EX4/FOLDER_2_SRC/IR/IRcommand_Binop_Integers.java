package IR;

import Temp.*;
import MIPS.*;

public class IRcommand_Binop_Integers extends IRcommand{
	public Temp t1;
	public Temp t2;
	public Temp dst;
	public String command;
        private static boolean divInit = false;
        private static String illegalDiv;
        public boolean isAddresses;
	
	public IRcommand_Binop_Integers(String command,Temp dst,Temp t1,Temp t2, boolean isAddresses)
	{
		this.command = command;
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
                this.isAddresses = isAddresses;
		                
	}
        public IRcommand_Binop_Integers(String command,Temp dst,Temp t1,Temp t2)
	{
		this.command = command;
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
                this.isAddresses = false;
		                
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		/******************************************************/
		/* [0] Allocate a fresh temporary t4 for the addition */
		/******************************************************/
		// Temp t1_bOp_t2 = Temp_FACTORY.getInstance().getFreshTemp();

            Temp intMin=null,intMax = null;
            String label_end=null,label_overflow_pos=null,label_overflow_neg = null;
            //isAddresses = true;

            if (!isAddresses) {
		/******************************************/
		/* [1] Allocate a fresh temporary INT_MAX */
		/******************************************/
		intMax = Temp_FACTORY.getInstance().getFreshTemp();
	//	intMin = Temp_FACTORY.getInstance().getFreshTemp();
		/************************************/
		/* [2] intMax := 32767 (= 2^15 - 1) */
		/*	   intMin := -32768 (= -2^15)	*/
		/************************************/
		sir_MIPS_a_lot.getInstance().li(intMax,32767);
		//sir_MIPS_a_lot.getInstance().li(intMin,-32768);
		/****************************************************/
		/* [3] Allocate a fresh label for possible overflow */
		/****************************************************/
		label_end = getFreshLabel("end");
		label_overflow_pos = getFreshLabel("overflow_pos");
		label_overflow_neg = getFreshLabel("overflow_neg");
                // String label_no_overflow = getFreshLabel("no_overflow");

            }
                /*********************/
		/* [4] t4 := t1 op t2 */
		/*********************/
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
                                if(!divInit){
                                    illegalDiv = getFreshLabel("illegal_div_by_zero");
                                }
				sir_MIPS_a_lot.getInstance().beq(t2 ,illegalDiv);
				sir_MIPS_a_lot.getInstance().div(dst,t1,t2);
				break;
                }
		
		
                if (!isAddresses) {
		/*********************************************************/
		/* [5] if (32767 <  t1_plus_t2) goto label_overflow;    */
        /*      if (t1_plus_t2 < -32768) goto label_overflow;   */
		/*     if (32767 >= t1_plus_t2 && t1_plus_t2 >= -32768)*/ 
		/*			goto label_no_overflow;                      */
		/*********************************************************/
		sir_MIPS_a_lot.getInstance().addBranch("blt", intMax, dst, label_overflow_pos);
		//sir_MIPS_a_lot.getInstance().addBranch("blt", dst, intMin, label_overflow_neg);
		sir_MIPS_a_lot.getInstance().addBranch("bge", intMax, dst, label_end);

               if(command.equals("div") && !divInit){
			sir_MIPS_a_lot.getInstance().label(illegalDiv);
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
		// sir_MIPS_a_lot.getInstance().label(label_no_overflow);
		// switch(command){
		// 	case "add":
		// 		sir_MIPS_a_lot.getInstance().add(dst,t1,t2);
		// 		break;
		// 	case "sub":
		// 		sir_MIPS_a_lot.getInstance().sub(dst,t1,t2);
		// 		break;
		// 	case "mul":
		// 		sir_MIPS_a_lot.getInstance().mul(dst,t1,t2);
		// 		break;
		// 	case "div":
		// 		sir_MIPS_a_lot.getInstance().div(dst,t1,t2);
		// 		break;
		// }
		// sir_MIPS_a_lot.getInstance().jump(label_end);

		/******************/
		/* [9] label_end: */
		/******************/
		sir_MIPS_a_lot.getInstance().label(label_end);		
                }
	}
}
