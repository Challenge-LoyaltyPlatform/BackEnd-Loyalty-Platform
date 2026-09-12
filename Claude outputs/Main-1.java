package br.com.fiap.loyalty.test;

import br.com.fiap.loyalty.dao.*;
import br.com.fiap.loyalty.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe executável de teste da Loyalty Platform.
 *
 * Instancia os objetos do domínio, exercita as quatro operações da camada DAO
 * (Create, Read, Update e Delete) sobre as classes modelo e simula a utilização
 * da aplicação aplicando as regras de negócio do sistema.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Camada de acesso a dados
    private static final NivelDAO nivelDAO = new NivelDAO();
    private static final EmpresaDAO empresaDAO = new EmpresaDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final EmpresaUsuarioDAO empresaUsuarioDAO = new EmpresaUsuarioDAO();
    private static final CampanhaDAO campanhaDAO = new CampanhaDAO();
    private static final MissaoDAO missaoDAO = new MissaoDAO();
    private static final SequenciaDAO sequenciaDAO = new SequenciaDAO();

    // Objetos de contexto carregados do banco na inicialização
    private static final Empresa empresa = empresaDAO.buscarPorId(1);
    private static final Usuario usuario = usuarioDAO.buscarPorId(1);
    private static final EmpresaUsuario vinculo = empresaUsuarioDAO.buscarPorChave(1, 1);
    private static final Campanha campanha = campanhaDAO.buscarPorId(1);
    private static final Missao missao = missaoDAO.buscarPorId(1);

    // A sequência pode ainda não existir: usuário sem nenhum acesso registrado
    private static final Sequencia sequenciaSalva = sequenciaDAO.buscarPorChave(1, 1);
    private static final Sequencia sequencia = sequenciaSalva != null
            ? sequenciaSalva
            : new Sequencia(1, 1, 0, StatusSequencia.ATIVA, null, null, null);

    public static void main(String[] args) {
        if (empresa == null || usuario == null || vinculo == null
                || campanha == null || missao == null) {
            System.out.println("Banco sem dados iniciais.");
            System.out.println("Execute a classe TestaConexao primeiro e rode novamente.");
            return;
        }

        System.out.println("===== LOYALTY PLATFORM =====");
        System.out.println("Empresa de contexto: " + empresa.getNomeEmpresa());
        System.out.println("Usuário de contexto: " + usuario.getNomeUsuario());

        int opcao;
        do {
            System.out.println("""

                    --- CAMPANHAS E MISSÕES ---
                     1 - Criar campanha
                     2 - Listar campanhas
                     3 - Editar campanha
                     4 - Ativar campanha
                     5 - Encerrar campanha
                     6 - Excluir campanha
                     7 - Cadastrar missão
                     8 - Listar missões da campanha
                     9 - Excluir missão

                    --- USUÁRIO FINAL ---
                    10 - Registrar acesso do dia
                    11 - Completar missão
                    12 - Consultar nível

                    --- SISTEMA ---
                    13 - Demonstrar herança e polimorfismo
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
            case 1  -> criarCampanha();
            case 2  -> listarCampanhas();
            case 3  -> editarCampanha();
            case 4  -> ativarCampanha();
            case 5  -> encerrarCampanha();
            case 6  -> excluirCampanha();
            case 7  -> cadastrarMissao();
            case 8  -> listarMissoes();
            case 9  -> excluirMissao();
            case 10 -> registrarAcesso();
            case 11 -> completarMissao();
            case 12 -> consultarNivel();
            case 13 -> demonstrarPolimorfismo();
            case 0  -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida.");
        }
    }

    // =====================================================================
    // CAMPANHAS — Create, Read, Update e Delete
    // =====================================================================

    /** CREATE — a campanha nasce INATIVA para que as missões sejam cadastradas antes. */
    private static void criarCampanha() {
        String nome = lerTexto("Nome da campanha: ");
        String descricao = lerTexto("Descrição: ");
        LocalDate inicio = lerData("Data de início (dd/MM/aaaa): ");
        LocalDate fim = lerData("Data de fim (dd/MM/aaaa): ");

        if (fim.isBefore(inicio)) {
            System.out.println("A data de fim não pode ser anterior à data de início.");
            return;
        }

        campanhaDAO.inserir(new Campanha(0, empresa.getId(), nome, descricao,
                inicio, fim, StatusCampanha.INATIVA));
        System.out.println("Campanha criada com status INATIVA.");
        System.out.println("Cadastre ao menos uma missão antes de ativá-la.");
    }

    /** READ — lista todas as campanhas e quantas missões cada uma possui. */
    private static void listarCampanhas() {
        List<Campanha> campanhas = campanhaDAO.listar();
        if (campanhas.isEmpty()) {
            System.out.println("Nenhuma campanha cadastrada.");
            return;
        }
        for (Campanha c : campanhas) {
            System.out.println(c + " | missões: " + missoesDaCampanha(c.getIdCampanha()).size());
        }
    }

    /** UPDATE — altera nome e descrição de uma campanha que ainda não foi encerrada. */
    private static void editarCampanha() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        if (c.getStatusCampanha() == StatusCampanha.ENCERRADA) {
            System.out.println("Campanha encerrada não pode ser editada.");
            return;
        }
        c.setNomeCampanha(lerTexto("Novo nome: "));
        c.setDescricaoCampanha(lerTexto("Nova descrição: "));
        campanhaDAO.atualizar(c);
        System.out.println("Campanha atualizada.");
    }

    /** Regra de negócio — só é possível ativar campanha que tenha ao menos uma missão. */
    private static void ativarCampanha() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        if (missoesDaCampanha(c.getIdCampanha()).isEmpty()) {
            System.out.println("Campanha sem missões associadas.");
            System.out.println("Cadastre ao menos uma missão antes de ativar.");
            return;
        }
        c.ativar();
        campanhaDAO.atualizar(c);
        System.out.println("Campanha ativada. A partir de agora ela concede pontos e recompensas.");
    }

    /** RN16 e RN23 — encerrar é terminal e a pontuação permanece com os usuários. */
    private static void encerrarCampanha() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        c.encerrar();
        campanhaDAO.atualizar(c);
        System.out.println("Campanha encerrada. A pontuação conquistada permanece com os usuários.");
        System.out.println("Tentando reativar, para demonstrar que o encerramento é terminal:");
        c.ativar();
    }

    /** DELETE — exclusão física só enquanto a campanha nunca entrou em operação. */
    private static void excluirCampanha() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        if (c.getIdCampanha() == campanha.getIdCampanha()) {
            System.out.println("Esta é a campanha de demonstração usada pelo sistema.");
            System.out.println("Crie uma campanha nova para testar a exclusão.");
            return;
        }
        if (c.getStatusCampanha() != StatusCampanha.INATIVA) {
            System.out.println("Só é possível excluir campanha que nunca foi ativada.");
            System.out.println("Use a opção 5 para encerrar sem perder o histórico.");
            return;
        }
        if (!missoesDaCampanha(c.getIdCampanha()).isEmpty()) {
            System.out.println("Exclua primeiro as missões desta campanha.");
            return;
        }
        campanhaDAO.remover(c.getIdCampanha());
        System.out.println(campanhaDAO.buscarPorId(c.getIdCampanha()) == null
                ? "Campanha excluída do banco de dados."
                : "Atenção: a campanha ainda existe.");
    }

    // =====================================================================
    // MISSÕES — Create, Read e Delete
    // =====================================================================

    /** CREATE — as missões são cadastradas com a campanha ainda inativa. */
    private static void cadastrarMissao() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        if (c.getStatusCampanha() == StatusCampanha.ENCERRADA) {
            System.out.println("Campanha encerrada não recebe novas missões.");
            return;
        }

        String nome = lerTexto("Nome da missão: ");
        String descricao = lerTexto("Descrição: ");
        int meta = lerInteiro("Meta: ");
        int pontos = lerInteiro("Pontos de recompensa: ");

        if (meta <= 0 || pontos <= 0) {
            System.out.println("Meta e pontos de recompensa devem ser maiores que zero.");
            return;
        }

        missaoDAO.inserir(new Missao(0, c.getIdCampanha(), nome, descricao,
                meta, pontos, StatusMissao.ATIVA));
        System.out.println("Missão cadastrada na campanha " + c.getNomeCampanha() + ".");
    }

    /** READ — lista as missões de uma campanha específica. */
    private static void listarMissoes() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        List<Missao> missoes = missoesDaCampanha(c.getIdCampanha());
        if (missoes.isEmpty()) {
            System.out.println("Esta campanha não possui missões.");
            return;
        }
        for (Missao m : missoes) {
            System.out.println(m);
        }
    }

    /**
     * DELETE — exclusão física enquanto a campanha nunca operou; a partir daí a
     * exclusão é lógica, preservando o histórico de conclusões dos usuários (RN25).
     */
    private static void excluirMissao() {
        Campanha c = escolherCampanha();
        if (c == null) {
            return;
        }
        List<Missao> missoes = missoesDaCampanha(c.getIdCampanha());
        if (missoes.isEmpty()) {
            System.out.println("Esta campanha não possui missões.");
            return;
        }
        for (Missao m : missoes) {
            System.out.println("  " + m.getIdMissao() + " - " + m.getNomeMissao()
                    + " [" + m.getStatusMissao() + "]");
        }

        Missao alvo = missaoDAO.buscarPorId(lerInteiro("Id da missão: "));
        if (alvo == null || alvo.getIdCampanha() != c.getIdCampanha()) {
            System.out.println("Missão não encontrada nesta campanha.");
            return;
        }

        if (c.getStatusCampanha() == StatusCampanha.INATIVA) {
            missaoDAO.remover(alvo.getIdMissao());
            System.out.println(missaoDAO.buscarPorId(alvo.getIdMissao()) == null
                    ? "Missão excluída do banco. A campanha nunca entrou em operação."
                    : "Atenção: a missão ainda existe.");
        } else {
            alvo.excluir();
            missaoDAO.atualizar(alvo);
            System.out.println("Missão marcada como EXCLUIDA.");
            System.out.println("O histórico de conclusões dos usuários foi preservado.");
        }
    }

    // =====================================================================
    // USUÁRIO FINAL — regras de negócio do domínio
    // =====================================================================

    /** RN12, RN13 e RN14 — a sequência conta um dia por dia de interação. */
    private static void registrarAcesso() {
        LocalDate dia = lerData("Data do acesso (dd/MM/aaaa): ");
        sequencia.avaliarJanela(dia.atTime(10, 0));

        boolean contou = sequencia.registrarAcesso(dia.atTime(10, 0));
        System.out.println(contou
                ? "Acesso contado. Sequência: " + sequencia.getDiasConsecutivos() + " dia(s)."
                : "Você já acessou neste dia. Sequência mantida em "
                + sequencia.getDiasConsecutivos() + " dia(s).");

        persistirSequencia();
    }

    /** A sequência pode não existir ainda: insere na primeira vez, atualiza depois. */
    private static void persistirSequencia() {
        if (sequenciaDAO.buscarPorChave(sequencia.getIdUsuario(), sequencia.getIdEmpresa()) == null) {
            sequenciaDAO.inserir(sequencia);
        } else {
            sequenciaDAO.atualizar(sequencia);
        }
    }

    /** RN06, RN08, RN09 e RN24 — regras encadeadas para conceder a recompensa. */
    private static void completarMissao() {
        System.out.println(missao.getNomeMissao() + " — meta " + missao.getMetaMissao()
                + ", vale " + missao.getPontosRecompensaMissao() + " pontos.");
        int progresso = lerInteiro("Progresso atingido: ");

        if (!missao.verificarMetaAtingida(progresso)) {
            System.out.println("Missão em andamento: " + progresso + "/" + missao.getMetaMissao());
            return;
        }
        if (!campanha.concedeRecompensa()) {
            System.out.println("A campanha não está ativa. Nenhum ponto foi concedido.");
            return;
        }
        if (!missao.concedeRecompensa()) {
            System.out.println("A missão não está ativa. Nenhum ponto foi concedido.");
            return;
        }

        vinculo.acumularPontos(missao.getPontosRecompensaMissao());
        System.out.println("Missão concluída! Pontuação nesta empresa: "
                + vinculo.getPontuacaoAcumulada());

        for (Nivel n : nivelDAO.listar()) {
            if (vinculo.atualizarNivel(n)) {
                System.out.println("Nível atualizado para: " + n.getNomeNivel());
                break;
            }
        }
        empresaUsuarioDAO.atualizar(vinculo);
    }

    /** RN10 e RN18 — progresso na faixa do nível e elegibilidade no ranking. */
    private static void consultarNivel() {
        Nivel atual = nivelDoVinculo();
        if (atual == null) {
            System.out.println("Nenhum nível cadastrado no banco.");
            return;
        }
        System.out.println("Pontuação acumulada: " + vinculo.getPontuacaoAcumulada());
        System.out.println("Nível: " + atual.getNomeNivel()
                + " (" + atual.getPontosMinNivel() + " a " + atual.getPontosMaxNivel() + ")");
        System.out.println("Progresso na faixa: " + vinculo.calcularProgressoNoNivel(atual) + "%");
        System.out.println("Participa do ranking: "
                + (vinculo.podeParticiparDoRanking(LocalDate.now()) ? "sim" : "não"));
    }

    // =====================================================================
    // DEMONSTRAÇÃO DE ORIENTAÇÃO A OBJETOS
    // =====================================================================

    private static void demonstrarPolimorfismo() {
        System.out.println("-- Herança: Usuario e Empresa tratados como Cadastro --");
        for (Cadastro c : new Cadastro[]{usuario, empresa}) {
            System.out.println(c.getTipo() + " -> " + c.getEmail()
                    + " (cadastro em " + c.getDataCadastro() + ")");
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

    // =====================================================================
    // AUXILIARES
    // =====================================================================

    /** Lista as campanhas existentes e devolve a que o usuário escolher. */
    private static Campanha escolherCampanha() {
        List<Campanha> campanhas = campanhaDAO.listar();
        if (campanhas.isEmpty()) {
            System.out.println("Nenhuma campanha cadastrada.");
            return null;
        }
        for (Campanha c : campanhas) {
            System.out.println("  " + c.getIdCampanha() + " - " + c.getNomeCampanha()
                    + " [" + c.getStatusCampanha() + "]");
        }
        Campanha escolhida = campanhaDAO.buscarPorId(lerInteiro("Id da campanha: "));
        if (escolhida == null) {
            System.out.println("Campanha não encontrada.");
        }
        return escolhida;
    }

    /** Filtra, entre todas as missões, as que pertencem a uma campanha. */
    private static List<Missao> missoesDaCampanha(int idCampanha) {
        List<Missao> resultado = new ArrayList<>();
        for (Missao m : missaoDAO.listar()) {
            if (m.getIdCampanha() == idCampanha) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    /** Recupera do banco o nível gravado no vínculo do usuário com a empresa. */
    private static Nivel nivelDoVinculo() {
        List<Nivel> niveis = nivelDAO.listar();
        for (Nivel n : niveis) {
            if (n.getIdNivel() == vinculo.getIdNivel()) {
                return n;
            }
        }
        return niveis.isEmpty() ? null : niveis.get(0);
    }

    private static String lerTexto(String rotulo) {
        System.out.print(rotulo);
        return sc.nextLine().trim();
    }

    /** Lê sempre com nextLine e converte depois: nextInt deixa a quebra de linha no buffer. */
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
