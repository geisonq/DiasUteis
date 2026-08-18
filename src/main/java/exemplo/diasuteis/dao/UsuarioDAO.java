package exemplo.diasuteis.dao;

import exemplo.diasuteis.factory.FactoryConnector;
import exemplo.diasuteis.model.Perfil;
import exemplo.diasuteis.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    public Usuario login(Usuario usr) {
        String sql = " SELECT usuario.*, "
                + "           perfil.codigo as codigo_perfil, perfil.perfil as nome_perfil "
                + "      FROM usuario left join perfil on usuario.perfil = perfil.codigo "
                + "     WHERE login = ? "
                + "       AND senha = ? ";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usr.getLogin());
            stmt.setString(2, usr.getSenha());

            try (ResultSet res = stmt.executeQuery()) {
                if (res.next()) {
                    Perfil perfil = new Perfil(res.getInt("codigo_perfil"), res.getString("nome_perfil"));
                    return new Usuario(res.getInt("codigo"),
                            res.getString("nome"),
                            res.getString("login"),
                            res.getString("senha"),
                            res.getString("email"),
                            perfil);
                }
            }

            return null;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return null;
        }
    }

    public boolean insert(Usuario usr) {
        String sql = "INSERT INTO usuario (nome, login, senha, email, perfil) VALUES (?,?,?,?,?)";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usr.getNome());
            stmt.setString(2, usr.getLogin());
            stmt.setString(3, usr.getSenha());
            stmt.setString(4, usr.getEmail());
            stmt.setInt(5, usr.getPerfil().getCodigo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }

    public boolean update(Usuario usr) {
        String sql = "UPDATE usuario SET nome = ?, login = ?, senha = ?, email = ?, perfil = ? WHERE codigo = ?";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usr.getNome());
            stmt.setString(2, usr.getLogin());
            stmt.setString(3, usr.getSenha());
            stmt.setString(4, usr.getEmail());
            stmt.setInt(5, usr.getPerfil().getCodigo());
            stmt.setInt(6, usr.getCodigo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }

    public ArrayList<Usuario> lista() {
        String sql = "SELECT usuario.*, perfil.codigo as codigo_perfil, perfil.perfil as nome_perfil "
                + "FROM usuario left join perfil on usuario.perfil = perfil.codigo";
        ArrayList<Usuario> listaUsuario = new ArrayList<>();

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                Perfil perfil = new Perfil(res.getInt("codigo_perfil"), res.getString("nome_perfil"));
                Usuario usuario = new Usuario(res.getInt("codigo"),
                        res.getString("nome"),
                        res.getString("login"),
                        res.getString("senha"),
                        res.getString("email"),
                        perfil);
                listaUsuario.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
        }

        return listaUsuario;
    }

    public boolean excluir(int codigo) {
        String sql = "DELETE FROM usuario WHERE codigo = ?";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }
}
