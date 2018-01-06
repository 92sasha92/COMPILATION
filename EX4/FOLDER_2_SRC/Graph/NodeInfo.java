package Graph;

import java.util.HashSet;
import java.util.Set;

import FlowGraph.FlowGraph;
import Graph.Node;
import Temp.Temp;
import Temp.TempList;

public class NodeInfo {
	public Set<Temp> in = new HashSet<Temp>(); 
	public Set<Temp> out = new HashSet<Temp>();
	public Set<Temp> use = new HashSet<Temp>();
	public Set<Temp> def = new HashSet<Temp>();

	NodeInfo(TempList u, TempList d) {
		for (TempList t = u; t != null; t = t.tail)
			use.add(t.head);
		for (TempList t = d; t != null; t = t.tail)
			def.add(t.head);
	}

	public NodeInfo(Node n) {
		for (TempList t = ((FlowGraph) n.mygraph).use(n); t != null; t = t.tail)
			use.add(t.head);
		for (TempList t = ((FlowGraph) n.mygraph).def(n); t != null; t = t.tail)
			def.add(t.head);
	}
}
