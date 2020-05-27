package utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.toList;

public class FileUtils {
    protected static int getNumberOfFilesByExtensionFrom(String folderPath, String extension) {
        return getFilesByExtensionFrom(folderPath, extension).size();
    }

    protected static List<File> getFilesByExtensionFrom(String folderPath, String extension) {
        return getAllFilesFromDirectory(folderPath).stream()
                .filter(file -> file.getName().endsWith(extension)).collect(toList());
        //return Arrays.asList(new File(folderPath).listFiles(filterFilesByExtension(extension)));
    }

    protected static List<File> getAllFilesFromDirectory(String folderPath) {
        return Arrays.asList(new File(folderPath).listFiles());
    }

    /* private static Predicate<File> filterFilesByExtension() {
        return file -> file.getName().endsWith("");
    } */

    /* private static FileFilter filterFilesByExtension(String extension) {
        return file -> file.getName().endsWith(extension);
    } */

    private static List<File> sortFilesByDate(List<File> files) {
       return files.stream().sorted(comparingLong(File::lastModified)).collect(toList());
    }

    protected static File getLatestFileFromFolderByExtension(String folderPath, String extension) {
        List<File> filesSortedByDate = sortFilesByDate(getFilesByExtensionFrom(folderPath, extension));
        int lastFileIndex = filesSortedByDate.size() - 1;
        return filesSortedByDate.get(lastFileIndex);
    }
}