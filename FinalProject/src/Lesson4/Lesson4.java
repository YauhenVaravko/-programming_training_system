public class Lesson4{

    public static void main(String[] args){
        int x = 100000, y=64000, z=-35000, sum;

        x = (byte) y+z;
        y = (byte) z+x;
        //z = (byte) x+y;
      //  z = (byte) (x+y);
        sum = (byte) (x*y-z);

        System.out.println(sum);

    }
}
