package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_FUNC extends AST_DEC
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String returnTypeName;
	public String name;
	public AST_TYPE_NAME_LIST params = null;
	public AST_STMT_LIST body;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_DEC_FUNC(String returnTypeName, String name, AST_TYPE_NAME_LIST params, AST_STMT_LIST body)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.returnTypeName = returnTypeName;
		this.name = name;
		this.params = params;
		this.body = body;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		System.out.format("FUNC(%s):%s\n",name,returnTypeName);

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (params != null) params.PrintMe();
		if (body   != null) body.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FUNC(%s)\n:%s\n",name,returnTypeName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,params.SerialNumber);		
		if (body   != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);		
	}

	public TYPE SemantMe() throws AST_EXCEPTION
	{
		TYPE t, paramT;
		TYPE returnType = null;
		TYPE_LIST type_list = null;
		TYPE_LIST p = null;
		int paramIndex = 0;
		int scopeIndex = 0;
		/*******************/
		/* [0] return type */
		/*******************/
		returnType = SYMBOL_TABLE.getInstance().find(returnTypeName);
		if (returnType == null)
		{
            throw new AST_EXCEPTION(String.format("Non existing return type %s\n", returnType), this.lineNum);			
		}
	    /*******************/
		/* [1] func dec */
		/*******************/
		scopeIndex = SYMBOL_TABLE.getInstance().findIndex("SCOPE-BOUNDARY");
		paramT = SYMBOL_TABLE.getInstance().find(name);
		paramIndex = SYMBOL_TABLE.getInstance().findIndex(name); 
		if(paramT != null && paramIndex >= scopeIndex){
			throw new AST_EXCEPTION(String.format("function %s already exists in the scope\n", name), this.lineNum);
		}

		/****************************/
		/* [2] Begin Function Scope */
		/****************************/
		SYMBOL_TABLE.getInstance().beginFuncScope(returnType);
		int index = SYMBOL_TABLE.getInstance().getTopIndex();
		/***************************/
		/* [3] Semant Input Params */
		/***************************/
		for (AST_TYPE_NAME_LIST it = params; it  != null; it = it.tail)
		{
			t = SYMBOL_TABLE.getInstance().find(it.head.type);
			if (t == null)
			{	
				throw new AST_EXCEPTION(String.format("Non existing type %s\n",it.head.type), this.lineNum);
			}
			else
			{
				paramT = SYMBOL_TABLE.getInstance().find(it.head.name);
				paramIndex = SYMBOL_TABLE.getInstance().findIndex(it.head.name);
				if (paramT != null && paramIndex >= index)
				{
					throw new AST_EXCEPTION(String.format("variable %s already exists in function scope\n", it.head.name), this.lineNum);			
				}
				
				if (type_list == null){
					type_list = new TYPE_LIST(t, null);
					p = type_list;
				} else {
					p.tail = new TYPE_LIST(t, null); 
					p = p.tail;
				}
				SYMBOL_TABLE.getInstance().enter(it.head.name,t);
			}
		}

		/*******************/
		/* [4] Semant Body */
		/*******************/
		body.SemantMe();

		/*****************/
		/* [5] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [6] Enter the Function Type to the Symbol Table */
		/***************************************************/
		SYMBOL_TABLE.getInstance().enter(name,new TYPE_FUNCTION(returnType,name,type_list));

		/*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;		
	}
	
}
