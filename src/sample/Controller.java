package sample;

import algorithm.Evolution;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class Controller {

    private boolean thread_start=false;
    private Chart2D chart;
    private Evolution evolution=new Evolution();
    @FXML
    private SwingNode mojeswing;
    @FXML
    private TextField wymiar_text;
    @FXML
    private TextField wielkosc_text;
    @FXML
    private TextField punkt0_text;
    @FXML
    private TextField sigma_text;
    @FXML
    private TextField krzyzowanie_text;
    @FXML
    private TextField mutacja_text;

    @FXML
    public void initialize(){
            chart=new Chart2D();
            mojeswing.setContent(chart);
    }


    //blokada na tylko int
    public void wymiar_text_key_pressed(KeyEvent keyEvent) {
        wymiar_text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,7}?"))
                    wymiar_text.setText(s);
            }
        });
    }
    //blokada na tylko int
    public void wielkosc_text_key_pressed(KeyEvent keyEvent) {
        wielkosc_text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,7}?"))
                    wielkosc_text.setText(s);
            }
        });
    }
    // blokada na tylko double razem z ujemnymi
    public void punkt0_text_key_pressed(KeyEvent keyEvent) {
        punkt0_text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("[\\-]{0,1}\\d{0,7}([\\.]\\d{0,4})?"))
                    punkt0_text.setText(s);
            }
        });
    }
    //blokada na tylko double
    public void sigma_text_key_pressed(KeyEvent keyEvent) {
        sigma_text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,7}([\\.]\\d{0,4})?"))
                    sigma_text.setText(s);
            }
        });
    }
    //blokada na tylko double
    public void Krzyzowanie_text_key_pressed(KeyEvent keyEvent) {
        krzyzowanie_text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,7}([\\.]\\d{0,4})?"))
                    krzyzowanie_text.setText(s);
            }
        });
    }
    //blokada na tylko double
    public void Mutacja_text_key_pressed(KeyEvent keyEvent) {
        mutacja_text.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,7}([\\.]\\d{0,4})?"))
                    mutacja_text.setText(s);
            }
        });
    }



    public void Start_click(ActionEvent actionEvent) {
        //for(int i=0;i<200;i++)
        evolution.iteracja();
        chart.rysuj(evolution.getLista_osobnikow(),evolution.getMin_value(),evolution.getMax_value(),evolution.getSigma());
    }

    public void OdNowa_click(ActionEvent actionEvent) {
        try{ evolution.setWymiar(Integer.parseInt(wymiar_text.getText()));}catch (Exception e){ evolution.setWymiar(3); }
        try{evolution.setWielkosc_populacji(Integer.parseInt(wielkosc_text.getText()));}catch (Exception e){evolution.setWielkosc_populacji(20);}
        try { evolution.setMediana_normrand(Double.parseDouble(punkt0_text.getText())); }catch (Exception e){evolution.setMediana_normrand(0);}
        try { evolution.setSzansa_na_skrzyzowanie(Double.parseDouble(krzyzowanie_text.getText())); }catch (Exception e){evolution.setSzansa_na_skrzyzowanie(0.5);}
        try { evolution.setWielkosc_mutacji(Double.parseDouble(mutacja_text.getText())); }catch (Exception e){evolution.setWielkosc_mutacji(0.25);}
        evolution.losuj_populacje_o_wymiaze();
        chart.rysuj(evolution.getLista_osobnikow(),evolution.getMin_value(),evolution.getMax_value(),evolution.getSigma());
    }


    public void AutoStart_click(ActionEvent actionEvent) {
        if(thread_start==false) {
            thread_start = true;
            Thread thread=new Thread(){
                public void run(){
                    while (thread_start==true){
                        evolution.iteracja();
                        chart.rysuj(evolution.getLista_osobnikow(),evolution.getMin_value(),evolution.getMax_value(),evolution.getSigma());
                        try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
                    }
                }
            };
            thread.start();
        }
    }

    public void AutoStop_click(ActionEvent actionEvent) {
        thread_start=false;
    }


}
