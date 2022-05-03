import java.io.*;
import java.net.*;
import java.util.Arrays;

public class ServeurTcp {
    private static int port;

    public static int longueur(String line) {
        return line.length();
    }

    public static String tri(String line) {
        char[] stringtoChar = line.toCharArray();
        Arrays.sort(stringtoChar);
        String SortedString = new String(stringtoChar);
        return new String(stringtoChar);
    }

    public static void main (String [] args) throws Exception {
        boolean boucle = true;
        Reader reader;
        PrintStream sortie=null;
        Socket soc;
        String line;

        if(args.length != 1) {
            System.out.println("usage : java ServeurTcp port");
            System.exit(0);
        }

        Integer port = Integer.parseInt(args[0]);
        ServerSocket s = new ServerSocket (port);
        System.out.println("La socket serveur est créée");
        while (true) {
            boucle = true;
            soc = s.accept();
            System.out.println("Connexion realise a " + soc.toString());
            reader = new InputStreamReader(soc.getInputStream());
            sortie = new PrintStream(soc.getOutputStream());
            BufferedReader keyboard = new BufferedReader (reader);

            while (boucle) {
                line = keyboard.readLine();
                System.out.println("Le client a saisi : " + line);

                if(line.equals("3")) {
                    boucle = false;
                    line = null;
                    soc.close();
                }
                else if (Character.compare(line.charAt(0), '#') != 0 || Character.compare(line.charAt(2), '#') != 0 || Character.compare(line.charAt(3), ' ') != 0){
                    sortie.println("Le protocole de communication n'est pas respecté");
                }
                else {
                    if (Character.compare(line.charAt(1), '1') == 0) {
                        sortie.println(longueur(line.substring(4)));
                    }
                    else if (Character.compare(line.charAt(1), '2') == 0) {
                        sortie.println(tri(line.substring(4)));
                    }
                    else {
                        sortie.println("Ce traitement n'existe pas");
                    }
                }
            }
        }
    }
}