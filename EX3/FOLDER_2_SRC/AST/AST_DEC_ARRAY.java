package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_ARRAY extends AST_DEC
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String type;
	public String name;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_ARRAY(String name,String type)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.type = type;
		this.name = name;
	}

	/********************************************************/
	/* The printing message for a declaration list AST node */
	/********************************************************/
	public void PrintMe()
	{
		/********************************/
		/* AST NODE TYPE = AST DEC LIST */
		/********************************/
		System.out.format("ARRAY-DEC(%s):%s\n",name,type);

		/**************************************/
		/* RECURSIVELY PRINT initialValue ... */
		/**************************************/


		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ARRAY\nDEC(%s)\n:%s",name,type));
			
	}

	public TYPE SemantMe()
	{
		TYPE t, scope;

	
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		t = SYMBOL_TABLE.getInstance().find(type);
		if (t == null)
		{
			throw new AST_EXCEPTION(String.format("Non existing type %s\n", type), this.lineNum);
		} else if (!(varType instanceof TYPE_INT) && !(varType instanceof TYPE_STRING) && !(varType instanceof TYPE_ARRAY) && !(varType instanceof TYPE_CLASS)) {
			throw new AST_EXCEPTION(String.format("'%s' is not a array type\n", type), this.lineNum);
		}

		/**************************************/
		/* [1.5] Check That Array is global */
		/**************************************/
		scope = SYMBOL_TABLE.getInstance().find("SCOPE-BOUNDARY");
		if (scope != null) {
			throw new AST_EXCEPTION(String.format("'%s' array should be global \n", type), this.lineNum);
		}
		
		/**************************************/
		/* [2] Check That Name does NOT exist */
		/**************************************/
		if (SYMBOL_TABLE.getInstance().find(name) != null)
		{
			throw new AST_EXCEPTION(String.format("array %s already exists", name));			
		}

		/***************************************************/
		/* [3] Enter the Array Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name, new TYPE_ARRAY(type, name));

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
	
}
