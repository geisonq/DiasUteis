package exemplo.diasuteis.dao;

import exemplo.diasuteis.factory.FactoryConnector;
import exemplo.diasuteis.model.Feriado;
import exemplo.diasuteis.model.TipoFeriado;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeriadoDAO {

    public ArrayList<Feriado> lista() {
        String sql = "SELECT feriado.*, tipoferiado.tipo as nome_tipo "
                + "  FROM feriado left join tipoferiado on feriado.tipo = tipoferiado.codigo "
                + " ORDER BY feriado.dia";
        ArrayList<Feriado> listaFeriado = new ArrayList<>();

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                TipoFeriado tipoFeriado = new TipoFeriado(res.getInt("tipo"), res.getString("nome_tipo"));

                Feriado feriado = new Feriado(res.getInt("codigo"),
                        res.getDate("dia").toLocalDate(),
                        res.getString("descricao"),
                        tipoFeriado);

                listaFeriado.add(feriado);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
        }

        return listaFeriado;
    }

    public boolean insert(Feriado feriado) {
        String sql = "INSERT INTO feriado (dia, descricao, tipo) VALUES (?,?,?)";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(feriado.getDia()));
            stmt.setString(2, feriado.getDescricao());
            stmt.setInt(3, feriado.getTpFeriado().getCodigo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }

    public boolean update(Feriado feriado) {
        String sql = "UPDATE feriado SET dia = ?, descricao = ?, tipo = ? WHERE codigo = ?";

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(feriado.getDia()));
            stmt.setString(2, feriado.getDescricao());
            stmt.setInt(3, feriado.getTpFeriado().getCodigo());
            stmt.setInt(4, feriado.getCodigo());
            stmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int codigo) {
        String sql = "DELETE FROM feriado WHERE codigo = ?";

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
