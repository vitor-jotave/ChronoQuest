import java.util.List;
import java.util.Scanner;

public class Batalha {
    private List<Personagem> personagens;
    private List<Inimigo> inimigos;
    private Scanner scanner;
    private int rodada;
    private int numeroChefesDerrotados;

    public Batalha(List<Personagem> personagens, List<Inimigo> inimigos) {
        this.personagens = personagens;
        this.inimigos = inimigos;
        this.scanner = new Scanner(System.in);
        this.rodada = 0;
        this.numeroChefesDerrotados = 0;
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printColored(String text, String colorCode) {
        System.out.println(colorCode + text + "\033[0m");
    }

    public void iniciar() {
        while (true) {
            if (inimigos.isEmpty() || personagens.isEmpty()) {
                iniciarNovaRodada();
            }
            limparTela();
            exibirStatus();
            for (Personagem p : personagens) {
                if (inimigos.isEmpty()) {
                    break;
                }
                realizarTurno(p);
            }

            for (Inimigo i : inimigos) {
                if (personagens.isEmpty()) {
                    break;
                }
                Personagem alvo = escolherAlvoPersonagem();
                i.atacar(alvo);
                if (alvo.getPontosVida() <= 0) {
                    printColored(alvo.getNome() + " foi derrotado.", "\033[31m");
                    personagens.remove(alvo);
                }
            }

            if (personagens.isEmpty()) {
                printColored("=================================", "\033[31m");
                printColored("Os inimigos venceram a batalha!", "\033[31m");
                printColored("=================================", "\033[31m");
                break;
            } else if (inimigos.isEmpty()) {
                printColored("=================================", "\033[32m");
                printColored("Os personagens venceram a batalha!", "\033[32m");
                printColored("=================================", "\033[32m");
                distribuirXP();
                distribuirPontosAtributos();
                if (rodada % 3 == 0) {
                    numeroChefesDerrotados++;
                    mostrarBau();
                }
            }
        }
    }

    private void iniciarNovaRodada() {
        rodada++;
        for (Personagem personagem : personagens) {
            personagem.resetarPoteDeVidaUsos();
        }
        for (Inimigo inimigo : inimigos) {
            inimigo.incrementarAtributos();
        }
        if (rodada % 3 == 0) {
            // Gerar chefe a cada 3 rodadas
            inimigos.clear();
            inimigos.add(GeradorDeInimigos.gerarChefe(personagens));
        } else {
            // Gerar monstros
            inimigos.clear();
            for (int i = 0; i < 3; i++) {
                inimigos.add(GeradorDeInimigos.gerarMonstro());
            }
        }
    }

    private void realizarTurno(Personagem personagem) {
        printColored("=================================", "\033[34m");
        printColored("Turno de " + personagem.getNome() + " (PV: " + personagem.getPontosVida() + ")", "\033[34m");
        System.out.println("Escolha uma ação:");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Habilidade");
        System.out.println("3. Defender");
        System.out.println("4. Curar - " + personagem.getPoteDeVidaUsos() + "/5 usos");
        System.out.println("5. Fugir (não implementado)");
        System.out.println("\n");

        int escolha = scanner.nextInt();

        switch (escolha) {
            case 1:
                Inimigo alvo = (Inimigo) escolherAlvoInimigo();
                personagem.atacar(alvo);
                if (alvo.getPontosVida() <= 0) {
                    printColored(alvo.getNome() + " foi derrotado.", "\033[31m");
                    inimigos.remove(alvo);
                    personagem.ganharXP(alvo.getRecompensaXP());
                }
                break;
            case 2:
                Habilidade habilidade = escolherHabilidade(personagem);
                if (habilidade != null) {
                    Inimigo alvoHabilidade = (Inimigo) escolherAlvoInimigo();
                    personagem.usarHabilidade(habilidade, alvoHabilidade);
                    if (alvoHabilidade.getPontosVida() <= 0) {
                        printColored(alvoHabilidade.getNome() + " foi derrotado.", "\033[31m");
                        inimigos.remove(alvoHabilidade);
                        personagem.ganharXP(alvoHabilidade.getRecompensaXP());
                    }
                }
                break;
            case 3:
                personagem.defender();
                break;
            case 4:
                personagem.usarPoteDeVida();
                break;
            case 5:
                System.out.println("Fugir não implementado.");
                break;
            default:
                System.out.println("Escolha inválida. Turno perdido.");
        }
    }

    private Personagem escolherAlvoInimigo() {
        printColored("=================================", "\033[34m");
        System.out.println("Escolha um alvo:");
        for (int i = 0; i < inimigos.size(); i++) {
            printColored((i + 1) + ". " + inimigos.get(i).getNome() + " (PV: " + inimigos.get(i).getPontosVida() + ") - FOR: " + inimigos.get(i).getForca() + ", DEF: " + inimigos.get(i).getDefesa(), "\033[31m");
        }
        int escolha = scanner.nextInt();
        return inimigos.get(escolha - 1);
    }

    private Habilidade escolherHabilidade(Personagem personagem) {
        printColored("=================================", "\033[34m");
        System.out.println("Escolha uma habilidade:");
        List<Habilidade> habilidades = personagem.getHabilidades();
        for (int i = 0; i < habilidades.size(); i++) {
            System.out.println((i + 1) + ". " + habilidades.get(i).getNome() + " (" + habilidades.get(i).getTipo() + ")");
        }
        int escolha = scanner.nextInt();
        if (escolha > 0 && escolha <= habilidades.size()) {
            return habilidades.get(escolha - 1);
        } else {
            System.out.println("Escolha inválida.");
            return null;
        }
    }

    private Personagem escolherAlvoPersonagem() {
        for (Personagem p : personagens) {
            if (p.getPontosVida() > 0) {
                return p;
            }
        }
        return null; // Caso todos os personagens estejam derrotados
    }

    private void exibirStatus() {
        printColored("=================================", "\033[34m");
        printColored("Status dos Personagens:", "\033[34m");
        for (Personagem p : personagens) {
            printColored(p.getNome() + " (PV: " + p.getPontosVida() + ") - FOR: " + p.getForca() + ", DEF: " + p.getDefesa(), "\033[33m");
        }
        printColored("=================================", "\033[34m");
        printColored("Status dos Inimigos:", "\033[34m");
        for (Inimigo i : inimigos) {
            printColored(i.getNome() + " (PV: " + i.getPontosVida() + ") - FOR: " + i.getForca() + ", DEF: " + i.getDefesa(), "\033[31m");
        }
        printColored("=================================", "\033[34m");
        System.out.println("\n");
    }

    private void distribuirXP() {
        for (Personagem p : personagens) {
            System.out.println("\n");
            if (p.getPontosVida() > 0) {
                System.out.println(p.getNome() + " ganhou " + p.getPontosXP() + " pontos de XP.");
            }
            System.out.println("\n");
        }
    }

    private void distribuirPontosAtributos() {
        for (Personagem p : personagens) {
            while (p.getPontosXP() > 0) {
                System.out.println(p.getNome() + " tem " + p.getPontosXP() + " pontos de XP para distribuir.\n");
                System.out.println("Escolha um atributo para melhorar:");
                System.out.println("1. Vida");
                System.out.println("2. Força");
                System.out.println("3. Defesa");

                int escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        p.usarPontoXP("vida");
                        break;
                    case 2:
                        p.usarPontoXP("forca");
                        break;
                    case 3:
                        p.usarPontoXP("defesa");
                        break;
                    default:
                        System.out.println("Escolha inválida.");
                }
            }
        }
    }

    private void mostrarBau() {
        Item[] itens = GeradorDeItens.gerarItensDoBau(numeroChefesDerrotados);
        System.out.println("\n");
        System.out.println("Você encontrou um baú com os seguintes itens:");
        for (int i = 0; i < itens.length; i++) {
            String cor;
            switch (itens[i].getRaridade()) {
                case "Normal":
                    cor = "\033[37m"; // Branco
                    break;
                case "Mágico":
                    cor = "\033[34m"; // Azul
                    break;
                case "Raro":
                    cor = "\033[33m"; // Amarelo
                    break;
                case "Único":
                    cor = "\033[35m"; // Laranja
                    break;
                default:
                    cor = "\033[37m"; // Branco
            }
            printColored((i + 1) + ". " + itens[i].getTipo() + " - " + itens[i].getRaridade() + ": " + itens[i].getAtributos(), cor);
        }

        System.out.println("\nEscolha um item para pegar:");
        int escolhaItem = scanner.nextInt();
        Item itemEscolhido = itens[escolhaItem - 1];

        System.out.println("\nEscolha um personagem para atribuir o item:");
        for (int i = 0; i < personagens.size(); i++) {
            System.out.println((i + 1) + ". " + personagens.get(i).getNome());
        }
        int escolhaPersonagem = scanner.nextInt();
        Personagem personagemEscolhido = personagens.get(escolhaPersonagem - 1);

        personagemEscolhido.adicionarItem(itemEscolhido);
        System.out.println("Item " + itemEscolhido.getNome() + " atribuído a " + personagemEscolhido.getNome());
    }
}
