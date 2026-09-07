package br.com.fiap.loyalty.model;

public class Nivel {

    //Definindo atributos de Nivel
    private int idNivel;
    private String nomeNivel;
    private int pontosMinNivel;
    private int pontosMaxNivel;

    //Construtor vazio
    public Nivel() {
    }

    //Construtor com todos os parâmetros
    public Nivel(int idNivel, String nomeNivel, int pontosMinNivel, int pontosMaxNivel) {
        this.idNivel = idNivel;
        this.nomeNivel = nomeNivel;
        this.pontosMinNivel = pontosMinNivel;
        this.pontosMaxNivel = pontosMaxNivel;
    }

    //Métodos Getters
    public int getIdNivel() {
        return idNivel;
    }

    public String getNomeNivel() {
        return nomeNivel;
    }

    public int getPontosMinNivel() {
        return pontosMinNivel;
    }

    public int getPontosMaxNivel() {
        return pontosMaxNivel;
    }

    //Métodos Setters
    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }

    public void setNomeNivel(String nomeNivel) {
        this.nomeNivel = nomeNivel;
    }

    public void setPontosMinNivel(int pontosMinNivel) {
        this.pontosMinNivel = pontosMinNivel;
    }

    public void setPontosMaxNivel(int pontosMaxNivel) {
        this.pontosMaxNivel = pontosMaxNivel;
    }

    //Metodo de negócio — o nível sabe qual faixa de pontuação ele cobre
    public boolean contemPontuacao(int pontuacao) {
        return pontuacao >= this.pontosMinNivel && pontuacao <= this.pontosMaxNivel;
    }

    @Override
    public String toString() {
        return "Dados do Nível{" +
                "Id do Nível= " + getIdNivel() +
                ", Nome= " + getNomeNivel() +
                ", Pontos Mínimos= " + getPontosMinNivel() +
                ", Pontos Máximos= " + getPontosMaxNivel() +
                "}";
    }
}
