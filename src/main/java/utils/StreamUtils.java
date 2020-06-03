package utils;

import java.util.Iterator;
import java.util.stream.Stream;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

public class StreamUtils {
    private static boolean IS_STREAM_PARALLEL = false;

    public static <T> Stream<T> getStreamFromIterator(Iterator<T> iterator) {
        return stream(spliteratorUnknownSize(iterator, ORDERED), IS_STREAM_PARALLEL);
    }
}