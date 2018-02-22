package AST;

import TYPES.*;
import Temp.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_FIELD extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public String fieldName;
        public int fieldOffset;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_FIELD(AST_EXP_VAR var,String fieldName)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== var -> var DOT ID( %s )\n",fieldName);
		this.var = var;
		this.fieldName = fieldName;
                this.fieldOffset = 0;
	}

	/*************************************************/
	/* The printing message for a field var AST node */
	/*************************************************/
	public void PrintMe()
	{
		/*********************************/
		/* AST NODE TYPE = AST FIELD VAR */
		/*********************************/
		System.out.format("FIELD\nNAME\n(___.%s)\n",fieldName);

		/**********************************************/
		/* RECURSIVELY PRINT VAR, then FIELD NAME ... */
		/**********************************************/
		if (var != null) var.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("FIELD\nVAR\n___.%s",fieldName));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);		
	}
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		TYPE t = null;
		TYPE_CLASS tc = null;
		this.varDefType = SYMBOL_TABLE.getInstance().findDefType(fieldName);
                if (this.varDefType == null) {
                    this.varDefType = SYMBOL_TABLE_ENTRY.varDefinitionType.FIELD;
                }
		/******************************/
		/* [1] Recursively semant var */
		/******************************/
		if (var != null) t = var.SemantMe();

		/*********************************/
		/* [2] Make sure type is a class */
		/*********************************/
		if (!(t instanceof TYPE_CLASS))
		{
			throw new AST_EXCEPTION(String.format("access %s field of a non-class variable\n", fieldName), this.lineNum);
		}
		else
		{
			tc = (TYPE_CLASS) t;
		}
		
		/************************************/
		/* [3] Look for fiedlName inside the class hierarchy */
		/************************************/
        for (TYPE_CLASS classType = tc; classType != null ; classType = classType.father) {
			for (TYPE_LIST it = classType.data_members; it != null; it = it.tail)
			{
				if (((TYPE_VAR_DEC)(it).head).name.equals(fieldName))
				{
					return ((TYPE_VAR_DEC)it.head).t;
				}
                                fieldOffset += 4;
			}
        }
		
		/*********************************************/
		/* [4] fieldName does not exist in class var */
		/*********************************************/
		throw new AST_EXCEPTION(String.format("field %s does not exist in class\n", fieldName), this.lineNum);
	}
        public Temp IRme(boolean shouldLoad)
        {
            // implement me
            return null;
        }
}
