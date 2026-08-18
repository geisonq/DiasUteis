package exemplo.diasuteis.model;

import java.time.LocalDate;

public class Feriado {

    private int codigo;
    private LocalDate dia;
    private String descricao;
    private TipoFeriado tpFeriado;

    public Feriado() {
    }

    public Feriado(int codigo, LocalDate dia, String descricao, TipoFeriado tpFeriado) {
        this.codigo = codigo;
        this.dia = dia;
        this.descricao = descricao;
        this.tpFeriado = tpFeriado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoFeriado getTpFeriado() {
        return tpFeriado;
    }

    public void setTpFeriado(TipoFeriado tpFeriado) {
        this.tpFeriado = tpFeriado;
    }
}
