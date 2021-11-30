package AdjacencyList;

import Collection.Pair;
import GraphAlgorithms.GraphTools;
import Nodes.UndirectedNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class UndirectedValuedGraph extends UndirectedGraph {

    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public UndirectedValuedGraph(int[][] matrixVal) {
        super();
        this.order = matrixVal.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (UndirectedNode n : this.getNodes()) {
            for (int j = n.getLabel(); j < matrixVal[n.getLabel()].length; j++) {
                UndirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getNeighbours().put(nn, matrixVal[n.getLabel()][j]);
                    nn.getNeighbours().put(n, matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------


    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
    	this.getNodeOfList(x).addNeigh(y, cost);
    	this.getNodeOfList(y).addNeigh(x, cost);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (UndirectedNode n : nodes) {
            s.append("neighbours of ").append(n).append(" : ");
            for (UndirectedNode sn : n.getNeighbours().keySet()) {
                s.append("(").append(sn).append(",").append(n.getNeighbours().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 15, false, true, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        UndirectedValuedGraph al = new UndirectedValuedGraph(matrixValued);
        System.out.println(al);
        System.out.println("Should be false : " + al.isEdge(al.getNodes().get(0), al.getNodes().get(1)));
        al.addEdge(al.getNodes().get(0), al.getNodes().get(1), 16);
        System.out.println("Should be true : " + al.isEdge(al.getNodes().get(0), al.getNodes().get(1)));
        // A completer
        System.out.println(al);
        al.dijkstra();
        System.out.println(al);
        al.bellman(al.getNodes().get(0));
    }

    public void dijkstra() {
        LinkedList<UndirectedNode> nonParcouru = new LinkedList<>(getNodes());
        HashMap<Integer, Integer> values = new HashMap<>();
        HashMap<UndirectedNode, UndirectedNode> predecesseur = new HashMap<>();

        UndirectedNode currentNode;
        values.put(getNodes().get(0).getLabel(), 0);

        while (!nonParcouru.isEmpty()) {
            int min = Integer.MAX_VALUE;
            currentNode = nonParcouru.get(0);
            for (UndirectedNode node : nonParcouru) {
                if (nonParcouru.contains(node) && values.containsKey(node.getLabel()) && values.get(node.getLabel()) < min) {
                    currentNode = node;
                    min = values.get(node.getLabel());
                }
            }
            nonParcouru.remove(currentNode);
            if (min < Integer.MAX_VALUE) {
                for (UndirectedNode node : currentNode.getNeighbours().keySet()) {
                    if (nonParcouru.contains(node) && (!values.containsKey(node.getLabel()) || min + currentNode.getNeighbours().get(node) < values.get(node.getLabel()))) {
                        values.put(node.getLabel(), min + currentNode.getNeighbours().get(node));
                        predecesseur.put(node, currentNode);
                    }
                }
            }
        }
        System.out.println(values);
        System.out.println(predecesseur);
    }

    public void bellman(UndirectedNode start){
        HashMap<Integer, Integer> values = new HashMap<>();
        HashMap<Pair<UndirectedNode, UndirectedNode>, Integer> edges = new HashMap<>();
        HashMap<UndirectedNode, UndirectedNode> predecesseur = new HashMap<>();

        for (UndirectedNode node:getNodes()){
            for (Map.Entry<UndirectedNode, Integer> neighbour:node.getNeighbours().entrySet()){
                edges.put(new Pair<>(node, neighbour.getKey()), neighbour.getValue());
            }
        }
        values.put(start.getLabel(), 0);

        for (UndirectedNode current:getNodes()){
            for (Map.Entry<Pair<UndirectedNode, UndirectedNode>, Integer> edge :edges.entrySet()){
                Integer pu = values.get(edge.getKey().getLeft().getLabel());
                Integer pv = values.get(edge.getKey().getRight().getLabel());
                if (pu != null && (pv == null || pv > pu + edge.getValue())){
                    values.put(edge.getKey().getRight().getLabel(), pu + edge.getValue());
                    predecesseur.put(edge.getKey().getRight(), edge.getKey().getLeft());
                }
            }
        }
        System.out.println(values);
        System.out.println(predecesseur);
    }
}
