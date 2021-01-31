import java.util.*;

public class Main {
    static void mostrandoCompetidores(ArrayList<Personagem> competidores){
        if (competidores.size() == 0) {
            System.out.println("Ainda não foi criado nenhum personagem");
            return;
        }
        for(Personagem p : competidores)
            System.out.println(p.getNome());
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
    static ArrayList<Personagem> deadFilter(ArrayList<Personagem> competidores){
        ArrayList<Personagem> clone = new ArrayList<>();
        for ( Personagem p : competidores){
            if (p.getAtributos()[0] > 0) clone.add(p);
        }
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
        System.out.println("O que você quer fazer?");
        System.out.println("Digite:");
        System.out.println("1 - Atacar\n" +
                           "2 - Defender\n" +
                           "3 - PowerUP\n" +
                           "4 - Mostrar Inventário\n" +
                           "5 - Usar Item\n" +
                           "6 - Ver Status\n" +
                           "7 - Pular Vez");
        if(vezDo.getAtributos()[4] >=50) System.out.println("8 - Ataque Especial");
        boolean bool = true;
        while (bool){
            try{
                int input = new Scanner(System.in).nextInt();
                if (input == 8){
                    System.out.println("Todos os Atuais Selecionados");
                    mostrandoCompetidores(selecionados);
                    System.out.println("Quem você quer atacar?");
                    String s = new Scanner(System.in).next();
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
                        String s = new Scanner(System.in).next();
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
                        vezDo.mostrarInventario();
                        break;
                    case 5:
                        vezDo.mostrarInventario();
                        System.out.println("Escreva o nome do Item que deseja usar.");
                        ArrayList<String> arrItensNomes = new ArrayList<>(Arrays.asList(
                                "Heal", "Shield", "Strength", "Mana"));
                        boolean loop = false;
                        do {
                            String item = new Scanner(System.in).next();
                            ArrayList<String> arrPocoesName = pocoesNome(vezDo.getInventario());
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
                    case 6:
                        System.out.println(vezDo.info());
                        break;
                    case 7:
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
    static void iniciarPartida(ArrayList<Personagem> selecionados){
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
            Personagem vezDo = selecionados.get(sort.get(c++));
            System.out.println("Vez do "+vezDo.getNome());
            vezFazer(vezDo, selecionados, round);
            selecionados = deadFilter(selecionados);
            round++;
        }
        System.out.println("O ganhador é o Competidor: "+selecionados.get(0));
        System.out.println(selecionados.get(0).info());
    }
    static ArrayList<Personagem> selecionar(ArrayList<Personagem> competidores){
        ArrayList<Personagem> selecionados = new ArrayList<>();
        ArrayList<String> arrNomes = competidoresNome(competidores);
        while (true){
            ArrayList<String> arrNomesSelecionados = competidoresNome(selecionados);
            System.out.println("Selecione os Personagens");
            String nome = new Scanner(System.in).next();
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
                String finish = new Scanner(System.in).next().toLowerCase();
                if ("f".equals(finish)) return selecionados;
            }
            System.out.println("Para sair sem Selecionar Competidor digite \"s\" sem as aspas.");
            String cancel = new Scanner(System.in).next().toLowerCase();
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
                System.out.println("Os Nomes não podem ter espaços!");
                System.out.println("Digite um nome:");
                ArrayList<String> arrNomes = competidoresNome(competidores);
                switch (input){
                    case 1:
                        String nomeG = new Scanner(System.in).next();
                        Guerreiro g = new Guerreiro(nomeG);
                        if (arrNomes.contains(nomeG)){
                            System.out.println("Já existe alguem com esse nome");
                            continue;
                        }
                        competidores.add(g);
                        break;
                    case 2:
                        String nomeA = new Scanner(System.in).next();
                        Arqueiro a = new Arqueiro(nomeA);
                        if (arrNomes.contains(nomeA)){
                            System.out.println("Já existe alguem com esse nome");
                            continue;
                        }
                        competidores.add(a);
                        break;
                    case 3:
                        String nomeF = new Scanner(System.in).next();
                        Feiticeiro f = new Feiticeiro(nomeF);
                        if (arrNomes.contains(nomeF)){
                            System.out.println("Já existe alguem com esse nome");
                            continue;
                        }
                        competidores.add(f);
                        break;
                    default:
                        System.out.println("Escolhar uma das opções do menu");
                }
                if (competidores.size() > 1){
                    System.out.println("Se deseja Finalizar aperte \"f\" sem aspas");
                    String finalizar = new Scanner(System.in).next();
                    if ("f".equals(finalizar)) return competidores;
                }
                System.out.println("Para sair sem adicionar Competidor aperte \"s\" sem aspas");
                String sair = new Scanner(System.in).next();
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
        String p = new Scanner(System.in).next();
        ArrayList<String> nomeComp = competidoresNome(competidores);
        if (nomeComp.contains(p)){
            System.out.println("Digite o novo nome dele:");
            String nome = new Scanner(System.in).next();
            if (competidores.get(0).getCompetidores().contains(nome)){
                System.out.println("Alguém já usa esse nome!");
            }else{
                int index = nomeComp.indexOf(p);
                competidores.get(index).setNome(nome);
            }
        }else System.out.println("Não existe nenhum competidor com esse nome!");
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
                               "5 - Salvar Jogo\n" +
                               "6 - Carregar Jogo Salvo");
            try {
                int s = new Scanner(System.in).nextInt();
                switch (s){
                    case 1:
                        if (competidores.size() <= 1){
                            System.out.println("Você não tem Competidores Suficientes");
                            System.out.println("Por favor crie Personagens");
                            bool = true;
                        } else{
                            iniciarPartida(selecionar(competidores));
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
                        break;
                    case 6:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Digite um Número Inteiro");
                bool = true;
            }
        }
    }
    public static void main(String[] args) {
        menu();
    }
}