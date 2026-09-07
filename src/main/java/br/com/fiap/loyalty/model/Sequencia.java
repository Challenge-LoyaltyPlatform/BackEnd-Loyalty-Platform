package br.com.fiap.loyalty.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Sequencia implements Ativavel {

    // Janelas da sequência de dias (RN13 e RN14)
    private static final int HORAS_PARA_SUSPENDER = 24;
    private static final int HORAS_PARA_ZERAR = 48;

    //Definindo atributos de Sequencia (chave composta: usuário + empresa)
    private int idUsuario;
    private int idEmpresa;
    private int diasConsecutivos;
    private StatusSequencia statusSequencia;
    private LocalDate dataInicioSequencia;
    private LocalDateTime ultimoAcesso;
    private LocalDate dataDesativacao;

    //Construtor padrão
    public Sequencia() {
    }

    //Construtor com todos os parâmetros
    public Sequencia(int idUsuario, int idEmpresa, int diasConsecutivos,
                     StatusSequencia statusSequencia, LocalDate dataInicioSequencia,
                     LocalDateTime ultimoAcesso, LocalDate dataDesativacao) {
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

    public LocalDate getDataInicioSequencia() {
        return dataInicioSequencia;
    }

    public LocalDateTime getUltimoAcesso() {
        return ultimoAcesso;
    }

    public LocalDate getDataDesativacao() {
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

    public void setDataInicioSequencia(LocalDate dataInicioSequencia) {
        this.dataInicioSequencia = dataInicioSequencia;
    }

    public void setUltimoAcesso(LocalDateTime ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public void setDataDesativacao(LocalDate dataDesativacao) {
        this.dataDesativacao = dataDesativacao;
    }

    //Métodos de negócio

    // RN12 — a sequência incrementa 1 por DIA de interação, não por interação.
    // RN13 — a próxima interação dentro da janela restaura a sequência automaticamente.
    // Devolve true se o dia foi contado, false se o usuário já havia interagido hoje.
    public boolean registrarAcesso(LocalDateTime dataHoraAcesso) {
        if (dataHoraAcesso == null) {
            throw new IllegalArgumentException("A data e hora do acesso não podem ser nulas.");
        }
        LocalDate diaDoAcesso = dataHoraAcesso.toLocalDate();

        // Já contou hoje: registra a interação mas não incrementa o contador
        if (this.ultimoAcesso != null && this.ultimoAcesso.toLocalDate().isEqual(diaDoAcesso)) {
            this.ultimoAcesso = dataHoraAcesso;
            return false;
        }

        if (this.diasConsecutivos == 0 || this.dataInicioSequencia == null) {
            this.dataInicioSequencia = diaDoAcesso;
        }

        this.statusSequencia = StatusSequencia.ATIVA;
        this.dataDesativacao = null;
        this.diasConsecutivos++;
        this.ultimoAcesso = dataHoraAcesso;
        return true;
    }

    // RN13 e RN14 — avalia quanto tempo passou desde o último acesso e aplica a janela
    public void avaliarJanela(LocalDateTime dataHoraReferencia) {
        if (this.ultimoAcesso == null || this.statusSequencia == StatusSequencia.ZERADA) {
            return;
        }
        long horas = ChronoUnit.HOURS.between(this.ultimoAcesso, dataHoraReferencia);
        if (horas >= HORAS_PARA_ZERAR) {
            this.dataDesativacao = dataHoraReferencia.toLocalDate();
            zerar();
        } else if (horas >= HORAS_PARA_SUSPENDER) {
            suspender(dataHoraReferencia.toLocalDate());
        }
    }

    // RN13 — passou de 24h sem interação, mas a sequência ainda é restaurável
    public void suspender(LocalDate dataDesativacao) {
        this.statusSequencia = StatusSequencia.SUSPENSA;
        this.dataDesativacao = dataDesativacao;
    }

    // RN14 — passou de 48h sem interação, a contagem reinicia
    public void zerar() {
        this.statusSequencia = StatusSequencia.ZERADA;
        this.diasConsecutivos = 0;
    }

    public boolean estaAtiva() {
        return this.statusSequencia == StatusSequencia.ATIVA;
    }

    @Override
    public void ativar() {
        if (this.statusSequencia == StatusSequencia.ATIVA) {
            throw new IllegalStateException("A sequência já está ativa.");
        }
        this.statusSequencia = StatusSequencia.ATIVA;
        this.dataDesativacao = null;
    }

    // Encerrar a sequência equivale a zerá-la
    @Override
    public void desativar() {
        zerar();
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
