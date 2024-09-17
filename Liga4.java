import java.util.Scanner;

public class Liga4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean jogarNovamente = true;
        while (jogarNovamente) {
            char[][] tabuleiro = new char[6][7]; // matriz 6x7 do tabuleiro

            // popular os espacos com B
            for (int i = 0; i < 6; i++) { // para cada linha
                for (int j = 0; j < 7; j++) { // para cada coluna
                    tabuleiro[i][j] = 'B';
                }
            }

            // cor do jogador
            String corJogador;
            String corComputador;
            do {
                System.out.print("Escolha a cor Vermelho(V) ou Azul(A): ");
                corJogador = scanner.nextLine().toUpperCase();

                if (!corJogador.equals("V") && !corJogador.equals("A") && !corJogador.equals("VERMELHO" ) && !corJogador.equals("AZUL")) {
                    System.out.println("Cor inválida! Digite novamente:");
                } else {
                    break;
                }
            } while (true);

            if (corJogador.equals("V") || corJogador.equals("VERMELHO")) {
                corComputador = "A";
            } else {
                corComputador = "V";
            }

            // exibir o tabuleiro antes do jogo começar
            System.out.println("Tabuleiro:");
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    System.out.print(tabuleiro[i][j] + " "); // "" espaco entre as colunas
                }
                System.out.println(); // pula linha
            }
            System.out.println("-------------------");

            // Loop principal do jogo
            boolean jogoAcabou = false;
            boolean vezDoJogador = true;
            int jogadas = 0;
            while (!jogoAcabou) { // enquanto jogo não acabou
                if (vezDoJogador) {
                    // jogada pessoa
                    int coluna;
                    do {
                        // recebe a jogada e valida se pode ser feita na coluna
                        System.out.print("Digite a coluna desejada (0-6): ");
                        coluna = scanner.nextInt();

                        if (coluna < 0 || coluna >= 7) {
                            System.out.println("Coluna inválida! Tente novamente.");
                            continue;
                        }

                        if (colunaCheia(tabuleiro, coluna)) {
                            System.out.println("A coluna está cheia! Tente novamente.");
                        } else {
                            break; // jogada valida. encerra o laco de validacao
                        }
                    } while (true);

                    int linha = encontrarLinhaVazia(tabuleiro, coluna);
                    if (linha >= 0) {
                        tabuleiro[linha][coluna] = corJogador.toUpperCase().charAt(0);
                    } else {
                        System.out.println("Esse erro nunca vai acontecer"); // pq a coluna pergunta antes da linha
                    }

                    // verifica se o jogador venceu
                    if (verificarVitoria(tabuleiro, corJogador.toUpperCase().charAt(0))) {
                        imprimeTabuleiro(tabuleiro); // exibir o tabuleiro após cada jogada
                        System.out.println("Você venceu!");
                        jogoAcabou = true;
                    }

                    vezDoJogador = false;
                } else {
                    // jogada do computador
                    int colunaComputador = sortearColuna(tabuleiro);
                    int linhaComputador = encontrarLinhaVazia(tabuleiro, colunaComputador);
                    tabuleiro[linhaComputador][colunaComputador] = corComputador.charAt(0);

                    // verifica se o computador venceu
                    if (verificarVitoria(tabuleiro, corComputador.charAt(0))) {
                        imprimeTabuleiro(tabuleiro);
                        System.out.println("O computador venceu!");
                        jogoAcabou = true;
                    }

                    vezDoJogador = true;
                }

                // exibe o tabuleiro após cada jogada
                imprimeTabuleiro(tabuleiro);
                jogadas++;
                if (jogadas == 42) {
                    System.out.println("EMPATE");
                    jogoAcabou = true;
                }
            }

            // jogar novamente
            boolean respostaValida;
            do {
                System.out.print("Deseja jogar novamente? (S/N): ");
                String resposta = scanner.next();
                jogarNovamente = resposta.equalsIgnoreCase("S");
                respostaValida = resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("N");
                if (!respostaValida) {
                    System.out.println("Opção inválida! Digite novamente:");
                }
            } while (!respostaValida);

            scanner.nextLine();
        }

        scanner.close();
    }

    public static void imprimeTabuleiro(char[][] tabuleiro) {
        System.out.println("Tabuleiro:");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(tabuleiro[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("-------------------");
    }

    public static boolean colunaCheia(char[][] tabuleiro, int coluna) {
        return tabuleiro[0][coluna] != 'B';
    }

    public static int encontrarLinhaVazia(char[][] tabuleiro, int coluna) {
        for (int i = 5; i >= 0; i--) {
            if (tabuleiro[i][coluna] == 'B') {
                return i; // retorna a linha encontrada
            }
        }
        return -1; // se nao encontrou posicao livre volta -1
    }

    public static boolean verificarVitoria(char[][] tabuleiro, char cor) {
        // verifica vitoria na horizontal
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) { // limita em 4 pq testa as 4 posicoes pra frente
                if (tabuleiro[i][j] == cor && tabuleiro[i][j + 1] == cor && tabuleiro[i][j + 2] == cor && tabuleiro[i][j + 3] == cor) {
                    return true;
                }
            }
        }

        // verifica vitoria na vertical
        for (int i = 0; i < 3; i++) { // limita em 3 pq testa 4 posicoes pra baixo
            for (int j = 0; j < 7; j++) {
                if (tabuleiro[i][j] == cor && tabuleiro[i + 1][j] == cor && tabuleiro[i + 2][j] == cor && tabuleiro[i + 3][j] == cor) {
                    return true;
                }
            }
        }

        // verifica vitoria na diagonal direita (\)
        for (int i = 0; i < 3; i++) { // limita em 3 pq testa 4 posicoes pra baixo
            for (int j = 0; j < 4; j++) { // limita em 4 pq testa as 4 posicoes pra frente
                if (tabuleiro[i][j] == cor && tabuleiro[i + 1][j + 1] == cor && tabuleiro[i + 2][j + 2] == cor && tabuleiro[i + 3][j + 3] == cor) {
                    return true;
                }
            }
        }

        // verifica vitoria na diagonal esquerda (/)
        for (int i = 0; i < 3; i++) { // limita em 3 pq testa 4 posicoes pra baixo
            for (int j = 3; j < 7; j++) { // limita em 3 pq testa 4 posicoes pra frente
                if (tabuleiro[i][j] == cor && tabuleiro[i + 1][j - 1] == cor && tabuleiro[i + 2][j - 2] == cor && tabuleiro[i + 3][j - 3] == cor) {
                    return true;
                }
            }
        }

        return false;
    }

       public static int sortearColuna(char[][] tabuleiro) {
        while (true) {
            int coluna = (int) (Math.random() * 7);
            if (!colunaCheia(tabuleiro, coluna)) {
                return coluna;
            }
        }
    }
}