package pl.clockworkjava.domain.room;

import pl.clockworkjava.domain.room.dto.RoomDTO;
import pl.clockworkjava.exceptions.WrongOptionException;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final static RoomRepository repository = new RoomRepository();

    public Room createNewRoom(int number, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for(int i=0; i<bedTypesOptions.length; i=i+1){

            BedType bedType;

            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in bed selection");
            }

            bedTypes[i] = bedType;
        }

        return repository.createNewRoom(number, bedTypes);
    }

    public List<Room> getAllRooms() {
        return this.repository.getAll();
    }

    public void saveAll(){
        this.repository.saveAll();
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void editRoom(int id, int number, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for(int i=0; i<bedTypesOptions.length; i=i+1){

            BedType bedType;

            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option in bed selection");
            }

            bedTypes[i] = bedType;
        }
        this.repository.edit(id, number, bedTypes);
    }

    public void removeRoom(int id) {
        this.repository.remove(id);
    }

    public Room getRoomById(int roomId) {
        return this.repository.getById(roomId);
    }

    public List<RoomDTO> getRoomsAsDTO() {
        List<RoomDTO> result = new ArrayList<RoomDTO>();

        List<Room> allRooms = repository.getAll();

        for (Room room : allRooms) {
            RoomDTO dto = room.generateDTO();
            result.add(dto);
        }
        return result;
    }
}
