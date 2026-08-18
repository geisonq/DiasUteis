package exemplo.diasuteis.view;

import exemplo.diasuteis.dao.TipoFeriadoDAO;
import exemplo.diasuteis.model.TipoFeriado;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TipoFeriadoController {

    @FXML
    private TableView<TipoFeriado> tblTipoFeriado;

    @FXML
    private TableColumn<TipoFeriado, Integer> colCodigo;

    @FXML
    private TableColumn<TipoFeriado, String> colTipo;

    @FXML
    private TextField txtTipo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnNovo;

    private TipoFeriado tipoSelecionado;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCodigo()).asObject());
        colTipo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipo()));

        tblTipoFeriado.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> selecionarTipo(newSel));

        atualizaTabela();
    }

    private void atualizaTabela() {
        TipoFeriadoDAO tipoFeriadoDao = new TipoFeriadoDAO();
        ObservableList<TipoFeriado> tipos = FXCollections.observableArrayList(tipoFeriadoDao.lista());
        tblTipoFeriado.setItems(tipos);
    }

    private void selecionarTipo(TipoFeriado tipo) {
        tipoSelecionado = tipo;

        if (tipo == null) {
            return;
        }

        txtTipo.setText(tipo.getTipo());
    }

    @FXML
    private void btnNovoAction(ActionEvent event) {
        limpaCampos();
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        if (txtTipo.getText().isEmpty()) {
            showAlert("Preencha o campo Tipo!", AlertType.ERROR);
            txtTipo.requestFocus();
            return;
        }

        TipoFeriado tipoFeriado = new TipoFeriado();
        tipoFeriado.setTipo(txtTipo.getText());

        TipoFeriadoDAO tipoFeriadoDao = new TipoFeriadoDAO();
        boolean retornoDb;

        if (tipoSelecionado != null) {
            tipoFeriado.setCodigo(tipoSelecionado.getCodigo());
            retornoDb = tipoFeriadoDao.update(tipoFeriado);
        } else {
            retornoDb = tipoFeriadoDao.insert(tipoFeriado);
        }

        if (retornoDb) {
            limpaCampos();
            atualizaTabela();
            showAlert("Tipo de feriado salvo com sucesso!", AlertType.INFORMATION);
        } else {
            showAlert("Erro ao salvar tipo de feriado!", AlertType.ERROR);
        }
    }

    @FXML
    private void btnDeletarAction(ActionEvent event) {
        if (tipoSelecionado == null) {
            showAlert("Selecione um tipo na tabela!", AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(AlertType.CONFIRMATION, "Deseja realmente excluir este tipo de feriado?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Tipos de Feriado");
        confirm.setHeaderText(null);

        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            TipoFeriadoDAO tipoFeriadoDao = new TipoFeriadoDAO();
            boolean retorno = tipoFeriadoDao.excluir(tipoSelecionado.getCodigo());

            if (retorno) {
                showAlert("Tipo de feriado excluído com sucesso!", AlertType.INFORMATION);
                limpaCampos();
                atualizaTabela();
            } else {
                showAlert("Erro ao excluir: verifique se não há feriados usando este tipo.", AlertType.ERROR);
            }
        }
    }

    private void limpaCampos() {
        tipoSelecionado = null;
        txtTipo.setText("");
        tblTipoFeriado.getSelectionModel().clearSelection();
        txtTipo.requestFocus();
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Tipos de Feriado");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
