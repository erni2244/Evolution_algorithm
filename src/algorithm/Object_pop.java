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

    public int sizeof(){return punkty.size();}
    public double getOcena() { return ocena; }

    public double getPunkt(int i){ return punkty.get(i); }

    public void setPunkt(Double x,int i) { this.punkty.set(i,x); }

    public   void funkcja_oceny(){
        double sum1=0,sum2=0;
        for(int i=0;i<punkty.size();i++){
            sum1+=pow(punkty.get(i),2);
            sum2+=pow(punkty.get(i)-r,2);
        }
        ocena= (exp(-5*sum1)+2*exp(-5*sum2));
        //ocena = exp(-5*(y*y+x*x))+2*exp(-5*(pow(x-1,2)  + y*y));
    }


}