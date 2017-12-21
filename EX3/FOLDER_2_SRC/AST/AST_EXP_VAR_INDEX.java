package AST;

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
}
