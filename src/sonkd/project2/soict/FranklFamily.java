package sonkd.project2.soict;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import sonkd.project2.soict.InputGraph.Node;

/*
 *
 * @author KimDinhSon
 * Kiem tra bo de Frankl (The Union-Closed Set Conjecture)
 * Version Grapth.
 * Ap dung he qua ( Tai lieu [1] ): Bo de dung voi Do thi lien thong 2 phia
 * Kiem tra bo de duoi dang Graph gom: Cay va Do thi Chu trinh chan
 * Thuat toan:
 *  INPUT: Do thi G(V,E) vo huong --> InitGraph()
 * 
 *      - Kiem tra tinh lien thong cua G --> CheckConnectivityGraph()
 *      - Dang cay:
 *
 *      - Chu trinh chan: 
 * 
 *  OUTPUT: Ket luan gia thuyet bo de. --> FranklFamily()
 */
public class FranklFamily {

    public ArrayList<ArrayList<Integer>> mis = new ArrayList<>();
    public static UndirectedGraph<Node, DefaultEdge> graph = new InitGraph().getGraph();
    private Collection<Set<Node>> cliques = new FinderCliques().getAllMaximalCliques();

    public static void main(String[] args) {
        FranklFamily unionClosedSet = new FranklFamily();
        CheckBipartiteGraph bg = new CheckBipartiteGraph(graph);

        if (bg.componentFA.isEmpty()) {
            unionClosedSet.versionTree(graph);
            unionClosedSet.kiemTraGiaThuyet();
        }
    }

    /*
     * Kiem tra gia thuyet Frankl (version Graph)
     */
    public void versionTree(UndirectedGraph<Node, DefaultEdge> g) {
        int enumMIS = 0; // so tap MIS
        int[] count = new int[10000];
        for (Set<Node> s : cliques) {
            enumMIS++;
        }

        System.out.println("so cac tap MIS <-> cac tap thuoc ho hop dong la: " + enumMIS);

        boolean check = false;
        // Dung ket qua cua Robert va SimpSon
        if (enumMIS <= 4 * g.vertexSet().size() - 1) {
            check = true;

            for (Set<Node> s : cliques) {
                for (Node n : s) {
                    count[n.index]++;
                }
            }
            for (Node n : graph.vertexSet()) {
                boolean stop = false;
                if (count[n.index] < enumMIS / 2) {
                    for (Node v : n.adjList) {
                        if (count[v.index] < enumMIS / 2) {
                            System.out.println("Hai dinh: " + n.index + " va " + v.index);
                            stop = true;
                            break;
                        }
                    }
                }
                if (stop) {
                    break;
                }
            }
        }

        if (check == false) {
            System.out.println("Gia thuyet Frankl khong dung.");
        } else {
            System.out.println("Gia thuyet Frankl dung.");
        }
    }

    /*
     * Kiem tra ho Frankl (version Graph) dang Chu trinh chan 
     */
    public void versionCycleEven() {
    }

    public void kiemTraGiaThuyet() {
        for (Node v : graph.vertexSet()) {
        }
    }
}
