import java.util.Random;

public class GeradorDeItens {
    private static final String[] tiposItens = {"Espada", "Escudo", "Armadura", "Elmo"};
    private static final String[] nomesUnicos = {"Raio de Zeus", "Cajado de Merlin", "Espada de Arthur", "Escudo de Aegis"};

    public static Item gerarItem(boolean raroOuUnico) {
        Random rand = new Random();
        String tipo = tiposItens[rand.nextInt(tiposItens.length)];
        if (raroOuUnico) {
            boolean unico = rand.nextBoolean();
            if (unico) {
                String nome = nomesUnicos[rand.nextInt(nomesUnicos.length)];
                Habilidade habilidadeExtra = new Habilidade("Habilidade Extra", "Mágica", 50); // Exemplo
                return new ItemUnico(nome, tipo, habilidadeExtra);
            } else {
                return new ItemRaro("Item Raro", tipo);
            }
        } else {
            int raridade = rand.nextInt(3);
            switch (raridade) {
                case 0:
                    return new ItemNormal("Item Normal", tipo);
                case 1:
                    return new ItemMagico("Item Mágico", tipo);
                case 2:
                    return new ItemRaro("Item Raro", tipo);
            }
        }
        return null;
    }

    public static Item[] gerarItensDoBau(int numeroChefesDerrotados) {
        Item[] itens = new Item[4];
        for (int i = 0; i < 4; i++) {
            itens[i] = gerarItem(numeroChefesDerrotados % 2 == 0 && numeroChefesDerrotados != 0);
        }
        return itens;
    }
}
