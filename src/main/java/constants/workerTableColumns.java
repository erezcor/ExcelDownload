package constants;

public enum  workerTableColumns {
    SERIAL_NUMBER (0),
    FIRST_NAME (1),
    LAST_NAME (2),
    GENDER (3),
    COUNTRY (4),
    AGE (5),
    DATE (6),
    ID (7);

    int columnIndex;

    workerTableColumns(int index) {
        this.columnIndex = index;
    }
}
