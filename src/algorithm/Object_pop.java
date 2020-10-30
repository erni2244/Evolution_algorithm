package algorithm;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.exp;
import static java.lang.StrictMath.pow;

public class Object_pop {
    private double x_value;
    private double y_value;
    private List<Double> punkty;
    private double ocena=0;
    private double r;

    public Object_pop(List<Double> l,double s) {

        try { x_value=l.get(0); }catch (Exception e){ x_value=0; }
        try { y_value = l.get(1); }catch (Exception e){ y_value = 0; }
        punkty=l;
        r=s;
        funkcja_oceny();
    }


    public double getX_value() { return x_value; }
    public double getY_value() { return y_value; }
    public double getOcena() { return ocena; }
    public void setX_value(double x_value) { this.x_value = x_value; }
    public void setY_value(double y_value) { this.y_value = y_value; }
    public void setOcena(double ocena) { this.ocena = ocena; }
    public double getPunkt(int i){ return punkty.get(i); }

    public void setPunkt(Double x,int i) { this.punkty.set(i,x); }

    private  void funkcja_oceny(){
        double sum1=0,sum2=0;
        for(int i=0;i<punkty.size();i++){
            sum1+=pow(punkty.get(i),2);
            sum2+=pow(punkty.get(i)-r,2);
        }
        ocena=exp(-5*sum1)+2*exp(-5*sum2);
        //ocena = exp(-5*(y*y+x*x))+2*exp(-5*(pow(x-1,2)  + y*y));
    }


}