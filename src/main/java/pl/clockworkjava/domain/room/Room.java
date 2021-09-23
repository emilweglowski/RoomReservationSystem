package pl.clockworkjava.domain.room;

import pl.clockworkjava.domain.room.dto.RoomDTO;

public class Room {

    private final int id;
    private final int number;
    private final BedType[] beds;

    Room(int id, int number, BedType[] beds){
        this.id = id;
        this.number=number;
        this.beds=beds;
    }

    public int getId() {
        return this.id;
    }

    public int getNumber() { return this.number; }

    public String getInfo(){

        StringBuilder bedInfo = new StringBuilder("\nRodzaje łóżek w pokoju:\n");
        for(BedType bed : beds){
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("%d Pokój numer %d %s", this.id, this.number, bedInfo.toString());
    }

    String toCSV() {

        String[] bedsAsString = getBedsAsStrings();

        String bedTypes = String.join("#", bedsAsString);

        return String.format("%d,%d,%s%s",
                this.id,
                this.number,
                bedTypes,
                System.getProperty("line.separator"));
    }

    private String[] getBedsAsStrings() {
        String[] bedsAsString = new String[this.beds.length];

        for (int i = 0; i < this.beds.length; i++) {
            bedsAsString[i] = this.beds[i].toString();
        }
        return bedsAsString;
    }

    public RoomDTO generateDTO() {
        String[] bedsAsString = getBedsAsStrings();

        String bedTypes = String.join(",", bedsAsString);

        int roomSize = 0;

        for (BedType bedType : beds) {
            roomSize += bedType.getSize();
        }

        return new RoomDTO(this.id, this.number, bedTypes, beds.length, roomSize);
    }
}
