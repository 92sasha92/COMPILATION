package Frame;

import Temp.*;
import Util.BoolList;

public abstract class Frame implements TempMap {
	public abstract Frame newFrame(Label name, BoolList formals);

	public Label name;
	//public AccessList formals = null; 

	//public abstract Access allocLocal(boolean escape); 

	//public abstract Tree.Exp externalCall(String func, Tree.ExpList args);

	public abstract Temp FP(); // 

	public abstract Temp SP(); //

	public abstract Temp RA(); //

	public abstract Temp RV(); //

	public abstract java.util.HashSet<Temp> registers(); 

	//public abstract Tree.Stm procEntryExit1(Tree.Stm body); 

	public abstract Assem.InstrList procEntryExit2(Assem.InstrList body); 

	public abstract Assem.InstrList procEntryExit3(Assem.InstrList body); 

	public abstract String string(Label label, String value);

	//public abstract Assem.InstrList codegen(Tree.Stm s); 
	// public abstract int wordSize(); 
	// public abstract TempList colors();
}
