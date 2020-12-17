package sample;

import algorithm.Object_pop;
import org.math.plot.Plot2DPanel;
import javax.swing.*;
import java.util.List;

import static java.lang.StrictMath.*;
import static java.lang.StrictMath.pow;

public class Chart2D extends JPanel {


    Plot2DPanel panel;
    private Object_pop wplyw_ojca;
    private double sigma=4;

    public Chart2D() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        panel=make_function(-10,10);

    }

    public void rysuj(List<Object_pop> lista, double min_x, double max_x, double sig,Object_pop wplyw_ojcaw){
        sigma=sig;
        this.remove(panel);
        panel=add_points(make_function(min_x,max_x),lista);
        this.add(panel);
        this.validate();
        wplyw_ojca = wplyw_ojcaw;
    }


    private Plot2DPanel add_points(Plot2DPanel plot2DPanel, List<Object_pop> lista){
        double[] x=new double[lista.size()];
        double[] y=new double[lista.size()];
        for(int i=0;i<lista.size();i++){
            x[i]+=lista.get(i).getPunkt(0);
            lista.get(i).funkcja_oceny(wplyw_ojca);
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
        //System.out.println(""+max_x+" "+min_x+" "+((abs(max_x-min_x)/krok)+1));
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
            if(wplyw_ojca!=null)
                y[i]=exp(-5*(x[i]*x[i]))+2*exp(-5*(pow(x[i]-sigma,2)  ))   +0.05*exp(-5*pow(x[i]-wplyw_ojca.getPunkt(0),2));
            else
                y[i]=exp(-5*(x[i]*x[i]))+2*exp(-5*(pow(x[i]-sigma,2)  ));
        return y;
    }



}
