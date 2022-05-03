import java.io.*; import java.net.*;

public class Serveur_TCP_Java {
	private static int port;
	
	public static void main (String [] args) throws Exception {
  	  boolean boucle = true;
	  Reader reader;
	  PrintStream sortie=null;
	  Socket soc;
	  String line;

	  if(args.length != 1) {		    
		System.out.println("usage : java serveur port");
		System.exit(0); }
	  port = Integer.valueOf(args[0]);

	  ServerSocket s = new ServerSocket (port);
	  System.out.println("La socket serveur est cree");
		
	  while (true)	{
		boucle = true;
		soc = s.accept();
		System.out.println("Connexion realise a " + soc.toString());
			
		reader = new InputStreamReader(soc.getInputStream());
		sortie = new PrintStream(soc.getOutputStream());
		BufferedReader keyboard = new BufferedReader (reader);
			
		while (boucle)	{
			line = keyboard.readLine();
			System.out.println("Vous avez saisi : " + line);
            
 if(line.equals("FIN")) {
                     boucle = false;
               			     line = null;
               		  	     soc.close();  }
                                                 else            {
		  StringBuffer lineReversed = (new StringBuffer(line)).reverse();
   		  sortie.println(lineReversed);     }
			}}}}