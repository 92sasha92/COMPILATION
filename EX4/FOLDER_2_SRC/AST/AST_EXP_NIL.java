package AST;

import TYPES.*;
import IR.*;
import Temp.*;

public class AST_EXP_NIL extends AST_EXP
{
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_NIL()
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== exp -> NIL\n");
	}

	/************************************************/
	/* The printing message for an NIL EXP AST node */
	/************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST NIL EXP */
		/*******************************/
		System.out.format("AST NODE NIL\n");

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NIL"));
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION {
            regsNeeded = 1;
		return TYPE_NIL.getInstance();
	}

        public Temp IRme() {
		Temp zero  = Temp_FACTORY.getInstance().getFreshTemp();
	        IR.getInstance().Add_IRcommand(new IRcommandConstInt(zero, 0));
                return zero;
        }


}
