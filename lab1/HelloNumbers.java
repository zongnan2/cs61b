public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        while (x < 10) {
            int sum = 0;
            int i = 0;
            while (i <= x) {
              sum = sum + i;
              i = i + 1;
            }
            System.out.print(sum + " ");
            x = x + 1;
        }
        System.out.println("");
    }
}
