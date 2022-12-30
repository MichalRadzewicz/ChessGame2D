import java.util.Scanner;

public class s21148_p01 {

    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";

    // Declaring the color
    // Custom declaration
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    private final String[][] board = {
            {"WW", "WS", "WG", "WH", "WK", "WG", "WS", "WW"},
            {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"", "", "", "", "", "", "", ""},
            {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
            {"BW", "BS", "BG", "BH", "BK", "BG", "BS", "BW"}
    };

    boolean firstPlayerMove = true;

    public static void main(String[] args) {
        s21148_p01 program = new s21148_p01();
        program.mainGame();

    }

    private void mainGame() {
        drawBoard();
        Scanner in = new Scanner(System.in);
        while (true) {

            if (firstPlayerMove) {
                System.out.println("Ruch białych:");
            } else {
                System.out.println("Ruch czarnych:");
            }

            String move = in.nextLine();
            String[] moveArr = move.split(" ");

            if (!checkMove(moveArr)) {
                System.out.println("Ruch nie może być wykonany");
                continue;
            }

            firstPlayerMove = !firstPlayerMove;
            drawBoard();
        }
    }

    private void drawBoard() {

        for (int i = 0; i < 9; i++) {
            for (int j = -1; j < 8; j++) {
                if (j == -1 && i != 8) {
                    System.out.print(ANSI_YELLOW + (8 - i) + " | " + ANSI_RESET);
                    continue;
                }

                if (i == 8 && j == -1) {
                    System.out.print("    ");
                    continue;
                }

                if (i == 8) {
                    System.out.print(ANSI_YELLOW + (char) (65 + j) + " | " + ANSI_RESET);
                    continue;
                }

                if (!board[7 - i][j].equals("")) {
                    if ((board[7 - i][j]).toCharArray()[0] == 'B') {
                        System.out.print(ANSI_BLACK + (board[7 - i][j]).toCharArray()[1] + ANSI_RESET);
                    } else {
                        System.out.print(ANSI_WHITE + (board[7 - i][j]).toCharArray()[1] + ANSI_RESET);
                    }
                    System.out.print(ANSI_YELLOW + " | " + ANSI_RESET);
                } else {
                    System.out.print(ANSI_YELLOW + "  | " + ANSI_RESET);
                }

            }

            System.out.print("\n");
            if (i == 8) continue;
            System.out.println(ANSI_YELLOW + "___________________________________" + ANSI_RESET);
        }

    }

    private boolean checkMove(String[] move) {

        int startLetter = move[0].charAt(0) - 65;
        int destLetter = move[2].charAt(0) - 65;

        int startDigit = Integer.parseInt(move[1]) - 1;
        int destDigit = Integer.parseInt(move[3]) - 1;

        if (startDigit < 0 || startDigit > 7 || destDigit < 0 || destDigit > 7 || startLetter < 0 || startLetter > 7 || destLetter < 0 || destLetter > 7) {
            return false;
        }

        String figure = board[startDigit][startLetter];
        String endPoint = board[destDigit][destLetter];

        if (figure.equals("")) {
            return false;
        }

        if ((figure.charAt(0) == 'B' && firstPlayerMove) || (figure.charAt(0) == 'W' && !firstPlayerMove)) {
            return false;
        }

        if (!endPoint.equals("") && endPoint.charAt(0) == figure.charAt(0)) {
            return false;
        }

        switch (figure.charAt(1)) {

            case 'P':
                if (figure.charAt(0) == 'W') {
                    if (destLetter - startLetter == -1 || destLetter - startLetter == 1) {
                        if (destDigit - startDigit == 1 && !endPoint.equals("")) {
                            board[startDigit][startLetter] = "";
                            if(destDigit == 7){
                                board[destDigit][destLetter] = "WH";
                            }else {
                                board[destDigit][destLetter] = figure;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    } else if (destLetter == startLetter) {
                        for (int i = 0; i < destDigit - startDigit; i++) {
                            if (!board[startDigit + i + 1][startLetter].equals("")) {
                                return false;
                            }
                        }
                        if (destDigit - startDigit == 2 && startDigit == 1) {
                            board[startDigit][startLetter] = "";
                            board[destDigit][destLetter] = figure;
                            return true;
                        } else if (destDigit - startDigit == 1) {
                            board[startDigit][startLetter] = "";
                            if(destDigit == 7){
                                board[destDigit][destLetter] = "WH";
                            }else {
                                board[destDigit][destLetter] = figure;
                            }
                            return true;
                        }
                    }
                } else {
                    if (destLetter - startLetter == -1 || destLetter - startLetter == 1) {
                        if (destDigit - startDigit == -1 && !endPoint.equals("")) {
                            board[startDigit][startLetter] = "";
                            if(destDigit == 0){
                                board[destDigit][destLetter] = "BH";
                            }else {
                                board[destDigit][destLetter] = figure;
                            }
                            return true;
                        } else {
                            return false;
                        }
                    } else if (destLetter == startLetter) {
                        for (int i = 0; i < Math.abs(destDigit - startDigit); i++) {
                            if (!board[startDigit - i - 1][startLetter].equals("")) {
                                return false;
                            }
                        }
                        if (destDigit - startDigit == -2 && startDigit == 6) {
                            board[startDigit][startLetter] = "";
                            board[destDigit][destLetter] = figure;
                            return true;
                        } else if (destDigit - startDigit == -1) {
                            board[startDigit][startLetter] = "";
                            if(destDigit == 0){
                                board[destDigit][destLetter] = "BH";
                            }else {
                                board[destDigit][destLetter] = figure;
                            }
                            return true;
                        }
                    }
                }
                return false;
            case 'W':
                return checkRook(startDigit, destDigit, startLetter, destLetter, figure);
            case 'S':
                if(destLetter - startLetter == 1 || destLetter - startLetter == -1){
                    if(destDigit - startDigit == 2 || destDigit - startDigit == -2){
                        board[startDigit][startLetter] = "";
                        board[destDigit][destLetter] = figure;
                        return true;
                    }
                }else if(destDigit - startDigit == 1 || destDigit - startDigit == -1){
                    if(destLetter - startLetter == 2 || destLetter - startLetter == -2){
                        board[startDigit][startLetter] = "";
                        board[destDigit][destLetter] = figure;
                        return true;
                    }
                }
                return false;
            case 'G':
                return checkBishop(startDigit, destDigit, startLetter, destLetter, figure);
            case 'H':
                if(!checkBishop(startDigit, destDigit, startLetter, destLetter, figure)){
                    return checkRook(startDigit, destDigit, startLetter, destLetter, figure);
                }else{
                    return true;
                }
            case 'K':
                if(Math.abs(destDigit - startDigit) > 1 || Math.abs(destLetter - startLetter) > 1) return false;
                if(!checkBishop(startDigit, destDigit, startLetter, destLetter, figure)){
                    return checkRook(startDigit, destDigit, startLetter, destLetter, figure);
                }else{
                    return true;
                }
        }

        return false;
    }

    private boolean checkBishop(int startDigit, int destDigit, int startLetter, int destLetter, String figure){
        if(Math.abs(destDigit - startDigit) == Math.abs(destLetter - startLetter)){

            int helpDigit = (destDigit - startDigit) / Math.abs(destDigit - startDigit);
            int helpLetter = (destLetter - startLetter) / Math.abs(destLetter - startLetter);

            for(int i = 0; i < Math.abs(destDigit - startDigit) - 1; i++){
                if(!board[startDigit + i * helpDigit + helpDigit][startLetter + i * helpLetter + helpLetter].equals("")) return false;
            }

            board[startDigit][startLetter] = "";
            board[destDigit][destLetter] = figure;
            return true;
        }

        return false;
    }

    private boolean checkRook(int startDigit, int destDigit, int startLetter, int destLetter, String figure){
        if (startLetter == destLetter) {
            if (destDigit - startDigit > 0) {
                for (int i = 0; i < destDigit - startDigit - 1; i++) {
                    if (!board[startDigit + i + 1][startLetter].equals("")) {
                        return false;
                    }
                }
                board[startDigit][startLetter] = "";
                board[destDigit][destLetter] = figure;
                return true;
            } else if (destDigit - startDigit < 0) {
                for (int i = 0; i < Math.abs(destDigit - startDigit + 1); i++) {
                    if (!board[startDigit - i - 1][startLetter].equals("")) {
                        return false;
                    }
                }
                board[startDigit][startLetter] = "";
                board[destDigit][destLetter] = figure;
                return true;
            }
        } else if (destDigit == startDigit) {
            if (destLetter - startLetter > 0) {
                for (int i = 0; i < destLetter - startLetter - 1; i++) {
                    if (!board[startDigit][startLetter + i + 1].equals("")) {
                        return false;
                    }
                }
                board[startDigit][startLetter] = "";
                board[destDigit][destLetter] = figure;
                return true;
            } else if (destLetter - startLetter < 0) {
                for (int i = 0; i < Math.abs(destLetter - startLetter + 1); i++) {
                    if (!board[startDigit][startLetter - i - 1].equals("")) {
                        return false;
                    }
                }
                board[startDigit][startLetter] = "";
                board[destDigit][destLetter] = figure;
                return true;
            }
        }

        return false;
    }
}
