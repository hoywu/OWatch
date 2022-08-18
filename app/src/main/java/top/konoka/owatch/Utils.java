package top.konoka.owatch;

public class Utils {
    public static String getMiddleString(String rawString, String leftString, String rightString) {
        if (rawString == null || leftString == null || rightString == null) {
            return null;
        }
        int left = rawString.indexOf(leftString);
        if (left == -1) {
            return null;
        }
        int right = rawString.indexOf(rightString, left);
        if (right == -1) {
            return null;
        }
        return rawString.substring(left + leftString.length(), right);
    }
}
