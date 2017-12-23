package AST;

import TYPES.*;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== exp -> exp BINOP exp\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.left = left;
		this.right = right;
		this.OP = OP;
	}
	
	/*************************************************/
	/* The printing message for a binop exp AST node */
	/*************************************************/
	public void PrintMe()
	{
		String sOP="";
		
		/*********************************/
		/* CONVERT OP to a printable sOP */
		/*********************************/
		if (OP == 0) {sOP = "+";}
		if (OP == 1) {sOP = "-";}
		if (OP == 2) {sOP = "*";}
		if (OP == 3) {sOP = "/";}
		if (OP == 4) {sOP = "<";}
		if (OP == 5) {sOP = ">";}
		if (OP == 6) {sOP = "=";}

		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE BINOP EXP\n");
		System.out.format("BINOP EXP(%s)\n",sOP);

		/**************************************/
		/* RECURSIVELY PRINT left + right ... */
		/**************************************/
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("BINOP(%s)",sOP));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,right.SerialNumber);
	}
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();
		//op = 6 is "="
		if(OP == 6){
			if(t1 == TYPE_NIL.getInstance() || t2 == TYPE_NIL.getInstance()){
				if(t1 instanceof TYPE_INT || t2 instanceof TYPE_INT 
				|| t1 instanceof TYPE_STRING || t2 instanceof TYPE_STRING){
					throw new AST_EXCEPTION("Primitive type cannot be compared to null", this.lineNum);
				} else {
					return TYPE_INT.getInstance();
				}
			} else if(t1 != t2){
				if(t2.getClass().isAssignableFrom(t1.getClass()) || t1.getClass().isAssignableFrom(t2.getClass())  ){
					return TYPE_INT.getInstance();
				} else {
					throw new AST_EXCEPTION("Non matching types", this.lineNum);
				}
			} else {
				return TYPE_INT.getInstance();
			}

		} else if ((t1 == TYPE_INT.getInstance()) && (t2 == TYPE_INT.getInstance())) {
			return TYPE_INT.getInstance();
		} else if((t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()) && OP == 0) {
			return TYPE_STRING.getInstance();
		} else {
			throw new AST_EXCEPTION("Non matching types", this.lineNum);
		}
	}

}
