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

    // AST_EXP_CALL is sometimes used from AST_STMT_METHOD, in that case we already have the TYPE_FUNCTION and we should use it
    public TYPE_FUNCTION stmtMethod;

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

    public void setStmtMethod(TYPE_FUNCTION stmtMethod) {
        this.stmtMethod = stmtMethod;
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
		TYPE_CLASS classTypeArg = null, classType = null;
		TYPE varArrayType, expArrayType;
		/****************************/
		/* [1] Check If Type exists */
		/****************************/
       if (stmtMethod == null) {
            funcType = SYMBOL_TABLE.getInstance().find(funcName);
            if (funcType == null)
            {
				throw new AST_EXCEPTION(String.format("Non existing function %s\n", funcName), this.lineNum);
            } else if(!(funcType instanceof TYPE_FUNCTION)) {
                throw new AST_EXCEPTION(String.format("'%s' is not a function type\n", funcType.name), this.lineNum);
            }
        }
        else {
			funcType = stmtMethod;
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
			} else if(typeList.head instanceof TYPE_CLASS && paramType instanceof TYPE_CLASS){
				classTypeArg = (TYPE_CLASS) paramType;
				classType = (TYPE_CLASS) typeList.head;
				if(!(classTypeArg.isSonOf(classType.name))) {
					throw new AST_EXCEPTION(String.format("%s is not a child class of %s", classTypeArg.name, classType.name), this.lineNum);
				}
			} else if(typeList.head instanceof TYPE_ARRAY && paramType instanceof TYPE_ARRAY) {
				varArrayType = ((TYPE_ARRAY)typeList.head).type;
				expArrayType = ((TYPE_ARRAY)paramType).type;
				if(varArrayType instanceof TYPE_CLASS && expArrayType instanceof TYPE_CLASS){
					classTypeArg = (TYPE_CLASS) varArrayType;
					classType = (TYPE_CLASS) expArrayType;
					if(!(classTypeArg.isSonOf(classType.name))) {
						throw new AST_EXCEPTION(String.format("%s is not a child class of %s", classTypeArg.name, classType.name), this.lineNum);
					}
					System.out.println("left: "+((TYPE_ARRAY)typeList.head).name + " right: " + ((TYPE_ARRAY)paramType).name);
					System.out.println("left: "+((TYPE_ARRAY)typeList.head).type.name + " right: " + ((TYPE_ARRAY)paramType).type.name);
					if(!(varArrayType.name.equals(expArrayType.name))){
						throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
					}
					if(!(((TYPE_ARRAY)paramType).name.equals(expArrayType.name))){
						if(!(((TYPE_ARRAY)typeList.head).name.equals(((TYPE_ARRAY)paramType).name))){
							throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
						}
					}
				} else if(varArrayType != expArrayType){	
					throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
				} else {
					if(it.head instanceof AST_EXP_NEW){
						if(!(((TYPE_ARRAY)typeList.head).type.name.equals(((TYPE_ARRAY)paramType).name))){
							throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
						}
					} else if(!(((TYPE_ARRAY)typeList.head).name.equals(((TYPE_ARRAY)paramType).name))){
						throw new AST_EXCEPTION("Type mismatch for type var := exp;\n", this.lineNum);
					}
				}
			} else if(paramType != typeList.head) {
				if(paramType instanceof TYPE_ARRAY){
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
