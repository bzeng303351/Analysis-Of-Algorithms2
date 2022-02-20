public class BirthdayMatch {

    public static final int NUM_DAYS_IN_YEAR = 365;

    public static double expectation(int numPeople, int numMatch) {
        return 1.0 * C(numPeople, numMatch) / Math.pow(365, numMatch - 1);
    }

    public static int peopleHavingNMatch(int numMatch) {
        int ret = 0;
        while (true) {
            ret++;
            if (expectation(ret, numMatch) > 1)
                break;
        }
        return ret;
    }

    public static double factorial(int n) {
        if (n <= 1)
            return 1;
        else
            return n * factorial(n - 1);
    }

    public static double A(int n, int m) {
        if (m == 1)
            return n;
        else if (m == 0)
            return 1;
        else
            return n * A(n - 1, m - 1);
    }

    public static double C(int n, int m) {
        return A(n, m) / factorial(m);
    }

    public static void main(String[] args) {
        int n = 3 * 365 + 2;
        System.out.println(n);
        System.out.println(expectation(n, 2));
        System.out.println(expectation(n, 3));
        System.out.println(A(365, 28) / Math.pow(365, 28));

        double p = 1.0;
        for (int i = 0; i < 28; i++) {
            p *= 1.0 * (365 - i) / 365;
        }
        System.out.println(p);
    }
}
