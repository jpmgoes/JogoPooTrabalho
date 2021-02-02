import java.util.*;
import java.io.*;
public class Main {
    static void mostrandoCompetidores(ArrayList<Personagem> competidores){
        if (competidores.size() == 0) {
            System.out.println("Ainda não foi criado nenhum personagem");
            return;
        }
        for(Personagem p : competidores)
            System.out.println(p.getNome());
    }
    static ArrayList<Personagem> removerPersonagem(ArrayList<Personagem> competidores, String nome) {
        ArrayList<String> arrNome = competidoresNome(competidores);
        if (!arrNome.contains(nome)){
            System.out.println("Não existe Competidor com esse nome");
            return competidores;
        }
        int index = arrNome.indexOf(nome);
        competidores.remove(index);
        return competidores;
    }
    static ArrayList<String> competidoresNome(ArrayList<Personagem> competidores){
        ArrayList<String> arrNomes = new ArrayList<>();
        for(Personagem p : competidores)
            arrNomes.add(p.getNome());
        return arrNomes;
    }
    static ArrayList<String> pocoesNome(ArrayList<Pocoes> pocoes){
        ArrayList<String> arrNomes = new ArrayList<>();
        for(Pocoes p : pocoes)
            arrNomes.add(p.getName());
        return arrNomes;
    }
    static int deadIndex(ArrayList<Personagem> competidores){
        ArrayList<Personagem> clone = new ArrayList<>();
        for(int i = 0; i < competidores.size(); i++){
            if (competidores.get(i).getAtributos()[0] <= 0) return i;
        }
        return -1;
    }
    static ArrayList<Personagem> cloneArrPersonagem(ArrayList<Personagem> competidores){
        ArrayList<Personagem> clone = new ArrayList<>();
        for (Personagem p : competidores) clone.add(p);
        return clone;
    }
    static ArrayList<Integer> sorteio(ArrayList<Personagem> selecionados){
        ArrayList<Integer> arrSort = new ArrayList<>();
        while (arrSort.size() != selecionados.size()){
            int random = new Random().nextInt(selecionados.size());
            if(!arrSort.contains(random))
                arrSort.add(random);
        }
        return arrSort;
    }
    static void vezFazer(Personagem vezDo, ArrayList<Personagem> selecionados, int round){
        boolean bool = true;
        while (bool){
            System.out.println("O que você quer fazer?");
            System.out.println("Digite:");
            System.out.println("1 - Atacar\n" +
                    "2 - Defender\n" +
                    "3 - PowerUP\n" +
                    "4 - " + vezDo.getSpecialSkill()+"\n"+
                    "5 - Mostrar Inventário\n" +
                    "6 - Usar Item\n" +
                    "7 - Ver Status\n" +
                    "8 - Pular Vez");
            if(vezDo.getAtributos()[4] >=50) System.out.println("9 - Ataque Especial");
            try{
                int input = new Scanner(System.in).nextInt();
                if (input == 9 && vezDo.getAtributos()[4] >= 50){
                    System.out.println("Todos os Atuais Selecionados");
                    mostrandoCompetidores(selecionados);
                    System.out.println("Quem você quer atacar?");
                    String s = new Scanner(System.in).nextLine();
                    ArrayList<String> arrNomeComp = competidoresNome(selecionados);
                    if (arrNomeComp.contains(s)) {
                        int index = arrNomeComp.indexOf(s);
                        vezDo.specialAtk(selecionados.get(index));
                        vezDo.getAtributos()[4] -= 50;
                        return;
                    }else {
                        System.out.println("Não existe esse Competidor!");
                    }
                    continue;
                }
                switch (input){
                    case 1:
                        System.out.println("Todos os Atuais Selecionados");
                        mostrandoCompetidores(selecionados);
                        System.out.println("Quem você quer atacar?");
                        String s = new Scanner(System.in).nextLine();
                        ArrayList<String> arrNomeComp = competidoresNome(selecionados);
                        if (arrNomeComp.contains(s)) {
                            int index = arrNomeComp.indexOf(s);
                            vezDo.atacar(selecionados.get(index), round);
                            return;
                        }else {
                            System.out.println("Não existe esse Competidor!");
                        }
                        break;
                    case 2:
                        System.out.println("Você escolheu se defender.");
                        vezDo.defender();
                        return;
                    case 3:
                        vezDo.powerUP();
                        return;
                    case 4:
                        System.out.println("A skill da "+vezDo.getClass()+" foi ativada:");
                        System.out.println(vezDo.getSpecialSkill());
                        vezDo.ativarSkill();
                        return;
                    case 5:
                        vezDo.mostrarInventario();
                        break;
                    case 6:
                        vezDo.mostrarInventario();
                        ArrayList<String> arrPocoesName = pocoesNome(vezDo.getInventario());
                        if (arrPocoesName.size() == 0) continue;
                        System.out.println("Escreva o nome do Item que deseja usar.");
                        ArrayList<String> arrItensNomes = new ArrayList<>(Arrays.asList(
                                "Heal", "Shield", "Strength", "Mana"));
                        boolean loop = false;
                        do {
                            String item = new Scanner(System.in).nextLine();
                            if (!arrItensNomes.contains(item)) {
                                System.out.println("Digite o nome Correto!");
                                loop = true;
                            }
                            else {
                                if (arrPocoesName.contains(item)){
                                    vezDo.ativarItem(item);
                                    return;
                                } else {
                                    System.out.println("Você não tem esse item");
                                    loop = false;
                                }
                            }
                        }while (loop);
                        break;
                    case 7:
                        System.out.print(vezDo.info());
                        break;
                    case 8:
                        System.out.println("Você Pulou a vez");
                        return;
                    default:
                        break;
                }
            }catch (Exception e){
                System.out.println("Digite um Número Inteiro");
            }
        }
    }
    static ArrayList<Personagem> iniciarPartida(ArrayList<Personagem> selecionados){
        ArrayList<Personagem> arrClone = cloneArrPersonagem(selecionados);
        if (selecionados.size() == 0) return selecionados;
        System.out.println("Jogadores Selecionados:");
        ArrayList<Integer> sort = sorteio(selecionados);
        System.out.println("Resultado do sorteio:");
        for (int i = 0; i < selecionados.size(); i++)
            System.out.println((i+1)+"° "+selecionados.get(sort.get(i)).getNome());
        int round = 1;
        int c = 0;
        while (selecionados.size() > 1){
            if (c % selecionados.size() == 0) c = 0;
            System.out.println("Round: "+round);

            int index = deadIndex(selecionados);

            if (index > -1) {
                int novoIndex = arrClone.indexOf(selecionados.get(index));
                selecionados.remove(index);
                sort.remove(sort.indexOf(novoIndex));
            }
            if (c % selecionados.size() == 0) c = 0;
            if (selecionados.size() == 1) break;

            int indexDaVez = sort.indexOf(sort.get(c));
            Personagem vezDo = selecionados.get(indexDaVez);

            System.out.println("Vez do "+vezDo.getNome());
            vezFazer(vezDo, selecionados, round);

            round++;
            c++;
        }
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println("O ganhador é o Competidor: "+selecionados.get(0).getNome());
        System.out.println("++++++++++++++++++++++++++++++++++");
        System.out.println(selecionados.get(0).info());
        selecionados.get(0).setAtributos(selecionados.get(0).getAtributosRecover());
        selecionados.get(0).setInventario(new ArrayList<>());
        return selecionados;
    }
    static ArrayList<Personagem> selecionar(ArrayList<Personagem> competidores){
        ArrayList<Personagem> selecionados = new ArrayList<>();
        ArrayList<String> arrNomes = competidoresNome(competidores);
        while (true){
            System.out.println("Lista dos Competidores:");
            mostrandoCompetidores(competidores);
            ArrayList<String> arrNomesSelecionados = competidoresNome(selecionados);
            System.out.println("Selecione os Personagens");
            String nome = new Scanner(System.in).nextLine();
            if (arrNomesSelecionados.contains(nome)){
                System.out.println("Esse competidor já foi adicionado!");
            } else if(arrNomes.contains(nome)){
                int index = arrNomes.indexOf(nome);
                selecionados.add(competidores.get(index));
            }else {
                System.out.println("Esse competidor não existe!");
                System.out.println("Lista dos Competidores:");
                mostrandoCompetidores(competidores);
                continue;
            }
            if (selecionados.size() >= 2){
                System.out.println("Atuais Selecinados para Partida");
                mostrandoCompetidores(selecionados);
                System.out.println("Para finalizar digite \"f\".");
                String finish = new Scanner(System.in).nextLine().toLowerCase();
                if ("f".equals(finish)) return selecionados;
            }
            System.out.println("Para sair sem Selecionar Competidor digite \"s\" sem as aspas.");
            String cancel = new Scanner(System.in).nextLine().toLowerCase();
            if ("s".equals(cancel)){
                selecionados.clear();
                return selecionados;
            }
        }
    }
    static void attCompetidores(ArrayList<Personagem> competidores){
        ArrayList<String> arrNomes = competidoresNome(competidores);
        for (Personagem p : competidores) p.setCompetidores(arrNomes);

    }
    static ArrayList<Personagem> criarPersonagem(ArrayList<Personagem> competidores){
        while (true){
            System.out.println("Selecione a Classe");
            System.out.println("Digite:");
            System.out.println("1 - Guerreiro\n" +
                               "2 - Arqueiro\n" +
                               "3 - Feiticeiro");
            try{
                int input = new Scanner(System.in).nextInt();
                if (input < 4 && input > 0){
                    System.out.println("Os Nomes não podem ter espaços!");
                    System.out.println("Digite um nome:");
                }
                ArrayList<String> arrNomes = competidoresNome(competidores);
                switch (input){
                    case 1:
                        String nomeG = new Scanner(System.in).nextLine();
                        Guerreiro g = new Guerreiro(nomeG);
                        if (arrNomes.contains(nomeG)){
                            System.out.println("Já existe alguem com esse nome");
                            continue;
                        }
                        competidores.add(g);
                        break;
                    case 2:
                        String nomeA = new Scanner(System.in).nextLine();
                        Arqueiro a = new Arqueiro(nomeA);
                        if (arrNomes.contains(nomeA)){
                            System.out.println("Já existe alguem com esse nome");
                            continue;
                        }
                        competidores.add(a);
                        break;
                    case 3:
                        String nomeF = new Scanner(System.in).nextLine();
                        Feiticeiro f = new Feiticeiro(nomeF);
                        if (arrNomes.contains(nomeF)){
                            System.out.println("Já existe alguem com esse nome");
                            continue;
                        }
                        competidores.add(f);
                        break;
                    default:
                        System.out.println("Escolhar uma das opções do menu");
                        continue;
                }
                if (competidores.size() > 1){
                    System.out.println("Se deseja Finalizar aperte \"f\" sem aspas");
                    String finalizar = new Scanner(System.in).nextLine();
                    if ("f".equals(finalizar)) return competidores;
                }
                System.out.println("Para sair sem adicionar Competidor aperte \"s\" sem aspas");
                String sair = new Scanner(System.in).nextLine();
                if ("s".equals(sair)){
                    competidores.clear();
                    return competidores;
                }

            }catch (Exception e){
                System.out.println("Digite um Número Inteiro");
            }
        }
    }
    static void editarPersonagem(ArrayList<Personagem> competidores){
        if (competidores.size() == 0) {
            System.out.println("Ainda não foi criado nenhum personagem");
            return;
        }
        System.out.println("Todos os competidores:");
        mostrandoCompetidores(competidores);
        System.out.println("Digite o Nome daquele que você quer alterar o nome");
        String p = new Scanner(System.in).nextLine();
        ArrayList<String> nomeComp = competidoresNome(competidores);
        if (nomeComp.contains(p)){
            System.out.println("Digite o novo nome dele:");
            String nome = new Scanner(System.in).nextLine();
            if (competidores.get(0).getCompetidores().contains(nome)){
                System.out.println("Alguém já usa esse nome!");
            }else{
                int index = nomeComp.indexOf(p);
                competidores.get(index).setNome(nome);
            }
        }else System.out.println("Não existe nenhum competidor com esse nome!");
    }
    static void salvar(ArrayList<Personagem> competidores) throws IOException {
        File save = new File("save.txt");
        save.delete();
        FileWriter fw;
        try {
            fw = new FileWriter(save, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < competidores.size(); i++) {
                bw.write(competidores.get(i).getNome());
                bw.newLine();
                bw.write(competidores.get(i).getClass().toString());
                bw.newLine();
            }
            bw.close();
            fw.close();
            System.out.println("Jogo Salvo com Sucesso");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    static ArrayList<Personagem> carregarSave(ArrayList<Personagem> competidoresPadrao) throws FileNotFoundException {
        ArrayList<Personagem> competidores = new ArrayList<>();
        try{
            FileReader fr = new FileReader("save.txt");
            BufferedReader br = new BufferedReader(fr);
            String nome = "";
            String classe = "";
            while (true){
                nome = br.readLine();
                classe = br.readLine();
                if(classe == null) break;
                if (classe.equals("class Guerreiro")) competidores.add(new Guerreiro(nome));
                else if (classe.equals("class Arqueiro")) competidores.add(new Arqueiro(nome));
                else if (classe.equals("class Feiticeiro")) competidores.add(new Feiticeiro(nome));
            }
            br.close();
            fr.close();
            System.out.println("Save foi Carregado");
            return competidores;
        }catch (FileNotFoundException e){
            System.out.println("Não há save!");
        }catch (IOException e){
            System.out.println("Erro na leitura do Save");
        }
        return competidoresPadrao;
    }
    static void menu(){
        boolean bool = true;
        ArrayList<Personagem> competidores = new ArrayList<>();
        while (bool) {
            System.out.println("Digite:");
            System.out.println("1 - Iniciar Partida\n" +
                               "2 - Criar Personagens\n" +
                               "3 - Editar Personagens\n" +
                               "4 - Mostrar Competidores\n" +
                               "5 - Remover Competidor\n"+
                               "6 - Salvar Jogo\n" +
                               "7 - Carregar Jogo Salvo\n" +
                               "8 - Sair do Jogo");
            try {
                int s = new Scanner(System.in).nextInt();
                switch (s){
                    case 1:
                        if (competidores.size() <= 1){
                            System.out.println("Você não tem Competidores Suficientes");
                            System.out.println("Por favor crie Personagens");
                            bool = true;
                        } else{
                            competidores = iniciarPartida(selecionar(competidores));
                        }
                        break;
                    case 2:
                        competidores = criarPersonagem(competidores);
                        attCompetidores(competidores);
                        break;
                    case 3:
                        editarPersonagem(competidores);
                        attCompetidores(competidores);
                        break;
                    case 4:
                        mostrandoCompetidores(competidores);
                        break;
                    case 5:
                        if (competidores.size() == 0){
                            System.out.println("Ainda não foram adicionados Competidores!");
                            continue;
                        }
                        System.out.println("Lista de Todos os Competidores");
                        mostrandoCompetidores(competidores);
                        System.out.println("Digite o nome daquele que você quer remover:");
                        String nome = new Scanner(System.in).nextLine();
                        competidores = removerPersonagem(competidores, nome);
                        attCompetidores(competidores);
                        break;
                    case 6:
                        System.out.println("Salvando...");
                        salvar(competidores);
                        break;
                    case 7:
                        System.out.println("Loading...");
                        competidores = carregarSave(competidores);
                        attCompetidores(competidores);
                        break;
                    case 8:
                        System.out.println("Saindo do jogo...");
                        System.out.println("Obrigado por Jogar MiddleRoyale :)");
                        return;
                    default:
                        System.out.println("Digite uma das opções viáveis");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Digite um Número Inteiro");
                bool = true;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("OBS: Os Personagens Mortos, como morreram não podem mais participar!");
        menu();
    }
}