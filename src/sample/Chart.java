package sample;

import algorithm.Object_pop;
import org.math.plot.Plot3DPanel;
import javax.swing.*;
import java.util.List;
import static java.lang.StrictMath.*;
public class Chart extends JPanel {


    Plot3DPanel panel;
    private static double sigma=1;
    public Chart() {
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        panel=make_function(-3,3);
        this.add(panel);
    }


    public void rysuj(List<Object_pop> lista,double min_x,double max_x,double sig){
        sigma=sig;
        this.remove(panel);
        panel=add_points(make_function(min_x,max_x),lista);
        this.add(panel);
        this.validate();
    }



    private Plot3DPanel add_points(Plot3DPanel plot3DPanel, List<Object_pop> lista){
        double[] x=new double[lista.size()];
        double[] y=new double[lista.size()];
        double[] z=new double[lista.size()];
        for(int i=0;i<lista.size();i++){
            x[i]=lista.get(i).getX_value();
            y[i]=lista.get(i).getY_value();
            z[i]=f1(x[i],y[i]);
        }

        plot3DPanel.addScatterPlot("punkty",x,y,z);
        return plot3DPanel;
    }



    private Plot3DPanel make_function(double min_x,double max_x){
        double[] y=buduj_wartosci_osi(min_x,max_x);
        double[] x=buduj_wartosci_osi(min_x,max_x);
        double[][] z=f1(x,y);
        Plot3DPanel plot3DPanel=new Plot3DPanel();
        plot3DPanel.addGridPlot("myplot",x,y,z);
        return  plot3DPanel;
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

    private static double f1(double x, double y){
        //double z=exp(-5*(y*y+x*x))+2*exp(-5*(pow(x-1,2)  + y*y));
        return exp(-5*(y*y+x*x))+2*exp(-5*(pow(x-sigma,2)  + pow(y-sigma,2)));
    }

    // grid version of the function
    private static double[][] f1(double[] x, double[] y) {
        double[][] z = new double[y.length][x.length];
        for (int i = 0; i < x.length; i++)
            for (int j = 0; j < y.length; j++)
                z[j][i] = f1(x[i], y[j]);
        return z;
    }




}
