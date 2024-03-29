package AST;

import TYPES.*;
import Temp.Temp;
import IR.*;
import SYMBOL_TABLE.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.cond = cond;
		this.body = body;
	}

	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE STMT IF\n");

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"WHILE (left)\nTHEN right");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		/****************************/
		/* [0] Semant the Condition */
		/****************************/
		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			throw new AST_EXCEPTION("condition inside While is not integral\n", this.lineNum);
		}
		
		/*************************/
		/* [1] Begin While Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [2] Semant Data Members */
		/***************************/
		body.SemantMe();
                this.varSize = body.totalLocalVarSize;

		/*****************/
		/* [3] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/*********************************************************/
		/* [4] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
	
	public Temp IRme(){
                String beforeCond = IRcommand.getFreshLabel("beforeCond");
		IR.getInstance().Add_IRcommand(new IRcommand_addLabel(beforeCond));
		Temp t = cond.IRme();
		String endLabel = "endWhile";
		String labelStartWhile = "whileCond";
		IRcommand_If temp = new IRcommand_If(t, labelStartWhile);
		labelStartWhile = temp.getLabel();
		IR.getInstance().Add_IRcommand(new IRcommand_addLabel(labelStartWhile));
		temp = new IRcommand_If(t, endLabel);
		endLabel = temp.getLabel();
		IR.getInstance().Add_IRcommand(temp);
		body.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_jump(beforeCond));
		IR.getInstance().Add_IRcommand(new IRcommand_addLabel(endLabel));
		return null;
	}
}
