package GraphAlgorithms;

import AdjacencyList.UndirectedGraph;
import AdjacencyList.UndirectedValuedGraph;
import Nodes.UndirectedNode;

import java.util.*;
import Collection.Triple;

public class GraphTools {

    private static int _DEBBUG = 0;

    public GraphTools() {

    }

    /**
     * @param n,     the number of vertices
     * @param multi, at true if we want a multi-graph
     * @param s,     at true if the graph is symmetric
     * @param c,     at true if the graph is connected
     * @param seed,  the unique seed giving a unique random graph
     * @return the generated matrix
     */
    public static int[][] generateGraphData(int n, boolean multi, boolean s, boolean c, int seed) {
        if (_DEBBUG > 0) {
            System.out.println("\n ------------------------------------------------");
            System.out.println("<< Lancement de la méthode generateGraphData en aléatoire complet>>");
        }

        Random rand = new Random(seed);
        int m = (rand.nextInt(n) + 1) * (n - 1) / 2;
        if (_DEBBUG > 0) {
            System.out.println("m = " + m);
        }
        int[][] matrix = new int[n][n];
        if (c) {
            List<Integer> vis = new ArrayList<>();
            int from = rand.nextInt(n);
            vis.add(from);
            from = rand.nextInt(n);
            while (vis.size() < n) {
                if (!vis.contains(from)) {
                    int indDest = rand.nextInt(vis.size());
                    int dest = vis.get(indDest);
                    if (s) {
                        matrix[dest][from] = 1;
                    }
                    matrix[from][dest] = 1;
                    vis.add(from);
                }
                from = rand.nextInt(n);
            }
            m -= n - 1;
        }

        while (m > 0) {
            int i = rand.nextInt(n);
            int j = rand.nextInt(n);
            if (_DEBBUG > 0) {
                System.out.println("i = " + i);
                System.out.println("j = " + j);
            }
            if (!multi) {
                if (i != j && matrix[i][j] != 1) {
                    if (s) {
                        matrix[j][i] = 1;
                    }
                    matrix[i][j] = 1;
                    m--;
                }
            } else {
                if (matrix[i][j] == 0) {
                    int val = (i != j ? (m < 3 ? m : rand.nextInt(3) + 1) : 1);
                    if (_DEBBUG > 0) {
                        System.out.println("Pour multi, val = " + val);
                    }
                    if (s) {
                        matrix[j][i] = val;
                    }
                    matrix[i][j] = val;
                    m -= val;
                }
            }
        }
        return matrix;
    }

    /**
     * @param n,     the number of vertices
     * @param m,     the number of edges
     * @param multi, at true if we want a multi-graph
     * @param s,     at true if the graph is symmetric
     * @param c,     at true if the graph is connexted
     * @param seed,  the unique seed giving a unique random graph
     * @return the generated matrix
     */
    public static int[][] generateGraphData(int n, int m, boolean multi, boolean s, boolean c, int seed) {
        if (_DEBBUG > 0) {
            System.out.println("\n ------------------------------------------------");
            System.out.println("<< Lancement de la méthode generateGraphData >>");
        }
        int[][] matrix = new int[n][n];
        Random rand = new Random(seed);
        if (c) {
            List<Integer> vis = new ArrayList<>();
            int from = rand.nextInt(n);
            vis.add(from);
            from = rand.nextInt(n);
            while (vis.size() < n) {
                if (!vis.contains(from)) {
                    int indDest = rand.nextInt(vis.size());
                    int dest = vis.get(indDest);
                    if (s) {
                        matrix[dest][from] = 1;
                    }
                    matrix[from][dest] = 1;
                    vis.add(from);
                }
                from = rand.nextInt(n);
            }
            m -= n - 1;
        }

        while (m > 0) {
            int i = rand.nextInt(n);
            int j = rand.nextInt(n);
            if (_DEBBUG > 0) {
                System.out.println("i = " + i);
                System.out.println("j = " + j);
            }
            if (!multi) {
                if (i != j && matrix[i][j] != 1) {
                    if (s) {
                        matrix[j][i] = 1;
                    }
                    matrix[i][j] = 1;
                    m--;
                }
            } else {
                if (matrix[i][j] == 0) {
                    int val = (i != j ? (m < 3 ? m : rand.nextInt(3) + 1) : 1);
                    if (_DEBBUG > 0) {
                        System.out.println("Pour multi, val = " + val);
                    }
                    if (s) {
                        matrix[j][i] = val;
                    }
                    matrix[i][j] = val;
                    m -= val;
                }
            }
        }
        return matrix;
    }

    /**
     * @param n,     the number of vertices
     * @param multi, at true if we want a multi-graph
     * @param s,     at true if the graph is symmetric
     * @param c,     at true if the graph is connexted
     * @param neg,   at true if the graph has negative weights
     * @param seed,  the unique seed giving a unique random graph
     */
    public static int[][] generateValuedGraphData(int n, boolean multi, boolean s, boolean c, boolean neg, int seed) {
        if (_DEBBUG > 0) {
            System.out.println("\n ------------------------------------------------");
            System.out.println("<< Lancement de la méthode generateValuedGraphData >>");
        }

        int[][] mat = generateGraphData(n, multi, s, c, seed);
        int[][] matValued = new int[mat.length][mat.length];
        Random rand = new Random(seed);
        int valNeg = 0;
        if (neg) {
            valNeg = -6;
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] > 0) {
                    int val = rand.nextInt(15) + 1 + valNeg;
                    matValued[i][j] = val;
                    if (s) {
                        matValued[j][i] = val;
                    }
                }
            }
        }

        return matValued;
    }

    /**
     * @param m a matrix
     */
    public static void afficherMatrix(int[][] m) {
        for (int[] line : m) {
            for (int v : line) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    /**
     * @param mat, a matrix
     * @return the symmetrical matrix
     */
    public static int[][] matrixSym(int[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 1) {
                    mat[j][i] = 1;
                }
                mat[i][j] = 0;
            }
        }
        return mat;
    }

    public static UndirectedValuedGraph prim(UndirectedValuedGraph valuedGraph, UndirectedNode root) {
        UndirectedValuedGraph primResult = new UndirectedValuedGraph(new int[valuedGraph.getNbNodes()][valuedGraph.getNbNodes()]);

        Set<UndirectedNode> marked = new HashSet<>();
        marked.add(root);

        BinaryHeapEdge edges = new BinaryHeapEdge();

        Queue<UndirectedNode> toVisit = new LinkedList<>();
        toVisit.offer(root);

        while (!toVisit.isEmpty()) {
            UndirectedNode current = valuedGraph.getNodeOfList(toVisit.poll());

            for (Map.Entry<UndirectedNode, Integer> entry: valuedGraph.getNodeOfList(current).getNeighbours().entrySet()) {
                UndirectedNode neigh = entry.getKey();
                int cost = entry.getValue();
                if (!marked.contains(neigh)) {
                    edges.insert(current, neigh, cost);
                }
            }

            UndirectedNode origin;
            UndirectedNode next;
            int cost;

            do {
                Triple<UndirectedNode, UndirectedNode, Integer> edge = edges.remove();
                origin = edge.getFirst();
                next = edge.getSecond();
                cost = edge.getThird();
            } while (!edges.isEmpty() && marked.contains(next));

            if (!edges.isEmpty()) {
                primResult.addEdge(origin, next, cost);
                marked.add(next);
                toVisit.offer(next);
            }

        }

        return primResult;
    }

    public static void main(String[] args) {
        int[][] mat = generateGraphData(10, 20, false, false, false, 100001);
        afficherMatrix(mat);
        int[][] mat2 = generateGraphData(10, 20, false, false, false, 100002);
        afficherMatrix(mat2);
        int[][] mat3 = generateGraphData(10, 20, false, true, true, 100003);
        afficherMatrix(mat3);
        int[][] matVal = generateValuedGraphData(10, false, false, true, true, 100007);
        afficherMatrix(matVal);

        // exemple du cours
        int[][] graphMatrix = new int[][]{
            { 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 6, 1, 5, 0, 0},
            { 0, 6, 0, 5, 0, 3, 0},
            { 0, 1, 5, 0, 5, 6, 4},
            { 0, 5, 0, 5, 0, 0, 2},
            { 0, 0, 3, 6, 0, 0, 6},
            { 0, 0, 0, 4, 2, 6, 0}
        };
        afficherMatrix(graphMatrix);

        UndirectedValuedGraph graph = new UndirectedValuedGraph(graphMatrix);
        System.out.println(graph);
        System.out.println(prim(graph, graph.getNodes().get(1)));
    }
}
