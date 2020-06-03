package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

import static constants.workerTableColumns.*;

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

    public Worker(List<String> workerDetails) {
        this (workerDetails.get(SERIAL_NUMBER.INDEX), workerDetails.get(FIRST_NAME.INDEX),
                workerDetails.get(LAST_NAME.INDEX), workerDetails.get(GENDER.INDEX),
                workerDetails.get(COUNTRY.INDEX), workerDetails.get(AGE.INDEX),
                workerDetails.get(DATE.INDEX), workerDetails.get(ID.INDEX));
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
