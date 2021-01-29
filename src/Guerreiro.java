public class Guerreiro extends Personagem{
    private int[] arr = {56, 32, 30, 6, 0, 20 }; // life, escudo, ataque , defesa, mana
    private double power = 1.2;

    public Guerreiro(String nome) {
        super(nome);
        setAtributos(this.arr);
        setPower(this.power);
    }
    void melhorarShield(){
        getAtributos()[1] += 10;
    }
}
