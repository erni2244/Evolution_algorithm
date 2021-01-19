package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static java.lang.StrictMath.*;

public class Evolution {

    private int wielkosc_populacji_poczatkowa=20;
    private double wielkosc_mutacji = 0.15;      
    private double szansa_na_skrzyzowanie=0.5;
    private List<Object_pop> lista_osobnikow;
    private int wymiar=2;       //wymiar jest o jeden mniej bo jedna wartość to ocena więc zamiast (x; y;) jest (x; ocena)
    private double sigma=1;
    private double mediana_normrand=0;
    private double min_value=mediana_normrand-10;
    private double max_value=mediana_normrand+10;
    private CollectData collectData;
    private boolean czy_zapisano=false;
    private boolean wplyw_najlepszego = false;
    //*******setery************
    public void setWielkosc_populacji(int wielkosc_populacji) { this.wielkosc_populacji_poczatkowa = wielkosc_populacji; }
    public void setWymiar(int wymiar) { this.wymiar = wymiar-1;}
    public void setMediana_normrand(double mediana_normrand) { this.mediana_normrand = mediana_normrand; }
    public void setSzansa_na_skrzyzowanie(double szansa_na_skrzyzowanie) { this.szansa_na_skrzyzowanie = szansa_na_skrzyzowanie; }
    public void setWielkosc_mutacji(double wielkosc_mutacji) { this.wielkosc_mutacji = wielkosc_mutacji; }
    public void setSigma(double sigma) { this.sigma = sigma; }
    public void setWplyw_najlepszego(boolean wplyw_najlepszego) { this.wplyw_najlepszego = wplyw_najlepszego; }

    public List<Object_pop> getLista_osobnikow() { return lista_osobnikow; }
    public double getMin_value() { return min_value; }
    public double getMax_value() { return max_value; }
    public double getSigma() { return sigma; }

    public Evolution(){losuj_populacje_o_wymiaze(); collectData=new CollectData();}

    public boolean iteracja(){
        if(lista_osobnikow==null)
            losuj_populacje_o_wymiaze();
        mutacja();
        krzyzowanie_srednia_genu();
        selekcja_ruletkowa();
        collectData.incerment_iteracje();
        if(cross_saddle(0.5))
            return collectData.zapis();
        else
            return false;

    }

    public Object_pop getnajlepszy(){
        Object_pop ob=lista_osobnikow.get(0);
        for (Object_pop object : lista_osobnikow)
            if(object.getOcena()>ob.getOcena())
                ob=object;
        return ob;
    }


    private boolean cross_saddle(double procent){
        int ile_przeszło=0;
        for (Object_pop object_pop : lista_osobnikow)
            if (object_pop.getOcena() > 1.1)
                ile_przeszło++;

        return ile_przeszło > (double) lista_osobnikow.size() * procent;
    }


    private double losoj_nowy(double mediana,double sig){
        return (new Random().nextGaussian()*sig)+mediana;  //normalny
    }

    public void losuj_populacje_o_wymiaze(){
        czy_zapisano=false;
        lista_osobnikow= new ArrayList<>();
        List<Double> lista_punktow;
        Object_pop ob;
        for(int i=0;i<wielkosc_populacji_poczatkowa;i++){
            lista_punktow=new ArrayList<>();
            for(int j=0;j<wymiar;j++){
                lista_punktow.add(losoj_nowy(mediana_normrand,sigma));
            }
            ob=new Object_pop(lista_punktow,sigma);
            lista_osobnikow.add(ob);
        }
    }

    //funkcja mutuje każdy gen każdego osobnika
    private void mutacja(){
        for (Object_pop object_pop : lista_osobnikow) {
            for (int j = 0; j < wymiar; j++)
                object_pop.setPunkt(losoj_nowy(object_pop.getPunkt(j), wielkosc_mutacji), j);
            if(wplyw_najlepszego)
                object_pop.funkcja_oceny(getnajlepszy());
            else
                object_pop.funkcja_oceny(null);
        }
    }

    //funkcja krzyżowania osobników przez średnią odpowiadających genów od rodziców i danie wyniku dziecku potomstwu (czasem jeden osobnik może skrzyżować się kilka razy !!!)
    private void krzyzowanie_srednia_genu(){
        List<Double> list;
        int object1,object2;

        for(int j=0;j<wielkosc_populacji_poczatkowa*szansa_na_skrzyzowanie;j++) {
            object1 = (int) round(random() * (wielkosc_populacji_poczatkowa - 1));
            object2 = (int) round(random() * (wielkosc_populacji_poczatkowa - 1));
            list=new ArrayList<>();
            for (int i = 0; i < wymiar; i++)
                list.add((lista_osobnikow.get(object1).getPunkt(i)+lista_osobnikow.get(object2).getPunkt(i))/2);
            lista_osobnikow.add(new Object_pop(list, sigma));
        }
    }

    // to jest bez powtazania się osobników rodzice przechodza
    private void selekcja_ruletkowa(){
        List<Double> ruletka=new ArrayList<>();
        List<Object_pop> ocaleni=new ArrayList<>();
        double sum;

        for (Object_pop object_pop : lista_osobnikow) {
            if(wplyw_najlepszego)
                object_pop.funkcja_oceny(getnajlepszy());
            else
                object_pop.funkcja_oceny(null);
            ruletka.add(object_pop.getOcena());
            //sum+=lista_osobnikow.get(i).getOcena();
        }

        double actual_sum,los;
        while (ocaleni.size()<wielkosc_populacji_poczatkowa){
            sum=0;
            for (Object_pop object_pop : lista_osobnikow) sum += object_pop.getOcena();

            los=random()*sum;
            actual_sum=0;
            for(int j=0;j<lista_osobnikow.size();j++){
                actual_sum+=ruletka.get(j);
                if(los<=actual_sum) {

                    ocaleni.add(lista_osobnikow.get(j));
                    //sum-=ruletka.get(j);
                    lista_osobnikow.remove(j);
                    ruletka.remove(j);
                    break;
                }
            }
        }
        lista_osobnikow=ocaleni;
    }


}
