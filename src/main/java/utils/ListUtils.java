package utils;

import java.util.List;

public class ListUtils {
    public static <T> T getLastElementFromList(List<T> list) {
        return list.get(getIndexOfLastElementFromList(list));
    }

    private static <T> int getIndexOfLastElementFromList(List<T> list) {
        return list.size() - 1;
    }
}
