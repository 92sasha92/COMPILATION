package AST;

import TYPES.*;
import Temp.*;

public abstract class AST_STMT extends AST_Node
{
    public int varSize;
    public String returnLabel;
    // public Temp classInstanceAddress;
	/*********************************************************/
	/* The default message for an unknown AST statement node */
	/*********************************************************/
	public void PrintMe()
	{
		System.out.print("UNKNOWN AST STATEMENT NODE");
	}
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		return null;
	}
}
