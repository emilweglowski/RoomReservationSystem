package pl.clockworkjava.ui.text;

import pl.clockworkjava.domain.guest.Guest;
import pl.clockworkjava.domain.guest.GuestService;
import pl.clockworkjava.domain.reservation.Reservation;
import pl.clockworkjava.domain.reservation.ReservationService;
import pl.clockworkjava.exceptions.OnlyNumberException;
import pl.clockworkjava.exceptions.PersistenceToFileException;
import pl.clockworkjava.exceptions.WrongOptionException;
import pl.clockworkjava.domain.room.Room;
import pl.clockworkjava.domain.room.RoomService;
import pl.clockworkjava.util.Properties;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {

    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();
    private final ReservationService reservationService = new ReservationService();

    private void readNewGuestData(Scanner input) {
        System.out.println("Tworzymy nowego gościa");
        try {
            System.out.print("Podaj imię: ");
            String firstName = input.next();
            System.out.print("Podaj nazwisko: ");
            String lastName = input.next();
            System.out.print("Podaj wiek: ");
            int age = input.nextInt();
            System.out.println("Podaj płeć:");
            System.out.println("\t1. mężczyzna");
            System.out.println("\t2. kobieta");
            int genderOption = input.nextInt();
            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = false;

            if (genderOption == 1) {
                isMale = true;
            }

            Guest newGuest = guestService.createNewGuest(firstName, lastName, age, isMale);
            System.out.println("Dodano nowego gościa: " + newGuest.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when choosing gender");
        }
    }

    private void readNewRoomData(Scanner input) {
        System.out.println("Tworzymy nowy pokój");
        try {
            System.out.print("Numer: ");
            int number = input.nextInt();
            int[] bedTypes = chooseBedType(input);
            Room newRoom = roomService.createNewRoom(number, bedTypes);
            System.out.println("Dodano nowy pokój: " + newRoom.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when creating new room");
        }
    }

    private int[] chooseBedType(Scanner input) {

        System.out.println("Ile łóżek w pokoju?");

        int bedNumber = input.nextInt();

        int[] bedTypes = new int[bedNumber];

        for (int i = 0; i < bedNumber; i = i + 1) {

            System.out.println("Typy łóżek:");
            System.out.println("\t1. Pojedyncze");
            System.out.println("\t2. Podwójne");
            System.out.println("\t3. Królewskie");

            int bedTypeOption = input.nextInt();

            bedTypes[i] = bedTypeOption;
        }
        return bedTypes;
    }

    public void showSystemInfo() {

        System.out.println("Witam w systemie rezerwacji dla hotelu " + Properties.HOTEL_NAME);
        System.out.println("Aktualna wersja systemu: " + Properties.SYSTEM_VERSION);
        System.out.println("Wersja deweloperska: " + Properties.IS_DEVELOPER_VERSION);

        System.out.println("\n=================================\n");
    }

    public void showMainMenu() {

        Scanner input = new Scanner(System.in);

        try {
            performAction(input);
        } catch (WrongOptionException | OnlyNumberException | PersistenceToFileException e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Kod błędu: " + e.getCode());
            System.out.println("Kominukat błędu: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Wystąpił niespodziewany błąd");
            System.out.println("Nieznany kod błędu");
            System.out.println("Komunikat błędu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void performAction(Scanner input) {

        int option = -1;

        while (option != 0) {

            option = getActionFromUser(input);

            if (option == 1) {
                readNewGuestData(input);
            } else if (option == 2) {
                readNewRoomData(input);
            } else if (option == 3) {
                showAllGuests();
            } else if (option == 4) {
                showAllRooms();
            } else if (option == 5) {
                removeGuest(input);
            } else if (option == 6) {
                editGuest(input);
            } else if (option == 7) {
                removeRoom(input);
            } else if (option == 8) {
                editRoom(input);
            } else if (option == 9) {
                createReservation(input);
            } else if (option == 0) {
                System.out.println("Wychodzę z aplikacji. Zapisuję dane.");
                this.guestService.saveAll();
                this.roomService.saveAll();
                this.reservationService.saveAll();
            } else {
                throw new WrongOptionException("Wrong option in main menu");
            }
        }
    }

    private void createReservation(Scanner input) {
        System.out.println("Od kiedy (DD.MM.YYY): ");
        String fromAsString = input.next();
        LocalDate from = LocalDate.parse(fromAsString, Properties.DATE_FORMATTER);
        System.out.println("Do kiedy (DD.MM.YYYY): ");
        String toAsString = input.next();
        LocalDate to = LocalDate.parse(toAsString, Properties.DATE_FORMATTER);
        System.out.println("ID pokoju: ");
        int roomId = input.nextInt();
        System.out.println("ID gościa: ");
        int guestId = input.nextInt();

        //TODO: handle nul reservation
        try {
            Reservation res = this.reservationService.createNewReservation(from, to, roomId, guestId);
            if (res!=null) {
                System.out.println("Udało się stworzyć rezerwację");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Data zakończenia rezerwacji nie może być wcześniejsza niż data jej rozpoczęcia");
        }
    }

    private void removeRoom(Scanner input) {
        System.out.println("Podaj ID pokoju do usunięcia");
        try {
            int id = input.nextInt();
            this.roomService.removeRoom(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when inserting ID");
        }
    }

    private void editRoom(Scanner input) {
        System.out.println("Podaj ID pokoju do edycji");
        try {
            int id = input.nextInt();
            System.out.print("Numer: ");
            int number = input.nextInt();
            int[] bedTypes = chooseBedType(input);
            roomService.editRoom(id, number, bedTypes);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when editing room");
        }
    }

    private void editGuest(Scanner input) {
        System.out.println("Podaj ID gościa do edycji");
        try {
            int id = input.nextInt();
            System.out.print("Podaj imię: ");
            String firstName = input.next();
            System.out.print("Podaj nazwisko: ");
            String lastName = input.next();
            System.out.print("Podaj wiek: ");
            int age = input.nextInt();
            System.out.println("Podaj płeć:");
            System.out.println("\t1. mężczyzna");
            System.out.println("\t2. kobieta");
            int genderOption = input.nextInt();
            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = false;

            if (genderOption == 1) {
                isMale = true;
            }

            guestService.editGuest(id, firstName, lastName, age, isMale);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when editing guest");
        }
    }

    private void removeGuest(Scanner input) {
        System.out.println("Podaj ID gościa do usunięcia");
        try {
            int id = input.nextInt();
            this.guestService.removeGuest(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when inserting ID");
        }
    }

    private void showAllGuests() {
        List<Guest> guests = this.guestService.getAllGuests();

        for (Guest guest : guests) {
            System.out.println(guest.getInfo());
        }
    }

    private void showAllRooms() {
        List<Room> rooms = this.roomService.getAllRooms();

        for (Room room : rooms) {
            System.out.println(room.getInfo());
        }
    }

    private int getActionFromUser(Scanner in) {

        System.out.println("1 - Dodaj nowego gościa");
        System.out.println("2 - Dodaj nowy pokój");
        System.out.println("3 - Wypisz gości");
        System.out.println("4 - Wypisz pokoje");
        System.out.println("5 - Usuń gościa");
        System.out.println("6 - Edytuj dane gościa");
        System.out.println("7 - Usuń pokój");
        System.out.println("8 - Edytuj pokój");
        System.out.println("9 - Stwórz rezerwację");
        System.out.println("0 - Wyjście z aplikacji");
        System.out.println("Wybierz opcję: ");

        int actionNumber;

        try {
            actionNumber = in.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers in main menu");
        }
        return actionNumber;
    }
}
