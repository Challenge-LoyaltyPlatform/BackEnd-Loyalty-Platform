package loyaltyplatform_modelo.src;

public class Ranking {
    private int idUsuario;
    private int posicaoRanking;
    private String dataRanking;
    private int pontuacaoRanking;

    public Ranking(){

    }
    public Ranking(int idUsuario, int posicaoRankig, String dataRanking, int pontuacaoRanking) {
        this.idUsuario = idUsuario;
        this.posicaoRanking = posicaoRankig;
        this.dataRanking = dataRanking;
        this.pontuacaoRanking = pontuacaoRanking;
    }
    // metodos getters e setters
    public int getIdUsuario() {
        return idUsuario;
    }

    public int getPontuacaoRanking() {
        return pontuacaoRanking;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getPosicaoRanking() {
        return posicaoRanking;
    }

    public void setPosicaoRankig(int posicaoRankig) {
        this.posicaoRanking = posicaoRankig;
    }

    public String getDataRanking() {
        return dataRanking;
    }

    public void setDataRanking(String dataRanking) {
        dataRanking = dataRanking;
    }

    public void setPontuacaoRanking(int pontuacaoRanking) {
        this.pontuacaoRanking = pontuacaoRanking;}

    public void atualizarPosicao(int novaPosicao) {
        if (novaPosicao >= 1 && posicaoRanking > 0){
            this.posicaoRanking = novaPosicao;
            System.out.println("SUa nova posição: " + getPosicaoRanking());
        }else {
            System.err.println("Posição inválida !! ou sem pontos ");
        }

    }

    @Override
    public String toString(){
        return "Dados do Ranking{" +
                "Id do Usuário= " + getIdUsuario() +
                ", Posição no Ranking= " + getPosicaoRanking() +
                ", Data de Atualização= " + getDataRanking() +
                ", Pontuação= " + getPontuacaoRanking() +
                "}";
    }
}
