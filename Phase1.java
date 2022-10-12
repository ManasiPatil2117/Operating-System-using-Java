import java.io.*;

public class Phase1 {
    static char M[][];
    static char R[] = new char[4];
    static char IR[] = new char[4];
    static int IC[] = new int[2];
    static int c, SI;
    static char buffer[] = new char[41];
    static String s;
    static BufferedReader br;
    static FileWriter outputF;

    static void init() {
        M = new char[100][4];
    }

    static void printMemory() {
        for (int i = 0; i < 100; i++) {
            System.out.print("M[" + i + "][] ");
            for (int j = 0; j < 4; j++) {
                System.out.print(M[i][j]);
            }
            System.out.println();
        }
    }

    private static void startExecution() throws Exception {
        IC[0] = 0;
        IC[1] = 0;
        executeUserProgram();
    }

    private static void executeUserProgram() throws Exception {
        while (IR[0] != 'H') {
            for (int i = 0; i < 4; i++)
                IR[i] = M[IC[0]][i];
            incrementIC();
            if (IR[0] == 'G' && IR[1] == 'D') {
                SI = 1;
            }
            if (IR[0] == 'P' && IR[1] == 'D') {
                SI = 2;
            }
            if (IR[0] == 'H') {
                SI = 3;
            }
            if (IR[0] == 'L' && IR[1] == 'R') {
                String ts = String.valueOf(IR[2]);
                ts += String.valueOf(IR[3]);
                int t1 = Integer.parseInt(ts);
                for (int i = 0; i < 4; i++) {
                    R[i] = M[t1][i];
                }
            }
            if (IR[0] == 'S' && IR[1] == 'R') {
                String ts = String.valueOf(IR[2]);
                ts += String.valueOf(IR[3]);
                int t1 = Integer.parseInt(ts);
                for (int i = 0; i < 4; i++) {
                    M[t1][i] = R[i];
                }
            }
            if (IR[0] == 'C' && IR[1] == 'R') {
                for (int i = 0; i < 4; i++) {
                    if (R[i] == M[IR[3]][IR[4]]) {
                        c = 1;
                    } else
                        c = 0;
                }
            }
            if (IR[0] == 'B' && IR[1] == 'T') {
                if (c == 1) {
                    IC[0] = IR[3];
                    IC[1] = IR[4];
                }
            }
            if (SI == 1) {
                readF();
            }
            if (SI == 2) {
                outputF = new FileWriter("F:\\VIT\\Module6\\Lab\\OS\\cp\\Output.txt", true);
                writeF(outputF);
                outputF.close();

            }
            if (SI == 3) {
                // terminate();
            }
        }
    }

    private static void readF() throws Exception {
        s = br.readLine();
        String ts = String.valueOf(IR[2]);
        ts += String.valueOf(IR[3]);
        int t1 = Integer.parseInt(ts);
        int c = 0;
        for (int i = 0; i < s.length(); i++) {
            M[t1][c] = s.charAt(i);
            c++;
            if (c == 4) {
                c = 0;
                t1++;
            }
        }
    }

    static void incrementIC() {

        IC[0] = IC[0] + 1;

    }

    private static void writeF(FileWriter outputF) throws Exception {
        String ts = String.valueOf(IR[2]);
        ts += String.valueOf(IR[3]);
        int t1 = Integer.parseInt(ts);
        int c = 0;
        while (M[t1][c] != 0) {
            outputF.write(M[t1][c++]);
            if (c == 4) {
                t1++;
                c = 0;
            }
        }
        outputF.write("\n\n");
    }

    public static void main(String[] args) throws Exception {
        FileReader file = new FileReader("F:\\VIT\\Module6\\Lab\\OS\\cp\\Input.txt");
        br = new BufferedReader(file);
        s = br.readLine();

        while (s != null) {

            if (s.startsWith("$AMJ")) {
                init();
                int b = 0;
                s = br.readLine();
                while (!s.startsWith("$DTA")) {
                    for (int i = 0; i < s.length(); i++) {
                        buffer[b++] = s.charAt(i);
                    }
                    s = br.readLine();
                }
                int m = 0;
                int k = 0;
                int c = 0;
                for (int i = 0; i < buffer.length; i++) {
                    if (buffer[i] == 'H')
                        break;
                    M[k][c] = buffer[i];
                    c++;
                    if (c == 4) {
                        c = 0;
                        k++;
                    }
                }
                M[k][c] = 'H';
            }
            if (s.startsWith("$DTA")) {
                startExecution();
            }

            if (s.startsWith("$END")) {
                printMemory();
                return;
            }
            s = br.readLine();
        }
    }

}
