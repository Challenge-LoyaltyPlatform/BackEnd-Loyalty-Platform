package loyaltyplatform_modelo.src;

public class Missao {

    // Definindo atributos de Missao
    private int idMissao;
    private int idCampanha;
    private String nomeMissao;
    private String descricaoMissao;
    private int metaMissao;
    private int pontosRecompensaMissao;


    // Construtor padrão
    public Missao() {

    }

    // Construtor com todos os parâmetros
    public Missao(int idMissao, int idCampanha, String nomeMissao, String descricaoMissao, int metaMissao, int pontosRecompensaMissao) {
        this.idMissao = idMissao;
        this.idCampanha = idCampanha;
        this.nomeMissao = nomeMissao;
        this.descricaoMissao = descricaoMissao;
        this.metaMissao = metaMissao;
        this.pontosRecompensaMissao = pontosRecompensaMissao;
    }

    // Métodos Getters
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

    // Métodos Setters
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

    // Método de negócio — verifica se o progresso atingiu a meta da missão
    public boolean verificarMetaAtingida(int progressoAtual) {
        if (progressoAtual >= this.metaMissao) {
            System.out.println("Missão '" + getNomeMissao() + "' concluída! Você ganhou " + getPontosRecompensaMissao() + " pontos.");
            return true;
        } else {
            System.out.println("Missão '" + getNomeMissao() + "' em andamento. Progresso: " + progressoAtual + "/" + getMetaMissao());
            return false;
        }
    }

    @Override
    public String toString() {
        return "Dados da Missão{" +
                "Id da Missão= " + getIdMissao() +
                ", Id da Campanha= " + getIdCampanha() +
                ", Nome da Missão= " + getNomeMissao() +
                ", Descrição= " + getDescricaoMissao() +
                ", Meta= " + getMetaMissao() +
                ", Pontos de Recompensa= " + getPontosRecompensaMissao() + "}";
    }
}
