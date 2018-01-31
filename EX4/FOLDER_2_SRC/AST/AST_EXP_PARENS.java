package AST;

import TYPES.*;
import Temp.*;
public class AST_EXP_PARENS extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP exp;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_PARENS(AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.exp = exp;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		if(exp != null) System.out.format("(exp)\n");
		
		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (exp != null) exp.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		if(exp != null) AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("(exp)\n"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);		
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION {
		if(exp != null) return exp.SemantMe();
		return null;
	}
	
	public Temp IRme()
	{
		if(exp != null) return exp.IRme();
		return null;
	}
}