package AST;

public class AST_DEC_CHILD extends AST_DEC{
	public int moish;
	public AST_DEC_VAR decChild;
	
	public AST_DEC_CHILD(AST_DEC_VAR decChild) {
		System.out.print("====================== dec -> varDec\n");
		this.decChild = decChild;
	}
	
	
	public void PrintMe()
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/************************************/
		/* AST NODE TYPE = EXP VAR AST NODE */
		/************************************/
		System.out.print("AST NODE EXP VAR\n");

		/*****************************/
		/* RECURSIVELY PRINT var ... */
		/*****************************/
		if (decChild != null) decChild.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"DEC\n");

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/

		if (decChild != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,decChild.SerialNumber);
			
	}
}
