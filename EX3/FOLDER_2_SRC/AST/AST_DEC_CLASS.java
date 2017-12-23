package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_CLASS extends AST_DEC
{
	/********/
	/* NAME */
	/********/
	public String className;
	public String extendName = null;

	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_CFIELD_LIST data_members;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_CLASS(String className, String extendName, AST_CFIELD_LIST data_members)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		this.className = className;
		this.extendName = extendName;
		this.data_members = data_members;
	}

	/*********************************************************/
	/* The printing message for a class declaration AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		System.out.format("CLASS DEC = %s\n",className);
		if (extendName != null) System.out.format("CLASS EXTENDS = %s\n",extendName);
		if (data_members != null) data_members.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		if (extendName != null)AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CLASS:%s\nEXTENDS:%s",className,extendName));
		if (extendName == null)AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CLASS\n%s",className));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,data_members.SerialNumber);		
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION
	{	
	
		TYPE classType, extendsType = null, scope, t;
		int scopeIndex, paramIndex;
		TYPE_LIST param_List = null;
		TYPE_LIST method_List = null;
		TYPE_LIST p = null; 
		/****************************/
		/* [1] Check If name of the class exists */
		/****************************/
		classType = SYMBOL_TABLE.getInstance().find(className);
		if(classType != null){
			throw new AST_EXCEPTION(String.format("variable name %s already exists in the scope\n", className), this.lineNum);
		}
		
		/*****************************************/
		/* [2] Check If the extend class exists */
		/****************************************/
		
		if(extendName != null){
			extendsType = SYMBOL_TABLE.getInstance().find(extendName);
			if(extendsType == null){
				throw new AST_EXCEPTION(String.format("type of extends class %s doesnt exists\n", extendName), this.lineNum);
			} else if(!(extendsType instanceof TYPE_CLASS)) {
				throw new AST_EXCEPTION(String.format("type of extends '%s' is not a class type\n", extendName), this.lineNum);
			}
		}
		
		
		/*************************/
		/* [3] Begin Class Scope */
		/*************************/
		SYMBOL_TABLE.getInstance().beginScope();

		/***************************/
		/* [4] Semant Data Members */
		/***************************/

                // COMMENTED OUR BY GUY!
		// SYMBOL_TABLE.getInstance().enter(className,new TYPE_CLASS(null, className, null, null));
		for (AST_CFIELD_LIST l = this.data_members; l != null;l = l.tail){
			if(l.head.varDec != null){
				t = l.head.varDec.SemantMe();
				if (param_List == null){
					param_List = new TYPE_LIST(t, null);
					p = param_List;
				} else {
					p.tail = new TYPE_LIST(t, null); 
					p = p.tail;
				}
			}


		}

		for (AST_CFIELD_LIST l = this.data_members; l != null;l = l.tail){
			if(l.head.funcDec != null){
				t = l.head.funcDec.SemantMe();
				if (method_List == null){
					method_List = new TYPE_LIST(t, null);
					p = method_List;
				} else {
					p.tail = new TYPE_LIST(t, null); 
					p = p.tail;
				}
			}


		}

		TYPE_CLASS cT = new TYPE_CLASS((TYPE_CLASS)extendsType,className, param_List, method_List);





		/*****************/
		/* [5] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/************************************************/
		/* [6] Enter the Class Type to the Symbol Table */
		/************************************************/
		SYMBOL_TABLE.getInstance().enter(className,cT);

		/*********************************************************/
		/* [7] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
}
