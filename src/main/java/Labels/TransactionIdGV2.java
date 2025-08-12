package Labels;
import javax.print.DocFlavor;
import java.util.Random;

public class TransactionIdGV2 {

    public static String TransactionId(int longitud){

        int min = (int) Math.pow(10, longitud -1);
        int max = (int) Math.pow(10, longitud ) -1;
        StringBuilder transactionId = new StringBuilder();

        Random  TranRandom = new Random();
        int stcTranid = TranRandom.nextInt(max - min) +1;
        transactionId.append(stcTranid);


        int pos1 = 0;
        char pos1stc = 'E';
        transactionId.insert(pos1,pos1stc);

        int pos2 = 3;
        char pos2stc = 'F';
        transactionId.insert(pos2,pos2stc);

        int pos3 = 7;
        String pos3stc = "BD";
        transactionId.insert(pos3,pos3stc);

        return String.valueOf(transactionId);

    }

    public static  void main(String [] args){
        TransactionIdGV2 transactionCompuesto = new TransactionIdGV2();
        int longitud = 8;
        String transactionId = transactionCompuesto.TransactionId(longitud);
        System.out.println(transactionId);
    }


}
