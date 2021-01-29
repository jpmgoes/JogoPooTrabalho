import java.util.Random;

public class Strength implements Pocoes{
    @Override
    public void buff(Personagem p) {
        p.getAtributos()[2] += 8;
    }

    @Override
    public void nerf(Personagem p) {
        p.getAtributos()[2] -= 3;
    }

    @Override
    public void active(Personagem p) {
        if (new Random().nextInt(5) == 1) {
            nerf(p);
            if (p.getAtributos()[2] <= 0) {
                System.out.println(p.getNome() + " está sem força");
                System.out.println("mas com a piedade dos Deuses você irá receber 20 de força");
                p.getAtributos()[2] = 20;
                return;
            }
            System.out.println(p.getNome() + " está mais fraco!");
            return;
        }
        buff(p);
        System.out.println("Ficando Mais Forte!");
    }
    @Override
    public String getName() {
        return "Strength";
    }
}
