package exemplo.diasuteis.view;

import exemplo.diasuteis.dao.FeriadoDAO;
import exemplo.diasuteis.dao.TipoFeriadoDAO;
import exemplo.diasuteis.model.Feriado;
import exemplo.diasuteis.model.TipoFeriado;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FeriadoController {

    @FXML
    private TableView<Feriado> tblFeriado;

    @FXML
    private TableColumn<Feriado, Integer> colCodigo;

    @FXML
    private TableColumn<Feriado, LocalDate> colDia;

    @FXML
    private TableColumn<Feriado, String> colDescricao;

    @FXML
    private TableColumn<Feriado, String> colTipo;

    @FXML
    private DatePicker dtFeriado;

    @FXML
    private TextField txtDescricao;

    @FXML
    private ComboBox<TipoFeriado> cmbTipoFeriado;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnNovo;

    private Feriado feriadoSelecionado;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCodigo()).asObject());
        colDia.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDia()));
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTpFeriado().getTipo()));

        tblFeriado.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> selecionarFeriado(newSel));

        carregarTiposFeriado();
        atualizaTabela();
    }

    private void carregarTiposFeriado() {
        TipoFeriadoDAO tipoFeriadoDao = new TipoFeriadoDAO();
        ArrayList<TipoFeriado> tipos = tipoFeriadoDao.lista();
        cmbTipoFeriado.setItems(FXCollections.observableArrayList(tipos));
    }

    private void atualizaTabela() {
        FeriadoDAO feriadoDao = new FeriadoDAO();
        ObservableList<Feriado> feriados = FXCollections.observableArrayList(feriadoDao.lista());
        tblFeriado.setItems(feriados);
    }

    private void selecionarFeriado(Feriado feriado) {
        feriadoSelecionado = feriado;

        if (feriado == null) {
            return;
        }

        dtFeriado.setValue(feriado.getDia());
        txtDescricao.setText(feriado.getDescricao());
        cmbTipoFeriado.getSelectionModel().select(feriado.getTpFeriado());
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        limpaCampos();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (!validaForm()) {
            return;
        }

        Feriado feriado = new Feriado();
        feriado.setDia(dtFeriado.getValue());
        feriado.setDescricao(txtDescricao.getText());
        feriado.setTpFeriado(cmbTipoFeriado.getValue());

        FeriadoDAO feriadoDao = new FeriadoDAO();
        boolean retornoDb;

        if (feriadoSelecionado != null) {
            feriado.setCodigo(feriadoSelecionado.getCodigo());
            retornoDb = feriadoDao.update(feriado);
        } else {
            retornoDb = feriadoDao.insert(feriado);
        }

        if (retornoDb) {
            limpaCampos();
            atualizaTabela();
            showAlert("Feriado salvo com sucesso!", AlertType.INFORMATION);
        } else {
            showAlert("Erro ao salvar feriado!", AlertType.ERROR);
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        if (feriadoSelecionado == null) {
            showAlert("Selecione um feriado na tabela!", AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(AlertType.CONFIRMATION, "Deseja realmente excluir este feriado?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Feriados");
        confirm.setHeaderText(null);

        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            FeriadoDAO feriadoDao = new FeriadoDAO();
            boolean retorno = feriadoDao.excluir(feriadoSelecionado.getCodigo());

            if (retorno) {
                showAlert("Feriado excluído com sucesso!", AlertType.INFORMATION);
                limpaCampos();
                atualizaTabela();
            } else {
                showAlert("Erro ao excluir feriado!", AlertType.ERROR);
            }
        }
    }

    private boolean validaForm() {
        if (dtFeriado.getValue() == null) {
            showAlert("Preencha o campo Dia!", AlertType.ERROR);
            dtFeriado.requestFocus();
            return false;
        }

        if (txtDescricao.getText().isEmpty()) {
            showAlert("Preencha o campo Descrição!", AlertType.ERROR);
            txtDescricao.requestFocus();
            return false;
        }

        if (cmbTipoFeriado.getValue() == null) {
            showAlert("Selecione o Tipo! (cadastre um Tipo de Feriado antes, se a lista estiver vazia)", AlertType.ERROR);
            cmbTipoFeriado.requestFocus();
            return false;
        }

        return true;
    }

    private void limpaCampos() {
        feriadoSelecionado = null;
        dtFeriado.setValue(null);
        txtDescricao.setText("");
        cmbTipoFeriado.getSelectionModel().clearSelection();
        tblFeriado.getSelectionModel().clearSelection();
        dtFeriado.requestFocus();
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Feriados");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
