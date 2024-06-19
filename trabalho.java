import java.util.Random;
import java.util.Scanner;

public class trabalho {
    public trabalho() {

        Scanner s = new Scanner(System.in);

        int vitoriasDerrotas[] = new int[2];
        char navios[][] = new char[8][8];
        char mapa[][] = new char[8][8];
        int escolha[] = new int[30];
        int coordenadaMapa[] = new int[9];
        int tentativas = 0;
        int acertos = 0;
        int coluna = 0;
        int choice = 0;
        int linha = 0;

        do {

            System.out.print(
                    " _                             _   _                 " + "                                _ " +
                            "\n| |             _____         | | | |                "
                            + "                               | |" +
                            "\n| |__    __ _  |_   _| __ _   | | | |__    __ _   "
                            + "    _ __    __ _  ___  __  __ _   | |" +
                            "\n| '_ \\  / _` |   | |  / _` |  | | | '_ \\  / _` |  "
                            + "   | '_ \\  / _` | \\  \\/ / / _` |  | |" +
                            "\n| |_) || (_| |_  | | | (_| |_ | | | | | || (_| |_ "
                            + "   | | | || (_| |_ \\   / | (_| |_ | |" +
                            "\n|_.__/  \\__,_(_) |_|  \\__,_(_)|_| |_| |_| \\__,_(_)"
                            + "   |_| |_| \\__,_(_) \\_/   \\__,_(_)|_|" +
                            "\n\n");

            for (int i = 0; i < escolha.length; i++) {
                escolha[i] = 0;
            }

            tentativas = 0;
            acertos = 0;

            // Gerando o mapa...
            for (int i = 0; i < coordenadaMapa.length; i++) {
                coordenadaMapa[i] = i;
            }
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa.length; j++) {
                    mapa[i][j] = '~';
                }
            }

            // Mostrando o mapa ...
            visuMapa(coordenadaMapa, mapa);

            // Colocando os navios "no mapa" ...
            random(navios, linha, coluna);

            // Iniciando o jogo
            do {
                tentativas++; // Já marca mais uma tentativa pois em algum momento o jogador vai jogar ...

                acertos = ataque(s, acertos, tentativas, mapa, navios, escolha, coluna, linha, vitoriasDerrotas);

                // Atualização do mapa, toda vez que o jogador atacar ...
                visuMapa(coordenadaMapa, mapa);

            } while (acertos != 10 && tentativas != 30);// O jogo acontece enquanto os acertos não chegarem a 10 e as
                                                        // tentativas não chegarem a 30
            // chama o metodo para fim do jogo, e verifica oq o jogador desejá fazer depois
            choice = gameOver(s, acertos, tentativas, choice, mapa, navios, coordenadaMapa, vitoriasDerrotas);

        } while (choice != 4);
    }

    // Nesse metodo, toda a busca de coordenadas, as verificações de
    // limitações e repetição de coordenada são feitas ...
    private int ataque(Scanner s, int acertos, int tentativas, char[][] mapa, char[][] navios, int escolha[],
            int coluna,
            int linha, int vitoriasDerrotas[]) {

        String answer = "1 1";
        int aux = 0;

        do {
            aux = 0;
            int a = 0;

            System.out.print("\nDigite as colunas para atacar (Linha e Coluna, separados por espaço) : ");

            answer = s.nextLine().replace(" ", "");

            try {
                a = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("\n\nReiniciando");
                aux = 2;
            }

            if (aux != 2) {
                for (int i = 0; i < tentativas; i++) {
                    if (escolha[i] == a) {
                        aux = 1; // Verifição de repetição: (se qualquer coordenada que o jogador ja colocou
                                 // é igual a nova coord) aux = 1 (algo errado)
                    }
                }
            }

            escolha[tentativas - 1] = a; // A nova coordenada é salva ...

            // Encontrando a linha e a coluna das coordenadas dadas... (se não houver algo
            // errado)
            if (aux == 0) {
                for (int i = 1; i < 9; i++) {
                    for (int j = 1; j < 9; j++) {

                        String x = i + "" + j; // Criação de "i" e "j" para encontrar a coordenada em forma inteira de
                                               // linha e coluna separados
                        int b = Integer.parseInt(x); // transformação para int para comparar com a coordenada

                        if (a == b) {
                            linha = i;
                            coluna = j;
                        }
                    }
                }
            } else if (aux == 1) { // se houver algo errado, é porque o jogador já atacou essa coordenada...
                System.out.println("\nVocê já atacou essa coordenada ...");
            }

            // Agora é a verificação para se o ataque estiver fora das coordenadas...
            if (aux == 0) { // Se ainda não houver nada errado (coord n repitida)
                if (linha == 0 || coluna == 0) { // E linha e coluna forem zero, ou seja fora do mapa...
                    System.out.println("\nCoordenada fora do mapa ...");
                    aux = 1;
                } else {// se não estiver fora, o aux volta pra 0 (tudo certo)
                    aux = 0;
                }
            }

            // Verificando se está tudo certo...
            if (aux == 0) {

                linha--;
                coluna--;

                // Se um navio está na coordenada dada, o mapa marca um "X"
                if (navios[linha][coluna] == 'N') {
                    mapa[linha][coluna] = 'X';
                    acertos++;
                    System.out.println("\nVocê Acertou UM Navio! faltam " + (10 - acertos) + " ...");
                } else { // Se não, marca com um "O"
                    mapa[linha][coluna] = 'O';
                    System.out.println(
                            "\nNão tinha nenhum navio ali, mas você ainda tem " + (30 - tentativas) + " tentativas.");
                }
            }

        } while (aux != 0);

        return acertos;
    }

    private void random(char navios[][], int lin, int col) {
        int aux = 10; // aux está como um contador para 10 navios ...
        Random random = new Random();
        for (int i = 0; i < aux; i += 0) { // toda vez que verificar se ja não existe um navio naquela coordenada,
            lin = random.nextInt(8); // vai se gerar os randoms ...
            col = random.nextInt(8);
            if (navios[lin][col] != 'N' && lin != 0 && col != 0) {
                navios[lin][col] = 'N';
                aux--;// ... E teremos menos um navio para gerar, ate chegar a zero.
            }
        }
    }

    private void visuMapa(int num[], char mapa[][]) {
        System.out.println();

        for (int i = 0; i < num.length; i++) {
            System.out.print(num[i] + "  ");
        }

        System.out.println();

        for (int i = 0; i < mapa.length; i++) {
            System.out.print(num[i + 1] + "  ");
            for (int j = 0; j < mapa.length; j++) {
                System.out.print(mapa[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private void verNavios(char mapa[][], char navios[][], int num[]) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa.length; j++) {
                if (navios[i][j] == 'N') {
                    mapa[i][j] = 'X';
                } else {
                    mapa[i][j] = '~';
                }
            }
        }
        visuMapa(num, mapa);
    }

    // Basicamente esse método
    private int gameOver(Scanner s, int acertos, int tentativas, int choice, char mapa[][], char navios[][],
            int num[], int vitoriasDerrotas[]) {
        if (acertos == 10) {
            vitoriasDerrotas[0]++;
            System.out.println("\nVocê Venceu!\n__   __            _    _ _       " +
                    "\n\\ \\ / /           | |  | (_)      " +
                    "\n \\ V /___  _   _  | |  | |_ _ __  " +
                    "\n  \\ // _ \\| | | | | |/\\| | | '_ \\ " +
                    "\n  | | (_) | |_| | \\  /\\  / | | | |" +
                    "\n  \\_/\\___/ \\__,_|  \\/  \\/|_|_| |_|" +
                    "");
        } else if (tentativas == 30) {
            vitoriasDerrotas[1]++;
            System.out.println("\nVocê Perdeu!\n  ________                        ________                     " +
                    "\n /  _____/_____    _____   ____   \\_____  \\___  __ ___________ " +
                    "\n/   \\  ___\\__  \\  /     \\_/ __ \\   /   |   \\  \\/ // __ \\_  __ \\" +
                    "\n\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/  /    |    \\   /\\  ___/|  | \\/" +
                    "\n \\______  (____  /__|_|  /\\___  > \\_______  /\\_/  \\___  \\__|" +
                    "\n        \\/     \\/      \\/     \\/          \\/          \\/");
        }
        do {
            System.out.print(
                    "\nO que desejas fazer agora : \n1 - Jogar novamente                  |\n2 - Ver localização dos navios       |"
                            + "\n3 - Ver estatísticas de meus jogos   |\n4 - Sair sem salvar                  | ");
            choice = s.nextInt();

            switch (choice) {
                case 1:
                    return choice;
                case 2:
                    verNavios(mapa, navios, num);
                    break;
                case 3:
                    System.out.println("\nVitórias : " + vitoriasDerrotas[0] + "\nDerrotas : " + vitoriasDerrotas[1]);
                    break;
                case 4:
                    break;
                default:
                    System.out.println("\nOpção Inválida...");
                    break;
            }

        } while (choice != 4);
        return choice;
    }

    public static void main(String[] args) {
        new trabalho();
    }
}
