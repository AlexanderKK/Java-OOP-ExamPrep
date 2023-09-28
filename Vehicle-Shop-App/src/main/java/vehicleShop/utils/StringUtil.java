package vehicleShop.utils;

public class StringUtil {

    private StringUtil() {
    }

    public static boolean isNullOrEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

}
