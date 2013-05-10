package sonkd.project2.soict;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import sonkd.project2.soict.InputGraph.Node;

/*
 * @author KimDinhSon
 * Do thi G(V,E) vo huong (Undirected Graph)
 * Cho truoc ma tran ket noi
 */
public class InitGraph {

    static UndirectedGraph<Node, DefaultEdge> Graph; //do thi vo huong
    static UndirectedGraph<Node, DefaultEdge> complement; //do thi bao phu
    //ReadFileGraph readFile = new ReadFileGraph();
    InputGraph readFile = new InputGraph();
    public ArrayList<Node> G = readFile.G;
    public ArrayList<Node> C = readFile.C;
    //Number of vertices
    static int size; // doc tu class ReadFileGraph()    
    //public static int[][] matrix = new int[100][100];
    public Node[] vertex = new Node[100];

    public static void main(String[] args) {
        InitGraph g = new InitGraph();
        g.createComplement();
        //g.getGraph();
        g.outPut(complement);
    }

    public void createGraph() {
        readFile.readGraphFromFile("inputListAdj.txt");
        size = G.size();

//        // Coppy Matran ket noi
//        for(int i = 0;i<size;i++){
//            System.arraycopy(readFile.matrix[i], 0, matrix[i], 0, size);
//        }

        Graph = new SimpleGraph<>(DefaultEdge.class);

        //Khoi tao dinh
        for (int i = 0; i < size; i++) {
            Graph.addVertex(G.get(i));
            //vertex.add(v);
        }

        //Thiet lap canh noi
        for (int i = 0; i < size; i++) {
            for (Node n : G.get(i).adjList) {
                Graph.addEdge(G.get(i), G.get(n.index - 1));
                //System.out.print(n.index);
            }
        }
//        for(int i = 0; i<size ; i++){
//            for(int j = 0; j<size ; j++){
//                Graph.removeAllEdges(i, j);
//                System.out.print(matrix[i][j]+"\t");
//                if(matrix[i][j]==1) {
//                    Graph.addEdge(i, j);
//                    //System.out.print("["+i+";"+j+"]"+", ");
//                }
//            }
//            System.out.println();
//
//        }
        System.out.println();

    }

    public void createComplement() {
        readFile.readComplementFromFile("inputListAdj.txt");
        size = C.size();
        
        complement = new SimpleGraph<>(DefaultEdge.class);

        //Khoi tao dinh
        for (int i = 0; i < size; i++) {
            complement.addVertex(C.get(i));
        }
    }

    public UndirectedGraph<Node, DefaultEdge> getGraph() {
        this.createGraph();
        return Graph;
    }

    public UndirectedGraph<Node, DefaultEdge> getComplement() {
        this.createComplement();
        return complement;
    }

    /*
     * Thay dinh cu bang dinh moi
     * Noi dinh moi do voi cac dinh da ket noi den dinh cu truoc do
     */
    public static boolean replaceVertex(Node oldVertex, Node newVertex) {
        if ((oldVertex == null) || (newVertex == null)) {
            return false;
        }
        Set<DefaultEdge> relatedEdges = Graph.edgesOf(oldVertex);
        Graph.addVertex(newVertex);

        Node Vertex;

        for (DefaultEdge e : relatedEdges) {
            Vertex = Graph.getEdgeSource(e);
            if (Vertex.equals(oldVertex)) {
                Graph.addEdge(newVertex, newVertex);
            } else {
                Graph.addEdge(Vertex, newVertex);
            }
        }
        Graph.removeVertex(oldVertex);
        return true;
    }

    /*
     * Kiem tra khoi tao Do thi
     */
    public void outPut(UndirectedGraph<Node, DefaultEdge> g) {
        System.out.print("Do thi G(V,E)" + " " + size + " dinh, va Danh sach ket noi: \n\n");

        Iterator<Node> iter =
                new DepthFirstIterator<>(g); // Duyet theo do sau
        Node vertex1;
        while (iter.hasNext()) {
            vertex1 = iter.next();
            System.out.print("Vertex " + vertex1.index + " -: ");
            for (Node n : vertex1.adjList) {
                if(vertex1.index!=n.index) {
                    System.out.print(n.index + " , ");
                }
            }
            System.out.println();
        }
        System.out.print("\n");
    }
}
