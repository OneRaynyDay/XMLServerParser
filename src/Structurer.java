
public class Structurer {
   String[][] masterKey;
   public Structurer(String[][] masterInput){
      masterKey = masterInput;
   }
   public String structureXML(String baseRoot, String indivNode){
      String[] CSVKey = masterKey[0];
      String[] temp = masterKey[1];
      String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<" + baseRoot + ">";
      for(int j = 0; j < temp.length/CSVKey.length; j++){
         body += "\n\t<" + indivNode + ">";
         for(int i = 0; i < CSVKey.length; i++){
            body += "\n\t\t<" + CSVKey[i] + ">" + temp[i + j*CSVKey.length] + "</" + CSVKey[i] + ">";
         }
         body += "\n\t</" + indivNode + ">";
      }
      
      body += "\n</" + baseRoot + ">";
      return body;
   }
   public String structureCSV(){
      String[] header = masterKey[0];
      String[] body = masterKey[1];
      String head = "";
      String bod = "";
      for(String element : header){
         head += element + ",";
      }
      //cut off comma
      head = head.substring(0, head.length());
      int counter = 0;
      for(String element : body){
         counter++;
         bod += element + ", ";
         if(counter % header.length == 0){
            //cut off comma
            bod = bod.substring(0, bod.length());
            bod += "\n";
         }
      }
      return head+bod;
   }
   
   //JSON stub
   public void structureJSON(){
   }
}
