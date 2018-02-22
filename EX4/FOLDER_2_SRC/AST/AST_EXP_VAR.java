package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import Temp.*;

public abstract class AST_EXP_VAR extends AST_EXP
{
        public SYMBOL_TABLE_ENTRY.varDefinitionType varDefType;

	public TYPE SemantMe() throws AST_EXCEPTION
	{
		return null;
	}
        public Temp IRme() {
            boolean shouldLoad = true;
            return this.IRme(shouldLoad);
        }
        
        public abstract Temp IRme(boolean shouldLoad);
}
