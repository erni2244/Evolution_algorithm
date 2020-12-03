package algorithm;

import java.util.List;
import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.pow;

public class Object_pop {
    private List<Double> punkty;
    private double ocena=0;
    private double r;

    public Object_pop(List<Double> l,double s) {
        punkty=l;
        r=s;
        funkcja_oceny();
    }

    public double getOcena() { return ocena; }
    public double getPunkt(int i){ return punkty.get(i); }
    public void setPunkt(Double x,int i) { this.punkty.set(i,x); }


    public void funkcja_oceny(){
        double sum1=0,sum2=0;
        for (Double aDouble : punkty) {
            sum1 += pow(aDouble, 2);
            sum2 += pow(aDouble - r, 2);
        }
        ocena= (exp(-5*sum1)+2*exp(-5*sum2));
    }

    public void funkcja_oceny(Object_pop naj){
        double sum1=0,sum2=0,sum3=0;
        for (Double aDouble : punkty) {
            sum1 += pow(aDouble, 2);
            sum2 += pow(aDouble - r, 2);
            if (naj != null)
                sum3 += pow(aDouble - naj.getPunkt(0), 2);
        }
        if(naj!=null)
            ocena= (exp(-5*sum1)+2*exp(-5*sum2))   +0.05*exp(-5*sum3);
        else
            funkcja_oceny();
    }

}