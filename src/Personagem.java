import java.util.List;
import java.util.ArrayList;

import java.util.List;
import java.util.ArrayList;

public abstract class Personagem {
    protected String nome;
    protected int nivel;
    protected int experiencia;
    protected int pontosXP;
    protected int pontosVida;
    protected int forca;
    protected int defesa;
    protected double chanceCritico = 0.1; // 10% de chance de golpe crítico
    protected List<Habilidade> habilidades;
    protected List<Item> itens;
    protected boolean defendendo = false;
    protected int poteDeVidaUsos = 5; // Usos de pote de vida por rodada

    public Personagem(String nome) {
        this.nome = nome;
        this.nivel = 1;
        this.experiencia = 0;
        this.pontosXP = 0;
        this.pontosVida = 100;
        this.forca = 10;
        this.defesa = 10;
        this.habilidades = new ArrayList<>();
        this.itens = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public int getPontosVida() {
        return pontosVida;
    }

    public int getForca() {
        return forca;
    }

    public int getDefesa() {
        return defesa;
    }

    public int getPoteDeVidaUsos() {
        return poteDeVidaUsos;
    }

    public void incrementarVida(int valor) {
        this.pontosVida += valor;
    }

    public void incrementarForca(int valor) {
        this.forca += valor;
    }

    public void incrementarDefesa(int valor) {
        this.defesa += valor;
    }

    public void receberDano(int dano) {
        if (defendendo) {
            dano /= 2; // Reduz o dano pela metade se estiver defendendo
            defendendo = false; // A defesa é ativa apenas para um ataque
        }
        pontosVida -= dano;
        if (pontosVida < 0) {
            pontosVida = 0;
        }
    }

    public int calcularDano(Personagem alvo) {
        int danoBase = forca - alvo.defesa;
        if (danoBase < 0) danoBase = 0;

        // Aplicando variação de dano (±10%)
        double variacao = 0.1;
        danoBase = (int) (danoBase * (1 + (Math.random() * (2 * variacao) - variacao)));

        // Verificando golpe crítico
        if (Math.random() < chanceCritico) {
            System.out.println("Golpe crítico!");
            danoBase *= 1.5;
        }

        return danoBase;
    }

    public int calcularDanoHabilidade(Habilidade habilidade, Personagem alvo) {
        int danoBase = habilidade.getDanoBase() - alvo.defesa;
        if (danoBase < 0) danoBase = 0;

        // Aplicando vulnerabilidades
        if (alvo.temVulnerabilidade(habilidade.getTipo())) {
            danoBase *= 1.5;
            System.out.println("\nÉ super eficaz!");
        } else if (alvo.temResistencia(habilidade.getTipo())) {
            danoBase *= 0.5;
            System.out.println("\nNão é muito eficaz...");
        }

        // Aplicando variação de dano (±10%)
        double variacao = 0.1;
        danoBase = (int) (danoBase * (1 + (Math.random() * (2 * variacao) - variacao)));

        // Verificando golpe crítico
        if (Math.random() < chanceCritico) {
            System.out.println("\nGolpe crítico!");
            danoBase *= 1.5;
        }

        return danoBase;
    }

    public boolean temVulnerabilidade(String tipo) {
        return false;
    }

    public boolean temResistencia(String tipo) {
        return false;
    }

    public void ganharXP(int xp) {
        experiencia += xp;
        while (experiencia >= 100) {
            experiencia -= 100;
            nivel++;
            pontosXP++;
            System.out.println(nome + " subiu para o nível " + nivel + "!");
        }
    }

    public abstract void atacar(Personagem alvo);

    public void usarHabilidade(Habilidade habilidade, Personagem alvo) {
        int dano = calcularDanoHabilidade(habilidade, alvo);
        System.out.println(nome + " usa " + habilidade.getNome() + " em " + alvo.getNome() + " causando " + dano + " de dano.\n");
        alvo.receberDano(dano);
    }

    public void defender() {
        System.out.println(nome + " está se defendendo.");
        defendendo = true;
    }

    public void adicionarHabilidade(Habilidade habilidade) {
        habilidades.add(habilidade);
    }

    public List<Habilidade> getHabilidades() {
        return habilidades;
    }

    public int getPontosXP() {
        return pontosXP;
    }

    public void usarPontoXP(String atributo) {
        if (pontosXP > 0) {
            switch (atributo) {
                case "vida":
                    pontosVida += 10;
                    break;
                case "forca":
                    forca += 1;
                    break;
                case "defesa":
                    defesa += 1;
                    break;
            }
            pontosXP--;
        }
    }

    public void adicionarItem(Item item) {
        itens.add(item);
        item.aplicarEfeito(this);
    }

    public void usarPoteDeVida() {
        if (poteDeVidaUsos > 0) {
            pontosVida += 25;
            if (pontosVida > 100) {
                pontosVida = 100;
            }
            poteDeVidaUsos--;
        } else {
            System.out.println("Pote de vida esgotado para esta rodada!");
        }
    }

    public void resetarPoteDeVidaUsos() {
        poteDeVidaUsos = 5;
    }

    public List<Item> getItens() {
        return itens;
    }
}

class Guerreiro extends Personagem {
    public Guerreiro(String nome) {
        super(nome);
        adicionarHabilidade(new Habilidade("Golpe Flamejante", "Fogo", 30));
        adicionarHabilidade(new Habilidade("Corte Gélido", "Gelo", 25));
        adicionarHabilidade(new Habilidade("Raio Divino", "Elétrico", 20));
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo);
        System.out.println(nome + " ataca " + alvo.getNome() + " causando " + dano + " de dano.");
        alvo.receberDano(dano);
    }

    @Override
    public boolean temResistencia(String tipo) {
        return tipo.equals("Elétrico");
    }

    @Override
    public boolean temVulnerabilidade(String tipo) {
        return tipo.equals("Gelo");
    }
}

class Mago extends Personagem {
    public Mago(String nome) {
        super(nome);
        adicionarHabilidade(new Habilidade("Bola de Fogo", "Fogo", 35));
        adicionarHabilidade(new Habilidade("Nevasca", "Gelo", 30));
        adicionarHabilidade(new Habilidade("Trovoada", "Elétrico", 25));
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo);
        System.out.println(nome + " lança um feitiço em " + alvo.getNome() + " causando " + dano + " de dano.");
        alvo.receberDano(dano);
    }

    @Override
    public boolean temResistencia(String tipo) {
        return tipo.equals("Fogo");
    }

    @Override
    public boolean temVulnerabilidade(String tipo) {
        return tipo.equals("Elétrico");
    }
}

class Arqueiro extends Personagem {
    public Arqueiro(String nome) {
        super(nome);
        adicionarHabilidade(new Habilidade("Flecha Flamejante", "Fogo", 30));
        adicionarHabilidade(new Habilidade("Flecha Gélida", "Gelo", 25));
        adicionarHabilidade(new Habilidade("Flecha Elétrica", "Elétrico", 20));
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo);
        System.out.println(nome + " atira uma flecha em " + alvo.getNome() + " causando " + dano + " de dano.");
        alvo.receberDano(dano);
    }

    @Override
    public boolean temResistencia(String tipo) {
        return tipo.equals("Gelo");
    }

    @Override
    public boolean temVulnerabilidade(String tipo) {
        return tipo.equals("Fogo");
    }
}
