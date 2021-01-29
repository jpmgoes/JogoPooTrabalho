import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
import java.util.stream.Collectors;


public class Main {
    static void mostrandoCompetidores(ArrayList<Personagem> competidores){
        for(Personagem p : competidores)
            System.out.println(p.getNome());
    }
    static ArrayList<String> competidoresNome(ArrayList<Personagem> competidores){
        ArrayList<String> arrNomes = new ArrayList<>();
        for(Personagem p : competidores)
            arrNomes.add(p.getNome());
        return arrNomes;
    }
    static ArrayList<Personagem> deadFilter(ArrayList<Personagem> competidores){
        ArrayList<Personagem> clone = new ArrayList<>();
        for ( Personagem p : competidores){
            if (p.getAtributos()[0] > 0) clone.add(p);
        }
        return clone;
    }
    static ArrayList<Boolean> arrVez(ArrayList<Personagem> selecionados){
        ArrayList<Boolean> vez = new ArrayList<>();
        for(Personagem s : selecionados) vez.add(s.isVez());
        return vez;
    }
    public static void main(String[] args) {
        // testes
        ArrayList<Personagem> competidores = new ArrayList<>();
        Guerreiro g1 = new Guerreiro("G1");
        competidores.add(g1);
        Guerreiro g2 = new Guerreiro("G2");
        competidores.add(g2);
        Arqueiro a1 = new Arqueiro("A1");
        competidores.add(a1);
        Feiticeiro f1 = new Feiticeiro("F1");
        competidores.add(f1);
        //
        int rodada = 0;
        while (competidores.size() > 1){
            System.out.println("rodada: "+rodada);
            int random = new Random().nextInt(competidores.size() + 1);
            int random2 = new Random().nextInt(competidores.size() + 1);
            if (random >= competidores.size() || random2 >= competidores.size()) continue;
            if (random == 0) {
                competidores.get(0).powerUP();
                System.out.println(competidores.get(0).getNome());
                continue;
            }
            if (random != random2){
                competidores.get(random).mostrarInventario();
                if (competidores.get(random).getInventario().size() > 0)
                    competidores.get(random).ativarItem("Heal");
                competidores.get(random2);
                competidores.get(random).atacar(competidores.get(random2), rodada);
                System.out.println("vida: "+competidores.get(random2).getAtributos()[0]);
                competidores = deadFilter(competidores);
            }
//            mostrandoCompetidores(competidores);
            competidores.size();
            rodada++;
        }
        System.out.println("-------------Ganhador-------------");
        System.out.println(competidores.get(0).info());

//        random vez ---
//        ArrayList<Boolean> arr = arrVez(competidores);
//        int random3 = new Random().nextInt(competidores.size());
//        arr.set(random3, true);
//        int indexVez = arr.indexOf(true);
//        --------------
//        for (int i = 0; i < 3; i++) {
//            System.out.println("Qual classe vai escolher? digite:");
//            System.out.println("1 - Guerreiro\n2 - Arqueiro\n3 - Feiticeiro\n4 - Para Sair");
//            Scanner classeNum = new Scanner(System.in);
//            switch(Integer.parseInt(classeNum.nextLine())) {
//                case 1:
//                    System.out.println("Você escolheu O Guerreiro");
//                    System.out.println("Qual o nome do seu Personagem?");
//                    Scanner nomeG = new Scanner(System.in);
//                    Personagem g = new Guerreiro(nomeG.nextLine())
//                    competidores.add(g);
//                    break;
//                case 2:
//                    System.out.println("Você escolheu O Arqueiro");
//                    System.out.println("Qual o nome do seu Personagem?");
//                    Scanner nomeA = new Scanner(System.in);
//                    competidores.add(new Arqueiro(nomeA.nextLine(), false, competidores));
//                    break;
//                case 3:
//                    System.out.println("Você escolheu O Feiticeiro");
//                    System.out.println("Qual o nome do seu Personagem?");
//                    Scanner nomeF = new Scanner(System.in);
//                    competidores.add(new Arqueiro(nomeF.nextLine(), false, competidores));
//                    break;
//            }
//        }
//        System.out.println("A competicao ira comecar, selecione qual ira lutar com quem");
//        System.out.println("Selecione:\n1 - Ver todos os jogadores\n2 - Selecionar os Competidores");
//
//        ArrayList<Personagem> selecionados = new ArrayList<>();
//        Scanner nomeSelecionado = new Scanner(System.in);
//        String inputNomeSelecionado = nomeSelecionado.nextLine();
//        ArrayList<String> arrCompetidoresNomes = competidoresNome(competidores);
//        ArrayList<String> arrSelecionadosNomes = competidoresNome(competidores);
//
//        if(arrCompetidoresNomes.contains(inputNomeSelecionado) && !arrSelecionadosNomes.contains(inputNomeSelecionado))
//        System.out.println("Para Selecionar o Competidor é necessario digitar o nome dele: ");
//        System.out.println("Esse Competidor não existe! Mostrando todos os Competidores");
//        mostrandoCompetidores(competidores);
//        System.out.println("Esse Competidor já foi adicionado! Mostrando todos os Competidores");
//        mostrandoCompetidores(competidores);

    }
}