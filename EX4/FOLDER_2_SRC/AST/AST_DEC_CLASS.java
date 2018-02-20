package AST;

import TYPES.*;
import SYMBOL_TABLE.*;
import Temp.*;
import IR.*;

public class AST_DEC_CLASS extends AST_DEC
{
	/********/
	/* NAME */
	/********/
	public String className;
	public String extendName = null;
        public int localVariablesCounter;


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
                this.localVariablesCounter = 0;
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
		TYPE classType, extendsType = null;

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
		SYMBOL_TABLE.getInstance().beginClassScope(null);

		/***************************/
		/* [4] Semant Data Members */
		/***************************/

        /* GUY - first we add TYPE_CLASS to the scope with no members and methods */
		SYMBOL_TABLE.getInstance().enter(className,new TYPE_CLASS((TYPE_CLASS)extendsType, className, null, null));

        /* GUY - Now we can semant the members and they will recognize the type of the class because it's in the scope
        * BUT - we pass true to tell the SemantMe's of FUNC_DEC and VAR_DEC to NOT semant the body,parameters,expressions etc - because it will cause problems if they
        * try to find a member of this class, because we inserted a class without members to the scope*/
        TYPE_CLASS cT = this.SemantClassMembers(true, (TYPE_CLASS)extendsType);

        /* GUY - now cT contains the TYPE_CLASS *WITH* all the members and methods, but we should semant them as well because we didn't do it the first time */
		SYMBOL_TABLE.getInstance().endScope();
		SYMBOL_TABLE.getInstance().beginClassScope(cT);
		SYMBOL_TABLE.getInstance().enter(className,cT);
        /* passing false tells the SemantMe's to Semant everything recursively */
        cT = this.SemantClassMembers(false, (TYPE_CLASS)extendsType);

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
    public TYPE_CLASS SemantClassMembers(boolean nonRecursive, TYPE_CLASS extendsType) throws AST_EXCEPTION{
			TYPE_LIST param_List=null;
            TYPE_LIST method_List=null;
            TYPE_LIST p=  null;
            TYPE t = null;
            for (AST_CFIELD_LIST l = this.data_members; l != null;l = l.tail){
                if(l.head.varDec != null){
					l.head.varDec.setIsFieldToTrue();
					l.head.varDec.setFatherClass(extendsType);
                                        if (!nonRecursive) { // we should run this line only once per field
					    l.head.varDec.localVariableIndex = ++localVariablesCounter;
                                        }
                    t = l.head.varDec.SemantMe(nonRecursive);
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
					l.head.funcDec.setIsMethodToTrue();
					l.head.funcDec.setFatherClass(extendsType);
					l.head.funcDec.setClassName(className);
                    t = l.head.funcDec.SemantMe(nonRecursive);
                    if (method_List == null){
                        method_List = new TYPE_LIST(t, null);
                        p = method_List;
                    } else {
                        p.tail = new TYPE_LIST(t, null); 
                        p = p.tail;
                    }
                }


            }


            return new TYPE_CLASS(extendsType,className, param_List, method_List);
    }

    public Temp IRme() {

        // build the virtualMethodTable
	    TYPE_CLASS classType = null,extendsType = null;
	    classType = (TYPE_CLASS)SYMBOL_TABLE.getInstance().find(className);
            String tableName = "class_" + this.className + "_table";
            IR.getInstance().Add_IRcommand(new IRcommand_addLabel(tableName));
            for (String funcName : classType.virtualMethodTable.keySet()) {
                IR.getInstance().Add_IRcommand(new IRcommand_addWord(funcName + "_" + classType.virtualMethodTable.get(funcName)));
            }

            // IR all the functions
            for (AST_CFIELD_LIST l = this.data_members; l != null;l = l.tail){
                if(l.head.funcDec != null){
                    l.head.funcDec.IRme();
                }
            }
	

        return null;
    }

}
