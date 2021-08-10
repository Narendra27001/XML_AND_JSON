package com.narendra.xml_and_json;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    TextView resXML,resJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resJSON=findViewById(R.id.resJSON);
        resXML=findViewById(R.id.resXML);
    }
    public void parseXML(View view){
        try {
            InputStream is=getAssets().open("city.xml");
            DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder=dbFactory.newDocumentBuilder();
            Document doc=dBuilder.parse(is);
            Element element=doc.getDocumentElement();
            element.normalize();
            NodeList nodeList=doc.getElementsByTagName("place");
            resXML.setText("XML DATA");
            for(int i=0;i<nodeList.getLength();i++){
                Node node=nodeList.item(i);
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    Element ele=(Element)node;
                    resXML.setText(resXML.getText()+"\nCity_Name : "+getValue("cityName",ele)+"\n");
                    resXML.setText(resXML.getText()+"\nLatitude : "+getValue("latitude",ele)+"\n");
                    resXML.setText(resXML.getText()+"\nLongitude : "+getValue("longitude",ele)+"\n");
                    resXML.setText(resXML.getText()+"\nTemperature : "+getValue("temperature",ele)+"\n");
                    resXML.setText(resXML.getText()+"\nHumidity : "+getValue("humidity",ele)+"\n");
                    resXML.setText(resXML.getText()+"\n-------------------------------------\n");
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
    public static String getValue(String key,Element element){
        NodeList nodeList=element.getElementsByTagName(key).item(0).getChildNodes();
        Node node=nodeList.item(0);
        return node.getNodeValue();
    }
    public void parseJSON(View view){
        String json;
        try {
            InputStream is=getAssets().open("city.json");
            int size=is.available();
            byte[] buffer=new byte[size];
            is.read(buffer);
            is.close();
            json=new String(buffer,"UTF-8");
            JSONArray jsonArray=new JSONArray(json);
            resJSON.setText("JSON data");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject obj=jsonArray.getJSONObject(i);
                resJSON.setText(resJSON.getText()+"\nCity_Name : "+obj.getString("name")+"\n");
                resXML.setText(resXML.getText()+"\nLatitude : "+obj.getString("latitude")+"\n");
                resXML.setText(resXML.getText()+"\nLongitude : "+obj.getString("longitude")+"\n");
                resXML.setText(resXML.getText()+"\nTemperature : "+obj.getString("temperature")+"\n");
                resXML.setText(resXML.getText()+"\nHumidity : "+obj.getString("humidity")+"\n");
                resXML.setText(resXML.getText()+"\n-------------------------------------\n");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}