public abstract class Inimigo extends Personagem {
    private String tipo;
    private int recompensaXP;

    public Inimigo(String nome, int pontosVida, int forca, int defesa, String tipo, int recompensaXP) {
        super(nome);
        this.pontosVida = pontosVida;
        this.forca = forca;
        this.defesa = defesa;
        this.tipo = tipo;
        this.recompensaXP = recompensaXP;
    }

    @Override
    public void atacar(Personagem alvo) {
        int dano = calcularDano(alvo);
        System.out.println(nome + " ataca " + alvo.getNome() + " causando " + dano + " de dano.");
        alvo.receberDano(dano);
    }

    public int getRecompensaXP() {
        return recompensaXP;
    }

    public void incrementarAtributos() {
        this.forca += 10;
        this.defesa += 10;
    }
}

class Monstro extends Inimigo {
    public Monstro(String nome) {
        super(nome, 100, 15, 15, "Monstro", (int) (50 + Math.random() * 50));
    }

    @Override
    public boolean temResistencia(String tipo) {
        return tipo.equals("Elétrico");
    }

    @Override
    public boolean temVulnerabilidade(String tipo) {
        return tipo.equals("Fogo");
    }
}

class Chefe extends Inimigo {
    public Chefe(String nome, int pontosVida, int forca, int defesa) {
        super(nome, pontosVida, forca, defesa, "Chefe", (int) (800 + Math.random() * 200));
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
