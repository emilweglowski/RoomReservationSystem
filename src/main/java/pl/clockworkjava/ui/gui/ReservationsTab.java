package pl.clockworkjava.ui.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.clockworkjava.domain.reservation.ReservationService;
import pl.clockworkjava.domain.reservation.dto.ReservationDTO;

import java.time.LocalDateTime;

public class ReservationsTab {

    private Tab reservationsTab;
    private ReservationService reservationService = new ReservationService();

    public ReservationsTab() {

        TableView<ReservationDTO> tableView = new TableView<>();

        TableColumn<ReservationDTO, LocalDateTime> fromColumn = new TableColumn<>("Od");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<ReservationDTO, LocalDateTime> toColumn = new TableColumn<>("Do");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));

        TableColumn<ReservationDTO, Integer> roomNumberColumn = new TableColumn<>("Numer pokoju");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<ReservationDTO, String> guestColumn = new TableColumn<>("Rezerwujący");
        guestColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        tableView.getColumns().addAll(fromColumn, toColumn, roomNumberColumn, guestColumn);

        tableView.getItems().addAll(reservationService.getReservationsAsDTO());

        this.reservationsTab = new Tab("Rezerwacje", tableView);
        this.reservationsTab.setClosable(false);
    }

    public Tab getReservationsTab() {
        return reservationsTab;
    }
}
