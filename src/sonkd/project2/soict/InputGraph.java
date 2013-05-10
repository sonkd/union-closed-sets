package sonkd.project2.soict;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author KimDinhSon Dung Danh sach ke
 */
public class InputGraph {

    public int nVertex; //  so dinh cua Do thi
    public ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    public ArrayList<Node> g = new ArrayList<>();
    public ArrayList<Node> G = new ArrayList<>();
    public ArrayList<Node> C = new ArrayList<>();
    private static String path = "inputListAdj.txt";

    /*
     * Nhap tu File
     * String grapth: ten file input
     */
    public void readGraphFromFile(String graph) {
        File file = new File(graph);
        try {
            Scanner input;
            try (FileInputStream fis = new FileInputStream(file)) {
                input = new Scanner(fis, "UTF-8");
                nVertex = input.nextInt();

//                FileReader fr= new FileReader(file);
//                BufferedReader br = new BufferedReader(fr);
//                String s = "";

                // init G
                for (int j = 0; j < nVertex; j++) {
                    G.add(j, new Node());
                    G.get(j).index = j + 1;
                }
                for (int j = 0; j < nVertex; j++) {
                    while (input.hasNextInt()) {
                        //System.out.print(input.hasNextInt()+"\n");
                        Node n = new Node();
                        n.index = input.nextInt();
                        G.get(j).adjList.add(n);
                    }
                    input.next();
                }
//                for (int j = 0; j < nVertex; j++) {
//                    int i = 0;
//                    while ((s = br.readLine())!= null) {
//                        System.out.print(s);
//                        // Skip empty lines.
//                        if (s.trim().length() == 0) {
//                            continue;
//                        }
//                        
//                        Node n = new Node();
//                        n.index = Integer.parseInt(s);
//                        
//                        //n.index = Integer.parseInt(s);
//                        G.get(j).adjList.add(n);
//                        i++;
//                    }
//                }

                //System.out.print("\n");
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }
    }

    public void readComplementFromFile(String graph) {
        File file = new File(graph);
        try {
            Scanner input;
            try (FileInputStream fis = new FileInputStream(file)) {
                input = new Scanner(fis, "UTF-8");
                nVertex = input.nextInt();
                for (int j = 0; j < nVertex; j++) {
                    C.add(j, new Node());
                    C.get(j).index = j + 1;
                }
                /*
                 for(Node n :C){
                 n.adjList.addAll(C);
                 }*/

                for (int j = 0; j < nVertex; j++) {
                    ArrayList<Integer> t = new ArrayList<>();
                    while (input.hasNextInt()) {
                        //System.out.print(input.hasNextInt()+"\n");
                        int k = input.nextInt();
                        t.add(k);
                    }
                    for (Node n : C) {
                        if (!t.contains(n.index) && n.index != j + 1) {
                            C.get(j).adjList.add(n);
                        }
                    }
                    System.out.println();
                    input.next();
                }

            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }
    }

    class List {

        int node;
        List next;
    }

    public class Node {

        int index;
        boolean remark = false;
        int color =0;// chua to mau
        Set<Node> adjList = new HashSet<>();
    }

    // Nhap tu ban phim
    public void insertVertices() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Nhap so dinh cua do thi: ");
        nVertex = (Integer.parseInt(br.readLine()));
        System.out.println();

        for (int i = 0; i < nVertex; i++) {
            g.add(i, new Node());
            g.get(i).index = i + 1;
            //g[i].visit = false;
            while (true) {
                System.out.println("Nhap 0 de dung.");
                System.out.println("Nhap cac nut ke cua nut " + (i + 1) + " :");
                int adjVertex = Integer.parseInt((br.readLine()));
                if (adjVertex == 0) {
                    break;
                } else {
                    Node n = new Node();
                    n.index = adjVertex;
                    g.get(i).adjList.add(n);
//                    if (g[i].adjList == null) {
//                        g[i].adjList.add(adjVertex);
//                    } else {
//                        while (!g[i].adjList.isEmpty()) {
//                            g[i].adjList.add(adjVertex);
//                        }
//                        List p = g[i].adjList;
//                        while (p.next != null) {
//                            p = p.next;
//                        }
//                        p.next = l;
//                    }
                }
            }
        }

        System.out.println("Bieu dien danh sach ke:");
        for (int i = 0; i < nVertex; i++) {
            ArrayList<Integer> adj = new ArrayList<>();
            System.out.print(g.get(i).index + " -> ");
            for (Node n : g.get(i).adjList) {
                System.out.print(n.index + " , ");
                adj.add(n.index);
            }
            graph.add(adj);
            System.out.println();
        }
        makeFile(graph, path);
    }

    public void makeFile(ArrayList<ArrayList<Integer>> graph, String path) throws IOException {
        BufferedWriter bufferedWriter = null;
        try {
            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter(path));
            //Start writing to the output stream
            bufferedWriter.write(graph.size() + "\n");
            bufferedWriter.write("\n");
            for (int i = 0; i < graph.size(); i++) {
                for (int j : graph.get(i)) {
                    bufferedWriter.write(j + " ");
                }
                bufferedWriter.write(";" + "\n");
            }
        } catch (FileNotFoundException ex) {
        } finally {
            //Close the BufferedWriter
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    public void reportComplement(ArrayList<Node> C) {
        System.out.println("Graph Complement: ");
        for (int i = 0; i < nVertex; i++) {
            System.out.print(C.get(i).index + " -> ");
            Set l = C.get(i).adjList;
            for (Node n : C.get(i).adjList) {
                if (n.index != i + 1) {
                    System.out.print(n.index + " ,");
                }
            }
            System.out.println();
        }
    }

    public void reportGraph(ArrayList<Node> C) {
        System.out.println("Graph: ");
        for (int i = 0; i < nVertex; i++) {
            System.out.print(C.get(i).index + " -> ");
            Set l = C.get(i).adjList;
            for (Node n : C.get(i).adjList) {
                if (n.index != i + 1) {
                    System.out.print(n.index + " ,");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        //new inputGraph().insertVertices();
        InputGraph g = new InputGraph();
        //g.insertVertices();
        g.readComplementFromFile(path);
        g.reportComplement(g.C);
        //g.readGraphFromFile(path);
        //g.reportGraph(g.G);
    }
}
