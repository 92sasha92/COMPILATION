package AST;

import TYPES.*;
import Temp.*;

public class AST_STMT_LIST extends AST_Node
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_STMT head;
	public AST_STMT_LIST tail;
        public int totalLocalVarSize;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
		if (tail == null) System.out.print("====================== stmts -> stmt      \n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.head = head;
		this.tail = tail;
	}

        public void setInstanceAddress(Temp instanceAddr) {
            if (head != null) {
                head.classInstanceAddress = instanceAddr;
            }
            if (tail == null) {
                return;
            }
            tail.setInstanceAddress(instanceAddr);
        }
	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/**************************************/
		/* AST NODE TYPE = AST STATEMENT LIST */
		/**************************************/
		System.out.print("AST NODE STMT LIST\n");

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
			"STMT\nLIST\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		TYPE headType = null;
		if (head != null) headType = head.SemantMe();
		if (tail != null) tail.SemantMe();
		//if (tail != null) tailType = tail.SemantMe();
                int headVarSize, tailTotalLocalVarSize;
                if (head == null) {
                    headVarSize = 0;
                }
                else {
                    headVarSize = head.varSize;
                }
                if (tail == null) {
                    tailTotalLocalVarSize = 0;
                }
                else {
                    tailTotalLocalVarSize = tail.totalLocalVarSize;
                }
                this.totalLocalVarSize = headVarSize + tailTotalLocalVarSize;
		return headType;

	}
	
	public Temp IRme()
	{
		if (head != null) head.IRme();
		if (tail != null) tail.IRme();
		
		return null;
	}
}
