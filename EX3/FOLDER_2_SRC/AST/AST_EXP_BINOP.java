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
		TYPE leftType = null;
		TYPE rightType = null;
		TYPE_CLASS classLeftType = null, classRightType = null;
		TYPE leftArrayType, rightArrayType;
		if (left  != null) leftType = left.SemantMe();
		if (right != null) rightType = right.SemantMe();
		//op = 6 is "="
		if(OP == 6){
			if(leftType == TYPE_NIL.getInstance() || rightType == TYPE_NIL.getInstance()){
				if(leftType instanceof TYPE_INT || rightType instanceof TYPE_INT 
				|| leftType instanceof TYPE_STRING || rightType instanceof TYPE_STRING){
					throw new AST_EXCEPTION("Primitive type cannot be compared to null", this.lineNum);
				} else {
					return TYPE_INT.getInstance();
				}
			} else if(leftType instanceof TYPE_CLASS && rightType instanceof TYPE_CLASS) {
				classLeftType = (TYPE_CLASS) leftType;
				classRightType = (TYPE_CLASS) rightType;
				if(!(classLeftType.isSonOf(classRightType.name)) && !(classRightType.isSonOf(classLeftType.name))) {
					throw new AST_EXCEPTION(String.format("%s is not a child class of %s and vice versa", classLeftType.name, classRightType.name), this.lineNum);
				}
			}else if(leftType instanceof TYPE_ARRAY && rightType instanceof TYPE_ARRAY) {
				leftArrayType = ((TYPE_ARRAY)leftType).type;
				rightArrayType = ((TYPE_ARRAY)rightType).type;
				if(leftArrayType instanceof TYPE_CLASS && rightArrayType instanceof TYPE_CLASS){
					classLeftType = (TYPE_CLASS) leftArrayType;
					classRightType = (TYPE_CLASS) rightArrayType;
					if(!(classLeftType.isSonOf(classRightType.name)) && !(classRightType.isSonOf(classLeftType.name))) {
						throw new AST_EXCEPTION(String.format("%s is not a child class of %s and vice versa", classLeftType.name, classRightType.name), this.lineNum);
					}
					if(!(((TYPE_ARRAY)leftType).name.equals(((TYPE_ARRAY)rightType).name))){
						throw new AST_EXCEPTION("Type mismatch for equal\n", this.lineNum);
					}
				} else if(leftArrayType != classRightType || !(((TYPE_ARRAY)leftType).name.equals(((TYPE_ARRAY)rightType).name))){
					throw new AST_EXCEPTION("Type mismatch for equal\n", this.lineNum);
				}
			}else if(leftType != rightType){
				throw new AST_EXCEPTION("Non matching types", this.lineNum);
			} else {
				return TYPE_INT.getInstance();
			}
			return TYPE_INT.getInstance();
		} else if ((leftType == TYPE_INT.getInstance()) && (rightType == TYPE_INT.getInstance())) {
			return TYPE_INT.getInstance();
		} else if((leftType == TYPE_STRING.getInstance()) && (rightType == TYPE_STRING.getInstance()) && OP == 0) {
			return TYPE_STRING.getInstance();
		} else {
			throw new AST_EXCEPTION("Non matching types", this.lineNum);
		}
	}

}
