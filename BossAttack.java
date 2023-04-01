import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BossAttack {

    public void KingSlimeAttack(int phase, int state){
        int slimeFallsCount = 0;
        
        switch (state) {
            case 1:
                slimeFallsCount = 3;
                break;
            case 2:
                slimeFallsCount = 6;
                break;
            case 3:
                slimeFallsCount = 10;
                break;
        }

        int[] slimeFallsColumn = new int[slimeFallsCount];

        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        for(int i = 0; i < slimeFallsCount; i++) {
            slimeFallsColumn[i] = list.get(i);
        }

        System.out.println(Arrays.toString(slimeFallsColumn));
    }
}
