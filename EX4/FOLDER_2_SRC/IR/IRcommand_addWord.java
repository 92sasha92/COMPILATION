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

import MIPS.*;

public class IRcommand_addWord extends IRcommand
{
	String word;
	public IRcommand_addWord(String word)
	{
		this.word = word;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
        sir_MIPS_a_lot.getInstance().word(word);

	}
}
