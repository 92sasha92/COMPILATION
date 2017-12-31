package AST;

import IR.*;
import TEMP.*;
import MIPS.*;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_VAR extends AST_DEC
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String type;
	public String name;
	public AST_EXP initialValue;
	public boolean isField = false;
	public TYPE_CLASS fatherClass = null;
	
	/************************************************/
	/* PRIMITIVE AD-HOC COUNTER FOR LOCAL VARIABLES */
	/************************************************/
	public static int localVariablesCounter = 0;
	public int localVariableIndex;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_VAR(String type,String name,AST_EXP initialValue)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.type = type;
		this.name = name;
		this.initialValue = initialValue;
		this.localVariableIndex = ++localVariablesCounter;
	}
	
	public void setIsFieldToTrue(){
		this.isField = true;
	}

	/********************************************************/
	/* The printing message for a declaration list AST node */
	/********************************************************/
	public void PrintMe()
	{
		/********************************/
		/* AST NODE TYPE = AST DEC LIST */
		/********************************/
		if (initialValue != null) System.out.format("VAR-DEC(%s):%s := initialValue\n",name,type);
		if (initialValue == null) System.out.format("VAR-DEC(%s):%s                \n",name,type);

		/**************************************/
		/* RECURSIVELY PRINT initialValue ... */
		/**************************************/
		if (initialValue != null) initialValue.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("VAR\nDEC(%s)\n:%s",name,type));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (initialValue != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,initialValue.SerialNumber);		
			
	}
	
	public void setFatherClass(TYPE_CLASS extendsType){
		this.fatherClass = extendsType;
	}

	public TYPE SemantMe() throws AST_EXCEPTION
	{
        return this.SemantMe(false);
    }
	
	public TEMP IRme()
	{
		if (initialValue != null)
		{
			IR.getInstance().Add_IRcommand(new IRcommand_Store(
				sir_MIPS_a_lot.getInstance().addressLocalVar(localVariableIndex),
				initialValue.IRme()));
		}
		return null;
	}
	
	public TYPE SemantMe(boolean nonRecursive) throws AST_EXCEPTION
	{
		TYPE varType, expType = null, paramT;
		int scopeIndex, paramIndex;
		TYPE_CLASS classVarType = null, classExpType = null;
		TYPE varArrayType, expArrayType;
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		varType = SYMBOL_TABLE.getInstance().find(type);
		if (varType == null)
		{
			throw new AST_EXCEPTION(String.format("Non existing type %s\n", type), this.lineNum);
		} else if (!(varType instanceof TYPE_INT) && !(varType instanceof TYPE_ARRAY) && !(varType instanceof TYPE_CLASS) && !(varType instanceof TYPE_STRING)) {//add for array as well
			throw new AST_EXCEPTION(String.format("'%s' is not a variable type\n", type), this.lineNum);
		}
		
		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		scopeIndex = SYMBOL_TABLE.getInstance().findIndex("SCOPE-BOUNDARY");
		paramT = SYMBOL_TABLE.getInstance().find(name);
		paramIndex = SYMBOL_TABLE.getInstance().findIndex(name); 

		if (SYMBOL_TABLE.getInstance().find(name) != null && paramIndex >= scopeIndex)
		{
			throw new AST_EXCEPTION(String.format("variable %s already exists in scope\n", name), this.lineNum);			
		}
		

		/***************************************************/
		/* [3] Enter the Var Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enterVar(name,varType,localVariableIndex); 
        if (!nonRecursive) {
			if (initialValue != null) expType = initialValue.SemantMe();
			if(isField){
				if(this.fatherClass != null){
					if(!(fatherClass.isLegalFieldDec(new TYPE_VAR_DEC(varType, this.name)))){
						throw new AST_EXCEPTION("Shadowing fields is illegal", this.lineNum);
					}
				}
				if(initialValue != null && !(initialValue instanceof AST_EXP_NIL) && !(initialValue instanceof AST_EXP_INT) && !(initialValue instanceof AST_EXP_STRING)){
					throw new AST_EXCEPTION("A data member can't be assigned to a non constant value", this.lineNum);
				}
			}
			
			if(initialValue == null){
				return new TYPE_VAR_DEC(varType, this.name);
			}
	        typesCheck(varType, expType);
        }

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return new TYPE_VAR_DEC(varType, this.name);		
	}
	
}
