package AdjacencyList;

import Abstraction.AbstractListGraph;
import Abstraction.IDirectedGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

import java.util.*;

public class DirectedGraph extends AbstractListGraph<DirectedNode> implements IDirectedGraph {

	private static int _DEBBUG =0;
		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<DirectedNode>();
	}

    public DirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<DirectedNode>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,0);
                    nn.getPreds().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public DirectedGraph(DirectedGraph g) {
        super();
        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        for(DirectedNode n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : g.getNodes()) {
        	DirectedNode nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn,0);
                snn.getPreds().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public boolean isArc(DirectedNode from, DirectedNode to) {
        return getNodeOfList(from).getSuccs().containsKey(getNodeOfList(to));
    }

    @Override
    public void removeArc(DirectedNode from, DirectedNode to) {
        if (isArc(from, to)) {
            getNodeOfList(from).getSuccs().remove(getNodeOfList(to));
            getNodeOfList(to).getPreds().remove(getNodeOfList(from));
        }
    }

    @Override
    public void addArc(DirectedNode from, DirectedNode to) {
        if (!isArc(from, to)) {
            getNodeOfList(from).getSuccs().put(getNodeOfList(to), 1);
            getNodeOfList(to).getPreds().put(getNodeOfList(from), 1);
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public DirectedNode makeNode(int label) {
        return new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public DirectedNode getNodeOfList(DirectedNode src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {
        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
                int IndSucc = j.getLabel();
                matrix[i][IndSucc] = 1;
            }
        }
        return matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {
        DirectedGraph g = new DirectedGraph();
        g.order = this.getNbNodes();
        g.m = this.getNbArcs();
        for(DirectedNode n : this.getNodes()) {
            g.nodes.add(makeNode(n.getLabel()));
        }
        for (DirectedNode n : this.getNodes()) {
            DirectedNode nn = g.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = g.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn,0);
                snn.getPreds().put(nn,0);
            }
        }
        return g;
    }

    public boolean stronglyConnected() {

        for(DirectedNode node: this.getNodes()) {
            boolean[] visited = new boolean[this.getNbNodes()];

            DFS(this, node.getLabel(), visited);

            for (boolean b: visited)
            {
                if (!b) {
                    return false;
                }
            }
        }

        return true;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    public void parcoursLargeur(){
        HashMap<Integer, Boolean> mark = new HashMap<>();
        for (DirectedNode node:getNodes()) {
            mark.put(node.getLabel(), Boolean.FALSE);
        }
        DirectedNode firstNode = getNodes().get(0);
        mark.put(0, Boolean.TRUE);
        
        Queue<DirectedNode> toVisit = new LinkedList<>();
        toVisit.add(firstNode);
        while (!toVisit.isEmpty()){
            DirectedNode currentNode = toVisit.poll();
            System.out.println(currentNode.getLabel());
            for (DirectedNode voisin: currentNode.getSuccs().keySet()) {
                if (!mark.get(voisin.getLabel())){
                    mark.put(voisin.getLabel(), Boolean.TRUE);
                    toVisit.add(voisin);
                }
            }
        }
    }

    public void parcoursProfondeur(){
        HashMap<Integer, Boolean> mark = new HashMap<>();
        for (DirectedNode node:getNodes()) {
            mark.put(node.getLabel(), Boolean.FALSE);
        }
        DirectedNode firstNode = getNodes().get(0);
        mark.put(0, Boolean.TRUE);
        Stack<DirectedNode> toVisit = new Stack<>();
        toVisit.add(firstNode);
        while (!toVisit.isEmpty()){
            DirectedNode currentNode = toVisit.pop();
            System.out.println(currentNode.getLabel());
            for (DirectedNode voisin: currentNode.getSuccs().keySet()) {
                if (!mark.get(voisin.getLabel())){
                    mark.put(voisin.getLabel(), Boolean.TRUE);
                    toVisit.add(voisin);
                }
            }
        }
    }

    private static void DFS(DirectedGraph graph, int from, boolean[] visited)
    {
        visited[from] = true;

        for (Map.Entry<DirectedNode, Integer> entry: graph.getNodeOfList(new DirectedNode(from)).getSuccs().entrySet())
        {
            if (!visited[entry.getKey().getLabel()]) {
                DFS(graph, entry.getKey().getLabel(), visited);
            }
        }
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);

        DirectedNode zero = al.makeNode(0);
        DirectedNode one = al.makeNode(1);

        al.addArc(zero, one);
        System.out.println("test add : " + al.isArc(zero, one));
        al.parcoursLargeur();
        System.out.println(al.toString());
        al.parcoursProfondeur();
        al.removeArc(zero, one);
        System.out.println("test remove : " + !al.isArc(zero, one));

        int[][] connected = new int[][]{
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
            {1, 0, 0, 0}
        };
        DirectedGraph conn = new DirectedGraph(connected);

        System.out.println("test connexe : " + conn.stronglyConnected());
    }
}
