package AST;

import IR.*;
import Temp.*;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_SIMPLE extends AST_EXP_VAR
{
	/************************/
	/* simple variable name */
	/************************/
	public String name;

	
	
	/************************************************/
	/* PRIMITIVE AD-HOC COUNTER FOR LOCAL VARIABLES */
	/************************************************/
	public static int localVariablesCounter = 0;
	public int VariableIndex = 0;
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_SIMPLE(String name)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== var -> ID( %s )\n",name);
		this.name = name;
	}

	/**************************************************/
	/* The printing message for a simple var AST node */
	/**************************************************/
	public void PrintMe()
	{
		/**********************************/
		/* AST NODE TYPE = AST SIMPLE VAR */
		/**********************************/
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}
	public TYPE SemantMe()
	{
            regsNeeded = 1;
		TYPE classScope = SYMBOL_TABLE.getInstance().findClassBoundry();
		TYPE_CLASS classType = null;
		TYPE field = null;
		TYPE varType = SYMBOL_TABLE.getInstance().find(name);
                this.varDefType = SYMBOL_TABLE.getInstance().findDefType(name);
                if (this.varDefType == null) {
                    this.varDefType = SYMBOL_TABLE_ENTRY.varDefinitionType.FIELD;
                }
		int varIndex = SYMBOL_TABLE.getInstance().findIndex(name);
		VariableIndex = SYMBOL_TABLE.getInstance().findVarIndex(name);
		int scopeIndex = SYMBOL_TABLE.getInstance().findIndex("SCOPE-BOUNDARY");
		int classIndex = SYMBOL_TABLE.getInstance().findClassBoundryIndex();
		if(classScope != null){
			classType = (TYPE_CLASS)((TYPE_FOR_SCOPE_BOUNDARIES)classScope).returnType;
			field = classType.getField(this.name);
			if(field != null){
                                if (varType == null) {
                                    VariableIndex = classType.getFieldIndex(this.name);
                                    if (VariableIndex < 0) {
                                        System.out.println("ERROR!!!!!!");
                                        return null;
                                    }
                                }
				if(classIndex <= scopeIndex && varType != null && varType.name.equals(((TYPE_VAR_DEC)field).t.name)){
					return varType;
				}
				if(classIndex == scopeIndex || varType == null || varIndex < scopeIndex){
					return ((TYPE_VAR_DEC)field).t;
				}	
			} 
	   } 
		
		return varType;
		
	}
	
	public Temp IRme(boolean shouldLoad)
	{
		Temp varAddress  = Temp_FACTORY.getInstance().getFreshTemp();
                if (varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.LOCAL) {
		    IR.getInstance().Add_IRcommand(new IRcommand_AdressStackAlloc(VariableIndex, varAddress));
                }
                else if (varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.PARAM) {
		    IR.getInstance().Add_IRcommand(new IRcommand_LoadParamToTemp(VariableIndex, varAddress));
                }
                else if (varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.FIELD){
		    IR.getInstance().Add_IRcommand(new IRcommand_LoadParamToTemp(1, varAddress)); // instace address is the first parameter
		    IR.getInstance().Add_IRcommand(new IRcommand_Load(varAddress, varAddress)); // load the address from the stack
                    IR.getInstance().Add_IRcommand(new IRcommand_Addi(varAddress,varAddress, VariableIndex * 4)); // note that localVariableIndex starts with 1, and this is good because we need to skip the virtualMethodTable
                }
                else if (varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.GLOBAL){
                    boolean shouldCreate = false;
                    IR.getInstance().Add_IRcommand(new IRcommand_GlobalVariable(varAddress, this.name, shouldCreate));
                }
                else {
                    System.out.println("ERROR!!!!!!!");
                    return null;
                }
                if (!shouldLoad) {
                    return varAddress;
                }
                else {
                    Temp t = Temp_FACTORY.getInstance().getFreshTemp();
                    IR.getInstance().Add_IRcommand(new IRcommand_Load(t,varAddress));

                    return t;
                }
	}
}
