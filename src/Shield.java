import java.util.Random;

public class Shield implements Pocoes {

    @Override
    public void buff(Personagem p) {
        p.getAtributos()[1] += 20; // Escudo
    }

    @Override
    public void nerf(Personagem p) {
        p.getAtributos()[1] -= 7; // Escudo
    }

    @Override
    public void active(Personagem p) {
        if (new Random().nextInt(5) == 1) {
            nerf(p);
            if (p.getAtributos()[1] <= 0) {
                System.out.println(p.getNome() + "Quebrou o Escudo");
                p.getAtributos()[1] = 0;
                return;
            }
            System.out.println("O Escudo de " + p.getNome() + " foi parcialmente corrompido");
            return;
        }
        buff(p);
        System.out.println("Melhorando Escudo...");
    }
    @Override
    public String getName() {
        return "Shield";
    }
}
