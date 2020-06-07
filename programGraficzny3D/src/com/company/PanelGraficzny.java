package com.company;

import com.sun.javafx.geom.Vec3d;
import javafx.geometry.Point3D;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PanelGraficzny extends JPanel{
    int szerokoscPanelu=800;
    int wysokoscPanelu=800;
    Przeksztalcenia przeksztalcenia;
    BufferedImage plotno;
    boolean czySciany=false;
    int srodekPanelu=400;
    public PanelGraficzny()
    {
        przeksztalcenia=new Przeksztalcenia();
        ustawRozmiar(new Dimension(szerokoscPanelu, wysokoscPanelu));
        rysujBialeTlo();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        //wyrysowanie naszego płótna na panelu
        g2d.drawImage(plotno, 0, 0, this);
    }

    public void ustawRozmiar(Dimension r) {
        //przygotowanie płótna
        plotno = new BufferedImage((int) r.getWidth(), (int) r.getHeight(), BufferedImage.TYPE_INT_RGB);
        setPreferredSize(r);
    }
    public void rysujBialeTlo() {

        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, plotno.getWidth(), plotno.getHeight());
        rysujKrzyz();
        repaint();
    }
    public void rysujKrzyz(){
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        g.setColor(Color.blue);
        g.drawLine(srodekPanelu,0,srodekPanelu,800);
        g.drawLine(0,srodekPanelu,800,srodekPanelu);
    }

    public void wczytajBryle() {
        przeksztalcenia.listaPunktowWczytane.clear();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                Scanner scanner = new Scanner(System.in);
                Scanner Plik = new Scanner(new File(chooser.getSelectedFile().getName()));
                int licznik=0;
                while (Plik.hasNext()) {
                    String line = Plik.nextLine();
                    if(line.equals("sciany"))
                        czySciany=true;
                    else if(czySciany==false) {
                        String[] tablicaZnakow = line.split(",");
                        String x = tablicaZnakow[0];
                        String y = tablicaZnakow[1];
                        String z = tablicaZnakow[2];
                        Point3D punkt = new Point3D(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(z));
                        przeksztalcenia.listaPunktowWczytane.add(punkt);
                    }
                    else{
                        String[] tablicaZnakow = line.split(",");
                        String w1 = tablicaZnakow[0];
                        String w2 = tablicaZnakow[1];
                        String w3 = tablicaZnakow[2];
                        String w4 = tablicaZnakow[3];

                        przeksztalcenia.sciany[licznik][0]=Integer.parseInt(w1);
                        przeksztalcenia.sciany[licznik][1]=Integer.parseInt(w2);
                        przeksztalcenia.sciany[licznik][2]=Integer.parseInt(w3);
                        przeksztalcenia.sciany[licznik][3]=Integer.parseInt(w4);
                        licznik++;
                    }
                }
                Plik.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Nie ma takiego pliku ");
            } catch (InputMismatchException ex) {
                System.out.println("Plik zawiera dane nibędące liczbami");
            }
        }
    }

    public void rysujBryle(ArrayList<Point3D> listaPunktowWczytane,boolean wybor) {
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        for (int i = 0; i < 6; i++) {
            if(wybor==false) {
                przeksztalcenia.tablicaWierzcholkow(i);
                if (przeksztalcenia.sprawdzCzyWidoczna()) {
                    rysujSciane(i, listaPunktowWczytane);
                }
            }
            else
                rysujSciane(i, listaPunktowWczytane);
            }
        repaint();
    }
    public void rysujSciane(int i, ArrayList<Point3D> listaPunktowWczytane){
        Graphics2D g = (Graphics2D) plotno.getGraphics();
        for (int j = 0; j < 3; j++) {
            g.setColor(Color.RED);
            g.drawLine((int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][j] - 1).getX() + srodekPanelu,
                    (int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][j] - 1).getY() + srodekPanelu,
                    (int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][j + 1] - 1).getX() + srodekPanelu,
                    (int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][j + 1] - 1).getY() + srodekPanelu);
        }
        g.drawLine((int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][3] - 1).getX() + srodekPanelu,
                (int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][3] - 1).getY() + srodekPanelu,
                (int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][0] - 1).getX() + srodekPanelu,
                (int) listaPunktowWczytane.get(przeksztalcenia.sciany[i][0] - 1).getY() + srodekPanelu);
        repaint();
    }
}