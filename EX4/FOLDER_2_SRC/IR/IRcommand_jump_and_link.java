package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;

public class IRcommand_jump_and_link extends IRcommand {

	private String label;
        private Temp funcAddr;
	
	public IRcommand_jump_and_link(String label)
	{
		this.label = label;
	}
	public IRcommand_jump_and_link(Temp funcAddr)
	{
		this.funcAddr = funcAddr;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
            if (label != null) {
		sir_MIPS_a_lot.getInstance().jal(this.label);
            }
            else if (funcAddr != null) {
		sir_MIPS_a_lot.getInstance().jalr(this.funcAddr);
            }
            else {
                System.out.println("ERROR!!!");
            }
	}

}
