/**
 * 
 */
package math;

import java.util.Random;
import com.google.common.annotations.VisibleForTesting;

/**
 * This little class contains methods that allow us to demonstrate unit testing.
 * 
 * @author Promineo
 *
 */
public class MathOperations {
  private Random random = new Random();

  /**
   * Multiply the two given numbers together if the numbers are positive. This is used to
   * demonstrate "normal" testing - calling a method and making assertions on the outcome.
   * 
   * @param a The first number
   * @param b The second number
   * @return The product of the two numbers
   * @throws IllegalArgumentException Thrown if one or both of the numbers are negative.
   */
  public int multiplyIfPositive(int a, int b) {
    if (a < 0 || b < 0) {
      throw new IllegalArgumentException("The parameters must be positive!");
    }

    return a * b;
  }

  /**
   * This method calls {@link #generateRandomInt()} to generate two numbers from zero to nine and
   * multiplies them together. Sweet!
   * 
   * @return The product of the two random numbers.
   */
  public int multiplyTwoRandomNumbers() {
    int a = generateRandomInt();
    int b = generateRandomInt();

    return multiplyIfPositive(a, b);
  }

  /**
   * This generates a random number from zero to nine. It is package access so that a test can mock
   * the method. See the test class for details.
   * 
   * @return The random number
   */
  @VisibleForTesting
  int generateRandomInt() {
    return random.nextInt(10);
  }
}
