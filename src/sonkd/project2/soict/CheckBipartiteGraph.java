package sonkd.project2.soict;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import sonkd.project2.soict.InputGraph.Node;

/**
 *
 * @author KimDinhSon Do thi G(V,E) Kiem tra G co la do thi 2 phia hay khong
 * OUTPUT: thanh phan do thi co it dinh nhat giua U va W
 */
public final class CheckBipartiteGraph {

    Set<Node> componentGreen = new HashSet<>(); // tap thanh phan thu nhat
    Set<Node> componentRed = new HashSet<>(); // tap thanh phan thu hai
    Set<Node> componentFA = new HashSet<>(); // tap thanh phan khong ket noi

    /*
     * TEST
     */
    public static void main(String[] args) {
        UndirectedGraph<Node, DefaultEdge> g = new InitGraph().getGraph();
        new CheckBipartiteGraph(g);
    }

    public CheckBipartiteGraph(UndirectedGraph<Node, DefaultEdge> g) {
        if (!bipartiteGraph(g)) {
            System.out.println("Khong phai do thi hai thanh phan\n");
            System.out.print("Tap chua duoc to mau: ");
            outPut(componentFA);
            System.out.print("Tap thanh phan mau Xanh: ");
            outPut(componentGreen);
            System.out.print("Tap thanh phan mau Do: ");
            outPut(componentRed);
        } else {
            System.out.println("La do thi hai thanh phan\n");
            System.out.print("Tap thanh phan mau Xanh: ");
            outPut(componentGreen);
            System.out.print("Tap thanh phan mau Do: ");
            outPut(componentRed);
        }
    }

    public Set<Node> getComponentGreen() {
        return this.componentGreen;
    }

    public Set<Node> getComponentRed() {
        return this.componentRed;
    }

    /*
     * Kiem tra do thi G
     */
    public boolean bipartiteGraph(UndirectedGraph<Node, DefaultEdge> g) {
        Iterator<Node> iter =
                new DepthFirstIterator<>(g);

        Node vertex;
        boolean[] mark = new boolean[1000];
        for (Node v : g.vertexSet()) {
            mark[v.index] = false;
        }
        int count = 0;
        while (iter.hasNext()) {
            vertex = iter.next();
            //System.out.print(" " + vertex.index);
            if (mark[vertex.index] == false) {
                if (count % 2 == 0) {
                    vertex.color = 1;//green
                } else {
                    vertex.color = -1;//red
                }
                mark[vertex.index] = true;

                //System.out.print(" " + vertex.index + "(" + vertex.color + ") ");
                for (Node n : vertex.adjList) {
                    if (mark[n.index] == false) {
                        n.color = -vertex.color;
                        mark[n.index] = true;
                        //System.out.print(" " + n.index + "(" + n.color + ") ");
                    }
                }
            }
        }

        for (Node v : g.vertexSet()) {

            if (v.color == 1 && v.adjList.size() > 0) {
                componentGreen.add(v);
            } else if (v.color == 0 && v.adjList.size() > 0) {
                componentRed.add(v);
            } else {
                componentFA.add(v);
            }
        }

        int counter = componentGreen.size() + componentRed.size();
        //System.out.print(counter);
        return counter == g.vertexSet().size();
    }

    public void outPut(Set<Node> V) {
        for (Node v : V) {
            System.out.print(" " + v.index);
        }
        System.out.println();
    }
}
