package br.com.fiap.loyalty.model;

public class Sequencia implements Ativavel {

    //Definindo atributos de Sequencia (chave composta: usuário + empresa)
    private int idUsuario;
    private int idEmpresa;
    private int diasConsecutivos;
    private StatusSequencia statusSequencia;
    private String dataInicioSequencia;
    private String ultimoAcesso;
    private String dataDesativacao;

    //Construtor padrão
    public Sequencia() {
    }

    //Construtor com todos os parâmetros
    public Sequencia(int idUsuario, int idEmpresa, int diasConsecutivos,
                     StatusSequencia statusSequencia, String dataInicioSequencia,
                     String ultimoAcesso, String dataDesativacao) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.diasConsecutivos = diasConsecutivos;
        this.statusSequencia = statusSequencia;
        this.dataInicioSequencia = dataInicioSequencia;
        this.ultimoAcesso = ultimoAcesso;
        this.dataDesativacao = dataDesativacao;
    }

    //Métodos Getters
    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public int getDiasConsecutivos() {
        return diasConsecutivos;
    }

    public StatusSequencia getStatusSequencia() {
        return statusSequencia;
    }

    public String getDataInicioSequencia() {
        return dataInicioSequencia;
    }

    public String getUltimoAcesso() {
        return ultimoAcesso;
    }

    public String getDataDesativacao() {
        return dataDesativacao;
    }

    //Métodos Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public void setDiasConsecutivos(int diasConsecutivos) {
        this.diasConsecutivos = diasConsecutivos;
    }

    public void setStatusSequencia(StatusSequencia statusSequencia) {
        this.statusSequencia = statusSequencia;
    }

    public void setDataInicioSequencia(String dataInicioSequencia) {
        this.dataInicioSequencia = dataInicioSequencia;
    }

    public void setUltimoAcesso(String ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public void setDataDesativacao(String dataDesativacao) {
        this.dataDesativacao = dataDesativacao;
    }

    //Métodos de negócio

    // A sequência incrementa 1 por DIA de interação, não por interação (RN12).
    // Se estava suspensa, a próxima interação dentro da janela restaura automaticamente (RN13).
    public void registrarAcesso(String dataAcesso) {
        if (this.statusSequencia == StatusSequencia.SUSPENSA) {
            this.statusSequencia = StatusSequencia.ATIVA;
            this.dataDesativacao = null;
        }
        this.diasConsecutivos++;
        this.ultimoAcesso = dataAcesso;
    }

    // Passou de 24h sem interação — ainda restaurável (RN13)
    public void suspender(String dataDesativacao) {
        this.statusSequencia = StatusSequencia.SUSPENSA;
        this.dataDesativacao = dataDesativacao;
    }

    // Passou de 48h sem interação — contagem reiniciada (RN14)
    public void zerar() {
        this.statusSequencia = StatusSequencia.ZERADA;
        this.diasConsecutivos = 0;
    }

    public boolean estaAtiva() {
        return this.statusSequencia == StatusSequencia.ATIVA;
    }

    @Override
    public void ativar() {
        this.statusSequencia = StatusSequencia.ATIVA;
        this.dataDesativacao = null;
    }

    @Override
    public void desativar() {
        this.statusSequencia = StatusSequencia.ZERADA;
        this.diasConsecutivos = 0;
    }

    @Override
    public String toString() {
        return "Dados da Sequência{" +
                "Id do Usuário= " + getIdUsuario() +
                ", Id da Empresa= " + getIdEmpresa() +
                ", Dias Consecutivos= " + getDiasConsecutivos() +
                ", Status= " + getStatusSequencia() +
                ", Data de Início= " + getDataInicioSequencia() +
                ", Último Acesso= " + getUltimoAcesso() +
                ", Data de Desativação= " + getDataDesativacao() +
                "}";
    }
}