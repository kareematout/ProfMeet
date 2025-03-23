package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DisplayOfficeHoursController {

    @FXML
    private TableView<OfficeHour> tableView;

    @FXML
    private TableColumn<OfficeHour, String> semesterColumn;

    @FXML
    private TableColumn<OfficeHour, String> yearColumn;

    @FXML
    private TableColumn<OfficeHour, String> daysColumn;

    private final ObservableList<OfficeHour> officeHoursList = FXCollections.observableArrayList();

    private static final List<String> SEMESTER_ORDER = Arrays.asList("Spring", "Summer", "Fall", "Winter");

    @FXML
    public void initialize() {
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));

        loadOfficeHoursFromCSV();
        tableView.setItems(officeHoursList);
    }

    private void loadOfficeHoursFromCSV() {
        File file = new File("data/office_hours.csv");

        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }

                String[] parts = line.split(",", 3); // split into 3 parts only to preserve commas in days
                if (parts.length == 3) {
                    OfficeHour oh = new OfficeHour(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    officeHoursList.add(oh);
                }
            }

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);

            officeHoursList.sort((o1, o2) -> {
                int year1 = Integer.parseInt(o1.getYear());
                int year2 = Integer.parseInt(o2.getYear());

                // Closest future year first, then past years
                int diff1 = Math.abs(year1 - currentYear);
                int diff2 = Math.abs(year2 - currentYear);

                if (diff1 != diff2) return Integer.compare(diff1, diff2);

                return Integer.compare(
                        SEMESTER_ORDER.indexOf(o1.getSemester()),
                        SEMESTER_ORDER.indexOf(o2.getSemester())
                );
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}