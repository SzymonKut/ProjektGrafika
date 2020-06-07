package com.company;

import javafx.geometry.Point3D;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OknoOperacje extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

    OknoPanel oknoPanel;

    int szerokoscOkna = 500;
    int wysokoscOkna = 700;
    int polozenieOknaX = 10;
    int polozenieOknaY = 10;

    JButton obserwatorBtn;
    JButton punktNaktoryPatrzyBtn;
    JButton rysujDrucianyBtn;
    JButton rysujWidzialneBtn;
    JButton wczytajBryle;
    JButton reset;

    JTextField punktNaKtoryPatrzyXTF;
    JTextField punktNaKtoryPatrzyYTF;
    JTextField punktNaktoryPatrzyZTF;
    JTextField obserwatorXTF;
    JTextField obserwatorYTF;
    JTextField obserwatorZTF;

    Label punktNaktoryPatrzyL;
    Label obserwatorL;
    Label macierzEtykiet[][] = new Label[4][4];
    Label dlugoscLabel;

    public OknoOperacje() {
        oknoPanel = new OknoPanel();

        setTitle("Operacje");
        setBounds(polozenieOknaX, polozenieOknaY, szerokoscOkna, wysokoscOkna);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        oknoPanel.panelGraficzny.addMouseListener(this);
        oknoPanel.panelGraficzny.addMouseMotionListener(this);

        ustawPrzyciski();
        ustawMacierzEtykiet();
        ustawEtykiety();
        ustawPolaTekstowe();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == obserwatorBtn) {
            ustawObserwatora();
        }
        if (e.getSource() == punktNaktoryPatrzyBtn) {
            ustawPunktNaKtoryPatrzy();
        }
        if (e.getSource() == rysujDrucianyBtn) {
            oknoPanel.panelGraficzny.rysujBialeTlo();
            oknoPanel.panelGraficzny.przeksztalcenia.program();
            oknoPanel.panelGraficzny.rysujBryle(oknoPanel.panelGraficzny.przeksztalcenia.listaPunktowrzutowane,true);
            ustawMacierz();
            oknoPanel.panelGraficzny.przeksztalcenia.reset();
        }
        if (e.getSource() == rysujWidzialneBtn) {
            oknoPanel.panelGraficzny.rysujBialeTlo();
            oknoPanel.panelGraficzny.przeksztalcenia.program();
            oknoPanel.panelGraficzny.rysujBryle(oknoPanel.panelGraficzny.przeksztalcenia.listaPunktowrzutowane,false);
            ustawMacierz();
            oknoPanel.panelGraficzny.przeksztalcenia.reset();
        }
        if (e.getSource() == wczytajBryle) {
            oknoPanel.panelGraficzny.wczytajBryle();
            oknoPanel.panelGraficzny.rysujBryle(oknoPanel.panelGraficzny.przeksztalcenia.listaPunktowWczytane,true);
        }
        if (e.getSource() == reset) {
            oknoPanel.panelGraficzny.rysujBialeTlo();
            oknoPanel.panelGraficzny.przeksztalcenia.reset();
            ustawMacierzEtykiet();
            ustawEtykiety();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public void ustawMacierz() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                macierzEtykiet[i][j].setText(String.valueOf(oknoPanel.panelGraficzny.przeksztalcenia.macierzWynikowa[i][j]));
            }
        }
        dlugoscLabel.setText("d = " + String.valueOf(oknoPanel.panelGraficzny.przeksztalcenia.d));
    }

    public JButton dajPrzycisk(JButton przycisk, int polX, int polY, int szerokosc, int wysokosc, String nazwa) {
        przycisk = new JButton(nazwa);
        przycisk.setBounds(polX, polY, szerokosc, wysokosc);
        przycisk.addActionListener(this);
        add(przycisk);
        return przycisk;
    }

    public JTextField dajPoleTekstowe(JTextField poleTekstowe, int polX, int polY, int szerokosc, int wysokosc) {
        poleTekstowe = new JTextField();
        poleTekstowe.setBounds(polX, polY, szerokosc, wysokosc);
        poleTekstowe.addActionListener(this);
        add(poleTekstowe);
        return poleTekstowe;
    }

    public Label dajEtykiete(Label etykieta, int polX, int polY, int szerokosc, int wysokosc, String napis) {
        etykieta = new Label(napis);
        etykieta.setBounds(polX, polY, szerokosc, wysokosc);
        add(etykieta);
        return etykieta;
    }

    public void ustawMacierzEtykiet() {
        int polX = 50;
        int polY = 400;
        int szerokoscEtykiety = 100;
        int wyskokoscEtykiety = 30;
        for (int i = 0; i < macierzEtykiet.length; i++) {
            polX = 50;
            for (int j = 0; j < macierzEtykiet[0].length; j++) {
                if (i != j)
                    macierzEtykiet[i][j] = dajEtykiete(macierzEtykiet[i][j], polX, polY, szerokoscEtykiety, wyskokoscEtykiety, "0");
                else
                    macierzEtykiet[i][j] = dajEtykiete(macierzEtykiet[i][j], polX, polY, szerokoscEtykiety, wyskokoscEtykiety, "1");
                polX += 100;
            }
            polY += 30;
        }
    }
    public void ustawPrzyciski(){
        obserwatorBtn=dajPrzycisk(obserwatorBtn,225,50,100,25, "Zatwierdz");
        punktNaktoryPatrzyBtn=dajPrzycisk(punktNaktoryPatrzyBtn,225,100,100,25, "zatwierdz");
        rysujDrucianyBtn=dajPrzycisk(rysujDrucianyBtn,50,150,150,25, "Rysuj widok druciany");
        rysujWidzialneBtn=dajPrzycisk(rysujWidzialneBtn,50,200,200,25, "Rysuj sciany widoczne");
        wczytajBryle=dajPrzycisk(wczytajBryle,200,150,100,25, "Wczytaj");
        reset=dajPrzycisk(reset,300,150,100,25, "Reset");
    }

    public void ustawEtykiety(){
        obserwatorL = dajEtykiete(obserwatorL, 50, 50, 100, 25, "Obserwator XYZ: ");
        punktNaktoryPatrzyL = dajEtykiete(punktNaktoryPatrzyL, 50, 100, 100, 25, "Punkt XYZ: ");
        dlugoscLabel = dajEtykiete(dlugoscLabel, 50, 520, 200, 30, "d = 0");
    }

    public void ustawPolaTekstowe(){
        punktNaKtoryPatrzyXTF = dajPoleTekstowe(punktNaKtoryPatrzyXTF, 150, 100, 25, 25);
        punktNaKtoryPatrzyXTF.setText("0");
        punktNaKtoryPatrzyYTF = dajPoleTekstowe(punktNaKtoryPatrzyYTF, 175, 100, 25, 25);
        punktNaKtoryPatrzyYTF.setText("0");
        punktNaktoryPatrzyZTF = dajPoleTekstowe(punktNaktoryPatrzyZTF, 200, 100, 25, 25);
        punktNaktoryPatrzyZTF.setText("0");
        obserwatorXTF = dajPoleTekstowe(obserwatorXTF, 150, 50, 25, 25);
        obserwatorXTF.setText("0");
        obserwatorYTF = dajPoleTekstowe(obserwatorYTF, 175, 50, 25, 25);
        obserwatorYTF.setText("0");
        obserwatorZTF = dajPoleTekstowe(obserwatorZTF, 200, 50, 25, 25);
        obserwatorZTF.setText("0");
    }
    public void ustawPunktNaKtoryPatrzy(){
        String x = punktNaKtoryPatrzyXTF.getText();
        String y = punktNaKtoryPatrzyYTF.getText();
        String z = punktNaktoryPatrzyZTF.getText();
        //System.out.println(x+" "+y+" " +z);
        Point3D punkt = new Point3D(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
        oknoPanel.panelGraficzny.przeksztalcenia.punktNaKtoryPatrzy = punkt;
    }
    public void ustawObserwatora(){
        String x = obserwatorXTF.getText();
        String y = obserwatorYTF.getText();
        String z = obserwatorZTF.getText();
        //System.out.println(x+" "+y+" " +z);
        Point3D punkt = new Point3D(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
        oknoPanel.panelGraficzny.przeksztalcenia.obserwator = punkt;
    }
}


