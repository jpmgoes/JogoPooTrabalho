public class Feiticeiro extends Personagem{
    private int[] arr = {36, 26, 26, 10, 50, 50 }; // vida, escudo, ataque , defesa, mana, powerUP
    private double power = 1.5;

    public Feiticeiro(String nome) {
        super(nome);
        setAtributos(this.arr);
        setPower(this.power);
        specialSkill = "Aumentar Mana";
        atributosRecover = new int[]{36, 26, 26, 10, 50, 50 };
    }

    @Override
    void ativarSkill() {
        getAtributos()[4] += 20;
    }
}
