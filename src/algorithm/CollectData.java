package algorithm;

import java.io.*;

public class CollectData {
    private int ilosc_iteracji=0;
    private double stala_odleglosc=0.3723297411059;
    File plik;
    private String path= "C:/Users/lasek/Desktop/dane.txt";


    public CollectData() {
         plik = new File(path);
        if(!plik.exists()) {
            try { plik.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void incerment_iteracje(){ilosc_iteracji++;}

    public boolean zapis(){
        try {
            FileWriter fileWriter= new FileWriter(path,true);
            fileWriter.write(""+ilosc_iteracji+"\n");
            fileWriter.close();
            ilosc_iteracji=0;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




}
