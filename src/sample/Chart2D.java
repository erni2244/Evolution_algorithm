package sample;

import algorithm.Object_pop;
import org.math.plot.Plot2DPanel;
import javax.swing.*;
import java.util.List;

import static java.lang.StrictMath.*;
import static java.lang.StrictMath.pow;

public class Chart2D extends JPanel {


    Plot2DPanel panel;
    private static double sigma=1;

    public Chart2D() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        panel=make_function(-3,3);

    }


    public void rysuj(List<Object_pop> lista, double min_x, double max_x, double sig){
        sigma=sig;
        this.remove(panel);
        panel=add_points(make_function(min_x,max_x),lista);
        this.add(panel);
        this.validate();
    }

    private Plot2DPanel add_points(Plot2DPanel plot2DPanel, List<Object_pop> lista){
        double[] x=new double[lista.size()];
        double[] y=new double[lista.size()];
        for(int i=0;i<lista.size();i++){
            //x[i]=0;
            //for(int j=0;j<lista.get(i).sizeof();j++)
                //x[i]+=lista.get(i).getPunkt(j);
            x[i]+=lista.get(i).getPunkt(0);
            lista.get(i).funkcja_oceny();
            y[i]=lista.get(i).getOcena();
        }
        plot2DPanel.addScatterPlot("punkty",x,y);
        return plot2DPanel;
    }

    private Plot2DPanel make_function(double min_x, double max_x){
        double[] x=buduj_wartosci_osi(min_x,max_x);
        Plot2DPanel plot2DPanel=new Plot2DPanel();
        plot2DPanel.addLinePlot("funkcja celu",x,fun(x));
        return  plot2DPanel;
    }

    private double[] buduj_wartosci_osi(double min_x,double max_x){
        if(max_x<min_x){
            double t=max_x;
            max_x=min_x;
            min_x=t;
        }
        double krok=0.1;
        double n=min_x-krok;
        double[] x = new double[(int) (abs(max_x-min_x)/krok)+1];
        for(int i=0;n<=max_x-(krok/2);i++){                                     //odejmuje krok/2 bo przez zaokrąglenia wychodzi poza rozmiar tablicy czasem
            n=n+krok;
            x[i]=n;
        }
        return x;
    }

    private double[] fun(double[] x){
        double[] y=new double[x.length];
        for(int i=0;i<x.length;i++)
            y[i]=exp(-5*(x[i]*x[i]))+2*exp(-5*(pow(x[i]-sigma,2)  ));
        return y;
    }



}
