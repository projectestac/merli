package cat.xtec.ws.proxies.correu.test;

import cat.xtec.ws.proxies.correu.CorreuSender;
import cat.xtec.ws.proxies.correu.types.CorreuException;
import cat.xtec.ws.proxies.correu.types.CorreuResponse;
import cat.xtec.ws.proxies.correu.types.EnviamentResponse;
import java.io.PrintStream;
import java.util.ArrayList;

public class CorreuTester
{
  public static void main(String[] args)
  {
    try
    {
      if (args.length != 4)
      {
        System.out.println("No s'han passat par�metres correctes. Els parametres que espera el tester son els seguents");
        System.out.println("1) Entorn d'execucio: els valors possibles son int, acc o prod");
        System.out.println("2) Id. de l'aplicaci�: ha d'estar donada d'alta al sistema");
        System.out.println("3) Adre�a origen: l'adre�a origen pot ser: apligest@correueducacio.xtec.cat, correus_aplicacions.educacio@xtec.cat o correus_aplicacions.educacio@gencat.cat");
        System.out.println("4) Adre�a dest�: l'adre�a de correu on s'enviar� el test"); return;
      }
      String entorn = args[0];
      if ((!"int".equals(entorn)) && (!"acc".equals(entorn)) && (!"prod".equals(entorn)))
      {
        System.out.println("El valor " + entorn + " del par�metre 'entorn' no es correspon a cap valor v�lid. Ha de ser: " + "int" + ", " + "acc" + " o " + "prod"); return;
      }
      String app = args[1];
      String origen = args[2];
      if ((!"correus_aplicacions.educacio@gencat.cat".equals(origen)) && (!"correus_aplicacions.educacio@xtec.cat".equals(origen)) && (!"apligest@correueducacio.xtec.cat".equals(origen)))
      {
        System.out.println("El valor " + origen + " del par�metre 'correu origen' no es correspon a cap valor v�lid. Pot ser: " + "apligest@correueducacio.xtec.cat" + ", " + "correus_aplicacions.educacio@xtec.cat" + " o " + "correus_aplicacions.educacio@gencat.cat"); return;
      }
      String desti = args[3];
      
      CorreuSender sender = new CorreuSender(app, entorn, true);
      String disponible = sender.consultaDisponibilitat(origen);
      if ("OK".equals(disponible))
      {
        EnviamentResponse resposta = sender.enviaCorreuAmbAdjunt(origen, desti, "Assumpte del missatge. Caracters: ������������������������'", "<html><body>Contingut del missatge en HTML<br/>Caracters: <b>������������������������'</b></body></html>", 1, "Hola, m�n!".getBytes(), "hola m�n.txt", "text/plain");
        if (resposta.isOk())
        {
          System.out.println("El seu correu s'ha enviat correctament a la direcci� de correu " + desti);
        }
        else if (resposta.unsendedMessages().size() > 0)
        {
          CorreuResponse response = (CorreuResponse)resposta.unsendedMessages().get(0);
          System.out.println("Error associat al correu enviat: " + response.getErrorMessage());
        }
        else
        {
          System.out.println("Error general: " + resposta.getMessage());
        }
      }
      else
      {
        System.out.println("Hi ha problemes de disponibilitat del servei: " + disponible);
      }
    }
    catch (CorreuException localCorreuException) {}catch (Exception localException) {}
    System.out.println();
  }
}
