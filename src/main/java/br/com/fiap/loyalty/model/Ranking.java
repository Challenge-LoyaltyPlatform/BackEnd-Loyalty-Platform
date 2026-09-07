package br.com.fiap.loyalty.model;

import java.time.LocalDate;

public class Ranking {

    //Definindo atributos do Ranking (chave composta: usuário + empresa)
    private int idUsuario;
    private int idEmpresa;
    private int posicaoRanking;
    private int pontuacaoRanking;
    private LocalDate dataRanking;

    //Construtor padrão
    public Ranking() {
    }

    //Construtor com todos os parâmetros
    public Ranking(int idUsuario, int idEmpresa, int posicaoRanking,
                   int pontuacaoRanking, LocalDate dataRanking) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.posicaoRanking = posicaoRanking;
        this.pontuacaoRanking = pontuacaoRanking;
        this.dataRanking = dataRanking;
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

    public int getPontuacaoRanking() {
        return pontuacaoRanking;
    }

    public LocalDate getDataRanking() {
        return dataRanking;
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

    public void setPontuacaoRanking(int pontuacaoRanking) {
        this.pontuacaoRanking = pontuacaoRanking;
    }

    public void setDataRanking(LocalDate dataRanking) {
        this.dataRanking = dataRanking;
    }

    //Metodo de negócio — a posição no ranking começa em 1
    public void atualizarPosicao(int novaPosicao, LocalDate dataAtualizacao) {
        if (novaPosicao < 1) {
            throw new IllegalArgumentException(
                    "Posição no ranking deve ser maior ou igual a 1. Recebido: " + novaPosicao);
        }
        this.posicaoRanking = novaPosicao;
        this.dataRanking = dataAtualizacao;
    }

    @Override
    public String toString() {
        return "Dados do Ranking{" +
                "Id do Usuário= " + getIdUsuario() +
                ", Id da Empresa= " + getIdEmpresa() +
                ", Posição= " + getPosicaoRanking() +
                ", Pontuação= " + getPontuacaoRanking() +
                ", Data de Atualização= " + getDataRanking() +
                "}";
    }
}
