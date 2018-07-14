package tool_bt3;

import static spark.Spark.get;
import static spark.Spark.port;

import java.util.ArrayList;

public class MainApplication {
//	private int count;
	public static final int DEFAULT_PORT = 8080;

	public static void main(String[] args) {
		port(DEFAULT_PORT);

		get("/prime", (req, res) -> {

			int n = Integer.parseInt(req.queryParams("n"));
			System.out.println("n = " + n);

			String result = "";
			ArrayList<Integer> primes = getPrimes(1, n);
			result += "<div>Number prime is : " + primes.size() + "</div>";
			result += "<div>List prime is : </div><div>";
			result += primes.toString();
			result += "</div>";
			return result;
		});

	}

	/**
	 * Calculate all prime numbers in range from min to max with include at two
	 * anchor
	 * 
	 * @param min
	 * @param max
	 * @return ArrayList contains prime numbers in range
	 */
	private static ArrayList<Integer> getPrimes(int min, int max) {
		ArrayList<Integer> primes = new ArrayList<>();

		// Initial boolean array check if prime number
		boolean[] isPrime = new boolean[max + 1];
		for (int i = 0; i < isPrime.length; ++i) {
			isPrime[i] = true;
		}
		isPrime[0] = isPrime[1] = false;

		int maxIndex = (int) Math.floor(Math.sqrt(max));
		for (int p = 2; p <= maxIndex; ++p) {
			if (isPrime[p] == true) {
				// Set true for each multiplier of p : p^2, p^2 + p,... <= max
				for (int i = p * p; i < isPrime.length; i += p) {
					isPrime[i] = false;
				}
			}
		}

		for (int i = min; i <= max; ++i) {
			if (isPrime[i]) {
				primes.add(i);
			}
		}
		System.out.printf("Found %d number primes : in range [%d, %d]\n", primes.size(), min, max);
		return primes;
	}

}
