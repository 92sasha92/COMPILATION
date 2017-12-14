package AST;

public class AST_EXP_NEW extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String type;
	public AST_EXP exp;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_NEW(String type,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.type = type;
		this.exp = exp;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		if(exp == null) System.out.format("NEW(%s)\n",type);
		if(exp != null) System.out.format("NEW(%s)[]\n",type);
		

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (exp != null) exp.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		if(exp == null) AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NEW(%s)\n",type));
		if(exp != null) AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("NEW(%s[])\n",type));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if(exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);		
	}
}