/**
 * @author qiushui
 * @Date 2023/8/11
 */
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestArrayDequeGold {

    @Test
    public void test(){
        StudentArrayDeque<Integer> sdeque = new StudentArrayDeque();
        ArrayDequeSolution<Integer> adeque = new ArrayDequeSolution();

        StringBuffer operations = new StringBuffer("operations:\n");

        for(int k = 0; k < 100000; k++){
            Integer i = StdRandom.uniform(1000);
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                operations.append("addLast(" + i + ")\n");
                sdeque.addLast(i);
                adeque.addLast(i);
            } else {
                operations.append("addFirst(" + i + ")\n");
                sdeque.addFirst(i);
                adeque.addFirst(i);
            }

            assertEquals(operations.toString(),adeque.isEmpty(),sdeque.isEmpty());
            assertEquals(operations.toString(),adeque.size(),sdeque.size());

//            ByteArrayOutputStream os1 = new ByteArrayOutputStream();
//            ByteArrayOutputStream os2 = new ByteArrayOutputStream();
//            PrintStream ps1 = new PrintStream(os1);
//            PrintStream ps2 = new PrintStream(os2);
//
//            PrintStream out = System.out;
//
//            System.setOut(ps1);
//            adeque.printDeque();
//            System.setOut(ps2);
//            sdeque.printDeque();
//            System.setOut(out);
//            assertEquals(operations.toString(),os1.toString(),os2.toString());

            if(numberBetweenZeroAndOne < 0.3){
                operations.append("removeFirst()\n");
                assertEquals(operations.toString(),adeque.removeFirst(),sdeque.removeFirst());
            }else if(numberBetweenZeroAndOne > 0.6){
                operations.append("removeLast()\n");
                assertEquals(operations.toString(),adeque.removeLast(),sdeque.removeLast());
            }
        }

    }

}
