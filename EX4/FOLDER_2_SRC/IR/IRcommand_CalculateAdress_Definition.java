package IR;

import MIPS.sir_MIPS_a_lot;
import Temp.*;


public class IRcommand_CalculateAdress_Definition extends IRcommand{

	
	
	public void MIPSme() {
	      
	      boolean shouldEnumerate = false;
	      String calculateAdress = getFreshLabel("Calculate_Adress", shouldEnumerate);

	      sir_MIPS_a_lot.getInstance().label(calculateAdress);

	      Temp varAddress = Temp_FACTORY.getInstance().getFreshTemp();
	      Temp arrayOffset = Temp_FACTORY.getInstance().getFreshTemp();
	      sir_MIPS_a_lot.getInstance().push("$ra");

	      Temp currentTemp;
	      for (int i = Temp_FACTORY.reservedTemps.values().length; i < 8 + Temp_FACTORY.reservedTemps.values().length; i++) {
	          currentTemp = Temp_FACTORY.getTemp(i);
	          sir_MIPS_a_lot.getInstance().push(currentTemp);
	      }
	      sir_MIPS_a_lot.getInstance().load(arrayOffset,"$sp",9 * 4);
	      sir_MIPS_a_lot.getInstance().load(varAddress,"$sp",10 * 4);
	      Temp regWithFour = sir_MIPS_a_lot.getInstance().initializeRegToZero();
	      sir_MIPS_a_lot.getInstance().addi(regWithFour, regWithFour, 4);
	      sir_MIPS_a_lot.getInstance().mul(arrayOffset, regWithFour, arrayOffset);
	      sir_MIPS_a_lot.getInstance().add(varAddress, varAddress, arrayOffset);


	      sir_MIPS_a_lot.getInstance().mixedMove("$v0",varAddress);


	      for (int i = Temp_FACTORY.reservedTemps.values().length + 7; i >=  Temp_FACTORY.reservedTemps.values().length; i--) {
	          currentTemp = Temp_FACTORY.getTemp(i);
	          sir_MIPS_a_lot.getInstance().pop(currentTemp);
	      }
	      sir_MIPS_a_lot.getInstance().pop("$ra");

	      sir_MIPS_a_lot.getInstance().addi("$sp","$sp",8);

	      sir_MIPS_a_lot.getInstance().jr("$ra");



		}
}
