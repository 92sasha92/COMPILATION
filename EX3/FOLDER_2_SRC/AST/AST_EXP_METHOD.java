package AST;

public class AST_EXP_METHOD extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public AST_EXP_VAR var;
	public AST_EXP_LIST expList;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_METHOD(AST_EXP_VAR var,AST_EXP_LIST expList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.var = var;
		this.expList = expList;
	}
	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe()
	{
		/********************************/
		/* AST NODE TYPE = AST EXP METHOD */
		/********************************/
		System.out.print("AST NODE EXP METHOD\n");

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (var != null) var.PrintMe();
		if (expList != null) expList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"EXP\nMETHOD\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (expList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expList.SerialNumber);
	}
}
