import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[][] arr = new int[7][5];
        arr[3][2] = 9;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j] == 9){
                    for (int k = -2; k <= 2; k++) {
                        for (int l = -2; l <= 2; l++) {
                            if(!(k==0 && l==0)){
                                if(i+k >= 0 && i+k < arr.length && j+l >= 0 && j+l < arr[j].length){
                                    arr[i+k][j+l]++;
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int[] is : arr) {
            System.out.println(Arrays.toString(is));
        }
    }
}
