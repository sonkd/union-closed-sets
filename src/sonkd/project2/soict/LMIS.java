package sonkd.project2.soict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import sonkd.project2.soict.InputGraph.Node;

/*
 *
 * @author KimDinhSon 
 * Ap dung giai thuat LMIS sinh tat ca cac tap MIS cua 
 * Do thi G(V,E)
 */
public class LMIS {

    public static InputGraph E = new InputGraph();
    private int nVertex = E.nVertex; // so dinh cua G
    Node u,k;
    public Set<Node> M = new HashSet<>(1000); // is an independent set
    public Set<Node> M1 = new HashSet<>(1000); // the vertex set the elements of which were used earlier in search
    public Set<Node> M2 = new HashSet<>(1000); // is the set of candidate vertices. < W U {u} \in F(G), all u \in M2 >
    public Set<Set<Node>> MIS = new HashSet<>(); // Family Maximum Independent Set
    public Set<Node> Z = new HashSet<>(1000);
    
    /*
     * Return intersection of two Sets
     */
    public ArrayList intersect(ArrayList U1, ArrayList U2) {
        ArrayList intersect = new ArrayList(1000);
        for (Iterator it = U1.iterator(); it.hasNext();) {
            if(U2.contains(it)) intersect.add(it);
        }
        return intersect;
    }
    
    /*
     * Return union of two Sets
     */
    public ArrayList union(ArrayList U1, ArrayList U2) {
        ArrayList union = U1;
        for (Iterator it = U1.iterator(); it.hasNext();) {
            if (!U2.contains(it)) {
                union.add(it);
            }
        }
        return union;
    }
    /*
     * set cover
     * set U1 covered by U2 (?)
     */
    public boolean checkSetCover(ArrayList U1, ArrayList U2){
        boolean check = true;
        if (U1.size() > U2.size()) {
            check = false;
        } else {
            for (Iterator it = U1.iterator(); it.hasNext();) {
                if (!U2.contains(it)) {
                    check = false;
                }
                break;
            }
        }
        return check;       
    }
    
    /*
     * Alorithm by Loukakis, in 1982, (LMIS)
     * t=0, if M1 in of the Adj(M)
     * t=1, otherwise
     */
    int t=0;
    public void LMIS() {
        /*
         * Step 1 (Initialize)
         */
        t = 0;
        for (int i = 0; i < nVertex; i++) {
            M2.add(E.G.get(i));
            //System.out.print("TEST\n");
        }

        // Choose u such that |Adj(u)| = min {|Adj(j)|: j=1,..., |V|}
        int minAdj = 10000, index = 0;
        for (int i = 0; i < nVertex; i++) {
            if (E.G.get(i).adjList.size() < minAdj) {
                minAdj = E.G.get(i).adjList.size();//
                index = i;
            }
        }
        u = E.G.get(index);
        // Step 2
        testMIS(u);
        // Step 3 
        BT(u);
        // Step 4
        Branch(u);
        //
    }
    
    /*
     * Step 2 (Test for MIS)
     */
    public void testMIS(Node u) {
        if (M2.isEmpty()) {
            for (int i = 0; i < M.size(); i++) {
                if (M.get(i).index > 0) {
                    MIS.add(M);
                    System.out.print(M.get(i).index + " , ");
                }
            }
            System.out.println();
            // go to 4
            Branch(u);
        }
        else {
            M2.set(0, u);
        }
    }
    
    /*
     * Step 3(Apply Backtracking)
     */
    public void BT(Node u) {
        if (intersect(u.adjList, M2).isEmpty()) {
            u.remark = true;
        }
    }
    
    /*
     * Step 4 (Brach)
     */
    public void Branch(Node u) {
        M.add(u);
        // M2 = M2 \ ({u} U (Adj(u) intersect M2))
        M2.removeAll(intersect(u.adjList, M2));
        if(M2.contains(u)) M2.remove(u) ;
        if (t == 1) {
            
        }//go to 7;
        else {
            testMIS(u); //go to 2
        };
    }
    
    /*
     * Step 5 (Backtrack, Terminate)
     */
    public void backtrackTerminate(){
        ArrayList<Node> R = new ArrayList<>(1000);
        Node v = null;
        int n = M.size();
        while(v.remark=false){
            v = M.get(n);
            n--;
//            if(v.remark==false) break;
//            else n--;
        }
        // R(v) = {j: j in M or -j in M and j or -j is right v}
        for (int j = n; j < M.size(); j++) {
            // M <- M \ R(v)
            R.add(M.get(j));
            M.remove(j);
        }
        v.index = - v.index;
        M2.addAll(union(R,intersect(v.adjList, M2)));
        
        for (Node it : M) {       
            if ( it.index<0 && v.adjList.contains(E.G.get(-it.index))) {
                Z.add(it);
            }
        }
        // if {x} U Adj(x) covered by Z -> terminate. Otherwise proceed to Step 6
        if (!Z.contains(v)) {
            Z.add(v);
        }
        if (checkSetCover(M.get(0).adjList, Z) && Z.contains(M.get(0)))
            Z.clear(); // terminate
    }
    
    /*
     * Step 6 (Close the vertices of Z)
     */
    public void closedZ() {
        k = Z.get(0);
        ArrayList<Node> v = intersect(k.adjList, M2);
        if (v.isEmpty()) {
            backtrackTerminate();
        } else {
            u = v.get(0);
            BT(u);
            t = 1;
        };
    }
    
    /*
     * Step 7 (Revise)
     */
    public void reviseZ(){
        // Z = Z \ ({k} U (Adj(u) intersect Z))
        Z.removeAll(intersect(u.adjList, Z));
        if(!Z.contains(k))
            Z.remove(k);
        
        if(Z.isEmpty()) closedZ();
        else {
            t = 0;
            testMIS(u);
        }
    }
    
    public static void main(String[] args) throws IOException {
        //GeneneratingMIS.E.insertVertices(); // Goi danh sach dinh ke
        LMIS.E.readGraphFromFile("inputListAdj.txt"); // Goi danh sach dinh ke
        System.out.println();      
        new LMIS().LMIS();
    }
}
