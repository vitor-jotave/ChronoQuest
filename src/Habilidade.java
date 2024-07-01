public class Habilidade {
    private String nome;
    private String tipo; // Fogo, Gelo, Elétrico, etc.
    private int danoBase;

    public Habilidade(String nome, String tipo, int danoBase) {
        this.nome = nome;
        this.tipo = tipo;
        this.danoBase = danoBase;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public int getDanoBase() {
        return danoBase;
    }
}
