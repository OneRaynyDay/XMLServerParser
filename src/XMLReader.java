import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLReader 
{
   public Document ReadXML(String sfile) throws ParserConfigurationException, SAXException, IOException
   {
      File file = new File(sfile);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      return doc; 
   }
   
   public Document loadXMLFromString(String xml) throws Exception
   {
       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       DocumentBuilder builder = factory.newDocumentBuilder();
       InputSource is = new InputSource(new StringReader(xml));
       Document doc = builder.parse(is);
       return doc;
   }
   
   public NodeList GetNodes(Document opendoc, String selement)
   {
      opendoc.getDocumentElement().normalize();
      NodeList nodelist = opendoc.getElementsByTagName(selement);
      return nodelist;
   }
   
   public Node GetNode(NodeList nodelist, int index)
   {  
      Node node = null;
      int length = nodelist.getLength();
      if (index > -1 && index < length)
         node = nodelist.item(index);
      return node;
   }
   
   public ArrayList<String> GetNodeStrings(NodeList nodelist, String[] selements)
   {
      ArrayList<String> slist = new ArrayList<String>();
        for(int s=0; s<nodelist.getLength() ; s++)
        {
            Node firstNode = GetNode(nodelist,s);
            if(firstNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element)firstNode;
                for (int x=0; x<selements.length; x++)
                {
                    NodeList nlist = element.getElementsByTagName(selements[x]);
                    Element item = (Element)nlist.item(0);
                    NodeList tlist = item.getChildNodes();
                    slist.add(((Node)tlist.item(0)).getNodeValue().trim());
                }
            }
        }
        return slist;
   }

   public boolean TestXML(String sfile,String selement,String[] sfields) throws ParserConfigurationException, SAXException, IOException
   {
      boolean breturn = false;
      try
      {
         Document doc = ReadXML(sfile);
         NodeList nodelist = GetNodes(doc,selement);
         ArrayList<String> slist = GetNodeStrings(nodelist,sfields);
         for (String s : slist)
            System.out.println(s);
      }
      finally 
      {
         //System.out.println("Error in xml file: " + sfile);
      }
      breturn = true;
      return breturn;
   }
}

