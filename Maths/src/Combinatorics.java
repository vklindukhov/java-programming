
public class Combinatorics {
	public static long nCr(long n, long r) {
		if (r <= 0 || r >= n) {
			if (r == 0 || r == n)
				return 1;
			else
				return 0;
		}
		if (r > n - r) // take advantage of symmetry
			r = n - r;
		/* ITERATIVE VERSION
		double result = 1;
		for (long i = 1; i <= r; i++)
			result *= (n- (r - i)) / (double) i;
		// should always be an integer
		return Math.round(result);
		*/
		return ((n - r) * nCr(n, r-1))/r;
	}
	
	public static long nCr(int n, int r) {
		if (r <= 0 || r >= n) {
			if (r == 0 || r == n)
				return 1;
			else
				return 0;
		}
		if (r > n - r) // take advantage of symmetry
			r = n - r;
		/* ITERATIVE VERSION
		long result = 1;
		for (int i = 1; i <= r; i++)
			result *= (n- (r - i)) / (double) i;
		// should always be an integer
		return Math.round(result);
		*/
		return ((n - r) * nCr(n, r-1))/r;
	}
	// generalised version
	public static double nCr(double n, long r) {
		if (Math.abs(n - 0.5) < 10e-15)
			return nCr(r);
		double result = 1;
		for (long i = 1; i <= r; i++)
			result *= (n - (r - i)) / i;
		// should always be an integer
		return result;
	}
	// generalised version
	public static double nCr(float n, int r) {
		if (Math.abs(n - 0.5) < 10e-15)
			return nCr(r);
		double result = 1;
		for (int i = 1; i <= r; i++)
			result *= (n - (r - i)) / i;
		// should always be an integer
		return result;
	}
	//special case for n = 1/2
	public static double nCr(int r) {
		int sign = (r % 2 == 0) ? -1 : 1;
		return sign*(nCr(2*r, r) / 2*r - 1) / (double)(1 << 2*r);
	}
	
	public static double nCr(long r) {
		int sign = (r % 2 == 0) ? -1 : 1;
		return sign*(nCr(2*r, r) / 2*r - 1) / (double)(1 << 2*r);
	}
	
	public static long[] rationalHalfCr(int r) {
		int sign = (r % 2 == 0) ? -1 : 1;
		long numerator = sign*(nCr(2*r, r) / (2*r - 1));
		long denominator = 1 << 2*r;
		return new long[] {numerator, denominator};
	}
	public static long[] rationalHalfCr(long r) {
		int sign = (r % 2 == 0) ? -1 : 1;
		long numerator = sign*(nCr(2*r, r) / (2*r - 1));
		long denominator = 1 << 2*r;
		return new long[] {numerator, denominator};
		
	}

	public static int gcd(int a, int b) {
	    if (a == 0)
	        return b;
	    while (b != 0) {
	        if (a > b)
	            a = a - b;
	        else
	            b = b - a;
	    }
	    return a;
	}
	public static long gcd(long a, long b) {
	    if (a == 0)
	        return b;
	    while (b != 0) {
	        if (a > b)
	            a = a - b;
	        else
	            b = b - a;
	    }
	    return a;
	}

	public static void simplifyRational(long[] rational) {
		long factor = gcd(Math.abs(rational[0]), Math.abs(rational[1]));
		rational[0] /= factor;
		rational[1] /= factor;
	}
	
	public static void simplifyRational(int[] rational) {
		int factor = gcd(Math.abs(rational[0]), Math.abs(rational[1]));
		rational[0] /= factor;
		rational[1] /= factor;
	}
	// calculates infinite series for (2/s)(1-sqrt(1-s)) -1 
	public static void main(String[] args) {
		for (int i = 1; i < 10; i++) {
			long[] result = rationalHalfCr(i);
			result[0] = (i % 2 == 0) ? -2*result[0] : 2*result[0];
			simplifyRational(result);
			System.out.println(result[0] + "/" + result[1] + " s^" + i);
		}
	}
}
