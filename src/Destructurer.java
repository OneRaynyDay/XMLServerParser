import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Destructurer {
   private String input;

   public final static String DELIM_CSV = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
   public final static String DELIM_XML = "<([^<>]+)>([^<>]+)</\\1>";

   // DELIM_JSON must be left undefined for extended classes

   public Destructurer(String arg) {
      input = arg;
   }

   /**
    * @param root for the XML document to find, sfields for subtree leaf elements
    *           required
    */
   public String[][] destructureXML(String root, String[] sfields) {
      XMLReader reader = new XMLReader();
      Document doc = null;
      try {
         doc = reader.loadXMLFromString(input);
      } catch (Exception e) {
         e.printStackTrace();
      }
      NodeList nodeList = reader.GetNodes(doc, root);
      String[][] returnArr = new String[][] { sfields,
            (String[]) (reader.GetNodeStrings(nodeList, sfields)).toArray() };
      return returnArr;
   }

   /**
    * @return String[CSVKey][Each Element]
    */
   public String[][] destructureCSV() {
      String product = "";
      Scanner reader = new Scanner(input);
      String[] CSVKey = null;
      String[] elements = new String[0];
      // skip white lines
      while (reader.nextLine() == "")
         ;
      CSVKey = reader.nextLine().split(DELIM_CSV);
      while (reader.hasNext()) {
         String line = reader.nextLine();
         String[] temp = line.split(DELIM_CSV);
         try {
            elements = concatArrays(elements, temp);

         } catch (Exception e) {
            System.out.println("Last elements tried to access : ");
            for (String s : temp)
               System.out.println(s);
            e.printStackTrace();
         }
      }
      String[][] returnArr = new String[][] { CSVKey, elements };
      return returnArr;
   }

   // Stub
   public String[] destructureJSON(String arg) {
      return null;
   }

   private String[] concatArrays(String[] e1, String[] e2) {
      Collection<String> collection = new ArrayList<String>();
      collection.addAll(Arrays.asList(e1));
      collection.addAll(Arrays.asList(e2));

      String[] e3 = collection.toArray(new String[] {});
      return e3;
   }
}
