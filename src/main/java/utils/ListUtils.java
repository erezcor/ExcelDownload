package utils;

import java.util.List;

public class ListUtils {
    public static <T> T getLastElementFromList(List<T> list) {
        return list.get(list.size() - 1);
    }
}
