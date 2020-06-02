package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Worker {
    private String serialNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private String age;
    private String date;
    private String id;

    // Maybe use enum for the index
    private static int SERIAL_NUMBER_INDEX = 0;
    private static int FIRST_NAME_INDEX = 1;
    private static int LAST_NAME_INDEX = 2;
    private static int GENDER_INDEX = 3;
    private static int COUNTRY_INDEX = 4;
    private static int AGE_INDEX = 5;
    private static int DATE_INDEX = 6;
    private static int ID_INDEX = 7;

    public Worker(List<String> workerDetails) {
        this (workerDetails.get(SERIAL_NUMBER_INDEX), workerDetails.get(FIRST_NAME_INDEX),
                workerDetails.get(LAST_NAME_INDEX), workerDetails.get(GENDER_INDEX),
                workerDetails.get(COUNTRY_INDEX), workerDetails.get(AGE_INDEX),
                workerDetails.get(DATE_INDEX), workerDetails.get(ID_INDEX));
    }
}
