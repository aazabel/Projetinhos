import java.text.DecimalFormat;
import java.util.Scanner;

public class projetinho {

    public projetinho() {

        Scanner s = new Scanner(System.in);

        int resposta = 0;
        int tamanho = 0;
        String clientes[] = new String[5];
        int totalPagar[] = new int[5];
        int estadia[] = new int[5];
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
                        tamanho = addCliente(s, clientes, auxClientes, quartos, tamanho, quarto, estadia, totalPagar);
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
                    tamanho = finalzarConta(s, clientes, quartos, quarto, totalPagar, tamanho);
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

    private int addCliente(Scanner s, String clientes[], String auxClientes, int[] quartos, int tamanho, int quarto,
            int[] estadia, int[] totalPagar) {
        if (tamanho < clientes.length) {

            if (quartos[quarto] != quarto + 1) {
                clientes[quarto] = auxClientes;
                quartos[quarto] = quarto + 1;
                tamanho++;

                System.out.print("\nQuantos dias de estadia : ");
                estadia[quarto] = s.nextInt();

                totalPagar(estadia, quartos, quarto, totalPagar);

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
        for (int i = 0; i < quartos.length; i++) {
            if (quartos[i] != -1) {
                System.out.println("\nCliente : " + clientes[i] + ", Quarto " + quartos[i]);
            }
        }
    }

    private int finalzarConta(Scanner s, String[] clientes, int quartos[], int quarto, int totalPagar[], int tamanho) {

        DecimalFormat df = new DecimalFormat("0.00");
        if (quarto > -1 & quarto < quartos.length) {
            if (quartos[quarto] > 0 & quartos[quarto] < 6) {
                System.out.print("\nTem certeza que deseja finalizar essa conta : "
                        + "\n\nCliente : " + clientes[quarto] + "\n\nSim ou Não : ");
                char escolha = s.next().toUpperCase().charAt(0);
                if (escolha == 'S') {

                    System.out.println("\nTotal a pagar : R$" + df.format(totalPagar[quarto]));
                    quartos[quarto] = -1;
                    clientes[quarto] = "";
                    totalPagar[quarto] = 0;
                }
            } else {
                System.out.println("\nQuarto não utilizado.");
            }
        } else {
            System.out.println("\nQuarto Inválido.");
        }

        return tamanho;
    }

    private void totalPagar(int estadia[], int quartos[], int quarto, int[] totalPagar) {
        totalPagar[quarto] = estadia[quarto] * 100;
    }

    private void quartosDisp(int[] quartos) {
        for (int i = 0; i < quartos.length; i++) {
            if (quartos[i] == -1) {
                System.out.print("\nQuarto : " + (i + 1));
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new projetinho();
    }
}


    public static void main(String[] args) {
        new projetinho();
    }
}
