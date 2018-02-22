package AST;
import TYPES.*;
import Temp.*;

public class AST_STMT_METHOD extends AST_STMT
{
	/****************/
	/* DATA MEMBERS */
	/****************/
        public AST_EXP_METHOD expMethod;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_STMT_METHOD(AST_EXP_VAR var, String methodName,AST_EXP_LIST expList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();
		if(expList == null) System.out.println("================STMT -> varExp DOT ID ();");
		if(expList != null) System.out.println("================STMT -> varExp DOT ID (expList);");

                expMethod = new AST_EXP_METHOD(var, methodName, expList);
	}
	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/********************************/
		/* AST NODE TYPE = AST EXP METHOD */
		/********************************/
		System.out.format("STMT\nMETHOD\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (expMethod != null) expMethod.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STMT\nMETHOD\n"));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (expMethod != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expMethod.SerialNumber);
	}
        public TYPE SemantMe() throws AST_EXCEPTION {
            return expMethod.SemantMe();
        }

        public Temp IRme() {
            return expMethod.IRme();
        }
}
