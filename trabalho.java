import java.util.Random;
import java.util.Scanner;

public class trabalho {
    public trabalho() {

        Scanner s = new Scanner(System.in);

        int coordsEscolhidas[] = new int[30];
        int vitoriasDerrotas[] = new int[2];
        int coordenadaMapa[] = new int[9];
        char navios[][] = new char[8][8];
        char mapa[][] = new char[8][8];
        int tentativas = 0;
        int escolha = 0;
        int acertos = 0;

        // Vai repetir até a escolha ser 4...
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
                            "\n");

            // Inicializando todas as escolhas de coordenadas com zero
            for (int i = 0; i < coordsEscolhidas.length; i++) {
                coordsEscolhidas[i] = 0;
            }

            tentativas = 0;
            acertos = 0;

            // Gerando o mapa e resetando navios ...
            for (int i = 0; i < coordenadaMapa.length; i++) {
                coordenadaMapa[i] = i;
            }
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa.length; j++) {
                    mapa[i][j] = '~';
                    navios[i][j] = '~';
                }
            }

            // Mostrando o mapa ...
            visuMapa(coordenadaMapa, mapa);

            // Colocando os navios "no mapa" ...
            random(navios);

            // Iniciando o jogo
            do {
                tentativas++; // Já marca mais uma tentativa pois em algum momento o jogador vai jogar ...

                acertos = ataque(s, acertos, tentativas, mapa, navios, coordsEscolhidas);

                // Atualização do mapa, toda vez que o jogador atacar ...
                visuMapa(coordenadaMapa, mapa);

            } while (acertos != 10 && tentativas != 30);// O jogo acontece enquanto os acertos não chegarem a 10 e as
                                                        // tentativas não chegarem a 30
            // chama o metodo para fim do jogo, e verifica oq o jogador desejá fazer depois
            escolha = gameOver(s, acertos, escolha, mapa, navios, coordenadaMapa, vitoriasDerrotas);

        } while (escolha != 4);
    }

    // Nesse metodo, toda a busca de coordenadas, as verificações de limitações e
    // repetição de coordenada são feitas, ele retorna os acertos para que a
    // contagem seja possivel (se chegar a dez, venceu)
    // O Scanner s é pra ler, tentativas é pra contar as tentativas, mapa é para
    // salvar X ou O no mapa, navios é para saber onde os navios estão,
    // coordsEscolhidas é para salvar as coordenadas já utilizadas pra não poder
    // repeti-las, e vitoriasDerrotas é para salvar quantas vezes você venceu ou
    // perdeu
    private int ataque(Scanner s, int acertos, int tentativas, char[][] mapa, char[][] navios, int coordsEscolhidas[]) {

        String reposta = "";
        int erro = 0;
        int linha = 0;
        int coluna = 0;

        do { // Repete até não haver nenhum erro de escolha ou repetição de coordenada ...
            erro = 0;
            int a = 0;

            System.out.print("\nDigite as colunas para atacar (Linha e Coluna, separados por espaço) : ");
            reposta = s.nextLine().replace(" ", "");

            try { // Pra não parar o sistema caso de um bug, ou a pessoa escolha uma letra...
                a = Integer.parseInt(reposta);
            } catch (NumberFormatException e) {
                System.out.println("\nOps... aconteceu alguma coisa, mas você já pode continuar :)");
                erro = 2;
            }

            if (erro != 2 && a != 00) {
                for (int i = 0; i < tentativas; i++) {
                    if (coordsEscolhidas[i] == a) {
                        erro = 1; // Verifição de repetição: (se qualquer coordenada que o jogador ja colocou
                                  // é igual a nova coordenada) erro = 1
                    }
                }
            }

            if (erro == 0) { // Se não há erro, fará as verificações necessárias

                for (int i = 1; i < 9; i++) { // Procura por todas as coordenadas possiveis
                    for (int j = 1; j < 9; j++) {

                        String x = i + "" + j; // Criação de "i" e "j" para encontrar a coordenada
                        int b = Integer.parseInt(x); // transformação para int para comparar com a coordenada escolhida

                        if (a == b) { // Se a coordenada é encontrada, ela é salva em linha e coluna separadas
                            linha = i;
                            coluna = j;
                            coordsEscolhidas[tentativas - 1] = a; // A coordenada escolhida é salva para comparar depois
                        }
                    }
                }
                if (linha != 0) { // Linha é zero, está fora do mapa...

                    linha--;
                    coluna--;

                    // Se um navio está na coordenada dada, o mapa marca um "X"
                    if (navios[linha][coluna] == 'N') {
                        mapa[linha][coluna] = 'X';
                        acertos++;
                        System.out.println("\nVocê Acertou UM Navio! faltam " + (10 - acertos) + " deles, e "
                                + (30 - tentativas) + " tentativas.");
                    } else { // Se não, marca com um "O"
                        mapa[linha][coluna] = 'O';
                        System.out.println(
                                "\nNão tinha nenhum navio ali, mas você ainda tem " + (30 - tentativas)
                                        + " tentativas.");
                    }
                } else {
                    System.out.println("\nCoordenada fora do mapa ...");
                    erro = 3; // erro 3, fora do mapa
                }
            } else if (erro == 1) { // Erro 1, coordenada está sendo repetida
                System.out.println("\nVocê já atacou essa coordenada ...");
            }

        } while (erro != 0);

        return acertos;
    }

    // Colocando navios aleatórios, não retorna nada, apenas salva os navios
    // aleatórios na matriz navios
    private void random(char navios[][]) {
        int aux = 10; // aux está como um contador para 10 navios ...
        int linha = 0;
        int coluna = 0;
        Random random = new Random();
        for (int i = 0; i < aux; i += 0) { // toda vez que verificar se ja não existe um navio naquela coordenada,
            linha = random.nextInt(8); // vai se gerar os randoms ...
            coluna = random.nextInt(8);
            if (navios[linha][coluna] != 'N' && linha != 0 && coluna != 0) { // Verificando repetição de coordenada e se
                                                                             // está em 0
                navios[linha][coluna] = 'N';
                aux--;// ... E teremos menos um navio para gerar, ate chegar a zero.
            }
        }
    }

    // Não retorna nada, apenas mostra o mapa, coordenadaMapa são os números de
    // coordenadas no mapa
    private void visuMapa(int coordenadaMapa[], char mapa[][]) {

        System.out.println();

        for (int i = 0; i < coordenadaMapa.length; i++) {
            System.out.print(coordenadaMapa[i] + "  ");
        }

        System.out.println();

        for (int i = 0; i < mapa.length; i++) {
            System.out.print(coordenadaMapa[i + 1] + "  ");
            for (int j = 0; j < mapa.length; j++) {
                System.out.print(mapa[i][j] + "  ");
            }
            System.out.println();
        }
    }

    // Mostra todos os navios na matriz mapa e troca todas as tentativas que você
    // errou (O), por água (~)...
    // o char mapa é para poder ler o mapa, navios é para poder ver onde os návios
    // estão e o
    private void verNavios(char mapa[][], char navios[][], int coordenadaMapa[]) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa.length; j++) {
                if (navios[i][j] == 'N') {
                    mapa[i][j] = 'X';
                } else {
                    mapa[i][j] = '~';
                }
            }
        }
        visuMapa(coordenadaMapa, mapa);
    }

    // Após o jogo terminar, esse método é chamado para retornar escolha, mostrará
    // se você venceu ou não, e tem um novo menu, dependendo da sua escolha
    // retornada, o jogo pode ser finalizado ou reiniciado.
    // Scanner s para ler, acertos para saber se venceu ou não, mapa para escrever o
    // mapa, navios para saber onde estão, coordenadaMapa para escrever o mapa
    // tambem, e vitoriasDerrotas para saber suas vitorias ou derrotas caso você
    // queira ver-las
    private int gameOver(Scanner s, int acertos, int escolha, char mapa[][], char navios[][],
            int coordenadaMapa[], int vitoriasDerrotas[]) {

        char simOuNao = '0';

        if (acertos == 10) {
            vitoriasDerrotas[0]++;
            System.out.println("\nVocê Venceu!\n__   __            _    _ _       " +
                    "\n\\ \\ / /           | |  | (_)      " +
                    "\n \\ V /___  _   _  | |  | |_ _ __  " +
                    "\n  \\ // _ \\| | | | | |/\\| | | '_ \\ " +
                    "\n  | | (_) | |_| | \\  /\\  / | | | |" +
                    "\n  \\_/\\___/ \\__,_|  \\/  \\/|_|_| |_|" +
                    "");
        } else {
            vitoriasDerrotas[1]++;
            System.out.println("\nVocê Perdeu!\n  ________                        ________                     " +
                    "\n /  _____/_____    _____   ____   \\_____  \\___  __ ___________ " +
                    "\n/   \\  ___\\__  \\  /     \\_/ __ \\   /   |   \\  \\/ // __ \\_  __ \\" +
                    "\n\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/  /    |    \\   /\\  ___/|  | \\/" +
                    "\n \\______  (____  /__|_|  /\\___  > \\_______  /\\_/  \\___  \\__|" +
                    "\n        \\/     \\/      \\/     \\/          \\/          \\/");
        }

        do {
            escolha = 0;

            System.out.print(
                    "\nO que desejas fazer agora : \n1 - Jogar novamente                  |\n2 - Ver localização dos navios       |"
                            + "\n3 - Ver estatísticas de meus jogos   |\n4 - Sair sem salvar                  | ");

            String aux = s.nextLine();

            try {
                escolha = Integer.parseInt(aux);
            } catch (NumberFormatException e) {
                System.out.println("\nEscolha um número por favor... ");
            }

            switch (escolha) {
                case 1:
                    return escolha;
                case 2:
                    verNavios(mapa, navios, coordenadaMapa);
                    break;
                case 3:
                    System.out.println("\nVitórias : " + vitoriasDerrotas[0] + "\nDerrotas : " + vitoriasDerrotas[1]);
                    break;
                case 4:
                    System.out.print("\nTem certeza que deseja finalizar o jogo?"
                            + "\nSuas ações serão apagadas!"
                            + "\n\nSim ou Não : ");
                    simOuNao = s.nextLine().toUpperCase().charAt(0);
                    if (simOuNao == 'S') {
                        System.out.println("\nFinalizando ...\n");
                    }
                    break;
                default:
                    System.out.println("\nOpção Inválida...");
                    break;
            }

        } while (simOuNao != 'S');
        return escolha;
    }

    public static void main(String[] args) {
        new trabalho();
    }
}
