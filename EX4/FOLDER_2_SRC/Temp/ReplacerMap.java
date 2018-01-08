package Temp;

public class ReplacerMap implements TempMap {
	public String tempMap(Temp t) {
            for (Temp_FACTORY.reservedTemps reserved: Temp_FACTORY.reservedTemps.values()) {
                if (reserved.value == t.getSerialNumber()) {
                    return "$" + reserved.toString();
                }
            }
            int firstMipsReg = Temp_FACTORY.reservedTemps.values().length;
            for (int i = firstMipsReg; i <  firstMipsReg + Temp_FACTORY.numOfMipsRegs; i++) {
                if (i == t.getSerialNumber()) {
                    return "$t" + (i - firstMipsReg);
                }
            }
            return null;
        }
}
