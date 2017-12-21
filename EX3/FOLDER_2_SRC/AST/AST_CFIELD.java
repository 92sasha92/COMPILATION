package AST;

import TYPES.*;

public class AST_CFIELD extends AST_Node
{

	public AST_DEC_VAR varDec;
	public AST_DEC_FUNC funcDec;

	public AST_CFIELD(AST_DEC_VAR varDec,AST_DEC_FUNC funcDec)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (varDec != null) System.out.print("====================== cField -> varDec\n");
		if (funcDec != null) System.out.print("====================== cField -> funcDec\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.varDec = varDec;
		this.funcDec = funcDec;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE CFIELD\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (varDec != null) varDec.PrintMe();
		if (funcDec != null) funcDec.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"cField\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (varDec != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,varDec.SerialNumber);
		if (funcDec != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,funcDec.SerialNumber);
	}

	public TYPE SemantMe() throws AST_EXCEPTION
	{
		if (varDec != null) return varDec.SemantMe();
		if (funcDec != null) return funcDec.SemantMe();
		return null;
	}
}