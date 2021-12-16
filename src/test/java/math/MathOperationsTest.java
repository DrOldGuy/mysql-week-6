/**
 * 
 */
package math;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * This class performs tests on the {@link MathOperations} class.
 * 
 * @author Promineo
 *
 */
class MathOperationsTest {

  private MathOperations mathOperations;

  /**
   * JUnit runs this method before each test. This method creates a new {@link MathOperations}
   * object. We don't really need to create a new object each time because the MathOperations class
   * does not have any instance variables, so there's no state that needs resetting. It's a good
   * practice, though.
   */
  @BeforeEach
  void setup() {
    mathOperations = new MathOperations();
  }

  /**
   * This method tests that {@link MathOperations#multiplyIfPositive(int, int)} returns the correct
   * product of two given numbers or throws an IllegalArgumentException if one or both of the
   * numbers are negative. The numbers used in this test come from the method
   * {@link #argumentsForMultiplyTest()}. JUnit reads the output of that method and supplies the
   * parameters for this test method.
   * 
   * @param a The first number
   * @param b The second number
   * @param expected
   * @param isExceptionThrown
   */
  @ParameterizedTest
  @MethodSource("math.MathOperationsTest#argumentsForMultiplyTest")
  void assertThatTwoNumbersAreMultipliedIfBothArePositive(int a, int b, int expected,
      boolean isExceptionThrown) {

    if (isExceptionThrown) {
      /*
       * The assertThatThrownBy method takes a zero-argument lambda expression used to call the
       * method under test. The method must throw an exception. The type of exception is tested by
       * the chained method isInstanceOf().
       */
      assertThatThrownBy(() -> mathOperations.multiplyIfPositive(a, b))
          .isInstanceOf(IllegalArgumentException.class);
    } else {
      /*
       * This is the "normal" case in which no exception is thrown. It tests that the result of
       * multiplyIfPositive returns the expected value.
       */
      assertThat(mathOperations.multiplyIfPositive(a, b)).isEqualTo(expected);
    }

  }

  /**
   * This method returns the parameter supplied to
   * {@link #assertThatTwoNumbersAreMultipliedIfBothArePositive(int, int, int, boolean)}. Each line
   * contains an arguments() method call. The arguments defined here are then used as parameters to
   * the test method. Eclipse probably won't find the import definition it needs for arguments. The
   * correct import statement is this:
   * 
   * <pre>
   * import static org.junit.jupiter.params.provider.Arguments.arguments;
   * </pre>
   * 
   * @return
   */
  static Stream<Arguments> argumentsForMultiplyTest() {
    // @formatter:off
    return Stream.of(
        arguments(2, 4, 8, false),
        arguments(-2, 4, 0, true),
        arguments(2, -4, 0, true),
        arguments(0, 503949894, 0, false)
    );
    // @formatter:on
  }

  /**
   * This method tests {@link MathOperations#multiplyTwoRandomNumbers()}. Since the numbers in the
   * method being tested are generated randomly, the test must replace that randomness with known
   * functionality. It does that by mocking the {@link MathOperations#generateRandomInt()} method.
   * When the method is mocked, it allows the test to specify the return value of
   * generateRandomInt(). This allows the test to run reliably.
   */
  @Test
  void assertThatMultiplyRandomNumbersWorks() {
    /* These are the numbers that will be returned by the mock. */
    int a = 2;
    int b = 5;

    /*
     * Create a mock on the MathOperations object with org.mockito.Mockito.spy. When spy creates a
     * mock, it allows the object to do what it does on all methods that are not mocked. In the
     * method under test, two random numbers are generated. Then MathOperations.multiplyIfPositive
     * is called. We don't want random *anything* in the test because the test will fail since it
     * cannot know what numbers are going to be generated. (Think about it. What is the product of
     * two unknown values?) This test replaces the functionality of generateRandomInt. It specifies
     * the numbers that will be returned.
     */
    MathOperations operations = spy(mathOperations);

    /*
     * The doReturn method tells Mockito to return the value of variable "a" the first time
     * generateRandomInt is called, and the value of "b" the second time it is called. The form used
     * is doReturn(a, b), or, in effect, doReturn(2, 5). This means that Mockito returns 2 the first
     * time generateRandomInt is called and 5 the second time it is called. By supplying the numbers
     * ourselves, we know what the product will be: 2 * 5. This removes the randomness from the
     * test.
     */
    doReturn(a, b).when(operations).generateRandomInt();

    /*
     * Call the method being tested. The code in the method being tested will call generateRandomInt
     * two times, which corresponds to the instructions in the mocked object.
     */
    int actual = operations.multiplyTwoRandomNumbers();

    /*
     * Now perform the assertion. Since the numbers used in the test are now known values, we can
     * assert on the product of the two numbers.
     */
    assertThat(actual).isEqualTo(a * b);
  }
}
