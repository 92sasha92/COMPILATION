package AST;

import TYPES.*;
import Temp.*;

public abstract class AST_EXP extends AST_Node
{
    int regsNeeded;
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		return null;
	}
	public Temp IRme()
	{
		return null;
	}
}
