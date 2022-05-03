import java.io.*;
import java.net.*;

public class ClientTcp {
    private static int port;

    public static void menu() {
        System.out.println("### MENU ###");
        System.out.println("1) Longueur");
        System.out.println("2) Trier");
        System.out.println("3) Quitter");
    }

    public static void main (String [] args) throws Exception {
        String adresse, line, line2, response;
        Reader readSoc;
        PrintStream sortie=null;

        if(args.length != 2) {
            System.out.println("usage : java ClientTcp nom_serveur port");
            System.exit(0);
        }

        adresse = args[0];
        port = Integer.parseInt(args[1]);
        Socket socket = new Socket(adresse, port);
        Reader reader = new InputStreamReader(System.in);
        BufferedReader keyboard = new BufferedReader(reader);
        sortie = new PrintStream(socket.getOutputStream());
        readSoc = new InputStreamReader(socket.getInputStream());
        BufferedReader keyboardSoc = new BufferedReader (readSoc);

        menu();
        while (true) {
            System.out.println("--- Choisir le traitement à appliquer : ");
            line = keyboard.readLine();
            // si on a tape "FIN" on quitte le client
            if(line.equals("3")) {
                sortie.println(line);
                break;
            }

            System.out.println("Entrer une ligne de texte : ");
            line2 = keyboard.readLine();
            sortie.println("#" + line + "# " + line2);

            response = keyboardSoc.readLine();
            System.out.println("Recu : " + response);
        }

        // fermeture de la socket
        socket.close();
    }
}