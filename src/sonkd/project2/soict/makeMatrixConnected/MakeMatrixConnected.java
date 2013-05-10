package sonkd.project2.soict.makeMatrixConnected;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author KimDinhSon
 * Tao ngau nhien mot Do thi
 * Tao ma tran lien ket
 */

public class MakeMatrixConnected {
    /*
     * Tao do thi G = (V,E)
     * int size: kich thuoc matrix - so dinh cua do thi |V|
     */
    public static void main(String[] args) throws IOException{
        MakeMatrixConnected mt = new MakeMatrixConnected();
        mt.makeMatrix(7);
    }
    
    public void makeMatrix(int size) throws IOException {
        Random randNumber = new Random();
        int max=2,tmp;
        BufferedWriter bufferedWriter = null;       
        try {          
            //Construct the BufferedWriter object
            bufferedWriter = new BufferedWriter(new FileWriter("InputGraph.txt"));       
            //Start writing to the output stream
			bufferedWriter.write(size+"\n\n");
			for(int j=0;j<size;j++){
				for(int i=0;i<size;i++){
					tmp = randNumber.nextInt(max); 
					bufferedWriter.write(tmp+" ");            
				}
				bufferedWriter.write("\n");
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
}
