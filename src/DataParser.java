
public class DataParser {
   public final static int XML_FORMAT = 1;
   public final static int CSV_FORMAT = 2;
   //stub for extended class
   public final static int JSON_FORMAT = 3;
   
   private String iString;
   private Destructurer init;
   private String iRoot;
   private String[] iFields;
   private static int initialType = CSV_FORMAT;
   private String fString;
   private Destructurer fin;
   private String fRoot;
   private String[] fFields;
   private static int finalType = XML_FORMAT;
   
   public static void setParseMode(int start, int end){
      initialType = start;
      finalType = end;
   }
   /**
    * This constructor assumes CSV_FORMAT is initial format.
    * @param initial string for CSV
    */
   public DataParser(String initial, String root){
      initialType = CSV_FORMAT;
      iString = initial;
      init = new Destructurer(iString);
      iRoot = root;
      iFields = null;
   }
   /**
    * 
    * @param initial string for XML
    * @param root for selected subtree of XML
    * @param sfields for selected fields wanted from the XML
    */
   public DataParser(String initial, String root, String[] sfields){
      initialType = XML_FORMAT;
      iString = initial;
      init = new Destructurer(iString);
      iRoot = root;
      iFields = sfields;
   }
   
   public String parse(){
      String[][] masterKey = null;
      if(initialType == finalType)
         return iString;
      if(initialType == CSV_FORMAT){
         masterKey = init.destructureCSV();
      }
      else if(initialType == XML_FORMAT){
         try{
            masterKey = init.destructureXML(iRoot, iFields);
         }catch(Exception e){
            System.out.println("Initialized DataParser with no root or fields. XML parsing is unsuccessful.");
         }
      }
      else; //JSON Stub
      
      Structurer ending = new Structurer(masterKey);
      if(finalType == CSV_FORMAT){
         return fString = ending.structureCSV();
      }
      else if(finalType == XML_FORMAT){
         return fString = ending.structureXML("document" , iRoot);
      }
      else; //JSON Stub
      
      return iString;
   }
   
   public String getInitial(){
      return iString;
   }
   
   public String getFinal(){
      return fString;
   }
   
}
