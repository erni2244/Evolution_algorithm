package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.*;

public class Evolution {

    private int wielkosc_populacji_poczatkowa=100;
    private double wielkosc_mutacji = 0.1;      //ile procęt populacji zmutuje podane w %
    private double szansa_na_skrzyzowanie=0.5;  //szansa na to czy dana para sie zkrzyżuje podana w %
    private List<Object_pop> lista_osobnikow;
    private int wymiar=2;       //wymiar jest o jeden mniej bo trzecia wartość to ocena więc zamiast (x; y; z) jest (x; y; ocena)
    private double sigma=1;
    private double mediana_normrand=0;
    private double min_value=mediana_normrand-3;
    private double max_value=mediana_normrand+3;

    //*******setery************
    public void setWielkosc_populacji(int wielkosc_populacji) { this.wielkosc_populacji_poczatkowa = wielkosc_populacji; }
    public void setWymiar(int wymiar) { this.wymiar = wymiar-1;}
    public void setMediana_normrand(double mediana_normrand) { this.mediana_normrand = mediana_normrand; }
    public void setSzansa_na_skrzyzowanie(double szansa_na_skrzyzowanie) { this.szansa_na_skrzyzowanie = szansa_na_skrzyzowanie; }
    public void setWielkosc_mutacji(double wielkosc_mutacji) { this.wielkosc_mutacji = wielkosc_mutacji; }

    public List<Object_pop> getLista_osobnikow() { return lista_osobnikow; }
    public double getMin_value() { return min_value; }
    public double getMax_value() { return max_value; }
    public double getSigma() { return sigma; }

    public Evolution(){losuj_populacje_o_wymiaze();}

    public void iteracja(){
        if(lista_osobnikow==null)
            losuj_populacje_o_wymiaze();
        mutacja();
        krzyzowanie_wybor_genu();
        //krzyzowanie_srednia_genu();
        selekcja_ruletkowa();
    }

    private double losoj_nowy(double mediana){
        return (new Random().nextGaussian()*sigma)+mediana;  //normalny
        //return random()*(abs(min_value-max_value))+min_value;   //jednostajny
    }

    // losuje nowa populacje o zadanej wielkosci i ''wstawia pod lista_osobnikow''
    public void losuj_populacje_o_wymiaze(){
        lista_osobnikow= new ArrayList<>();
        List<Double> lista_punktow;
        Object_pop ob;
        for(int i=0;i<wielkosc_populacji_poczatkowa;i++){
            lista_punktow=new ArrayList<>();
            for(int j=0;j<wymiar;j++){
                lista_punktow.add(losoj_nowy(mediana_normrand));
            }
            ob=new Object_pop(lista_punktow,sigma);
            lista_osobnikow.add(ob);
        }
    }

    //funkcja mutuje części osobników (czasem jeden osobnik może zmutować kilka razy !!!)
    private void mutacja(){
        int a;
        int b;
        for(int i=0;i<lista_osobnikow.size()*wielkosc_mutacji;i++){
            a=(int) round(random()*(lista_osobnikow.size()-1));
            b=(int) round(random()*(wymiar-1));
            lista_osobnikow.get(a).setPunkt(losoj_nowy(lista_osobnikow.get(a).getPunkt(b)),b);
            lista_osobnikow.get(a).funkcja_oceny();
        }
    }

    //*******************************************nie uzywane narazie (opcjonalne)***********************************************************
    //funkcja krzyżowania osobników przez wybór genów od rodziców i danie ich potomstwu (czasem jeden osobnik może skrzyżować się kilka razy !!!)
    private  void krzyzowanie_wybor_genu(){
        List<Double> list=new ArrayList<>();
        int object1,object2;

        for(int j=0;j<wielkosc_populacji_poczatkowa*szansa_na_skrzyzowanie;j++) {
            object1 = (int) round(random() * (wielkosc_populacji_poczatkowa - 1));
            object2 = (int) round(random() * (wielkosc_populacji_poczatkowa - 1));
            list=new ArrayList<>();
            for (int i = 0; i < wymiar; i++)
                if (random() < 0.5)
                    list.add(lista_osobnikow.get(object1).getPunkt(i));
                 else
                    list.add(lista_osobnikow.get(object2).getPunkt(i));

            lista_osobnikow.add(new Object_pop(list, sigma));
        }
    }
    //**************************************************************************************************************************************

    //funkcja krzyżowania osobników przez średnią odpowiadających genów od rodziców i danie wyniku dziecku potomstwu (czasem jeden osobnik może skrzyżować się kilka razy !!!)
    private void krzyzowanie_srednia_genu(){
        List<Double> list=new ArrayList<>();
        int object1,object2;

        for(int j=0;j<wielkosc_populacji_poczatkowa*szansa_na_skrzyzowanie;j++) {
            object1 = (int) round(random() * (wielkosc_populacji_poczatkowa - 1));
            object2 = (int) round(random() * (wielkosc_populacji_poczatkowa - 1));
            for (int i = 0; i < wymiar; i++)
                list.add((lista_osobnikow.get(object1).getPunkt(i)+lista_osobnikow.get(object2).getPunkt(i))/2);
            lista_osobnikow.add(new Object_pop(list, sigma));
        }
    }

    private void selekcja_ruletkowa(){
        List<Double> ruletka=new ArrayList<>();
        List<Object_pop> ocaleni=new ArrayList<>();
        double sum=0;

        for(int i=0;i<lista_osobnikow.size();i++){
            ruletka.add(lista_osobnikow.get(i).getOcena());
            lista_osobnikow.get(i).funkcja_oceny();
            //sum+=lista_osobnikow.get(i).getOcena();
        }

        double actual_sum,los;
        while (ocaleni.size()<wielkosc_populacji_poczatkowa){
            sum=0;
            for(int i=0;i<lista_osobnikow.size();i++)
                sum+=lista_osobnikow.get(i).getOcena();

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
        System.out.println("ok");
        lista_osobnikow=ocaleni;
    }


}
