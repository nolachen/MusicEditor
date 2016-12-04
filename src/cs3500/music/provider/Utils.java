package cs3500.music.provider;

/**
 * cs3500.model.model.Utils class.
 */
public final class Utils {

  /**
   * Center pad the string. Negative number mean that there will be no padding.
   *
   * @param str  string to be center
   * @param size the size of padding
   * @return the centered string
   */
  public static String stringCenter(String str, int size) {
    int right = (size - str.length()) / 2;
    int left = size - right - str.length();
    StringBuilder buff = new StringBuilder("");
    //padding left
    buff = padding(buff, " ", left);
    //append word
    buff.append(str);
    //padding right
    buff = padding(buff, " ", right);
    return buff.toString();
  }

  /**
   * Padding the String.
   *
   * @param builder      the builder
   * @param repeatedChar the char to be pad with
   * @param padding      number of padding
   * @return the padded string
   */
  private static StringBuilder padding(StringBuilder builder,
                                       String repeatedChar, int padding) {
    for (int i = 0; i < padding; i++) {
      builder.append(repeatedChar);
    }
    return builder;
  }

  /**
   * Number less than 10 will be padded with 0. Took for hw01.
   *
   * @param number any number of type Long
   * @return the string of that number
   */
  public static String padding(int number) {
    if (number <= 9) {
      return " " + Integer.toString(number);
    } else {
      return Long.toString(number);
    }
  }

  /**
   * Treat negative number as postivie when mod to get the remainder.
   * @param num the number
   * @param mod the mod
   * @return return the remainder
   */
  public static int toPosMod(int num, int mod) {
    if (num < 0) {
      return Math.abs(num) % mod;
    }
    return num % mod;
  }

  /**
   * Throw IllegalArgs when the object is null.
   *
   * @param o the object
   */
  public static <T> T requireNonNull(T o, String msg) {
    if (o == null || msg == null) {
      throw new IllegalArgumentException(msg);
    }
    return o;
  }
}
