import java.util.Scanner;

public class questao01 {

    public questao01() {

        Scanner s = new Scanner(System.in);

        int pesoMax, peso, tamanho, menu;
        int itemPesado = Integer.MIN_VALUE;
        tamanho = 0;
        peso = 0;

        System.out.print("Qual o tamanho da mochila : ");
        String objetos[] = new String[s.nextInt()];
        int pesos[] = new int[objetos.length];

        System.out.print("Qual o peso máximo na mochila : ");
        pesoMax = s.nextInt();

        do {

            System.out.print("\nEscolha uma opção: \n1 - Inserir item           |"
                    + "\n2 - Imprimir mochila       |"
                    + "\n3 - Ver item mais pesado   |\n4 - Ordenar mochila        |\n"
                    + "5 - Tirar item da mochila  |\n6 - Sair do sistema        |      -> ");

            menu = s.nextInt();

            switch (menu) {
                case 1:
                    System.out.print("\nO que você quer colocar na mochila : ");
                    String vetorObj = s.next();
                    System.out.print("\nQual o peso desse objeto (kg) : ");
                    int vetorPeso = s.nextInt();
                    tamanho = inserir(pesoMax, tamanho, objetos, peso, vetorPeso, vetorObj, pesos);

                    break;
                case 2:
                    imprimir(objetos, pesos, tamanho);
                    break;
                case 3:
                    itemPesado = valorItemMaisPesado(pesos, itemPesado, tamanho);
                    System.out.print("\nPeso : " + itemPesado);
                    for (int i = 0; i < tamanho; i++) {
                        if (pesos[i] == itemPesado) {
                            System.out.println("kg, " + objetos[i]);
                        }
                    }
                    break;
                case 4:
                    System.out.println();
                    ordenarMochila(pesos, objetos, tamanho);
                    imprimir(objetos, pesos, tamanho);
                    break;
                case 5:
                    System.out.println("\nQual o número do item que deseja excluir : ");
                    int aux = s.nextInt() - 1;
                    if (aux < 0 || aux >= tamanho){
                        System.out.println("Objeto não encontrado!");
                        break;
                    }
                    System.out.print("Objeto " + (aux + 1) + " : " + objetos[aux]
                            + "\nCom peso : " + pesos[aux]
                            + " kg \nTem certeza que deseja excluir : (1 - sim | 2 - não) ");

                    int escolha = s.nextInt();

                    if (escolha == 1){
                        int quantidade = excluirItemDaMochila(objetos, pesos, tamanho, peso, aux);
                        if (quantidade != -1) {
                            tamanho = quantidade;
                            System.out.println("\nItem excluído com sucesso");
                        } else {
                            System.out.println("\nItem inexistente, não foi excluído");
                        }
                    }
                    break;
                case 6:
                    break;
                default:
                    System.out.println("\nOpção inválida");
                    break;
            }
        } while (menu != 6);

        s.close();
    }

    private void ordenarMochila(int pesos[], String[] objetos, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            for (int j = i; j < tamanho - 1; j++) {
                if (pesos[j] > pesos[j + 1]) {
                    int aux = pesos[j];
                    pesos[j] = pesos[j + 1];
                    pesos[j + 1] = aux;

                    String s = objetos[j];
                    objetos[j] = objetos[j + 1];
                    objetos[j + 1] = s;
                }
            }
        }
    }

    private int inserir(int pesoMax, int tamanho, String objetos[], int peso,
            int vetorPeso, String vetorObj, int pesos[]) {
        for (int i = 0; i < tamanho; i++) {
            peso += pesos[i];
        }
        if (tamanho < objetos.length & (peso + vetorPeso) <= pesoMax) {
            objetos[tamanho] = vetorObj;
            pesos[tamanho] = vetorPeso;
            peso += vetorPeso;
            tamanho++;
            System.out.println("\nItem inserido com sucesso.");
        } else if (tamanho < objetos.length) {
            System.out.println("\nItem não inserido, pois excede o peso máximo.");
        } else {
            System.out.println("\nItem não inserido, pois não há espaço.");
        }
        return tamanho;
    }

    private void imprimir(String objetos[], int pesos[], int tamanho) {
        System.out.println();
        for (int i = tamanho - 1; i > -1; i--) {
            System.out.println("Objeto (" + (i + 1) + ") = " + objetos[i] + " " + pesos[i] + "kg");
        }
    }

    private int valorItemMaisPesado(int pesos[], int itemPesado, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (pesos[i] > itemPesado) {
                itemPesado = pesos[i];
            }
        }
        return itemPesado;
    }

    private int excluirItemDaMochila(String objetos[], int pesos[], int tamanho, int peso, int aux) {
        if (aux < 0 || aux >= tamanho) {
            return -1;
        }
        for (int i = aux; i < tamanho - 1; i++) {
            objetos[i] = objetos[i + 1];
            pesos[i] = pesos[i + 1];
        }
        tamanho--;
        return tamanho;
    }

    public static void main(String[] args) {
        new questao01();
    }
}
