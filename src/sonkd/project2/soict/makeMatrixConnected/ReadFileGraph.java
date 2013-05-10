package sonkd.project2.soict.makeMatrixConnected;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author KimDinhSon 
 * Doc vao (tu file) mot do thi vo huonng G=(V,E),
 *  bieu dien boi Ma tran lien ket
 */
public class ReadFileGraph {

    public static int nVertex; //  so dinh cua Do thi
    public int[][] matrix = new int[100][100];

    /*
     * String grapth: ten file input
     */
    public void inputGrapthFromFile(String graph) {
        File file = new File(graph);
        try {
            Scanner input;
            try (FileInputStream is = new FileInputStream(file)) {
                input = new Scanner(is, "UTF-8");
                while (input.hasNextInt()) {
                    nVertex = input.nextInt();
                    for (int i = 0; i < nVertex; i++) {
                        for (int j = 0; j < nVertex; j++) {
                            matrix[i][j] = input.nextInt();
                            //System.out.print(matrix[i][j] + " ");
                        }
                        //System.out.print("\n");
                    }
                }
                //System.out.print("\n");
            }
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }
    }

    /*
     * Kiem tra doc tu file
     */
    public void outPut() {
        System.out.print("Do thi G(V,E)"+" "+nVertex+" dinh, va ma tran ket noi: \n\n");
        for (int i = 0; i < nVertex; i++) {
            for (int j = 0; j < nVertex; j++) {
                System.out.print("\t" + matrix[i][j]);
            }
            System.out.println();
        }
        System.out.print("\n\n");
    }
    
    public static void main(String[] args) {
        ReadFileGraph dothi = new ReadFileGraph();

        String name = "inputGraph.txt";
        
//        System.out.print("Nhap ten file input: ");
//        String name = new Scanner(System.in).next();
//        System.out.println();
        dothi.inputGrapthFromFile(name);
        dothi.outPut();
    }
}
