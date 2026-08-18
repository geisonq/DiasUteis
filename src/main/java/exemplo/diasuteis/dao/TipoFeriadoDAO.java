package exemplo.diasuteis.dao;

import exemplo.diasuteis.factory.FactoryConnector;
import exemplo.diasuteis.model.TipoFeriado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TipoFeriadoDAO {

    public ArrayList<TipoFeriado> lista() {
        String sql = "SELECT * FROM tipoferiado ORDER BY tipo";
        ArrayList<TipoFeriado> listaTipoFeriado = new ArrayList<>();

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                TipoFeriado tipoFeriado = new TipoFeriado(res.getInt("codigo"), res.getString("tipo"));
                listaTipoFeriado.add(tipoFeriado);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
        }

        return listaTipoFeriado;
    }

    public boolean insert(TipoFeriado tipoFeriado) {
        String sql = "INSERT INTO tipoferiado (tipo) VALUES (?)";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoFeriado.getTipo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }

    public boolean update(TipoFeriado tipoFeriado) {
        String sql = "UPDATE tipoferiado SET tipo = ? WHERE codigo = ?";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoFeriado.getTipo());
            stmt.setInt(2, tipoFeriado.getCodigo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        String sql = "DELETE FROM tipoferiado WHERE codigo = ?";

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
