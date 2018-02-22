package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import Temp.*;
import IR.*;

public class AST_STMT_RETURN extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp)
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
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE STMT RETURN\n");

		/*****************************/
		/* RECURSIVELY PRINT exp ... */
		/*****************************/
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"RETURN");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION {
		TYPE returnType = null;
		TYPE funcReturnType = ((TYPE_FOR_SCOPE_BOUNDARIES )SYMBOL_TABLE.getInstance().findFuncBoundry()).returnType;
		if(exp == null) {
			returnType = TYPE_VOID.getInstance();
		} else {
			returnType = exp.SemantMe();
		}
		typesCheck(funcReturnType, returnType);
		
		return new TYPE_RETURN(returnType);
	}

        public Temp IRme() {
            if (exp != null) {
	        IR.getInstance().Add_IRcommand(new IRcommand_mixedMove("$v0",exp.IRme()));
            }
	    IR.getInstance().Add_IRcommand(new IRcommand_jump(this.returnLabel));
            return null;
        }
	
}
