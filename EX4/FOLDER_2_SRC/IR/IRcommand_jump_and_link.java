package IR;

import MIPS.sir_MIPS_a_lot;

public class IRcommand_jump_and_link extends IRcommand {

	private String label;
	
	public IRcommand_jump_and_link(String label)
	{
		this.label = label;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().jal(this.label);
	}

}
