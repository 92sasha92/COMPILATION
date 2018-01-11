/***********/
/* PACKAGE */
/***********/
package IR;
import Temp.*;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/

public abstract class IRcommand
{
	/*****************/
	/* Label Factory */
	/*****************/
	protected static int label_counter=0;
	public static String getFreshLabel(String msg)
	{
        return (new Label(String.format("Label_%s_",msg))).name;
		//return String.format("Label_%d_%s",label_counter++,msg);
	}

	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();
}
