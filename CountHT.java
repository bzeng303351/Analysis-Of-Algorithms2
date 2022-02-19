//flip coins n times, count the numbers of times that H and T happens consecutively.
//use 0 and 1 to represent H and T, then an integer number can represent the result of n flips.

public class CountHT {

    private int nFlip;
    private String pattern;

    public CountHT(int nFlip, String pattern) {
        this.nFlip = nFlip;
        this.pattern = pattern;
    }

    public int countTotal() {
        int count = 0;
        for (int i = 0; i < Math.pow(2, nFlip); i++) {
            count += countMatch(toBinaryString(i));
        }
        return count;
    }

    public double ratio() {
        return 1.0 * countTotal() / (nFlip - pattern.length() + 1) / Math.pow(2, nFlip);
    }

    public double expect() {
        return ratio() * (nFlip - pattern.length() + 1);
    }

    public int totalPositions() {
        return (nFlip - pattern.length() + 1) * (int)Math.pow(2, nFlip);
    }

    private int countMatch(String s) {
        int count = 0;
        for (int i = 0; i <= s.length() - pattern.length(); i++) {
            String sub = s.substring(i, i + pattern.length());
            if (sub.equals(pattern))
                count++;
        }
        return count;
    }

    private String toBinaryString(int n) {
        StringBuilder temp = new StringBuilder(Integer.toBinaryString(n));
        for (int i = temp.length(); i < nFlip; i++) {
            temp.insert(0, "0");
        }
        return temp.toString();
    }

    public static void main(String[] args) {
        CountHT model = new CountHT(10, "011");
        System.out.format("For %d flips, there are %d permutations.\n" +
                "For \"%s\" pattern, each permutation has %d positions. In total, %d positions.\n" +
                "Out of all the positions in all the permutations, there are %d positions that match the pattern.\n" +
                "The chance that the pattern appears is %f.\n" +
                        "The expectation of the pattern appearing in the %d flips is %f.",
                model.nFlip, ((int)Math.pow(2, model.nFlip)), model.pattern,
                (model.nFlip - model.pattern.length() + 1), model.totalPositions(), model.countTotal(),
                model.ratio(), model.nFlip, model.expect());
    }
}
