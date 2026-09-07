package br.com.fiap.loyalty.model;

public class Ranking {

    //Definindo atributos do Ranking (chave composta: usuário + empresa)
    private int idUsuario;
    private int idEmpresa;
    private int posicaoRanking;
    private String dataRanking;
    private int pontuacaoRanking;

    //Construtor padrão
    public Ranking() {
    }

    //Construtor com todos os parâmetros
    public Ranking(int idUsuario, int idEmpresa, int posicaoRanking,
                   String dataRanking, int pontuacaoRanking) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.posicaoRanking = posicaoRanking;
        this.dataRanking = dataRanking;
        this.pontuacaoRanking = pontuacaoRanking;
    }

    //Métodos Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public int getPosicaoRanking() {
        return posicaoRanking;
    }

    public String getDataRanking() {
        return dataRanking;
    }

    public int getPontuacaoRanking() {
        return pontuacaoRanking;
    }

    //Métodos Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setPosicaoRanking(int posicaoRanking) {
        this.posicaoRanking = posicaoRanking;
    }

    public void setDataRanking(String dataRanking) {
        this.dataRanking = dataRanking;
    }

    public void setPontuacaoRanking(int pontuacaoRanking) {
        this.pontuacaoRanking = pontuacaoRanking;
    }

    //Metodo de negócio — a posição no ranking começa em 1
    public void atualizarPosicao(int novaPosicao) {
        if (novaPosicao < 1) {
            throw new IllegalArgumentException(
                    "Posição no ranking deve ser maior ou igual a 1. Recebido: " + novaPosicao);
        }
        this.posicaoRanking = novaPosicao;
    }

    @Override
    public String toString() {
        return "Dados do Ranking{" +
                "Id do Usuário= " + getIdUsuario() +
                ", Id da Empresa= " + getIdEmpresa() +
                ", Posição no Ranking= " + getPosicaoRanking() +
                ", Data de Atualização= " + getDataRanking() +
                ", Pontuação= " + getPontuacaoRanking() +
                "}";
    }
}