//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package s25.cs151.application;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EditOfficeHoursController extends NavigationController {
    @FXML
    private TextField searchField;
    @FXML
    private TableView<OfficeHourEntry> scheduleTable;
    @FXML
    private TableColumn<OfficeHourEntry, String> nameCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> dateCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> slotCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> courseCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> reasonCol;
    @FXML
    private TableColumn<OfficeHourEntry, String> commentCol;
    @FXML
    private Button editButton;
    private final ObservableList<OfficeHourEntry> fullList = FXCollections.observableArrayList();
    private final ObservableList<OfficeHourEntry> filteredList = FXCollections.observableArrayList();

    public EditOfficeHoursController() {
    }

    @FXML
    public void initialize() {
        this.nameCol.setCellValueFactory((cell) -> new SimpleStringProperty(((OfficeHourEntry)cell.getValue()).getStudentName()));
        this.dateCol.setCellValueFactory((cell) -> new SimpleStringProperty(((OfficeHourEntry)cell.getValue()).getScheduleDate()));
        this.slotCol.setCellValueFactory((cell) -> new SimpleStringProperty(((OfficeHourEntry)cell.getValue()).getTimeSlot()));
        this.courseCol.setCellValueFactory((cell) -> new SimpleStringProperty(((OfficeHourEntry)cell.getValue()).getCourse()));
        this.reasonCol.setCellValueFactory((cell) -> new SimpleStringProperty(((OfficeHourEntry)cell.getValue()).getReason()));
        this.commentCol.setCellValueFactory((cell) -> new SimpleStringProperty(((OfficeHourEntry)cell.getValue()).getComment()));
        this.loadCSVData();
        this.scheduleTable.setItems(this.filteredList);
        this.searchField.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> this.handleSearch());
    }

    private void loadCSVData() {
        File file = new File("data/office_hour_schedule.csv");
        if (file.exists()) {
            try {
                Throwable e = null;
                Object var3 = null;

                try {
                    Scanner scanner = new Scanner(file);

                    try {
                        if (scanner.hasNextLine()) {
                            scanner.nextLine();
                        }

                        while(scanner.hasNextLine()) {
                            String[] row = scanner.nextLine().split(",", -1);
                            if (row.length >= 6) {
                                this.fullList.add(new OfficeHourEntry(row[0], row[1], row[2], row[3], row[4], row[5]));
                            }
                        }

                        this.sortList(this.fullList);
                        this.filteredList.setAll(this.fullList);
                    } finally {
                        if (scanner != null) {
                            scanner.close();
                        }

                    }
                } catch (Throwable var13) {
                    if (e == null) {
                        e = var13;
                    } else if (e != var13) {
                        e.addSuppressed(var13);
                    }

                    throw e;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                this.showErrorMessage("Failed to load office hour schedule.");
            }

        }
    }

    private void sortList(ObservableList<OfficeHourEntry> list) {
        list.sort((a, b) -> {
            int dateCompare = b.getParsedDate().compareTo(a.getParsedDate());
            return dateCompare != 0 ? dateCompare : b.getStartTime().compareTo(a.getStartTime());
        });
    }

    @FXML
    private void handleSearch() {
        String query = this.searchField.getText().toLowerCase().trim();
        if (query.isEmpty()) {
            this.filteredList.setAll(this.fullList);
        } else {
            this.filteredList.setAll((Collection)this.fullList.stream().filter((e) -> e.getStudentName().toLowerCase().contains(query)).collect(Collectors.toList()));
        }

        this.sortList(this.filteredList);
    }

    @FXML
    private void handleEdit() {
        OfficeHourEntry selected = (OfficeHourEntry)this.scheduleTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            this.showErrorMessage("Please select a schedule to edit.");
        } else {
            EditScheduleForm.show(selected, this.fullList, this.filteredList);
        }
    }
}
