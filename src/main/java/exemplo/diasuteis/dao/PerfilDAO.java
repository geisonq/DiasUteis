package exemplo.diasuteis.dao;

import exemplo.diasuteis.factory.FactoryConnector;
import exemplo.diasuteis.model.Perfil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PerfilDAO {

    public ArrayList<Perfil> lista() {
        String sql = "SELECT * FROM perfil ORDER BY perfil";
        ArrayList<Perfil> listaPerfil = new ArrayList<>();

        try (Connection conn = FactoryConnector.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                Perfil perfil = new Perfil(res.getInt("codigo"), res.getString("perfil"));
                listaPerfil.add(perfil);
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + "-" + e.getMessage());
        }

        return listaPerfil;
    }
}
