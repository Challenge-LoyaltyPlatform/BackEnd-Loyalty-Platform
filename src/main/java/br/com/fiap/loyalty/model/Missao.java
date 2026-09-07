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

    // RN24 — missão inativa ou excluída não concede pontos nem recompensas
    public boolean concedeRecompensa() {
        return this.statusMissao == StatusMissao.ATIVA;
    }

    // RN25 — a exclusão é lógica e terminal: missão excluída não é reativada
    @Override
    public void ativar() {
        if (this.statusMissao == StatusMissao.EXCLUIDA) {
            throw new IllegalStateException(
                    "Missão excluída não pode ser reativada: " + getNomeMissao());
        }
        if (this.statusMissao == StatusMissao.ATIVA) {
            throw new IllegalStateException("Missão já está ativa: " + getNomeMissao());
        }
        this.statusMissao = StatusMissao.ATIVA;
    }

    @Override
    public void desativar() {
        if (this.statusMissao == StatusMissao.EXCLUIDA) {
            throw new IllegalStateException(
                    "Missão excluída não pode ser desativada: " + getNomeMissao());
        }
        if (this.statusMissao == StatusMissao.INATIVA) {
            throw new IllegalStateException("Missão já está inativa: " + getNomeMissao());
        }
        this.statusMissao = StatusMissao.INATIVA;
    }

    // RN25 — exclusão lógica: o histórico de conclusões é preservado
    public void excluir() {
        if (this.statusMissao == StatusMissao.EXCLUIDA) {
            throw new IllegalStateException("Missão já foi excluída: " + getNomeMissao());
        }
        this.statusMissao = StatusMissao.EXCLUIDA;
    }

    @Override
    public String toString() {
        return "Dados da Missão{" +
                "Id da Missão= " + getIdMissao() +
                ", Id da Campanha= " + getIdCampanha() +
                ", Nome= " + getNomeMissao() +
                ", Descrição= " + getDescricaoMissao() +
                ", Meta= " + getMetaMissao() +
                ", Pontos de Recompensa= " + getPontosRecompensaMissao() +
                ", Status= " + getStatusMissao() +
                "}";
    }
}
