import java.util.ArrayList;
import java.util.List;

public class Jogo {
    public static void main(String[] args) {
        Guerreiro guerreiro = new Guerreiro("Arthur");
        Mago mago = new Mago("Merlin");
        Arqueiro arqueiro = new Arqueiro("Legolas");

        List<Personagem> personagens = new ArrayList<>();
        personagens.add(guerreiro);
        personagens.add(mago);
        personagens.add(arqueiro);

        List<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(GeradorDeInimigos.gerarMonstro());
        inimigos.add(GeradorDeInimigos.gerarMonstro());
        inimigos.add(GeradorDeInimigos.gerarMonstro());

        Batalha batalha = new Batalha(personagens, inimigos);
        batalha.iniciar();
    }
}
