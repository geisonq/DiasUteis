package exemplo.diasuteis.view;

import exemplo.diasuteis.dao.FeriadoDAO;
import exemplo.diasuteis.model.Feriado;
import exemplo.diasuteis.view.util.DateUtils;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DiasUteisController {

    @FXML
    private DatePicker dtInicial;

    @FXML
    private DatePicker dtFinal;

    @FXML
    private Button btnCalcular;

    @FXML
    private Label lblDias;

    @FXML
    private TableView<Feriado> tblFeriado;

    @FXML
    private TableColumn<Feriado, LocalDate> colDia;

    @FXML
    private TableColumn<Feriado, String> colDescricao;

    private ObservableList<Feriado> feriados;

    @FXML
    private void initialize() {
        colDia.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDia()));
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));

        FeriadoDAO feriadoDao = new FeriadoDAO();
        feriados = FXCollections.observableArrayList(feriadoDao.lista());
        tblFeriado.setItems(feriados);
    }

    @FXML
    private void btnCalcularAction(ActionEvent event) {
        if (dtInicial.getValue() == null || dtFinal.getValue() == null) {
            showAlert("Preencha a Data Inicial e a Data Final!");
            return;
        }

        List<LocalDate> diasFeriados = feriados.stream()
                .map(Feriado::getDia)
                .collect(Collectors.toList());

        int dias = DateUtils.getWorkingDays(dtInicial.getValue(), dtFinal.getValue(), diasFeriados);
        lblDias.setText(String.valueOf(dias));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Dias Úteis");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
