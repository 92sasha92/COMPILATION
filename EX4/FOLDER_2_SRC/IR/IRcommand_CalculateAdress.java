package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;

public class IRcommand_CalculateAdress extends IRcommand {

	private Temp varAddress;
	private Temp offset;
	
	public IRcommand_CalculateAdress(Temp varAddress, Temp offset){
		this.varAddress = varAddress;
		this.offset = offset;
	}
	
	@Override
	public void MIPSme() {
        boolean shouldEnumerate = false;
        String funcName = getFreshLabel("Calculate_Address", shouldEnumerate);

        sir_MIPS_a_lot.getInstance().push(varAddress);
        sir_MIPS_a_lot.getInstance().push(offset);
        sir_MIPS_a_lot.getInstance().jal(funcName);
        sir_MIPS_a_lot.getInstance().mixedMove(varAddress, "$v0");
	}


}
