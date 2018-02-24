/***********/
/* PACKAGE */
/***********/
package ExtraFunctions;

/*******************/
/* GENERAL IMPORTS */
/*******************/
import java.util.*;
import IR.*;

/*******************/
/* PROJECT IMPORTS */
/*******************/

public class ExtraFunctions 
{

    public LinkedList<IRcommand> extraFunctions;
    private static ExtraFunctions instance = null;
    /*****************/
    /* Label Factory */
    /*****************/
    public ExtraFunctions ()
    {
        extraFunctions = new LinkedList<IRcommand>();
    }
    public void addFunction(IRcommand newcmd)
    {
        for (IRcommand cmd : extraFunctions ) {
            if (cmd.getClass().getSimpleName().equals(newcmd.getClass().getSimpleName())) {
                return; // already exists in list
            }
        }
        extraFunctions.add(newcmd);

    }
    public void writeFunctions() {
        for (IRcommand cmd : extraFunctions ) {
            IR.getInstance().Add_IRcommand(cmd);
        }
    }

    public static ExtraFunctions getInstance() {
        if (instance == null)
        {
            /*******************************/
            /* [0] The instance itself ... */
            /*******************************/
            instance = new ExtraFunctions();
        }
        return instance;

    }
}
