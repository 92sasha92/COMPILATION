package TempReplacer;

import java.io.*;
import Assem.*;
import Temp.*;
import FlowGraph.*;
import RegAlloc.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TempReplacer {
	static String dirname = "FOLDER_5_OUTPUT/";
	public static final String inputFilename = dirname + "MIPS.txt";
	public static final String originalFilename = dirname + "MIPS_TEMPS.txt";
	public static InstrList last = null, instList = null;
	public static FlowGraph flowGraph = null;

	public static void ReplaceTemps() throws IOException {


		BufferedReader file_reader;
		file_reader = new BufferedReader(new FileReader(inputFilename));
		BufferedWriter originalCopy = new BufferedWriter(new FileWriter(originalFilename));
		String line;
		String tokens[] = null;
		String delims = "[ ,()]";
		String command = null;
		String labelName = null,labelDefName = null;
		TempList dstTemps = null, srcTemps = null;
		LabelList labelList = null;
		LABEL labelDefinition = null;
		boolean isPrevBranch = false;
		while ((line = file_reader.readLine()) != null) {
                    originalCopy.write(line);
                    originalCopy.newLine();
			tokens = line.replace("\t", "").split(delims);
			command = tokens[0];
			System.out.println(command);
			switch (command) {
			case "add":
			case "sub":
			case "mul":
			case "div":
				dstTemps = new TempList(getTemp(tokens[1]), null);
				srcTemps = new TempList(getTemp(tokens[2]), new TempList(getTemp(tokens[3]), null));
				OPER op = new OPER(line, dstTemps, srcTemps);
				addInstruction(op);
				break;
			case "addi":
				dstTemps = new TempList(getTemp(tokens[1]), null);
				srcTemps = new TempList(getTemp(tokens[2]), null);
				OPER addi = new OPER(line, dstTemps, srcTemps);
				addInstruction(addi);
				break;
			case "j":
				labelName = tokens[1];
				labelList = new LabelList(Label.getLabel(labelName), null);
				OPER j = new OPER(line, null, null, labelList);
				addInstruction(j);
				break;
			case "jal":
				OPER jal = new OPER(line, null, null);
				addInstruction(jal);
				break;
			case "move":
				MOVE move = new MOVE(line, getTemp(tokens[1]), getTemp(tokens[2]));
				addInstruction(move);
				break;
			case "li":
                        case "la":
				dstTemps = new TempList(getTemp(tokens[1]), null);
				OPER li = new OPER(line, dstTemps, null);
				addInstruction(li);
				break;
			case "lb":
			case "lw":
				dstTemps = new TempList(getTemp(tokens[1]), null);
				srcTemps = new TempList(getTemp(tokens[3]), null);
				OPER lw = new OPER(line, dstTemps, srcTemps);
				addInstruction(lw);
				break;
			case "sw":
			case "sb":
				srcTemps = new TempList(getTemp(tokens[1]), new TempList(getTemp(tokens[3]), null));
				OPER sw = new OPER(line, null, srcTemps);
				addInstruction(sw);
				break;
			case "blt":
			case "bge":
			case "beq":
			case "bne":
				srcTemps = new TempList(getTemp(tokens[1]), new TempList(getTemp(tokens[2]), null));
				labelName = tokens[3];
//				labelList = new LabelList(Label.getLabel(labelName), null);
//				OPER bOp = new OPER(line, null, srcTemps, labelList);
//				addInstruction(bOp);
				isPrevBranch = true;
				break;
			default:
				if (command.startsWith("Label_")) {
					labelDefName = command.substring(0, command.length() - 1);
					if(isPrevBranch == true){
						labelList = new LabelList(Label.getLabel(labelName), new LabelList(Label.getLabel(labelDefName), null));
						OPER bOp = new OPER(line, null, srcTemps, labelList);
						addInstruction(bOp);
						isPrevBranch = false;
					}
					labelDefinition = new LABEL(command, Label.getLabel(labelDefName));
					addInstruction(labelDefinition);

					break;
				}

			}
		}
                originalCopy.close();
		printInstrList();
		flowGraph = new AssemFlowGraph(instList);
		flowGraph.show(System.out);
		InterferenceGraph interGraph = new Liveness(flowGraph);
		// Frame f = new Frame();
		int numOfReservedTemps = Temp_FACTORY.reservedTemps.values().length;
		HashSet<Temp> registers = new java.util.HashSet();
		for (int i = numOfReservedTemps; i < numOfReservedTemps + Temp_FACTORY.numOfMipsRegs; i++)
			registers.add(Temp_FACTORY.getTemp(i));
		ReplacerMap replacerMap = new ReplacerMap();
		Color color = new Color(interGraph, replacerMap, registers);
		file_reader.close();
		tempReplaceToReg(color);
		// color.tempMap(color.map.temp);

	}
	
	private static void tempReplaceToReg(Color color) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader file_reader = new BufferedReader(new FileReader(inputFilename));
		String line;
		String tokens[] = null;
		String delims = "[ ,()]";
		List<String> outLines = new ArrayList<String>();
		while ((line = file_reader.readLine()) != null) {
			tokens = line.replace("\t", "").split(delims);
			int i = 1;

			while(i < tokens.length){
				if(tokens[i].startsWith("Temp_")){
					Temp temp = getTemp(tokens[i]);
					line= line.replace(tokens[i], color.tempMap(temp));
				}
				i++;
			}
			outLines.add(line);
		}
		file_reader.close();
		FileWriter fileW = new FileWriter(inputFilename, false);
		BufferedWriter bufW = new BufferedWriter(fileW);
		for(String lineOut : outLines){
			
			bufW.write(lineOut);
			bufW.newLine();
		}
		bufW.close();
	}
	
	public static Temp getTemp(String tempStr) {

		int tempNum;
		String delim = "_";
		if (tempStr.charAt(0) != '$') {
			tempNum = Integer.parseInt(tempStr.split(delim)[1]);
			return Temp_FACTORY.allTemps.get(tempNum);
		}
		return Temp_FACTORY.getTemp(Temp_FACTORY.getInstance().getReservedTempNumber(tempStr.substring(1)));
	}

	public static void addInstruction(Instr inst) {

		if (last != null) {
			last.tail = new InstrList(inst, null);
			last = last.tail;
		} else {
			instList = new InstrList(inst, null);
			last = instList;
		}
	}

	public static void printInstrList() {
		InstrList kaki = instList;
		for (; kaki != null; kaki = kaki.tail) {
			System.out.println("@@@@@@" + kaki.head.assem);
		}

	}

}
