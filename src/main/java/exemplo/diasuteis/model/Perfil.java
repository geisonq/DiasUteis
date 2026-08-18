package exemplo.diasuteis.model;

public class Perfil {

    private int codigo;
    private String perfil;

    public Perfil() {
    }

    public Perfil(int codigo) {
        this.codigo = codigo;
    }

    public Perfil(int codigo, String perfil) {
        this.codigo = codigo;
        this.perfil = perfil;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return perfil;
    }
}
