package br.com.fiap.loyalty.model;

public class Missao implements Ativavel {

    //Definindo atributos de Missao
    private int idMissao;
    private int idCampanha;
    private String nomeMissao;
    private String descricaoMissao;
    private int metaMissao;
    private int pontosRecompensaMissao;
    private StatusMissao statusMissao;

    //Construtor padrão
    public Missao() {
    }

    //Construtor com todos os parâmetros
    public Missao(int idMissao, int idCampanha, String nomeMissao, String descricaoMissao,
                  int metaMissao, int pontosRecompensaMissao, StatusMissao statusMissao) {
        this.idMissao = idMissao;
        this.idCampanha = idCampanha;
        this.nomeMissao = nomeMissao;
        this.descricaoMissao = descricaoMissao;
        this.metaMissao = metaMissao;
        this.pontosRecompensaMissao = pontosRecompensaMissao;
        this.statusMissao = statusMissao;
    }

    //Métodos Getters
    public int getIdMissao() {
        return idMissao;
    }
    public int getIdCampanha() {
        return idCampanha;
    }
    public String getNomeMissao() {
        return nomeMissao;
    }
    public String getDescricaoMissao() {
        return descricaoMissao;
    }
    public int getMetaMissao() {
        return metaMissao;
    }
    public int getPontosRecompensaMissao() {
        return pontosRecompensaMissao;
    }
    public StatusMissao getStatusMissao() {
        return statusMissao;
    }

    //Métodos Setters
    public void setIdMissao(int idMissao) {
        this.idMissao = idMissao;
    }
    public void setIdCampanha(int idCampanha) {
        this.idCampanha = idCampanha;
    }
    public void setNomeMissao(String nomeMissao) {
        this.nomeMissao = nomeMissao;
    }
    public void setDescricaoMissao(String descricaoMissao) {
        this.descricaoMissao = descricaoMissao;
    }
    public void setMetaMissao(int metaMissao) {
        this.metaMissao = metaMissao;
    }
    public void setPontosRecompensaMissao(int pontosRecompensaMissao) {
        this.pontosRecompensaMissao = pontosRecompensaMissao;
    }
    public void setStatusMissao(StatusMissao statusMissao) {
        this.statusMissao = statusMissao;
    }

    //Métodos de negócio

    // Verifica se o progresso informado atingiu a meta da missão
    public boolean verificarMetaAtingida(int progressoAtual) {
        return progressoAtual >= this.metaMissao;
    }

    // RN24 — missão inativa não concede pontos nem recompensas
    public boolean concedeRecompensa() {
        return this.statusMissao == StatusMissao.ATIVA;
    }

    @Override
    public void ativar() {
        if(statusMissao == StatusMissao.INATIVA){
            this.statusMissao = StatusMissao.ATIVA;
        } else{
            throw new IllegalStateException(
                    "Missão já está ativada.");
        }

    }

    @Override
    public void desativar() {
        if(statusMissao == StatusMissao.ATIVA) {
            this.statusMissao = StatusMissao.INATIVA;
        } else {
            throw new IllegalStateException(
                    "Missão já está desativada.");
        }
    }

    public void excluir() {
        this.statusMissao = StatusMissao.EXCLUIDA;
    }

    @Override
    public String toString() {
        return "Dados da Missão{" +
                "Id da Missão= " + getIdMissao() +
                ", Id da Campanha= " + getIdCampanha() +
                ", Nome da Missão= " + getNomeMissao() +
                ", Descrição= " + getDescricaoMissao() +
                ", Meta= " + getMetaMissao() +
                ", Pontos de Recompensa= " + getPontosRecompensaMissao() +
                ", Status= " + getStatusMissao() +
                "}";
    }
}