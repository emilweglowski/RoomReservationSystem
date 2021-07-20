package pl.clockworkjava.domain.guest;

public class GuestService {

    private final  GuestRepository repository = new GuestRepository();

    public Guest createNewGuest (String firstName, String lastName, int age, int genderOption){

        Gender gender = Gender.FEMALE;

        if (genderOption==1) {
            gender = Gender.MALE;
        }

        return repository.createNewGuest(firstName, lastName, age, gender);
    }
}