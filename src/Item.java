import java.util.Random;

public abstract class Item {
    protected String nome;
    protected String tipo; // Espada, Escudo, Armadura, etc.
    protected String raridade; // Normal, Mágico, Raro, Único

    public Item(String nome, String tipo, String raridade) {
        this.nome = nome;
        this.tipo = tipo;
        this.raridade = raridade;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getRaridade() {
        return raridade;
    }

    public abstract void aplicarEfeito(Personagem personagem);

    public abstract String getAtributos();
}

class ItemNormal extends Item {
    private String atributo;
    private int valor;

    public ItemNormal(String nome, String tipo) {
        super(nome, tipo, "Normal");
        definirAtributo();
    }

    private void definirAtributo() {
        Random rand = new Random();
        int atributo = rand.nextInt(3);
        switch (atributo) {
            case 0:
                this.atributo = "PV";
                this.valor = 10;
                break;
            case 1:
                this.atributo = "FOR";
                this.valor = 5;
                break;
            case 2:
                this.atributo = "DEF";
                this.valor = 5;
                break;
        }
    }

    @Override
    public void aplicarEfeito(Personagem personagem) {
        switch (atributo) {
            case "PV":
                personagem.incrementarVida(valor);
                break;
            case "FOR":
                personagem.incrementarForca(valor);
                break;
            case "DEF":
                personagem.incrementarDefesa(valor);
                break;
        }
    }

    @Override
    public String getAtributos() {
        return atributo + " +" + valor;
    }
}

class ItemMagico extends Item {
    private String[] atributos;
    private int[] valores;

    public ItemMagico(String nome, String tipo) {
        super(nome, tipo, "Mágico");
        definirAtributos();
    }

    private void definirAtributos() {
        Random rand = new Random();
        atributos = new String[2];
        valores = new int[2];
        for (int i = 0; i < 2; i++) {
            int atributo = rand.nextInt(3);
            switch (atributo) {
                case 0:
                    atributos[i] = "PV";
                    valores[i] = 10;
                    break;
                case 1:
                    atributos[i] = "FOR";
                    valores[i] = 5;
                    break;
                case 2:
                    atributos[i] = "DEF";
                    valores[i] = 5;
                    break;
            }
        }
    }

    @Override
    public void aplicarEfeito(Personagem personagem) {
        for (int i = 0; i < atributos.length; i++) {
            switch (atributos[i]) {
                case "PV":
                    personagem.incrementarVida(valores[i]);
                    break;
                case "FOR":
                    personagem.incrementarForca(valores[i]);
                    break;
                case "DEF":
                    personagem.incrementarDefesa(valores[i]);
                    break;
            }
        }
    }

    @Override
    public String getAtributos() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < atributos.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(atributos[i]).append(" +").append(valores[i]);
        }
        return sb.toString();
    }
}

class ItemRaro extends Item {
    private String[] atributos;
    private int[] valores;

    public ItemRaro(String nome, String tipo) {
        super(nome, tipo, "Raro");
        definirAtributos();
    }

    private void definirAtributos() {
        Random rand = new Random();
        atributos = new String[3];
        valores = new int[3];
        for (int i = 0; i < 3; i++) {
            int atributo = rand.nextInt(3);
            switch (atributo) {
                case 0:
                    atributos[i] = "PV";
                    valores[i] = 10;
                    break;
                case 1:
                    atributos[i] = "FOR";
                    valores[i] = 5;
                    break;
                case 2:
                    atributos[i] = "DEF";
                    valores[i] = 5;
                    break;
            }
        }
    }

    @Override
    public void aplicarEfeito(Personagem personagem) {
        for (int i = 0; i < atributos.length; i++) {
            switch (atributos[i]) {
                case "PV":
                    personagem.incrementarVida(valores[i]);
                    break;
                case "FOR":
                    personagem.incrementarForca(valores[i]);
                    break;
                case "DEF":
                    personagem.incrementarDefesa(valores[i]);
                    break;
            }
        }
    }

    @Override
    public String getAtributos() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < atributos.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(atributos[i]).append(" +").append(valores[i]);
        }
        return sb.toString();
    }
}

class ItemUnico extends Item {
    private Habilidade habilidadeExtra;

    public ItemUnico(String nome, String tipo, Habilidade habilidadeExtra) {
        super(nome, tipo, "Único");
        this.habilidadeExtra = habilidadeExtra;
    }

    @Override
    public void aplicarEfeito(Personagem personagem) {
        personagem.incrementarVida(10);
        personagem.incrementarForca(5);
        personagem.incrementarDefesa(5);
        personagem.adicionarHabilidade(habilidadeExtra);
    }

    @Override
    public String getAtributos() {
        return "PV +10, FOR +5, DEF +5, Habilidade Extra: " + habilidadeExtra.getNome();
    }

    public Habilidade getHabilidadeExtra() {
        return habilidadeExtra;
    }
}
