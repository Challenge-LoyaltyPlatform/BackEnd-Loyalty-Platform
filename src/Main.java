package loyaltyplatform_modelo.src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //classe nivel
        Nivel nivel1 = new Nivel(1, "Platina", 100, 500);
        //classe usuario
        Usuario user1 = new Usuario(1 , 1,"", "bolinha@gmail.com", "", "",467 );
        Usuario user2 = new Usuario(2 , 2,"", "quadrado@gmail.com", "", "",467 );
        //classe campanha
        Campanha campanha1 = new Campanha(1,"Campanha 1", "05/05/2026", "20/05/2026", "Campanha Finalizada");
        //classe missao
        Missao missao1 = new Missao(1 , 1, "MISSAO DE INFLUÊCIA", "Mande 20 videos para 3 amigos diferentes", 20, 200);
        //classe ranking
        Ranking rankingUsers = new Ranking(1, 2, "20/05/2026", 800);
        //classe sequencia
        Sequencia streak = new Sequencia(1,23,"Sequencia Ativa","10/04/2026", "10/05/2026", "00/00/000");

        //Scanner com get e set
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== SEJA BEM VINDO À ====");
        System.out.println("***** LOYALTY PLATFORM *****");


        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();
        user1.setNomeUsuario(nome);
        System.out.println("Usuário " + user1.getNomeUsuario() + " cadastrado com Sucesso!");

        System.out.println("--------*--------");

        System.out.println("Defina a senha: ");
        String senhaDigitada = scanner.nextLine();
        user1.setSenhaUsuario(senhaDigitada);
        System.out.println("Senha cadastrada com sucesso!");

        System.out.println("--------*--------");

        System.out.println("Em qual dia voce se cadastrou na plataforma?! ");
        String dataCadastro = scanner.nextLine();
        user1.setDataCadastro((dataCadastro));
        System.out.println("Você se cadastrou em: " + user1.getDataCadastro() + " !");

        System.out.println("--------*--------");

        System.out.print("Quantos dias consecutivos o usuário está ativo na plataforma? ");
        int dias = scanner.nextInt(); scanner.nextLine();
        streak.setDiasConsecutivos(dias);
        System.out.println("Sequência atualizada para " + streak.getDiasConsecutivos() + " dias!");

        //usuario 2 agora

        System.out.println("--------*--------");

        System.out.print("Digite o nome do usuário 2: ");
        String nome2 = scanner.nextLine();
        user2.setNomeUsuario(nome2);
        System.out.println("Usuário " + user2.getNomeUsuario() + " cadastrado com Sucesso!");

        System.out.println("--------*--------");

        System.out.println("Defina a senha: ");
        String senhaDigitada2 = scanner.nextLine();
        user2.setSenhaUsuario(senhaDigitada2);
        System.out.println("Senha cadastrada com sucesso!");

        System.out.println("--------*--------");

        System.out.println("Em qual dia voce se cadastrou na plataforma?! ");
        String dataCadastro2 = scanner.nextLine();
        user2.setDataCadastro((dataCadastro2));
        System.out.println("Você se cadastrou em: " + user2.getDataCadastro() + " !");

        System.out.println("--------*--------");

        System.out.print("Quantos dias consecutivos o usuário está ativo na plataforma? ");
        int diasUser2 = scanner.nextInt(); scanner.nextLine();
        streak.setDiasConsecutivos(diasUser2);
        System.out.println("Sequência atualizada para " + streak.getDiasConsecutivos() + " dias!");

        System.out.println("\n========== DADOS DO SISTEMA ==========");

        System.out.println("\n--- Nível ---");
        System.out.println(nivel1);
        System.out.println("\n--- Usuário ---");
        System.out.println(user1);
        System.out.println("\n--- Campanha ---");
        System.out.println(campanha1);
        System.out.println("\n--- Missão ---");
        System.out.println(missao1);
        System.out.println("\n--- Ranking ---");
        System.out.println(rankingUsers);
        System.out.println("\n--- Sequência ---");
        System.out.println(streak);

        // For com Ativavel
        System.out.println("\n========== ATIVANDO ==========");
        Ativavel[] ativaveis = {campanha1, streak};
        for (Ativavel a : ativaveis) {
            a.ativar();
        }

        System.out.println("\n========== DESATIVANDO ==========");
        for (Ativavel a : ativaveis) {
            a.desativar();
        }

        // For com Cadastro
        System.out.println("\n========== CADASTROS ==========");
        Cadastro[] cadastros = {user1, user2};
        for (Cadastro c : cadastros) {
            System.out.println(c.getTipo() + ": " + c);
        }


        System.out.println("\n========== MÉTODOS DE NEGÓCIO ==========");
        user1.acumularPontos(50);
        missao1.verificarMetaAtingida(3);
        streak.registrarAcesso("23/05/2026");
        rankingUsers.atualizarPosicao(1);

    }
}
