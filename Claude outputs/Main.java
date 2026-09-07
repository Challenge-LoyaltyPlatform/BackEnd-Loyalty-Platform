package br.com.fiap.loyalty.test;

import br.com.fiap.loyalty.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Classe executável de teste da Loyalty Platform.
 * Instancia os objetos do domínio e simula a utilização da aplicação.
 * Os dados ficam em memória nesta etapa; a camada DAO assume a persistência depois.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Nivel[] NIVEIS = {
            new Nivel(1, "Bronze", 0, 99),
            new Nivel(2, "Prata", 100, 499),
            new Nivel(3, "Platina", 500, 1000)
    };

    private static final Empresa empresa = new Empresa(1, "contato@soulup.com.br",
            LocalDate.of(2026, 1, 15), "SoulUp", "12345678000199",
            "(11) 3333-4444", StatusEmpresa.ATIVA);

    private static final Usuario usuario = new Usuario(1, "Andrei Sousa",
            "andrei@email.com", "senha123", LocalDate.of(2026, 3, 1));

    private static final EmpresaUsuario vinculo =
            new EmpresaUsuario(1, 1, 1, 80, LocalDate.of(2026, 3, 1));

    private static final Campanha campanha = new Campanha(1, 1, "Campanha de Indicação",
            "Indique amigos e ganhe pontos", LocalDate.of(2026, 9, 1),
            LocalDate.of(2026, 12, 31), StatusCampanha.ATIVA);

    private static final Missao missao = new Missao(1, 1, "Missão de Influência",
            "Compartilhe 20 vídeos com 3 amigos", 20, 50, StatusMissao.ATIVA);

    private static final Sequencia sequencia =
            new Sequencia(1, 1, 0, StatusSequencia.ATIVA, null, null, null);

    public static void main(String[] args) {
        System.out.println("===== LOYALTY PLATFORM =====");

        int opcao;
        do {
            System.out.println("""

                    1 - Exibir dados
                    2 - Registrar acesso do dia
                    3 - Completar missão
                    4 - Consultar nível e ranking
                    5 - Encerrar campanha
                    6 - Demonstrar polimorfismo
                    0 - Sair""");

            opcao = lerInteiro("Opção: ");
            try {
                executar(opcao);
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println("Operação não permitida: " + e.getMessage());
            }
        } while (opcao != 0);

        System.out.println("Sistema encerrado.");
    }

    private static void executar(int opcao) {
        switch (opcao) {
            case 1 -> exibirDados();
            case 2 -> registrarAcesso();
            case 3 -> completarMissao();
            case 4 -> consultarNivel();
            case 5 -> encerrarCampanha();
            case 6 -> demonstrarPolimorfismo();
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void exibirDados() {
        System.out.println(empresa);
        System.out.println(usuario);
        System.out.println(vinculo);
        System.out.println(campanha);
        System.out.println(missao);
        System.out.println(sequencia);
    }

    // RN12, RN13 e RN14 — a sequência conta um dia por dia de interação
    private static void registrarAcesso() {
        LocalDate dia = lerData("Data do acesso (dd/MM/aaaa): ");
        sequencia.avaliarJanela(dia.atTime(10, 0));

        boolean contou = sequencia.registrarAcesso(dia.atTime(10, 0));
        System.out.println(contou
                ? "Acesso contado. Sequência: " + sequencia.getDiasConsecutivos() + " dia(s)."
                : "Você já acessou neste dia. Sequência mantida em "
                  + sequencia.getDiasConsecutivos() + " dia(s).");
    }

    // RN06, RN08, RN09 e RN24 — regras encadeadas para conceder a recompensa
    private static void completarMissao() {
        System.out.println(missao.getNomeMissao() + " — meta " + missao.getMetaMissao()
                + ", vale " + missao.getPontosRecompensaMissao() + " pontos.");
        int progresso = lerInteiro("Progresso atingido: ");

        if (!missao.verificarMetaAtingida(progresso)) {
            System.out.println("Missão em andamento: " + progresso + "/" + missao.getMetaMissao());
            return;
        }
        if (!campanha.concedeRecompensa() || !missao.concedeRecompensa()) {
            System.out.println("Campanha ou missão inativa: nenhum ponto concedido.");
            return;
        }

        vinculo.acumularPontos(missao.getPontosRecompensaMissao());
        System.out.println("Missão concluída! Pontuação nesta empresa: "
                + vinculo.getPontuacaoAcumulada());

        for (Nivel n : NIVEIS) {
            if (vinculo.atualizarNivel(n)) {
                System.out.println("Nível atualizado para: " + n.getNomeNivel());
                return;
            }
        }
    }

    // RN10 e RN18 — progresso na faixa do nível e elegibilidade no ranking
    private static void consultarNivel() {
        Nivel atual = nivelDoVinculo();
        System.out.println("Pontuação: " + vinculo.getPontuacaoAcumulada());
        System.out.println("Nível: " + atual.getNomeNivel()
                + " (" + atual.getPontosMinNivel() + " a " + atual.getPontosMaxNivel() + ")");
        System.out.println("Progresso na faixa: " + vinculo.calcularProgressoNoNivel(atual) + "%");
        System.out.println("Participa do ranking: "
                + (vinculo.podeParticiparDoRanking(LocalDate.now()) ? "sim" : "não"));
    }

    // RN16 e RN23 — encerrar é terminal e a pontuação permanece com os usuários
    private static void encerrarCampanha() {
        campanha.encerrar();
        System.out.println("Campanha encerrada. A pontuação conquistada permanece com os usuários.");
        System.out.println("Tentando reativar para demonstrar que o encerramento é terminal:");
        campanha.ativar();
    }

    private static void demonstrarPolimorfismo() {
        System.out.println("-- Herança: Usuario e Empresa tratados como Cadastro --");
        for (Cadastro c : new Cadastro[]{usuario, empresa}) {
            System.out.println(c.getTipo() + " -> " + c.getEmail());
        }

        System.out.println("-- Interface: três tipos diferentes como Ativavel --");
        for (Ativavel a : new Ativavel[]{campanha, missao, sequencia}) {
            try {
                a.desativar();
                System.out.println(a.getClass().getSimpleName() + " desativado(a).");
            } catch (IllegalStateException e) {
                System.out.println(a.getClass().getSimpleName() + " -> " + e.getMessage());
            }
        }
    }

    private static Nivel nivelDoVinculo() {
        for (Nivel n : NIVEIS) {
            if (n.getIdNivel() == vinculo.getIdNivel()) {
                return n;
            }
        }
        return NIVEIS[0];
    }

    // Lê sempre com nextLine e converte depois: nextInt deixa a quebra de linha no buffer
    private static int lerInteiro(String rotulo) {
        while (true) {
            System.out.print(rotulo);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    private static LocalDate lerData(String rotulo) {
        while (true) {
            System.out.print(rotulo);
            try {
                return LocalDate.parse(sc.nextLine().trim(), FMT);
            } catch (DateTimeParseException e) {
                System.out.println("Use o formato dd/MM/aaaa.");
            }
        }
    }
}
