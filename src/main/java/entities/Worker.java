package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Worker)) return false;
        Worker worker = (Worker) object;
        return Objects.equals(serialNumber, worker.serialNumber) &&
                Objects.equals(firstName, worker.firstName) &&
                Objects.equals(lastName, worker.lastName) &&
                Objects.equals(gender, worker.gender) &&
                Objects.equals(country, worker.country) &&
                Objects.equals(age, worker.age) &&
                Objects.equals(date, worker.date) &&
                Objects.equals(id, worker.id);
    }
}
