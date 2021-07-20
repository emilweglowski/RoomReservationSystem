package pl.clockworkjava.domain.room;

public class Room {

    private final int number;
    private final BedType[] beds;

    Room(int number, BedType[] beds){
        this.number=number;
        this.beds=beds;
    }

    public String getInfo(){

        StringBuilder bedInfo = new StringBuilder("\nRodzaje łóżek w pokoju:\n");
        for(BedType bed : beds){
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("Dodano nowy pokój - numer %d %s", this.number, bedInfo.toString());
    }
}
