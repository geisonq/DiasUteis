package exemplo.diasuteis.view;

import exemplo.diasuteis.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class PrincipalController {

    @FXML
    private BorderPane painelPrincipal;

    @FXML
    private Label lblBoasVindas;

    @FXML
    private void initialize() {
        String nome = App.usuarioLogado != null ? App.usuarioLogado.getNome() : "";
        lblBoasVindas.setText("Bem-vindo" + (nome != null && !nome.isEmpty() ? ", " + nome : "") + "!");
    }

    @FXML
    private void abrirDiasUteis(ActionEvent event) throws IOException {
        abrirTela("diasUteis");
    }

    @FXML
    private void abrirFeriados(ActionEvent event) throws IOException {
        abrirTela("feriado");
    }

    @FXML
    private void abrirTiposFeriado(ActionEvent event) throws IOException {
        abrirTela("tipoFeriado");
    }

    private void abrirTela(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("view/" + fxml + ".fxml"));
        Node tela = loader.load();
        painelPrincipal.setCenter(tela);
    }

    @FXML
    private void sair(ActionEvent event) throws IOException {
        App.usuarioLogado = null;
        App.setRoot("login");
    }
}
