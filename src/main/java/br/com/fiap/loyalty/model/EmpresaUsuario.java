package br.com.fiap.loyalty.model;

public class EmpresaUsuario {

    // Mínimo de dias de vínculo para participar do ranking (RN10)
    private static final int DIAS_MINIMOS_RANKING = 3;

    //Definindo atributos do vínculo entre usuário e empresa
    private int idUsuario;
    private int idEmpresa;
    private int idNivel;
    private int pontuacaoAcumulada;
    private String dataAssociacao;

    //Construtor padrão
    public EmpresaUsuario(){}

    //Construtor com todos os parâmetros
    public EmpresaUsuario(int idUsuario, int idEmpresa, int idNivel,
                          int pontuacaoAcumulada, String dataAssociacao) {
        this.idUsuario = idUsuario;
        this.idEmpresa = idEmpresa;
        this.idNivel = idNivel;
        this.pontuacaoAcumulada = pontuacaoAcumulada;
        this.dataAssociacao = dataAssociacao;
    }

    //Métodos Getters
    public int getIdUsuario() {
        return idUsuario;
    }
    public int getIdEmpresa() {
        return idEmpresa;
    }
    public int getIdNivel() {
        return idNivel;
    }
        public int getPontuacaoAcumulada() {
        return pontuacaoAcumulada;
    }
    public String getDataAssociacao() {
        return dataAssociacao;
    }

        //Métodos Setters
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }
    public void setPontuacaoAcumulada(int pontuacaoAcumulada) {
        this.pontuacaoAcumulada = pontuacaoAcumulada;
    }
    public void setDataAssociacao(String dataAssociacao) {
        this.dataAssociacao = dataAssociacao;
    }

    //Métodos de negócio

    // Acumula pontos no vínculo. Pontuação de missão deve ser inteiro positivo (RN06)
    public void acumularPontos(int pontos) {
        if (pontos <= 0) {
            throw new IllegalArgumentException(
                    "Pontuação deve ser um inteiro positivo. Recebido: " + pontos);
        }
        this.pontuacaoAcumulada += pontos;
    }

    // O nível é definido pela pontuação acumulada no vínculo (RN08/RN17)
    public boolean atualizarNivel(Nivel nivel) {
        if (this.pontuacaoAcumulada >= nivel.getPontosMinNivel()
                && this.pontuacaoAcumulada <= nivel.getPontosMaxNivel()) {
                this.idNivel = nivel.getIdNivel();
                return true;
        }
        return false;
    }

    // Progresso percentual dentro da faixa do nível atual (RN18)
    public int calcularProgressoNoNivel(Nivel nivel) {
        int faixa = nivel.getPontosMaxNivel() - nivel.getPontosMinNivel();
        if (faixa <= 0) {
            return 100;
        }
        int avanco = this.pontuacaoAcumulada - nivel.getPontosMinNivel();
        if (avanco <= 0) {
            return 0;
        }
        if (avanco >= faixa) {
            return 100;
        }
        return (avanco * 100) / faixa;
    }

    // Somente vínculos com mais de 3 dias participam do ranking (RN10)
    public boolean podeParticiparDoRanking(int diasDesdeAssociacao) {
        return diasDesdeAssociacao >= DIAS_MINIMOS_RANKING;
    }

    @Override
    public String toString() {
        return "Vínculo Empresa-Usuário{" +
               "Id do Usuário= " + getIdUsuario() +
               ", Id da Empresa= " + getIdEmpresa() +
               ", Id do Nível= " + getIdNivel() +
               ", Pontuação Acumulada= " + getPontuacaoAcumulada() +
               ", Data de Associação= " + getDataAssociacao() +
               "}";
        }
    }
