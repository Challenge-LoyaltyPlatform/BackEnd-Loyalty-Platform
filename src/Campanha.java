package loyaltyplatform_modelo.src;

public class Campanha implements Ativavel {

    //Definindo atributos da Campanha
    private int idCampanha;
    private String nomeCampanha;
    private String dataIniCampanha;
    private String dataFimCampanha;
    private String statusCampanha;

    //Construtor padrão
    public Campanha(){

    }

    //Construtor com todos os parâmetros
    public Campanha(int idCampanha, String nomeCampanha, String dataIniCampanha, String dataFimCampanha, String statusCampanha){
        this.idCampanha = idCampanha;
        this.nomeCampanha = nomeCampanha;
        this.dataIniCampanha = dataIniCampanha;
        this.dataFimCampanha = dataFimCampanha;
        this.statusCampanha = statusCampanha;
    }

    //Métodos Getters
    public int getIdCampanha(){
        return idCampanha;
    }
    public String getNomeCampanha(){
        return nomeCampanha;
    }
    public String getDataIniCampanha(){
        return dataIniCampanha;
    }
    public String getDataFimCampanha(){
        return dataFimCampanha;
    }
    public String getStatusCampanha(){
        return statusCampanha;
    }

    //Métodos Setters
    public void setIdCampanha(int idCampanha){
        this.idCampanha = idCampanha;
    }
    public void setNomeCampanha(String nomeCampanha){
        this.nomeCampanha = nomeCampanha;
    }
    public void setDataIniCampanha(String dataIniCampanha){
        this.dataIniCampanha = dataIniCampanha;
    }
    public void setDataFimCampanha(String dataFimCampanha){
        this.dataFimCampanha = dataFimCampanha;
    }
    public void setStatusCampanha(String statusCampanha){
        this.statusCampanha = statusCampanha;
    }

    @Override
    public String toString(){
        return "Dados da Campanha{" +
                "Id da Campanha= " + getIdCampanha() +
                ", Nome da Campanha= "+getNomeCampanha() +
                ", Data de Inicio da Campanha= "+getDataIniCampanha()+
                ", Data de Fim da Campanha= "+getDataFimCampanha()+
                ", Status da Campanha= "+getStatusCampanha()+
                "}";
    }

    @Override
    public void ativar() {
        this.statusCampanha = "ativa";
        System.out.println("Campanha: " + getNomeCampanha() + " Ativada com sucesso");
    }

    @Override
    public void desativar() {
        this.statusCampanha = "inativa";
        System.out.println("Campanha: " + getNomeCampanha() + " encerrada.");
    }
}
