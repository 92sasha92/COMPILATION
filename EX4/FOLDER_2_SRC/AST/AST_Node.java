package AST;

import Temp.*;
import TYPES.*;

public abstract class AST_Node
{
	/*******************************************/
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	/*******************************************/
	public int SerialNumber;
	public int lineNum = 0;
	
	/***********************************************/
	/* The default message for an unknown AST node */
	/***********************************************/
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}
	
	/*****************************************/
	/* The default IR action for an AST node */
	/*****************************************/
	public Temp IRme()
	{
		return null;
	}
	
	//public Type SemantMe() throws Exception {
	//	return null;
	//}
	
	public void typesCheck(TYPE varType, TYPE expType) throws AST_EXCEPTION{
		
		TYPE_CLASS classVarType = null, classExpType = null;
		TYPE varArrayType, expArrayType;
		TYPE_ARRAY varArray= null, expArray = null;
		if(expType == TYPE_NIL.getInstance()){
			if(varType instanceof TYPE_INT || varType instanceof TYPE_STRING){
				throw new AST_EXCEPTION("Primitive type cannot be defined to be nil", this.lineNum);
			}	
		} else if(varType instanceof TYPE_CLASS && expType instanceof TYPE_CLASS) {
			classVarType = (TYPE_CLASS) varType;
			classExpType = (TYPE_CLASS) expType;
			if(!(classExpType.isSonOf(classVarType.name))) {
				throw new AST_EXCEPTION(String.format("%s is not a child class of %s", classExpType.name, classVarType.name), this.lineNum);
			}
		} else if(varType instanceof TYPE_ARRAY && expType instanceof TYPE_ARRAY) {
			varArray = (TYPE_ARRAY)varType;
			expArray = (TYPE_ARRAY)expType;
			varArrayType = varArray.type;
			expArrayType = expArray.type;
			if(varArrayType instanceof TYPE_CLASS && expArrayType instanceof TYPE_CLASS){
				if(!(varArrayType.name.equals(expArrayType.name))){
					throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
				}
				if(!(expArray.name.equals(expArrayType.name))){
					if(!(varArray.name.equals(expArray.name))){
						throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
					}
				}
			} else if(varArrayType != expArrayType){
				throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
			} else{
				if(expArray.name.equals(expArrayType.name)){
					if(!(varArrayType.name.equals(expArray.name))){
						throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
					}
				} else if(!(varArray.name.equals(expArray.name))){
					throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
				}
			}
		} else if(varType != expType) {
			throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
		}
	}
}
