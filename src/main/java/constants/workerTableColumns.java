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

    public int INDEX;

    workerTableColumns(int index) {
        this.INDEX = index;
    }
}
