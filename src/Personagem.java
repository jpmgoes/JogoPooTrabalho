import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class Personagem {
    private String nome;
    private int[] atributos = {0, 0, 0, 0, 0, 0 }; // vida, escudo, ataque , defesa, mana, powerUP
    private int[] atributosClone = {0, 0, 0, 0, 0, 0}; // vida, escudo, ataque , defesa, mana, powerUP
    protected int[] atributosRecover;
    private double power;
    protected String specialSkill;
    private boolean defender = false;
    private ArrayList<Pocoes> inventario = new ArrayList<>();
    private ArrayList<String> competidores = new ArrayList<>();

    public Personagem(String nome) {
        this.nome = nome;
    }

    void specialAtk(Personagem p){
        // para usar tem q ter 50 de mana
        if (new Random().nextInt(2) == 1){
            if(this instanceof Guerreiro) {
            System.out.println("Ataque especial da Classe do Guerreiro");
            System.out.println("DUAL-BLADES - ELUCIDATOR");
            } else if (this instanceof Arqueiro) {
                System.out.println("Ataque especial da Classe do Arqueiro");
                System.out.println("TURBILHAO DE FLECHAS CELESTIAIS");
            } else {
                System.out.println("Ataque especial da Classe do Feiticeiro");
                System.out.println("BAKURETSU MAHOU - EXPLOOOOSIOON");
            }
            int dano =  2 * getAtributos()[2];
            System.out.println(getNome()+" deu "+dano+" de dano no "+p.getNome());
            atk(p, dano);
        }
        else System.out.println("Seu ataque não foi efetivo");
    }

    private void atk(Personagem p, int dano){
        if(p.getAtributos()[1] > 0) p.getAtributos()[1] -= dano;
        else p.getAtributos()[0] -= dano;
        if (p.getAtributos()[1] < 0) {
            p.getAtributos()[0] += p.getAtributos()[1];
            p.getAtributos()[1] = 0;
            int random = new Random().nextInt(10);
            Pocoes[] arrP = {new Shield(), new Heal(), new Strength(), new Mana()};
            if (random <= 3) {
                System.out.println(arrP[random].getName()+" Adquirido!");
                inventario.add(arrP[random]);
            }
        }
        if(p.getAtributos()[0] <= 0) {
            System.out.println("≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥");
            System.out.println(p.getNome()+" Morreu!");
            System.out.println("≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥≤≥");
            p.getAtributos()[0] = 0;
            int random = new Random().nextInt(10);
            Pocoes[] arrP = {new Shield(), new Heal(), new Strength(), new Mana()};
            if (random <= 3) {
                System.out.println(arrP[random].getName()+" Adquirido!");
                inventario.add(arrP[random]);
            }
        }
        cloneArray();
        this.atributos = this.atributosClone;
    }

    String info() {

        String classe;
        if(this instanceof Guerreiro) classe = "Guerreiro";
        else if (this instanceof Arqueiro) classe = "Arqueiro";
        else classe = "Feiticeiro";

        return ("Classe: "+classe+"\n"+
                "Nome: "+getNome()+"\n"+
                "Vida: "+getAtributos()[0]+"\n"+
                "Escudo: "+getAtributos()[1]+"\n"+
                "Ataque: "+getAtributos()[2]+"\n"+
                "Defesa: "+getAtributos()[3]+"\n"+
                "Mana: "+getAtributos()[4]+"\n"+
                "PowerUP: "+getAtributos()[5]+"%\n");
    }

    void powerUP(){
        this.atributos[2] *= getPower();
        System.out.println("PowerUP!!");
        System.out.println("Aumento de "+getAtributos()[5]+"% do ataque");
        System.out.println("Atualmente com "+getAtributos()[2]+" de ataque");
    }

    private void cloneArray(){
        for (int i = 0; i < this.atributos.length;i++)
            this.atributosClone[i] = getAtributos()[i];

        if(this instanceof Guerreiro) this.atributosClone[2]= 30; // ataque
        else if (this instanceof Arqueiro) this.atributosClone[2] = 32;
        else this.atributosClone[2] = 26;
    }

    void atacar(Personagem p, int rodada) {
        if(p.isDefender()) {
            System.out.println(p.getNome()+" defendeu o ataque!!");
            p.setDefender(false);
            return;
        }
        int ataque = getAtributos()[2];
        if(rodada%3 == 0) {
            float critico = new Random().nextFloat();
            ataque += critico*10+1;
            System.out.println("Critical!!!");
        }

        int dano = ataque - autoDefender(ataque);
        atk(p, dano);

        System.out.println(getNome()+" deu "+dano+" de dano no "+p.getNome());

    }

    void defender(){
        this.defender = true;
    }

    private int autoDefender(int ataque){
        return (getAtributos()[3]*ataque)/20;
    }

    void mostrarInventario(){
        int[] arrQnt = {0, 0, 0, 0};
        System.out.println("Inventario");
        ArrayList<String> arrNomes = new ArrayList<>(Arrays.asList("Heal", "Shield", "Strength", "Mana"));
        for (Pocoes p:getInventario()){
            if (p.getName() == "Heal") arrQnt[0]++;
            if (p.getName() == "Shield") arrQnt[1]++;
            if (p.getName() == "Strength") arrQnt[2]++;
            if (p.getName() == "Mana") arrQnt[3]++;
        }
        if(Arrays.stream(arrQnt).sum() == 0) {
            System.out.println("Você não tem item!");
            return;
        }
        System.out.println(getNome()+ " tem:");
        int i = 0;
        for (String s:arrNomes){
            if (arrQnt[i++] == 0) continue;
            System.out.println(s+": "+arrQnt[--i]);
            i++;
        }
    }

    private ArrayList<Pocoes> removeItem(ArrayList<Pocoes> p, int index){
        ArrayList<Pocoes> arr = new ArrayList<>();
        for (int i = 0; i < p.size(); i++) {
            if (index == i) continue;
            arr.add(p.get(i));
        }
        return arr;
    }

    void ativarItem(String s){
        for (Pocoes p:getInventario()){
            if(s.equals(p.getName())){
                p.active(this);
                int index = this.inventario.indexOf(p);
                this.inventario = removeItem(this.inventario, index);
                break;
            };
        }
    }

    abstract void ativarSkill();

    // get
    public String getNome() {
        return nome;
    }
    public ArrayList<String> getCompetidores() {
        return competidores;
    }
    public int[] getAtributos() {
        return atributos;
    }
    public int[] getAtributosClone() {
        return atributosClone;
    }
    public boolean isDefender() {
        return defender;
    }
    public ArrayList<Pocoes> getInventario() {
        return inventario;
    }
    public double getPower() {
        return power;
    }
    public String getSpecialSkill() {
        return specialSkill;
    }
    public int[] getAtributosRecover() {
        return atributosRecover;
    }

    // set
    public void setAtributos(int[] atributos) {
        this.atributos = atributos;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setAtributosClone(int[] atributosClone) {
        this.atributosClone = atributosClone;
    }
    public void setPower(double power) {
        this.power = power;
    }
    public void setDefender(boolean defender) {
        this.defender = defender;
    }
    public void setCompetidores(ArrayList<String> competidores) {
        this.competidores = competidores;
    }
    public void setInventario(ArrayList<Pocoes> inventario) {
        this.inventario = inventario;
    }
}
