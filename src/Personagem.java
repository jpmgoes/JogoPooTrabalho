import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public abstract class Personagem {
    private String nome;
    private int[] atributos = {0, 0, 0, 0, 0, 0 }; // vida, escudo, ataque , defesa, mana, powerUP
    private int[] atributosClone = {0, 0, 0, 0, 0, 0};
    private double power;
    private boolean vez = false;
    private boolean defender = false;
    private ArrayList<Pocoes> inventario = new ArrayList<>();
    private ArrayList<Personagem> competidores = new ArrayList<>();

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
            atk(p, dano);
        }
        else System.out.println("Seu ataque não foi efetivo");
        encerrarVez();
    }

    void atk(Personagem p, int dano){
        if(p.getAtributos()[1] > 0) p.getAtributos()[1] -= dano;
        else p.getAtributos()[0] -= dano;
        if (p.getAtributos()[1] < 0) {
            p.getAtributos()[0] += p.getAtributos()[1];
            p.getAtributos()[1] = 0;
            int random = new Random().nextInt(10);
            Pocoes[] arrP = {new Shield(), new Heal(), new Strength(), new Mana()};
            if (random < 3) inventario.add(arrP[random]);
        }
        if(p.getAtributos()[0] < 0) p.getAtributos()[0] = 0;
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

    void editarNome(String nome){
        ArrayList<String> arrNomes = new ArrayList<>();
        for (Personagem p:getCompetidores())
            arrNomes.add(p.getNome());
        if(arrNomes.contains(nome)){
            System.out.println("Esse nome já foi usado!");
            return;
        };
        setNome(nome);
    }

    void attCompetidores(ArrayList<Personagem> competidores){
        this.competidores = competidores;
    }

    void powerUP(){
        cloneArray();
        this.atributos[2] *= getPower();
        System.out.println("PowerUP!!");
        System.out.println(this.atributos[2]+" ----------");
        encerrarVez();
    }

    void cloneArray(){
        for (int i = 0; i < this.atributos.length;i++)
            this.atributosClone[i] = getAtributos()[i];
        if(this instanceof Guerreiro) this.atributosClone[2]= 16;
        else if (this instanceof Arqueiro) this.atributosClone[2] = 32;
        else this.atributosClone[2] = 26;
    }

    void atacar(Personagem p, int rodada) {
        if(p.isDefender()) {
            System.out.println(p.getNome()+" defendeu o ataque!!");
            return;
        }
        int ataque = getAtributos()[2];
        if(rodada%3 == 0) {
            float critico = new Random().nextFloat();
            ataque += critico*10;
            System.out.println("Critical!!!");
            System.out.println(ataque);
        }

        int dano = ataque - autoDefender(ataque);
        atk(p, dano);

        System.out.println(getNome()+" deu "+dano+" de dano no "+p.getNome());
        cloneArray();

        encerrarVez();
    }

    void defender(){
        this.defender = true;
        encerrarVez();
    }

    int autoDefender(int ataque){
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
        System.out.println(getNome()+ " tem:");
        int i = 0;
        for (String s:arrNomes){
            if (arrQnt[i++] == 0) continue;
            System.out.println(s+": "+arrQnt[--i]);
            i++;
        }
    }

    ArrayList<Pocoes> removeItem(ArrayList<Pocoes> p, int index){
        ArrayList<Pocoes> arr = new ArrayList<>();
        for (int i = 0; i < p.size(); i++) {
            if (index == i) continue;
            arr.add(p.get(i));
        }
        return arr;
    }

    void ativarItem(String s){
        for (Pocoes p: this.inventario){
            if(s == p.getName()){
                p.active(this);
                int index = this.inventario.indexOf(p);
                this.inventario = removeItem(this.inventario, index);
                break;
            };
        }
        encerrarVez();
    }

    void encerrarVez(){
        this.vez = false;
    }

    // get
    public String getNome() {
        return nome;
    }
    public ArrayList<Personagem> getCompetidores() {
        return competidores;
    }
    public int[] getAtributos() {
        return atributos;
    }
    public int[] getAtributosClone() {
        return atributosClone;
    }
    public boolean isVez() {
        return vez;
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
}