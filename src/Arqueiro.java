public class Arqueiro extends Personagem{
    private int[] arr = {34, 22, 32, 8, 0, 30 }; // life, escudo, ataque , defesa, mana
    private double power = 1.3;

    public Arqueiro(String nome) {
        super(nome);
        setAtributos(this.arr);
        setPower(this.power);
        specialSkill = "Aumentar Vida";
    }

    @Override
    void ativarSkill() {
        if (getAtributos()[0] == 0) return;
        getAtributos()[0] += 15;
    }
}
