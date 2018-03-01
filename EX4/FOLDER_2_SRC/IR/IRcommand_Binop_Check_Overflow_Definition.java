/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import Temp.*;
import MIPS.*;

public class IRcommand_Binop_Check_Overflow_Definition extends IRcommand
{

    /***************/
    /* MIPS me !!! */
    /***************/
    public void MIPSme()
    {

        boolean shouldEnumerate = false;
        String checkOverflow = getFreshLabel("Check_Overflow", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().label(checkOverflow);


        Temp dst = Temp_FACTORY.getInstance().getFreshTemp();

        sir_MIPS_a_lot.getInstance().push("$ra");

        Temp currentTemp;
        for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().push(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().load(dst,"$sp",9 * 4);

        /******************************************/
        /* [1] Allocate a fresh temporary INT_MAX */
        /******************************************/
        Temp intMax = Temp_FACTORY.getInstance().getFreshTemp();
        Temp intMin = Temp_FACTORY.getInstance().getFreshTemp();
        /************************************/
        /* [2] intMax := 32767 (= 2^15 - 1) */
        /*	   intMin := -32768 (= -2^15)	*/
        /************************************/
        sir_MIPS_a_lot.getInstance().li(intMax,32767);
        sir_MIPS_a_lot.getInstance().li(intMin,-32768);
        /****************************************************/
        /* [3] Allocate a fresh label for possible overflow */
        /****************************************************/
        String label_end = getFreshLabel("end");
        String label_overflow_pos = getFreshLabel("overflow_pos");
        String label_overflow_neg = getFreshLabel("overflow_neg");


        /*********************************************************/
        /* [5] if (32767 <  t1_plus_t2) goto label_overflow;    */
        /*      if (t1_plus_t2 < -32768) goto label_overflow;   */
        /*     if (32767 >= t1_plus_t2 && t1_plus_t2 >= -32768)*/ 
        /*			goto label_no_overflow;                      */
        /*********************************************************/
        sir_MIPS_a_lot.getInstance().addBranch("blt", intMax, dst, label_overflow_pos);
        sir_MIPS_a_lot.getInstance().addBranch("blt", dst, intMin, label_overflow_neg);
        sir_MIPS_a_lot.getInstance().addBranch("bge", intMax, dst, label_end);

        /***********************/
        /* [6] label_overflow: */
        /*                     */
        /*         t3 := 32767 */
        /*         goto end;   */
        /*                     */
        /***********************/
        sir_MIPS_a_lot.getInstance().label(label_overflow_pos);
        sir_MIPS_a_lot.getInstance().li(dst,32767);
        sir_MIPS_a_lot.getInstance().jump(label_end);

        /***************************/
        /* [7] label_overflow_neg: */
        /*                         */
        /*         t3 := 32767     */
        /*         goto end;       */
        /*                         */
        /***************************/
        sir_MIPS_a_lot.getInstance().label(label_overflow_neg);
        sir_MIPS_a_lot.getInstance().li(dst,-32768);
        sir_MIPS_a_lot.getInstance().jump(label_end);

        /******************/
        /* [8] label_end: */
        /******************/
        sir_MIPS_a_lot.getInstance().label(label_end);		

        sir_MIPS_a_lot.getInstance().mixedMove("$v0",dst);


        for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
            currentTemp = Temp_FACTORY.getTemp(i);
            sir_MIPS_a_lot.getInstance().pop(currentTemp);
        }
        sir_MIPS_a_lot.getInstance().pop("$ra");

        sir_MIPS_a_lot.getInstance().addi("$sp","$sp",4);

        sir_MIPS_a_lot.getInstance().jr("$ra");
    } 


}
