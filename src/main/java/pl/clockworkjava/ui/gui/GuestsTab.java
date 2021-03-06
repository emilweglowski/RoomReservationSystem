package pl.clockworkjava.ui.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.clockworkjava.domain.guest.GuestService;
import pl.clockworkjava.domain.guest.dto.GuestDTO;

public class GuestsTab {

    private Tab guestsTab;
    private GuestService guestService = new GuestService();

    public GuestsTab() {

        TableView<GuestDTO> tableView = new TableView<>();

        TableColumn<GuestDTO, String> firstNameColumn = new TableColumn<>("Imię");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<GuestDTO, String> lastNameColumn = new TableColumn<>("Nazwisko");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<GuestDTO, Integer> ageColumn = new TableColumn<>("Wiek");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<GuestDTO, String> genderColumn = new TableColumn("Płeć");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, ageColumn, genderColumn);

        tableView.getItems().addAll(guestService.getGuestsAsDTO());

        this.guestsTab = new Tab("Goście", tableView);
        this.guestsTab.setClosable(false);
    }

    public Tab getGuestsTab() {
        return guestsTab;
    }

}
