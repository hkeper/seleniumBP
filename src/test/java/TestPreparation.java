import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TestPreparation {

    public static void main(String[] args) {
        List<List<Integer>> c = Arrays.asList(Arrays.asList(11,2,4),Arrays.asList(4,5,6),Arrays.asList(10,8,-12));
        System.out.println(diagonalDifference(c));
    }

    public static void plusMinus(List<Integer> arr) {
        // Write your code here
        double negative = 0.0;
        double positive = 0.0;
        double zero = 0.0;

        for (Integer integer : arr) {
            if (integer < 0) negative++;
            if (integer == 0) zero++;
            if (integer > 0) positive++;
        }
        System.out.printf("%.6f %n",positive/arr.size());
        System.out.printf("%.6f %n",negative/arr.size());
        System.out.printf("%.6f %n",zero/arr.size());
    }

    public static String timeConversion(String s) {
        // Write your code here
        DateFormat df = new SimpleDateFormat("hh:mm:ssaa");
        DateFormat outputformat = new SimpleDateFormat("HH:mm:ss");
        String output = null;
        try{
            Date date = df.parse(s);
            output = outputformat.format(date);
        }catch(ParseException pe){
            pe.printStackTrace();
        }
        return output;
    }

    public static int findMedian(List<Integer> arr) {
        // Write your code here
        Integer median = 0;

        Collections.sort(arr);
        if (arr.size()%2==0){
            median = (arr.get((arr.size() / 2) - 1) + arr.get(arr.size() / 2)) / 2;
        }else {
            median = arr.get(arr.size() / 2);
        }

        return median;
    }

    public static int lonelyinteger(List<Integer> a) {
        // Write your code here
        int result = 0;

        for(int n: a) {
            if (Collections.frequency(a, n) == 1) {
                result = n;
            }
        }
        return result;
    }

    public static int diagonalDifference(List<List<Integer>> arr) {
        // Write your code here
        int sum1 = 0;
        int sum2 = 0;
        int s = 0;
        int e = arr.get(0).size()-1;
        for (List<Integer> l: arr) {
            sum1 += l.get(s);
            sum2 += l.get(e);
            s++;
            e--;
        }
        return Math.abs(sum1 - sum2);
    }


}

