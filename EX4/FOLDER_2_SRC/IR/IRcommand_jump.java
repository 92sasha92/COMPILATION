package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_jump extends IRcommand {

	
	
	
	private String label;
	
	public IRcommand_jump(String label)
	{
		this.label = label;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().jump(this.label);
	}

}
