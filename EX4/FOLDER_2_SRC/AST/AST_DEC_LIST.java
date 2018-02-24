package AST;

import TYPES.*;
import Temp.*;
import IR.*;

public class AST_DEC_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_DEC head;
	public AST_DEC_LIST tail;
        public String mainLabel;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_LIST(AST_DEC head,AST_DEC_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.head = head;
		this.tail = tail;
                boolean shouldEnumerate = false;
                this.mainLabel = IRcommand.getFreshLabel("main",shouldEnumerate);
	}

	public TYPE SemantMe() throws AST_EXCEPTION
	{		
		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		
		return null;	
	}

	/********************************************************/
	/* The printing message for a declaration list AST node */
	/********************************************************/
	public void PrintMe()
	{
		/********************************/
		/* AST NODE TYPE = AST DEC LIST */
		/********************************/
		System.out.print("AST NODE DEC LIST\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"DEC\nLIST\n");
				
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public Temp IRme(boolean globalInitialization)
	{
		if (head != null) {
                    if ((globalInitialization && (head instanceof AST_DEC_VAR)) || (!globalInitialization && !(head instanceof AST_DEC_VAR)))  {
                        head.IRme();
                    }

                }
		if (tail != null) tail.IRme(globalInitialization);
		
		return null;			
	}
        public void printMainLabel() {
	        IR.getInstance().Add_IRcommand(new IRcommand_addLabel(this.mainLabel));
        }
        public void printConcatStringDefinition() {
	        IR.getInstance().Add_IRcommand(new IRcommand_Binop_Concat_Strings_Definition());
        }
        public void jumpToRealMain() {
	        IR.getInstance().Add_IRcommand(new IRcommand_jump("main_real"));
        }
}
