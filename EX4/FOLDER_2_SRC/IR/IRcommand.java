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
        return (Label.getLabel(String.format("Label_%s_",msg), true)).name;
        // return (new Label(String.format("Label_%s_",msg))).name;
		//return String.format("Label_%d_%s",label_counter++,msg);
	}
        public static String getFreshLabel(String msg, boolean shouldEnumerate)
	{
            if (shouldEnumerate) {
                return (Label.getLabel(String.format("Label_%s_",msg), shouldEnumerate)).name;
            }
            return (Label.getLabel(String.format("%s",msg), shouldEnumerate)).name;

	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public abstract void MIPSme();
}
