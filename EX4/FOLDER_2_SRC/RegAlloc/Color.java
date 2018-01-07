package RegAlloc;

import Graph.*;
import Temp.*;
import java.util.*;

public class Color implements TempMap {
	private Stack selStack = new Stack();
	private Hashtable<Temp, Temp> map = new Hashtable<Temp, Temp>();
	private TempMap init;

	// public TempList spills();

	public String tempMap(Temp t) {

		return init.tempMap(map.get(t));
	}

	public Color(InterferenceGraph interGraph, TempMap init, HashSet registers) {

		HashSet regs = new HashSet(registers);
		this.init = init;

		int number = 0;

		for (NodeList nodes = interGraph.nodes(); nodes != null; nodes = nodes.tail) {

			++number;
			Temp temp = interGraph.gtemp(nodes.head);

			if (init.tempMap(temp) != null) {

				--number;
				selStack.add(nodes.head);

				map.put(temp, temp);

				for (NodeList adj = nodes.head.succ(); adj != null; adj = adj.tail)
					interGraph.rmEdge(nodes.head, adj.head);

			}
		}

		for (int i = 0; i < number; ++i) {
			Node node = null;
			int max = -1;

			for (NodeList n = interGraph.nodes(); n != null; n = n.tail)

				if (init.tempMap(interGraph.gtemp(n.head)) == null && !selStack.contains(n.head)) {

					int num = n.head.outDegree(); 
					if (max < num && num < regs.size()) {

						max = num;
						node = n.head;
					}
				}
			if (node == null) {

				System.err.println("Color.color() :");
				break;
			}

			selStack.add(node);

			for (NodeList adj = node.pred(); adj != null; adj = adj.tail)
				if (!selStack.contains(adj.head))
					interGraph.rmEdge(adj.head, node);
		}

		for (int i = 0; i < number; ++i) {
			Node node = (Node) selStack.pop();

			Set available = new HashSet(regs);

			for (NodeList adj = node.succ(); adj != null; adj = adj.tail) {
				available.remove(map.get(interGraph.gtemp(adj.head)));

			}
			Temp reg = (Temp) available.iterator().next();

			map.put(interGraph.gtemp(node), reg);

		}
	}
}
