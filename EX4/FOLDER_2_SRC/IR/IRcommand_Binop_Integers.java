package IR;

import Temp.*;
import MIPS.*;

public class IRcommand_Binop_Integers extends IRcommand{
	public Temp t1;
	public Temp t2;
	public Temp dst;
	public String command;
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
            
                /*********************/
		/* [4] t4 := t1 op t2 */
		/*********************/
            boolean shouldEnumerate = false;
            String illegalDiv = getFreshLabel("Div_By_Zero", shouldEnumerate);
            String checkOverflow = getFreshLabel("Check_Overflow", shouldEnumerate);
            shouldEnumerate = true;
            String legalDiv = getFreshLabel("Legal_Div", shouldEnumerate);
            switch(command){
                        case "add":
                                sir_MIPS_a_lot.getInstance().add(dst,t1,t2);
                                if (!isAddresses) {
                                sir_MIPS_a_lot.getInstance().push(dst);
                                sir_MIPS_a_lot.getInstance().jal(checkOverflow);
                                sir_MIPS_a_lot.getInstance().mixedMove(dst,"$v0");
                                }
				break;
			case "sub":
				sir_MIPS_a_lot.getInstance().sub(dst,t1,t2);
                                if (!isAddresses) {
                                sir_MIPS_a_lot.getInstance().push(dst);
                                sir_MIPS_a_lot.getInstance().jal(checkOverflow);
                                sir_MIPS_a_lot.getInstance().mixedMove(dst,"$v0");
                                }
				break;
			case "mul":
				sir_MIPS_a_lot.getInstance().mul(dst,t1,t2);
                                if (!isAddresses) {
                                sir_MIPS_a_lot.getInstance().push(dst);
                                sir_MIPS_a_lot.getInstance().jal(checkOverflow);
                                sir_MIPS_a_lot.getInstance().mixedMove(dst,"$v0");
                                }
				break;
			case "div":
				sir_MIPS_a_lot.getInstance().bne(t2 ,legalDiv);
                                sir_MIPS_a_lot.getInstance().jal(illegalDiv);
                                sir_MIPS_a_lot.getInstance().label(legalDiv);
				sir_MIPS_a_lot.getInstance().div(dst,t1,t2);
				break;
                    }

	}
}
