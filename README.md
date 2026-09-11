# Loyalty Platform — Backend

Backend em Java puro (JDBC + Oracle) do desafio **Loyalty Platform**, desenvolvido para a empresa cliente **SoulUp (Prospera)** como parte do FIAP Challenge.

A plataforma gerencia o relacionamento entre empresas e seus usuários através de um sistema de gamificação: campanhas com missões, pontuação acumulada por empresa, níveis de progressão e sequência de dias consecutivos de interação (streak).

## Stack

- **Java** (JDK 17+)
- **JDBC** puro — sem framework de persistência, acesso direto via `PreparedStatement`
- **Oracle Database** (Oracle 12c+)
- **Maven** para gerenciamento de dependências

## Estrutura do projeto

```
src/main/java/br/com/fiap/loyalty/
├── model/    → entidades de domínio (Empresa, Usuario, Campanha, Missao, Nivel,
│               EmpresaUsuario, Sequencia) + enums de status + regras de negócio
├── dao/      → camada de persistência, CRUD completo por entidade
├── db/       → ConnectionFactory (conexão com o Oracle)
└── test/     → Main (aplicação executável) e TestaConexao (teste de conexão)
```

## Funcionalidades

### Camada de domínio (regras de negócio)

- Cálculo de progresso do usuário dentro da faixa do nível atual
- Elegibilidade para ranking (mínimo de 3 dias de vínculo)
- Concessão de pontos condicionada a campanha e missão ativas
- Encerramento de campanha é terminal (não pode ser reativada depois)
- Exclusão de missão é lógica e terminal (histórico de conclusões preservado)
- Sequência de acessos com janela de tolerância: suspende após 24h sem acesso, zera após 48h

### Persistência (DAOs)

CRUD completo (inserir, buscar, listar, atualizar, remover) para as 7 entidades:

| DAO | Chave | Observação |
|---|---|---|
| `NivelDAO` | simples (`id_nivel`) | faixas de pontuação (Bronze, Prata, Platina) |
| `EmpresaDAO` | simples (`id_empresa`) | empresas clientes da plataforma |
| `UsuarioDAO` | simples (`id_usuario`) | usuários finais |
| `EmpresaUsuarioDAO` | **composta** (`id_empresa` + `id_usuario`) | vínculo usuário-empresa, guarda pontuação e nível |
| `CampanhaDAO` | simples (`id_campanha`) | campanhas de gamificação, FK para empresa |
| `MissaoDAO` | simples (`id_missao`) | missões dentro de uma campanha |
| `SequenciaDAO` | **composta** (`id_usuario` + `id_empresa`) | streak de dias consecutivos por empresa |

### Aplicação executável (`Main.java`)

Menu interativo no console com 6 operações, todas lendo e gravando no Oracle através dos DAOs:

1. **Exibir dados** — mostra o estado atual de todas as entidades carregadas
2. **Registrar acesso do dia** — conta um dia na sequência do usuário (ou mantém, se já registrado hoje)
3. **Completar missão** — valida meta atingida, campanha/missão ativas, credita pontos e atualiza nível
4. **Consultar nível e ranking** — mostra pontuação, nível atual e elegibilidade para ranking
5. **Encerrar campanha** — encerra a campanha (ação terminal) e demonstra a regra de não-reativação
6. **Demonstrar polimorfismo** — exercita herança (`Cadastro`) e interface (`Ativavel`) entre as entidades

## Como rodar

### 1. Banco de dados

Execute o script DDL (`Claude outputs/loyalty_platform_ddl.sql` ou o script mais recente do time) no Oracle SQL Developer. Ele recria as 10 tabelas do zero (contém `DROP TABLE ... CASCADE CONSTRAINTS`).

### 2. Credenciais de conexão

Em `src/main/java/br/com/fiap/loyalty/db/ConnectionFactory.java`, configure:

```java
private static final String URL  = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
private static final String USER = "SEU_RM";
private static final String PASS = "SUA_SENHA";
```

### 3. Testar a conexão

Rode `TestaConexao.java` — deve imprimir `Conexão aberta com sucesso!`.

### 4. Popular dados de teste

Insira ao menos um registro em `TB_NIVEL` (recomendado: 3 níveis — Bronze, Prata, Platina), `TB_EMPRESA`, `TB_USUARIO`, `TB_EMPRESA_USUARIO`, `TB_CAMPANHA` e `TB_MISSAO` com `id = 1`, seja via SQL direto ou chamando o `inserir()` de cada DAO. `TB_SEQUENCIA` **não precisa** ser semeada — ela é criada automaticamente no primeiro "Registrar acesso" do menu.

### 5. Rodar a aplicação

Execute `Main.java` e navegue pelo menu.

## Como testar

Passe pelas opções do menu nessa ordem e confira no SQL Developer que os dados persistem de verdade (não só na memória do programa):

| Passo | Ação | O que conferir no banco |
|---|---|---|
| 1 | Opção 1 — Exibir dados | Nenhum campo deve aparecer `null` |
| 2 | Opção 6 — Demonstrar polimorfismo | Roda sem erros, não altera dados |
| 3 | Opção 2 — Registrar acesso (data nova) | `SELECT * FROM TB_SEQUENCIA WHERE TB_USUARIO_id_usuario=1 AND TB_EMPRESA_id_empresa=1;` deve mostrar a linha criada/atualizada |
| 4 | Opção 2 novamente, mesma data | `dias_consecutivos` não deve mudar |
| 5 | Opção 3 — Completar missão (progresso ≥ meta) | `SELECT pontuacao_acumulada, TB_NIVEL_id_nivel FROM TB_EMPRESA_USUARIO ...` deve refletir a pontuação somada e o nível atualizado |
| 6 | Opção 4 — Consultar nível | Deve exibir a pontuação e o nível já atualizados do passo anterior |
| 7 | Opção 5 — Encerrar campanha | `SELECT status_campanha FROM TB_CAMPANHA WHERE id_campanha=1;` deve estar `'ENCERRADA'`; a tentativa de reativar em seguida deve lançar exceção tratada ("Operação não permitida") |
| 8 | Reiniciar o `Main` do zero | Na opção 1, todos os dados devem vir exatamente como ficaram na execução anterior — confirma que a persistência é real |

## Regras de negócio relevantes

- A pontuação e o nível são **por vínculo empresa-usuário**, não globais — o mesmo usuário pode estar em níveis diferentes em empresas diferentes
- Uma campanha encerrada não concede mais pontos, mas os pontos já conquistados permanecem com os usuários
- Uma missão excluída preserva o histórico de conclusões já registradas
- A sequência de acessos é avaliada antes de cada novo registro: mais de 24h sem acesso suspende (mas ainda é restaurável), mais de 48h zera a contagem
