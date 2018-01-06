package AST;

import IR.*;
import Temp.*;
import MIPS.*;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_SIMPLE extends AST_EXP_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;
	
	
	/************************************************/
	/* PRIMITIVE AD-HOC COUNTER FOR LOCAL VARIABLES */
	/************************************************/
	public static int localVariablesCounter = 0;
	public int localVariableIndex = 0;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_SIMPLE(String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== var -> ID( %s )\n",name);
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}
	public TYPE SemantMe()
	{
		TYPE classScope = SYMBOL_TABLE.getInstance().findClassBoundry();
		TYPE_CLASS classType = null;
		TYPE field = null;
		TYPE varType = SYMBOL_TABLE.getInstance().find(name);
		int varIndex = SYMBOL_TABLE.getInstance().findIndex(name);
		localVariableIndex = SYMBOL_TABLE.getInstance().findVarIndex(name);
		int scopeIndex = SYMBOL_TABLE.getInstance().findIndex("SCOPE-BOUNDARY");
		int classIndex = SYMBOL_TABLE.getInstance().findClassBoundryIndex();
		if(classScope != null){
			classType = (TYPE_CLASS)((TYPE_FOR_SCOPE_BOUNDARIES)classScope).returnType;
			field = classType.getField(this.name);
			if(field != null){
				if(classIndex <= scopeIndex && varType != null && varType.name.equals(((TYPE_VAR_DEC)field).t.name)){
					return varType;
				}
				if(classIndex == scopeIndex || varType == null || varIndex < scopeIndex){
					return ((TYPE_VAR_DEC)field).t;
				}	
			} 
	   } 
		
		return varType;
		
	}
	
	public Temp IRme()
	{
		Temp t = Temp_FACTORY.getInstance().getFreshTemp();
		IR.getInstance().Add_IRcommand(new IRcommand_Load(
			t,
			sir_MIPS_a_lot.getInstance().addressLocalVar(localVariableIndex)));
		System.out.println("varIndex: " + localVariableIndex + " name: " + name);

		return t;
	}
}
