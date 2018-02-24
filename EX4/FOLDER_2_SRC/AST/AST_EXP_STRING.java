package AST;

import TYPES.*;
import Temp.*;
import IR.*;

public class AST_EXP_STRING extends AST_EXP
{
	public String value;
	
	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_STRING(String value)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== exp -> STRING( %s )\n", value);
		this.value = value.replace("\"","");
	}

	/******************************************************/
	/* The printing message for a STRING EXP AST node */
	/******************************************************/
	public void PrintMe()
	{
		/*******************************/
		/* AST NODE TYPE = AST STRING EXP */
		/*******************************/
		System.out.format("AST NODE STRING( %s )\n",value);

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("STRING\n%s",value.replace('"','\'')));
	}
	public TYPE SemantMe() throws AST_EXCEPTION
	{
		return TYPE_STRING.getInstance();
        }
        public Temp IRme()
        {
            Temp t = Temp_FACTORY.getInstance().getFreshTemp();
            IR.getInstance().Add_IRcommand(new IRcommandConstString(t,value));
            return t;
        }
}
