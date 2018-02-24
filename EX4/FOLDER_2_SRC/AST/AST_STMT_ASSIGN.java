package AST;

import TYPES.*;
import Temp.*;
import IR.*;
import SYMBOL_TABLE.*;

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
		if (var != null) varType = var.SemantMe();
		if (exp != null){
			expType = exp.SemantMe();
		} else{
			return null;
		}
		typesCheck(varType, expType);
		return null;
        }
        public Temp IRme() {

            boolean shouldLoad = false;
            Temp t = null;
            Temp toStoreAddress = null;
            if (var instanceof AST_EXP_VAR_SIMPLE) {
                t = exp.IRme();
                toStoreAddress  = var.IRme(shouldLoad);
            }
            else {
                toStoreAddress  = var.IRme(shouldLoad);
                t = exp.IRme();
            }
            IR.getInstance().Add_IRcommand(new IRcommand_Store(toStoreAddress,t));
            return null;

            // if (var instanceof AST_EXP_VAR_SIMPLE) {
            //     if (((AST_EXP_VAR_SIMPLE)var).varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.LOCAL) {
            //         // get the temp with the address of the value

            //         // calculate address to store the value in
            //         Temp toStoreAddress  = Temp_FACTORY.getInstance().getFreshTemp();
            //         IR.getInstance().Add_IRcommand(new IRcommand_AdressStackAlloc(((AST_EXP_VAR_SIMPLE)var).VariableIndex, toStoreAddress));

            //         // store the content of exp to the address
            //         IR.getInstance().Add_IRcommand(new IRcommand_Store(toStoreAddress,t));
            //         return null;
            //     }
            //     else if (((AST_EXP_VAR_SIMPLE)var).varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.FIELD) {
            //             Temp toStoreAddress  = Temp_FACTORY.getInstance().getFreshTemp();

		        // IR.getInstance().Add_IRcommand(new IRcommand_LoadParamToTemp(1, toStoreAddress)); // the instance address parameter is the first parameter
		        // IR.getInstance().Add_IRcommand(new IRcommand_Load(toStoreAddress, toStoreAddress)); // load the instance address from the stack
            //             IR.getInstance().Add_IRcommand(new IRcommand_Addi(toStoreAddress,toStoreAddress, ((AST_EXP_VAR_SIMPLE)var).VariableIndex * 4)); // note that localVariableIndex starts with 1, and this is good because we need to skip the virtualMethodTable
            //             // store the content of exp to the address
            //             IR.getInstance().Add_IRcommand(new IRcommand_Store(toStoreAddress,t));
            //             return null;
            //     }
            //     else if (((AST_EXP_VAR_SIMPLE)var).varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.PARAM) { // is this even possible?
            //         return null;
            //     }
            //     else if (((AST_EXP_VAR_SIMPLE)var).varDefType == SYMBOL_TABLE_ENTRY.varDefinitionType.GLOBAL) { // is this even possible?
            //         return null;
            //     }

            // }
            // else if (var instanceof AST_EXP_VAR_FIELD) {
            //     // get the AST_EXP_VAR inside of the AST_EXP_VAR_FIELD, get the address of the object in the heap that it points to, use the fieldOffset to calculate the address...
            // }
            // else if (var instanceof AST_EXP_VAR_INDEX) {
            //     return null;
            // }
            // System.out.println("ERROR!!!");
            // return null;
        }

        // public void setInstanceAddress(Temp tempWithAddr) {
        //     this.classInstanceAddress = tempWithAddr;
        // }
}
