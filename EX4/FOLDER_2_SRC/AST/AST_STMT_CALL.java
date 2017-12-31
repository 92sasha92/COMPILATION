package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import TEMP.*;

public class AST_STMT_CALL extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP_CALL expCall;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_CALL(AST_EXP_CALL expCall)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.expCall = expCall;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		System.out.format("STMT\nCALL\n");

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (expCall != null) expCall.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STMT\nCALL"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expCall.SerialNumber);		
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION{
		return expCall.SemantMe();
	}
	
	public TEMP IRme()
	{
		if (expCall != null) expCall.IRme();
		
		return null;
	}
}
