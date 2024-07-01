import java.util.List;
import java.util.Random;

public class GeradorDeInimigos {
    private static final String[] nomesMonstros = {"Goblin", "Orc", "Troll", "Lobisomem", "Esqueleto"};
    private static final String[] nomesChefes = {"Dragão", "Titã", "Demônio", "Lich", "Gigante"};

    public static Inimigo gerarMonstro() {
        Random rand = new Random();
        String nome = nomesMonstros[rand.nextInt(nomesMonstros.length)];
        return new Monstro(nome);
    }

    public static Inimigo gerarChefe(List<Personagem> personagens) {
        Random rand = new Random();
        String nome = nomesChefes[rand.nextInt(nomesChefes.length)];

        int nivelMedio = personagens.stream().mapToInt(p -> p.nivel).sum() / personagens.size();
        int pontosVida = 500;
        int forca = 30 + nivelMedio;
        int defesa = 15 + nivelMedio;

        return new Chefe(nome, pontosVida, forca, defesa);
    }
}
