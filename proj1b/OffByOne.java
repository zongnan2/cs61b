public class OffByOne implements CharacterComparator{

    @Override
    public boolean equalChars(char x, char y) {
        if(x <= 64 || y <= 64) {
            System.out.println("non-alphabetical characters");
            return false;
        }
        if((x > 90 && x < 97)||(y > 90 && y < 97)) {
            System.out.println("non-alphabetical characters");
            return false;
        }
        if(x > 122 || y > 122) {
            System.out.println("non-alphabetical characters");
            return false;
        }
        int diff = x - y;
        if(diff == 1 || diff == -1) {
            return true;
        }
        return false;
    }


}
