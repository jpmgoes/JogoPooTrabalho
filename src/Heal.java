import java.util.Random;

public class Heal implements Pocoes{
    @Override
    public void buff(Personagem p) {
        p.getAtributos()[0] += 30;
    }

    @Override
    public void nerf(Personagem p) {
        p.getAtributos()[0] -= 15;
    }

    @Override
    public void active(Personagem p) {
        if (new Random().nextInt(5) == 1){
            nerf(p);
            System.out.println("Você foi envenenado!");
            if (p.getAtributos()[0] <= 0) {
                System.out.println("e morto pela ganancia de querer viver mais");
                System.out.println("esse foi o seu pecado!");
                p.getAtributos()[0] = 0;
            }
            return;
        }
        System.out.println("Curando...");
        buff(p);
    }

    @Override
    public String getName() {
        return "Heal";
    }

}
