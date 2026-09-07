package br.com.fiap.loyalty.model;

import java.time.LocalDate;

public class Campanha implements Ativavel {

    //Definindo atributos da Campanha
    private int idCampanha;
    private int idEmpresa;
    private String nomeCampanha;
    private String descricaoCampanha;
    private LocalDate dataIniCampanha;
    private LocalDate dataFimCampanha;
    private StatusCampanha statusCampanha;

    //Construtor padrão
    public Campanha() {
    }

    //Construtor com todos os parâmetros
    public Campanha(int idCampanha, int idEmpresa, String nomeCampanha, String descricaoCampanha,
                    LocalDate dataIniCampanha, LocalDate dataFimCampanha, StatusCampanha statusCampanha) {
        this.idCampanha = idCampanha;
        this.idEmpresa = idEmpresa;
        this.nomeCampanha = nomeCampanha;
        this.descricaoCampanha = descricaoCampanha;
        this.dataIniCampanha = dataIniCampanha;
        this.dataFimCampanha = dataFimCampanha;
        this.statusCampanha = statusCampanha;
    }

    //Métodos Getters
    public int getIdCampanha() {
        return idCampanha;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public String getDescricaoCampanha() {
        return descricaoCampanha;
    }

    public LocalDate getDataIniCampanha() {
        return dataIniCampanha;
    }

    public LocalDate getDataFimCampanha() {
        return dataFimCampanha;
    }

    public StatusCampanha getStatusCampanha() {
        return statusCampanha;
    }

    //Métodos Setters
    public void setIdCampanha(int idCampanha) {
        this.idCampanha = idCampanha;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public void setDescricaoCampanha(String descricaoCampanha) {
        this.descricaoCampanha = descricaoCampanha;
    }

    public void setDataIniCampanha(LocalDate dataIniCampanha) {
        this.dataIniCampanha = dataIniCampanha;
    }

    public void setDataFimCampanha(LocalDate dataFimCampanha) {
        this.dataFimCampanha = dataFimCampanha;
    }

    public void setStatusCampanha(StatusCampanha statusCampanha) {
        this.statusCampanha = statusCampanha;
    }

    //Métodos de negócio

    // RN23 — encerrar é terminal: campanha encerrada não volta a ser ativa
    @Override
    public void ativar() {
        if (this.statusCampanha == StatusCampanha.ENCERRADA) {
            throw new IllegalStateException(
                    "Campanha encerrada não pode ser reativada: " + getNomeCampanha());
        }
        if (this.statusCampanha == StatusCampanha.ATIVA) {
            throw new IllegalStateException("Campanha já está ativa: " + getNomeCampanha());
        }
        this.statusCampanha = StatusCampanha.ATIVA;
    }

    @Override
    public void desativar() {
        if (this.statusCampanha == StatusCampanha.ENCERRADA) {
            throw new IllegalStateException(
                    "Campanha encerrada não pode ser desativada: " + getNomeCampanha());
        }
        if (this.statusCampanha == StatusCampanha.INATIVA) {
            throw new IllegalStateException("Campanha já está inativa: " + getNomeCampanha());
        }
        this.statusCampanha = StatusCampanha.INATIVA;
    }

    // RN16 — a pontuação já conquistada permanece com os usuários após o encerramento
    public void encerrar() {
        if (this.statusCampanha == StatusCampanha.ENCERRADA) {
            throw new IllegalStateException("Campanha já está encerrada: " + getNomeCampanha());
        }
        this.statusCampanha = StatusCampanha.ENCERRADA;
    }

    // RN09 — somente campanha ativa concede pontos e recompensas
    public boolean concedeRecompensa() {
        return this.statusCampanha == StatusCampanha.ATIVA;
    }

    @Override
    public String toString() {
        return "Dados da Campanha{" +
                "Id da Campanha= " + getIdCampanha() +
                ", Id da Empresa= " + getIdEmpresa() +
                ", Nome= " + getNomeCampanha() +
                ", Descrição= " + getDescricaoCampanha() +
                ", Data de Início= " + getDataIniCampanha() +
                ", Data de Fim= " + getDataFimCampanha() +
                ", Status= " + getStatusCampanha() +
                "}";
    }
}
