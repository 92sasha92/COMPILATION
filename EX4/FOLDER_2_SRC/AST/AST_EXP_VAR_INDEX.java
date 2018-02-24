package AST;

import TYPES.*;
import Temp.*;
import IR.*;

public class AST_EXP_VAR_INDEX extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public AST_EXP index;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_INDEX(AST_EXP_VAR var,AST_EXP index)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		System.out.print("====================== var -> var [ exp ]\n");
		this.var = var;
		this.index = index;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST ARRAY INDEX VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (index != null) index.PrintMe();
				AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ARRAY(left)\nINDEX(right)\n"));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (index != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,index.SerialNumber);
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION {
		TYPE t = null, iType;
		TYPE_ARRAY arrayType = null;
                // this.varDefType = SYMBOL_TABLE.getInstance().findDefType(name);
                // if (this.varDefType == null) {
                //     this.varDefType = SYMBOL_TABLE_ENTRY.varDefinitionType.FIELD;
                // }
		/******************************/
		/* [1] Recursively semant var */
		/******************************/
		if (this.var != null) t = var.SemantMe();
		if (!(t instanceof TYPE_ARRAY))
		{
			throw new AST_EXCEPTION(String.format("is non-array variable\n"), this.lineNum);
		} else {
			arrayType = (TYPE_ARRAY) t;
		}

		iType = this.index.SemantMe();
		if(!(iType instanceof TYPE_INT)) {
			throw new AST_EXCEPTION(String.format("index is not an integer\n"), this.lineNum);
		}


		

		return arrayType.type;

	}
        public Temp IRme(boolean shouldLoad) {
            Temp varAddress = var.IRme(true); // do not load content
            Temp arrayOffset = index.IRme();
            Temp regWithFour = Temp_FACTORY.getInstance().getFreshTemp();
            IR.getInstance().Add_IRcommand(new IRcommandConstInt(regWithFour,4));

            boolean isAddresses = true; // to not check for overflow
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_Integers("mul",arrayOffset,arrayOffset, regWithFour,isAddresses));
            IR.getInstance().Add_IRcommand(new IRcommand_Binop_Integers("add",varAddress,varAddress, arrayOffset,isAddresses));

            if (!shouldLoad) {
                return varAddress;
            }
            else {
                Temp t = Temp_FACTORY.getInstance().getFreshTemp();
                IR.getInstance().Add_IRcommand(new IRcommand_Load(t,varAddress));

                return t;
            }
        }
}
