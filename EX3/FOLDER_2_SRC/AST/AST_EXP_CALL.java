package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_CALL extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String funcName;
	public AST_EXP_LIST params;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_CALL(String funcName,AST_EXP_LIST params)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.funcName = funcName;
		this.params = params;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		System.out.format("CALL(%s)\nWITH:\n",funcName);

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (params != null) params.PrintMe();
		
		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CALL(%s)\nWITH",funcName));
		
		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,params.SerialNumber);		
	}
	
	public TYPE SemantMe() throws AST_EXCEPTION {
				
		TYPE funcType = null;
		TYPE_LIST typeList = null;
		TYPE paramType = null;
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
		funcType = SYMBOL_TABLE.getInstance().find(funcName);
		if (funcType == null)
		{
			throw new AST_EXCEPTION(String.format("Non existing function %s\n", funcName), this.lineNum);
		} else if(!(funcType instanceof TYPE_FUNCTION)) {
			throw new AST_EXCEPTION(String.format("'%s' is not a function type\n", funcType.name), this.lineNum);
		}
		typeList = ((TYPE_FUNCTION)funcType).params;
		for (AST_EXP_LIST it = params; it  != null; it = it.tail){
			paramType = it.head.SemantMe();
			if(typeList == null){
				throw new AST_EXCEPTION(String.format("Too many arguments for function %s", funcName), this.lineNum);
			} else if(paramType == TYPE_NIL.getInstance()){
			    if(typeList.head instanceof TYPE_INT || typeList.head instanceof TYPE_STRING){
				   throw new AST_EXCEPTION("Primitive type of an argument cannot be defined to be nil", this.lineNum);
			    }
			    return null;	
			} else if(paramType != typeList.head) {
				if(paramType.getClass().isAssignableFrom(typeList.head.getClass())){
					return null;
				} else {
					throw new AST_EXCEPTION("Type mismatch for call func with wrong parameters;\n", this.lineNum);
				}
			}

			typeList = typeList.tail;
		}
		if(typeList != null){
			throw new AST_EXCEPTION(String.format("Not enouth arguments for function %s", funcName), this.lineNum);
		}
		
		return ((TYPE_FUNCTION)funcType).returnType;
	}
}
