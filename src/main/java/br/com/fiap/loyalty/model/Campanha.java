package br.com.fiap.loyalty.model;

public class Campanha implements Ativavel {

    //Definindo atributos da Campanha
    private int idCampanha;
    private int idEmpresa;
    private String nomeCampanha;
    private String dataIniCampanha;
    private String dataFimCampanha;
    private StatusCampanha statusCampanha;

    //Construtor padrão
    public Campanha() {
    }

    //Construtor com todos os parâmetros
    public Campanha(int idCampanha, int idEmpresa, String nomeCampanha,
                    String dataIniCampanha, String dataFimCampanha, StatusCampanha statusCampanha) {
        this.idCampanha = idCampanha;
        this.idEmpresa = idEmpresa;
        this.nomeCampanha = nomeCampanha;
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
    public String getDataIniCampanha() {
        return dataIniCampanha;
    }
    public String getDataFimCampanha() {
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
    public void setDataIniCampanha(String dataIniCampanha) {
        this.dataIniCampanha = dataIniCampanha;
    }
    public void setDataFimCampanha(String dataFimCampanha) {
        this.dataFimCampanha = dataFimCampanha;
    }
    public void setStatusCampanha(StatusCampanha statusCampanha) {
        this.statusCampanha = statusCampanha;
    }

    //Métodos de negócio
    @Override
    public void ativar() {
        if (this.statusCampanha == StatusCampanha.ENCERRADA) {
            throw new IllegalStateException(
                    "Campanha encerrada não pode ser reativada: " + getNomeCampanha());
        }
        this.statusCampanha = StatusCampanha.ATIVA;
    }

    @Override
    public void desativar() {
        if (this.statusCampanha == StatusCampanha.ENCERRADA) {
            throw new IllegalStateException(
                    "Campanha já encerrada: " + getNomeCampanha());
        }
        this.statusCampanha = StatusCampanha.INATIVA;
    }

    // Encerramento é terminal — a pontuação já conquistada permanece com os usuários (RN)
    public void encerrar() {
        this.statusCampanha = StatusCampanha.ENCERRADA;
    }

    // Só campanha ativa concede pontos e recompensas (RN)
    public boolean concedeRecompensa() {
        return this.statusCampanha == StatusCampanha.ATIVA;
    }

    @Override
    public String toString() {
        return "Dados da Campanha{" +
                "Id da Campanha= " + getIdCampanha() +
                ", Id da Empresa= " + getIdEmpresa() +
                ", Nome da Campanha= " + getNomeCampanha() +
                ", Data de Início= " + getDataIniCampanha() +
                ", Data de Fim= " + getDataFimCampanha() +
                ", Status= " + getStatusCampanha() +
                "}";
    }
}