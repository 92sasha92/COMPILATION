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

public class IRcommand_Binop_EQ_Integers extends IRcommand
{
	public Temp t1;
	public Temp t2;
	public Temp dst;
        public boolean isStrings;

	public IRcommand_Binop_EQ_Integers(Temp dst,Temp t1,Temp t2, boolean isStrings)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
                this.isStrings = isStrings;
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
		/* [2] if (t1 = t2) goto label_AssignOne;  */
		/*     if (t1 != t2) goto label_AssignZero; */
		/******************************************/
                if (!this.isStrings) {
                    sir_MIPS_a_lot.getInstance().addBranch("beq", t1, t2, label_AssignOne);
                    sir_MIPS_a_lot.getInstance().addBranch("bne", t1, t2, label_AssignZero);
                }
                else {
		    Temp counter = sir_MIPS_a_lot.getInstance().initializeRegToZero(); 
                    Temp t1_byte = Temp_FACTORY.getInstance().getFreshTemp();
                    Temp t2_byte = Temp_FACTORY.getInstance().getFreshTemp();
		    String compareLoop = getFreshLabel("compareLoop");
		    String bytesEqual = getFreshLabel("bytesEqual");




		    sir_MIPS_a_lot.getInstance().label(compareLoop);

		    sir_MIPS_a_lot.getInstance().add(t1_byte,t1,counter);
		    sir_MIPS_a_lot.getInstance().load_byte(t1_byte,t1_byte);

		    sir_MIPS_a_lot.getInstance().add(t2_byte,t2,counter);
		    sir_MIPS_a_lot.getInstance().load_byte(t2_byte,t2_byte);
                    
                    sir_MIPS_a_lot.getInstance().addBranch("beq", t1_byte, t2_byte, bytesEqual);
                    sir_MIPS_a_lot.getInstance().jump(label_AssignOne);
                    // sir_MIPS_a_lot.getInstance().beq(t1_byte , label_AssignOne);
                    // sir_MIPS_a_lot.getInstance().beq(t2_byte , label_AssignOne);
		    // sir_MIPS_a_lot.getInstance().addi(counter,counter,1);
		    // sir_MIPS_a_lot.getInstance().jump(compareLoop);


		    sir_MIPS_a_lot.getInstance().label(bytesEqual);
                    sir_MIPS_a_lot.getInstance().beq(t1_byte , label_AssignZero);
		    sir_MIPS_a_lot.getInstance().addi(counter,counter,1);
		    sir_MIPS_a_lot.getInstance().jump(compareLoop);


                    
                }

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
		/*         t3 := 0       */
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
