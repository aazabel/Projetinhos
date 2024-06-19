import java.util.Scanner;

public class projetinho {

    public projetinho() {

        Scanner s = new Scanner(System.in);

        int resposta = 0;
        int tamanho = 0;
        String clientes[] = new String[5];
        int quartos[] = new int[5];

        for (int i = 0; i < quartos.length; i++) {
            quartos[i] = -1;
        }

        do {
            System.out.print("\nMENU :                  \n1 - Adicionar cliente         |"
                    + "\n2 - Ver clientes e quartos    |\n3 - Finalizar conta           |"
                    + "\n4 - Quartos disponiveis       v\n5 - Sair do sistema           ");
            resposta = s.nextInt();

            switch (resposta) {
                case 1:
                    System.out.print("\nNome do Cliente (ou Família) para adicionar : ");
                    String auxClientes = s.next();
                    System.out.print("\nQual quarto deseja hospeda-lo : ");
                    int quarto = s.nextInt() - 1;
                    if (quarto > -1 & quarto < quartos.length) {
                        tamanho = addCliente(s, clientes, auxClientes, quartos, tamanho, quarto);
                    } else {
                        System.out.println("\nQuarto Inválido.");
                    }
                    break;
                case 2:
                    verClientes(clientes, quartos, tamanho);
                    break;
                case 3:
                    System.out.print("\nQual o quarto do cliente que deseja finalizar a conta : ");
                    quarto = s.nextInt() - 1;
                    tamanho = finalzarConta(clientes, quartos, quarto, tamanho);
                    break;
                case 4:
                    quartosDisp(quartos);
                    break;
                case 5:
                    System.out.println("\nTem certeza que deseja sair ? sim = 1 | não = 2");
                    resposta = s.nextInt();
                    if (resposta == 1) {
                        resposta = 5;
                    }
                    break;

                default:
                    System.out.println("\nNúmero Inválido ! ");
                    break;
            }
        } while (resposta != 5);
        s.close();
    }

    private int addCliente(Scanner s, String clientes[], String auxClientes, int[] quartos, int tamanho, int quarto) {
        if (tamanho < clientes.length) {
            int aux = 0;
            for (int i = 0; i < tamanho; i++) {
                if (quartos[i] == quarto) {
                    aux = 1;
                }
            }
            if (aux == 0) {
                clientes[tamanho] = auxClientes;
                quartos[tamanho] = quarto;
                tamanho++;
                System.out.println("\nCliente adicionado com sucesso.");
            } else {
                System.out.println("\nQuarto já em utilização.");
            }

        } else {
            System.out.println("\nTodos os quartos já em utilização.");
        }
        return tamanho;
    }

    private void verClientes(String clientes[], int[] quartos, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (quartos[i] != 1) {
                System.out.println("\nCliente : " + clientes[i] + ", Quarto " + (quartos[i] + 1));
            }
        }
    }

    private int finalzarConta(String[] clientes, int quartos[], int quarto, int tamanho) {
        int aux = 0;
        if (quarto > -1 & quarto < quartos.length) {
            for (int i = 0; i < tamanho; i++) {
                if (quartos[i] == quarto) {
                    aux = 1;
                }
            }
            if(aux == 0){
                System.out.println("\nQuarto não utilizado.");
            }
        } else {
            System.out.println("\nQuarto Inválido.");
        }
        if (aux == 1) {
            for (int j = tamanho; j < quartos.length - 1; j++) {
                quartos[j] = quartos[j + 1];
                clientes[j] = clientes[j + 1];
            }
            tamanho--;
        }
        return tamanho;
    }

    private void quartosDisp(int[] quartos){
        for (int i = 0; i < quartos.length; i++) {
            if(quartos[i] == -1){
                System.out.println("Quarto : " + (quartos[i] + 1));
            }
        }
    }

    public static void main(String[] args) {
        new projetinho();
    }
}
