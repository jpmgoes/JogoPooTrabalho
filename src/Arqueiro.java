public class Arqueiro extends Personagem{
    private int[] arr = {34, 22, 32, 8, 0, 30 }; // vida, escudo, ataque , defesa, mana, powerUP
    private double power = 1.3;

    public Arqueiro(String nome) {
        super(nome);
        setAtributos(this.arr);
        setPower(this.power);
        specialSkill = "Aumentar Vida";
        atributosRecover = new int[]{34, 22, 32, 8, 0, 30 };
    }

    @Override
    void ativarSkill() {
        if (getAtributos()[0] == 0) return;
        getAtributos()[0] += 15;
    }
}
