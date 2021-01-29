import java.util.Random;

public class Mana implements Pocoes{
    @Override
    public void buff(Personagem p) {
        p.getAtributos()[4] += 25;
    }

    @Override
    public void nerf(Personagem p) {
        p.getAtributos()[4] -= 5;
    }

    @Override
    public void active(Personagem p) {
        if (new Random().nextInt(5) == 1) {
            nerf(p);
            if (p.getAtributos()[4] <= 0) {
                System.out.println(p.getNome() + " está sem mana");
                p.getAtributos()[4] = 0;
                return;
            }
            System.out.println(p.getNome() + " teve parte da mana drenada");
            return;
        }
        buff(p);
        System.out.println("Absorvendo Mana");
    }
    @Override
    public String getName() {
        return "Mana";
    }
}
