package exemplo.diasuteis.view;

import exemplo.diasuteis.App;
import exemplo.diasuteis.dao.UsuarioDAO;
import exemplo.diasuteis.model.Usuario;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField pwdSenha;

    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException {
        if (!validaForm()) {
            return;
        }

        Usuario usr = new Usuario();
        usr.setLogin(txtLogin.getText());
        usr.setSenha(pwdSenha.getText());

        UsuarioDAO usrDao = new UsuarioDAO();
        Usuario usrLogin = usrDao.login(usr);

        if (usrLogin != null) {
            App.usuarioLogado = usrLogin;
            App.setRoot("principal");
        } else {
            showAlert("Usuário ou Senha inválido!", AlertType.ERROR);
        }
    }

    private boolean validaForm() {
        if (txtLogin.getText().isEmpty()) {
            showAlert("Preencha o campo Login!", AlertType.ERROR);
            txtLogin.requestFocus();
            return false;
        }

        if (pwdSenha.getText().isEmpty()) {
            showAlert("Preencha o campo Senha!", AlertType.ERROR);
            pwdSenha.requestFocus();
            return false;
        }

        return true;
    }

    private void showAlert(String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Login");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
