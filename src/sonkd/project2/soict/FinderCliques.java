/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sonkd.project2.soict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import sonkd.project2.soict.InputGraph.Node;

/**
 * Project II
 *
 * @author KimDinhSon Dung giai thuat Bron-Kerbosch Input: Graph G(V,E)
 * (complement G') Output: Tat ca cac tap MIS (hay doi phan MC)
 */
public class FinderCliques {

    //public static inputGraph E = new inputGraph();
    public static UndirectedGraph<Node, DefaultEdge> complement = new InitGraph().getComplement();
    private Collection<Set<Node>> cliques = new HashSet<>();
    public static ArrayList<ArrayList<Integer>> g = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        FinderCliques mis = new FinderCliques();
        //mis.E.readGrapthFromFile("inputListAdj.txt");
        Set<Node> potentialClique = new HashSet<>();
        Set<Node> candidates = new HashSet<>();
        Set<Node> checkedBacktrack = new HashSet<>();
        candidates.addAll(FinderCliques.complement.vertexSet());
        
        System.out.println();
        //mis.findCliques(potentialClique, candidates, checkedBacktrack);
        //mis.findCliquesByMe(complement);
        mis.getAllMaximalCliques();
        new InputGraph().makeFile(g, "mis_mc.txt");
    }
    
    public Collection<Set<Node>> getAllMaximalCliques() {
        Set<Node> potentialClique = new HashSet<>();
        Set<Node> candidates = new HashSet<>();
        Set<Node> checkedBacktrack = new HashSet<>();
        candidates.addAll(FinderCliques.complement.vertexSet());
        this.findCliques(potentialClique, candidates, checkedBacktrack);
        for (Set<Node> set : cliques) {
            ArrayList<Integer> adj = new ArrayList<>();
            for (Node n : set) {
                adj.add(n.index);
            }
            g.add(adj);
            report(set);
            //System.out.println();
        }
        return this.cliques;
    }
    
    public Set<Node> intersection(Set<Node> U1, Set<Node> U2) {
        Set<Node> intersection = new HashSet<>();
        for (Node n : U1) {
            if (U2.contains(n)) {
                intersection.add(n);
            }
        }
        return intersection;
    }
    
    public Node getPivotVertex(Set<Node> set) {
        // Chon theo dinh co bac lon nhat
        Node pivot = null;
        int max_deg = 0;
        for (Node n : set) {
            if (max_deg < n.adjList.size()) {
                max_deg = n.adjList.size();
                pivot = n;
            }
        }
        return pivot;
    }

    /*
     * Bron-Kerbosch Algorithm
     * find cliques
     */
    public void findCliques(
            Set<Node> potentialClique,
            Set<Node> candidates,
            Set<Node> checkedBacktrack // aready found
            ) {
        Set<Node> candidatesSet = new HashSet<>(candidates);
        if (!endFindCliques(candidates, checkedBacktrack)) {
            // Voi moi node trong candidates

            for (Node candidate : candidatesSet) {
                Set<Node> new_candidates = new HashSet<>();
                Set<Node> new_maximalClique = new HashSet<>();
                // chuyen candidate sang potentialClique
                potentialClique.add(candidate);
                candidates.remove(candidate);

                // tao new_candidates boi cac node bi xoa khong ket noi den candidate trong candidates
                for (Node new_candidate : candidates) {
                    if (candidate.adjList.contains(new_candidate)) {
                        new_candidates.add(new_candidate);
                    }
                }

                // tao new_maximalClique boi cac node bi xoa khong ket noi den candidate trong maximalClique
                for (Node new_found : checkedBacktrack) {
                    if (candidate.adjList.contains(new_found)) {
                        new_maximalClique.add(new_found);
                    }
                }

                // <Giai thuat Bron-Kerbosch>
                // Neu new_candidates & new_maximalCliques rong
                if (new_candidates.isEmpty() && new_maximalClique.isEmpty()) {
                    // potential_clique la maximal_clique
                    System.out.print("\t");
                    // report(potentialClique);
                    // System.out.println("======");
                    cliques.add(new HashSet<>(potentialClique));
                } else {
                    // goi de qui
                    // report(potentialClique);
                    // report(candidates);
                    // report(checkedBacktrack);
                    // System.out.println("======");
                    findCliques(potentialClique, new_candidates, new_maximalClique);
                }
                // Chuyen node candidate tu potentialClique sang maximalClique
                checkedBacktrack.add(candidate);
                potentialClique.remove(candidate);
            }
        }
    }
    
    private boolean endFindCliques(Set<Node> candidates, Set<Node> maximalClique) {
        // Neu node trong maximalClique <tam thoi> ket noi voi tat ca tat ca cac node trong candidates 
        boolean end = false;
        int edgeCounter;
        for (Node found : maximalClique) {
            edgeCounter = 0;
            for (Node candidate : candidates) {
                if (complement.containsEdge(found, candidate)) {
                    edgeCounter++;
                }
                if (edgeCounter == candidates.size()) {
                    end = true;
                }
            }
        }
        
        return end;
    }
    
    public UndirectedGraph<Node, DefaultEdge> completeGraph(UndirectedGraph<Node, DefaultEdge> g) {
        UndirectedGraph<Node, DefaultEdge> graph = g;
        
        for (Node v : graph.vertexSet()) {
            v.adjList.clear();
        }
        
        for (Node v : graph.vertexSet()) {
            v.adjList.clear();
        }
        
        for (Node v : graph.vertexSet()) {
            for (Node v1 : graph.vertexSet()) {
                if (!g.containsEdge(v, v1) && v1.index != v.index) {
                    v.adjList.add(v1);
                }
            }
        }
        return graph;
    }

    /*
     * by KimDinhSon
     */
    public void findCliquesByMe(UndirectedGraph<Node, DefaultEdge> g) {
        CheckBipartiteGraph bg = new CheckBipartiteGraph(new InitGraph().getGraph());
        
        Set<Node> V1 = new HashSet<>();
        Set<Node> V2 = new HashSet<>();
        int[] indexV1 = new int[1000];
        int[] indexV2 = new int[1000];
        int i = 0, k = 0;
        
        for (Node v : bg.componentGreen) {
            indexV1[i] = v.index;
            i++;
        }
        
        for (Node v : bg.componentRed) {
            indexV2[k] = v.index;
            k++;
        }
        
        for (int j = 0; j < indexV1.length; j++) {
            for (Node v : g.vertexSet()) {
                if (v.index == indexV1[j]) {
                    V1.add(v);
                }
            }
        }
        for (int j = 0; j < indexV2.length; j++) {
            for (Node v : g.vertexSet()) {
                if (v.index == indexV2[j]) {
                    V2.add(v);
                }
            }
        }
        
        for (Node v : V1) {
            Set<Node> R = new HashSet<>();
            Set<Node> Z = new HashSet<>();
            //Z:= Adj(v)/V1
            for (Node v1 : V2) {
                if (v.adjList.contains(v1)) {
                    Z.add(v1);
                }
            }
            //R:=R U {v} U Adj(v)\V1
            R.add(v);
            //report(R);
            //cliques.add(new HashSet<Node>(R));
            R.clear();
        }
    }
    
    public boolean vertexCovering(Node v, Set<Node> V) {
        for (Node p : V) {
            if (!p.adjList.contains(v)) {
                return false;
            }
        }
        return true;
    }

    // check Complete Graph
    public boolean checkCompleteGraph(Set<Node> V) {
        for (Node s : V) {
            for (Node t : V) {
                if (!t.equals(s) && !s.adjList.contains(t)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void report(Set<Node> set) {
        System.out.print("[ ");
        for (Node n : set) {
            System.out.print(n.index + " ,");
        }
        System.out.print("]");
        System.out.println();
    }
}
