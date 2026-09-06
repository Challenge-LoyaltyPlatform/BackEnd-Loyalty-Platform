package loyaltyplatform_modelo.src;

public class Nivel {

    //Definindo atributos de loyaltyplatform_modelo.src.Nivel
    private int idNivel;
    private String nomeNivel;
    private int pontosMinNivel;
    private int pontosMaxNivel;

    //Construtor vazio
    public Nivel(){
    }

    //Construtor com todos os parâmetros
    public Nivel(int idNivel, String nomeNivel, int pontosMinNivel, int pontosMaxNivel){
        this.idNivel = idNivel;
        this.nomeNivel = nomeNivel;
        this.pontosMinNivel = pontosMinNivel;
        this.pontosMaxNivel = pontosMaxNivel;
    }

    //Metodos Getters
    public int getIdNivel(){
        return this.idNivel;
    }
    public String getNomeNivel() {
        return this.nomeNivel;
    }
    public int getPontosMinNivel() {
        return this.pontosMinNivel;
    }
    public int getPontosMaxNivel() {
        return this.pontosMaxNivel;
    }

    //Metodo Set
    public void setIdNivel(int idNivel){
        this.idNivel = idNivel;
    }
    public void setNomeNivel(String nomeNivel){
        this.nomeNivel = nomeNivel;
    }
    public void setPontosMinNivel(int pontosMinNivel){
        this.pontosMinNivel = pontosMinNivel;
    }
    public void setPontosMaxNivel(int pontosMaxNivel){
        this.pontosMaxNivel = pontosMaxNivel;
    }

    @Override
    public String toString(){
        return "Nível{" +
                "Id Nível= " + getIdNivel() +
                ", nome do nivel= "+ getNomeNivel() +
                ", Pontos Mínimos para "+getNomeNivel() + ": "+getPontosMinNivel()+
                ", Pontos Máximos para esse nível "+getPontosMaxNivel()+
                "}";
    }

}
