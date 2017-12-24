package AST;

import TYPES.*;

public class AST_STMT_ASSIGN extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_EXP_VAR var;
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_ASSIGN(AST_EXP_VAR var,AST_EXP exp)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== stmt -> var ASSIGN exp SEMICOLON\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.exp = exp;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		/********************************************/
		/* AST NODE TYPE = AST ASSIGNMENT STATEMENT */
		/********************************************/
		System.out.print("AST NODE ASSIGN STMT\n");

		/***********************************/
		/* RECURSIVELY PRINT VAR + EXP ... */
		/***********************************/
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"ASSIGN\nleft := right\n");
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (var != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (exp != null)AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,exp.SerialNumber);
	}
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		TYPE varType = null;
		TYPE expType = null;
				TYPE_CLASS classVarType = null, classExpType = null;
		TYPE varArrayType, expArrayType;
		if (var != null) varType = var.SemantMe();
		if (exp != null){
			expType = exp.SemantMe();
		} else{
			return null;
		}
		
		if(expType == TYPE_NIL.getInstance()){
			if(varType instanceof TYPE_INT || varType instanceof TYPE_STRING){
				throw new AST_EXCEPTION("Primitive type cannot be defined to be nil", this.lineNum);
			}	
		} else if(varType instanceof TYPE_CLASS && expType instanceof TYPE_CLASS) {
			classVarType = (TYPE_CLASS) varType;
			classExpType = (TYPE_CLASS) expType;
			if(!(classExpType.isSonOf(classVarType.name))) {
				throw new AST_EXCEPTION(String.format("%s is not a child class of %s", classExpType.name, classVarType.name), this.lineNum);
			}
		} else if(varType instanceof TYPE_ARRAY && expType instanceof TYPE_ARRAY) {
			varArrayType = ((TYPE_ARRAY)varType).type;
			expArrayType = ((TYPE_ARRAY)expType).type;
			if(varArrayType instanceof TYPE_CLASS && ((TYPE_ARRAY)expType).type instanceof TYPE_CLASS){
				classVarType = (TYPE_CLASS) varArrayType;
				classExpType = (TYPE_CLASS) expArrayType;
				if(!(classExpType.isSonOf(classVarType.name))) {
					throw new AST_EXCEPTION(String.format("%s is not a child class of %s", classExpType.name, classVarType.name), this.lineNum);
				}
			} else if(varArrayType != expArrayType || ((TYPE_ARRAY)varType).name != ((TYPE_ARRAY)expType).name){
				throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
			}
		} else if(varType != expType) {
			throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
		}
		return null;
	}
}
