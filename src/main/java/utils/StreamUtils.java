package utils;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.NONNULL;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;

public class StreamUtils {
    private static int spliteratorCharacteristics = ORDERED | NONNULL;
    private static boolean SEQUENTIAL_STREAM = false;

    public static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator) {
        return StreamSupport.stream(spliteratorUnknownSize(iterator, ORDERED), SEQUENTIAL_STREAM);
    }
}
