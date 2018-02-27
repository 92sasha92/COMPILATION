package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;

public class IRcommand_Check_Invalid_Pointer extends IRcommand {

	private Temp varAddress;
	
	public IRcommand_Check_Invalid_Pointer(Temp varAddress){
		this.varAddress = varAddress;
	}
	
	@Override
	public void MIPSme() {
        boolean shouldEnumerate = false;
        String funcName = getFreshLabel("Check_Invalid_Pointer_Definition", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().push(varAddress);
        sir_MIPS_a_lot.getInstance().jal(funcName);
	}


}
