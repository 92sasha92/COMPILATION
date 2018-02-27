package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import Temp.*;
import IR.*;

public class AST_DEC_FUNC extends AST_DEC
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String returnTypeName;
	public String name;
        public String funcLabel;
        public String returnLabel;
	public AST_TYPE_NAME_LIST params = null;
	public AST_STMT_LIST body;
	public boolean isMethod = false;
	public TYPE_CLASS fatherClass = null;
        public String className = null;
        public int localVariablesCounter;
        public int localParamCounter;
	
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
                this.localVariablesCounter = 0;
                this.localParamCounter = 0;
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

	public void setIsMethodToTrue(){
		this.isMethod = true;
                this.localParamCounter = 1; //because the class instace address is always the first parameter, so start counting from 2 and not 1
	}
	public void setClassName(String className){
		this.className = className;
	}
	public void setFatherClass(TYPE_CLASS cl){
		this.fatherClass = cl;
	}
	public TYPE SemantMe() throws AST_EXCEPTION {
        return this.SemantMe(false);
    }

	public Temp IRme()
	{
		IR.getInstance().Add_IRcommand(new IRcommand_addLabel(funcLabel));
                int totalLocalVarSize;
                if (body == null) {
                    totalLocalVarSize = 0;
                }
                else {
                    totalLocalVarSize = body.totalLocalVarSize;
                }
		IR.getInstance().Add_IRcommand(new IRcommand_Func_Prolog(funcLabel, totalLocalVarSize));
		if (body != null) {
                    Temp returnTemp = null;
                    body.IRme();
                }
		IR.getInstance().Add_IRcommand(new IRcommand_addLabel(this.returnLabel));
		IR.getInstance().Add_IRcommand(new IRcommand_Func_Epilog(funcLabel, totalLocalVarSize));
		return null;
	}

        public void assignLocalVariablesIndex(AST_STMT_LIST stmt) {

            if (stmt == null) {
                return;
            }

            if (stmt.head instanceof AST_STMT_DEC_VAR){
                (((AST_STMT_DEC_VAR)stmt.head).var).localVariableIndex = ++localVariablesCounter;
            }

            else if (stmt.head instanceof AST_STMT_IF) {
                assignLocalVariablesIndex(((AST_STMT_IF)stmt.head).body);
            }

            else if (stmt.head instanceof AST_STMT_WHILE) {
                assignLocalVariablesIndex(((AST_STMT_WHILE)stmt.head).body);
            }

            assignLocalVariablesIndex(stmt.tail);


        }
	
	public TYPE SemantMe(boolean nonRecursive) throws AST_EXCEPTION
	{
		TYPE t, paramT;
		TYPE returnType = null;
		TYPE_LIST type_list = null;
		TYPE_LIST p = null;
		int paramIndex = 0;
		int scopeIndex = 0;
                if (!nonRecursive) {
                    assignLocalVariablesIndex(body);
                }

                // for(AST_STMT_LIST body1 = body; body1 != null; body1 = body1.tail){
                //     if(body1.head instanceof AST_STMT_DEC_VAR){
                //         if (!nonRecursive)  localVariablesCounter++;
                //         ((AST_STMT_DEC_VAR)body1.head).var.localVariableIndex = localVariablesCounter;
                //         AST_DEC_VAR.localVariablesCounter = localVariablesCounter;
                //     }
                // }
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
				SYMBOL_TABLE.getInstance().enterVar(it.head.name,t,++localParamCounter, SYMBOL_TABLE_ENTRY.varDefinitionType.PARAM);
			}
		}
		
		
		if(this.isMethod && fatherClass != null){
			if(!(fatherClass.isLegalMethodDec(name,new TYPE_FUNCTION(returnType,name,type_list)))){
				throw new AST_EXCEPTION(String.format("%s function overiding is illigal\n", this.name), this.lineNum);
			}
		}
		boolean shouldEnumerate;
                if (name.equals("main")) {
                    shouldEnumerate = false;
                    this.funcLabel = IRcommand.getFreshLabel("main_real", shouldEnumerate);
                }
                else {
                    if (this.isMethod && this.className != null) {
                        shouldEnumerate = false;
                        this.funcLabel = IRcommand.getFreshLabel(this.name + "_" + className, shouldEnumerate);
                    }
                    else {
                        shouldEnumerate = true;
                        this.funcLabel = IRcommand.getFreshLabel(this.name, shouldEnumerate);
                    }
                }


                TYPE_FUNCTION typeFunc = new TYPE_FUNCTION(returnType,name,type_list);
                typeFunc.setFuncLabel(funcLabel);
		SYMBOL_TABLE.getInstance().enter(name,typeFunc);
		/*******************/
		/* [4] Semant Body */
		/*******************/
        if (!nonRecursive) {
			body.SemantMe();
        }

		/*****************/
		/* [5] End Scope */
		/*****************/
		SYMBOL_TABLE.getInstance().endScope();

		/***************************************************/
		/* [6] Enter the Function Type to the Symbol Table */
		/***************************************************/
                this.returnLabel = IRcommand.getFreshLabel(this.funcLabel + "_return", false);
                body.insertReturnLabel(this.returnLabel);

        typeFunc = new TYPE_FUNCTION(returnType,name,type_list);
        typeFunc.setFuncLabel(funcLabel);
		SYMBOL_TABLE.getInstance().enter(name,typeFunc);

		/*********************************************************/
		/* [6] Return value is irrelevant for class declarations */
		/*********************************************************/
		return typeFunc;		
	}
	
}
