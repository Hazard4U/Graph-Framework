package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Collection.Triple;
import Nodes.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * 
	 */
	private  List<Triple<UndirectedNode,UndirectedNode,Integer>> edges;

    public BinaryHeapEdge() {
        this.edges = new ArrayList<>();
    }

    public boolean isEmpty() {
        return edges.isEmpty();
    }

    /**
	 * Insert a new edge in the binary heap
	 * 
	 * @param from one node of the edge
	 * @param to one node of the edge
	 * @param val the edge weight
	 */
    public void insert(UndirectedNode from, UndirectedNode to, int val) {
		edges.add(new Triple<>(from, to, val));

		percolateUp(edges.size() - 1);
    }

	private void percolateUp(int src) {
		int fatherIndex = getFatherIndex(src);
		if (edges.get(fatherIndex).getThird() > edges.get(src).getThird()) {
			swap(fatherIndex, src);
			percolateUp(fatherIndex);
		}
	}

	private int getFatherIndex(int src) {
		return (src - 1) / 2;
	}

    
    /**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
	 * 
	 * @return the edge with the minimal value (root of the binary heap)
	 * 
	 */
    public Triple<UndirectedNode,UndirectedNode,Integer> remove() {
		swap(edges.size()-1, 0);

		Triple<UndirectedNode,UndirectedNode,Integer> value = edges.remove(edges.size()-1);

		percolateDown(0);
		return value;
    }

	private void percolateDown(int src) {
		if (isLeaf(src)) {
			return;
		}

		int smallest = smallestChildIndex(src);

		if (edges.get(src).getThird() > edges.get(smallest).getThird()) {
			swap(src, smallest);

			percolateDown(smallest);
		}
	}

	private int smallestChildIndex(int src) {
		int[] childs = getChildsIndex(src);
		int smallestChildIndex = Integer.MAX_VALUE;
		if (edges.size() > childs[1]){
			smallestChildIndex = edges.get(childs[0]).getThird() > edges.get(childs[1]).getThird() ? childs[1] : childs[0];
		}else if (edges.size() > childs[0]){
			smallestChildIndex = childs[0];
		}
		return smallestChildIndex;
	}

	private int[] getChildsIndex(int src) {
		return new int[]{src * 2 + 1, src * 2 + 2};
	}


	/**
	 * From an edge indexed by src, find the child having the least weight and return it
	 * 
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
    private int getBestChildnodesPos(int src) {
    	int lastIndex = edges.size()-1;
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
        	// To complete
        	return Integer.MAX_VALUE;
        }
    }

    private boolean isLeaf(int src) { return edges.size() <= src * 2 + 1; }

    
    /**
	 * Swap two edges in the binary heap
	 * 
	 * @param father an index of the list edges
	 * @param child an index of the list edges
	 */
    private void swap(int father, int child) {         
    	Triple<UndirectedNode,UndirectedNode,Integer> temp = new Triple<>(edges.get(father).getFirst(), edges.get(father).getSecond(), edges.get(father).getThird());
    	edges.get(father).setTriple(edges.get(child));
    	edges.get(child).setTriple(temp);
    }

    
    /**
	 * Create the string of the visualisation of a binary heap
	 * 
	 * @return the string of the binary heap
	 */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Triple<UndirectedNode,UndirectedNode,Integer> no: edges) {
            s.append(no).append(", ");
        }
        return s.toString();
    }
    
    
    private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}
	
	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */	
	public void lovelyPrinting(){
		int nodeWidth = this.edges.get(0).toString().length();
		int depth = 1+(int)(Math.log(this.edges.size())/Math.log(2));
		int index=0;
		
		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index< edges.size()){
				System.out.print(edges.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// ------------------------------------
    // 					TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    private boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	int lastIndex = edges.size()-1;
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= lastIndex) {
                return edges.get(left).getThird() >= edges.get(root).getThird() && testRec(left);
            } else {
                return edges.get(left).getThird() >= edges.get(root).getThird() && testRec(left)
                    && edges.get(right).getThird() >= edges.get(root).getThird() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 10;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));                        
            jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k+30), rand);            
            k--;
        }
		jarjarBin.lovelyPrinting();

        System.out.println("test : " + jarjarBin.test());

		System.out.println("\nRemove root :");
		jarjarBin.remove();
		System.out.println("test : " + jarjarBin.test());
		jarjarBin.lovelyPrinting();

    }

}

