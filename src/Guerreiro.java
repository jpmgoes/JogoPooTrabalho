public class Guerreiro extends Personagem{
    private int[] arr = {56, 32, 30, 6, 0, 20 }; // vida, escudo, ataque , defesa, mana, powerUP
    private double power = 1.2;

    public Guerreiro(String nome) {
        super(nome);
        setAtributos(this.arr);
        setPower(this.power);
        specialSkill = "Melhorar Shield";
        atributosRecover = new int[]{56, 32, 30, 6, 0, 20};
    }

    @Override
    void ativarSkill() {
        getAtributos()[1] += 10;
    }
}
