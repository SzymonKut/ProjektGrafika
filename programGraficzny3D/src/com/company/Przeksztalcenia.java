package com.company;
import com.sun.javafx.geom.Vec3d;
import javafx.geometry.Point3D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Przeksztalcenia {
    public ArrayList<Point3D> listaPunktowWczytane = new ArrayList<Point3D>();
    public ArrayList<Point3D> listaPunktowPrzemnozone = new ArrayList<Point3D>();
    public ArrayList<Point3D> listaPunktowrzutowane = new ArrayList<Point3D>();
    double macierzPrzeksztalcen[][];
    double macierzT[][];
    double macierzObroruX[][];
    double macierzObroruY[][];
    double macierzObroruZ[][];
    double macierzWynikowa[][];
    Point3D obserwator;
    Point3D punktNaKtoryPatrzy;
    int[][] sciany = new int[6][4];
    int[][]tablicaWierzcholkow=new int [3][3];
    double d;


    public double[][] wymnozTablice(double[][] tab1, double[][] tab2) {

        double[][] macierzPomnozona = new double[tab1.length][tab2[0].length];
        if (tab1[0].length == tab2.length) {
            for (int i = 0; i < tab1.length; i++) {//ilosc wierszy tab1
                for (int j = 0; j < tab2[0].length; j++) { //ilosc kolumn tab2
                    double temp = 0;
                    for (int w = 0; w < tab2.length; w++) { //ilosc wierszy tab2
                        temp += tab1[i][w] * tab2[w][j];
                    }
                    macierzPomnozona[i][j] = temp;
                }
            }
        }
        else{
            throw new RuntimeException("Podane tablice mają niewłasciwe wymiary");
        }
        return macierzPomnozona;
    }

    public double[][] dajMacierzZero(int wiersze, int kolumny) {
        double macierz[][] = new double[wiersze][kolumny];
        for (int i = 0; i < macierz.length; i++)
            for (int j = 0; j < macierz[0].length; j++) {
                macierz[i][j] = 0;
            }
        return macierz;
    }

    public double[][] dajMacierzJednostkowa(int wiersze, int kolumny) {
        double macierz[][] = dajMacierzZero(wiersze, kolumny);
        for (int i = 0; i < macierz.length; i++)
            for (int j = 0; j < macierz[0].length; j++)
                if (i == j)
                    macierz[i][j] = 1;
        return macierz;
    }

    public double[][] dajmacierzObroruX(int wiersze, int kolumny) {

        double macierz[][] = dajMacierzJednostkowa(wiersze, kolumny);
        double s = Math.sqrt(Math.pow(obserwator.getX(), 2) + Math.pow(obserwator.getY(), 2));

        macierz[0][0] = obserwator.getX() * (1.0 / s);
        macierz[0][1] = -obserwator.getY() * (1.0 / s);
        macierz[1][0] = obserwator.getY() * (1.0 / s);
        macierz[1][1] = obserwator.getX() * (1.0 / s);
        macierz[2][2] = s * (1.0 / s);
        macierz[3][3] = s * (1.0 / s);

        return macierz;
    }

    public double[][] dajmacierzObroruY(int wiersze, int kolumny) {

        double macierz[][] = dajMacierzJednostkowa(wiersze, kolumny);
        double s = Math.sqrt(Math.pow(obserwator.getX(), 2) + Math.pow(obserwator.getY(), 2));
        double t = Math.sqrt(Math.pow(s, 2) + Math.pow(obserwator.getZ(), 2));

        macierz[0][0] =-obserwator.getZ() * (1.0 / t);
        macierz[0][2] =(-s) * (1.0 / t);
        macierz[1][1] = t * (1.0 / t);
        macierz[2][0] = s * (1.0 / t);
        macierz[2][2] = (-obserwator.getZ()) * (1.0 / t);
        macierz[3][3] = t * (1.0 / t);

        return macierz;
    }

    public double[][] dajmacierzObroruZ(int wiersze, int kolumny) {

        double macierz[][] = dajMacierzJednostkowa(wiersze, kolumny);
        double s = Math.sqrt(Math.pow(obserwator.getX(), 2) + Math.pow(obserwator.getY(), 2));
        double t = Math.sqrt(Math.pow(s, 2) + Math.pow(obserwator.getZ(), 2));
        double u = Math.sqrt(Math.pow((obserwator.getY()), 2) * Math.pow((obserwator.getZ()), 2)
                + Math.pow(t, 2) * Math.pow((obserwator.getX()), 2));
        //System.out.println(s+" "+t+" "+u);
        macierz[0][0] = t * obserwator.getX() * (1.0 / u);
        macierz[0][1] = ((-obserwator.getY()) * obserwator.getZ() * (1.0 / u));
        macierz[1][0] = obserwator.getY() * obserwator.getZ() * (1.0 / u);
        macierz[1][1] = t * obserwator.getX() * (1.0 / u);
        macierz[2][2] = u * (1.0 / u);
        macierz[3][3] = u * (1.0 / u);

        return macierz;
    }

    public double[][] dajMacierzT(int wiersze, int kolumny) {
        double macierz[][] = dajMacierzJednostkowa(wiersze, kolumny);
        macierz[3][0] = -punktNaKtoryPatrzy.getX();
        macierz[3][1] = -punktNaKtoryPatrzy.getY();
        macierz[3][2] = -punktNaKtoryPatrzy.getZ();

        return macierz;
    }

    public double[][] wymnozMacierze(double macierzT[][], double macierzObroruX[][], double macierzObroruY[][], double macierzObroruZ[][]) {

        double macierzWynikowa[][] = wymnozTablice(macierzT, macierzObroruX);
        macierzWynikowa = wymnozTablice(macierzWynikowa, macierzObroruY);
        macierzWynikowa = wymnozTablice(macierzWynikowa, macierzObroruZ);

        return macierzWynikowa;
    }
    public void dajMacierzWynikowa(){
        macierzPrzeksztalcen = dajMacierzJednostkowa(4, 4);
        if(obserwator.getX()!=0||obserwator.getY()!=0){
            macierzT = dajMacierzT(4, 4);
            macierzObroruX = dajmacierzObroruX(4, 4);
            macierzObroruY = dajmacierzObroruY(4, 4);
            macierzObroruZ = dajmacierzObroruZ(4, 4);
            macierzWynikowa = wymnozMacierze(macierzT, macierzObroruX, macierzObroruY, macierzObroruZ);
        }
        else
            macierzWynikowa = dajMacierzJednostkowa(4,4);
    }

    public void wyswietlListe() {

        System.out.println(listaPunktowrzutowane.toString());
    }

    public double[][] dajMacierzPunkt(int wiersze, int kolumny, int i) {

        double macierz[][] = new double[wiersze][kolumny];
        macierz[0][0] = listaPunktowWczytane.get(i).getX();
        macierz[0][1] = listaPunktowWczytane.get(i).getY();
        macierz[0][2] = listaPunktowWczytane.get(i).getZ();
        macierz[0][3] = 1;

        return macierz;
    }

   /* public void wyswietl() {
        for (int i = 0; i < macierzWynikowa.length; i++) {
            for (int j = 0; j < macierzWynikowa[0].length; j++)
                System.out.print(macierzWynikowa[i][j] + ", ");
            System.out.println();
        }

    }*/

    public void mnozPunktytranslacji() {
        double tablica[][] = new double[1][4];
        for (int i = 0; i < listaPunktowWczytane.size(); i++) {
            tablica = wymnozTablice(dajMacierzPunkt(1, 4, i), macierzWynikowa);
            Point3D punkt = new Point3D(tablica[0][0], tablica[0][1], tablica[0][2]);
            listaPunktowPrzemnozone.add(punkt);
        }
    }
    public void program() {
        dajMacierzWynikowa();
        mnozPunktytranslacji();
        obliczDlugosc();
        rzutujNaPlaszczyzne();
        wyswietlListe();
    }
    public void obliczDlugosc(){
        d = Math.sqrt(Math.pow(punktNaKtoryPatrzy.getX() - obserwator.getX(), 2) +
                Math.pow(punktNaKtoryPatrzy.getY() - obserwator.getY(), 2) +
                Math.pow(punktNaKtoryPatrzy.getZ() - obserwator.getZ(), 2));
    }
    public void rzutujNaPlaszczyzne(){
        for (int i = 0; i < listaPunktowPrzemnozone.size(); i++) {
            int x = (int) (listaPunktowPrzemnozone.get(i).getX() * (d / (d + listaPunktowPrzemnozone.get(i).getZ())));
            int y = (int) (listaPunktowPrzemnozone.get(i).getY() * (d / (d + listaPunktowPrzemnozone.get(i).getZ())));
            int z = (int) (listaPunktowPrzemnozone.get(i).getZ() * (d / (d + listaPunktowPrzemnozone.get(i).getZ())));
            listaPunktowrzutowane.add(new Point3D(x, y, z));
        }
    }

    public void reset() {
        listaPunktowPrzemnozone.clear();
        listaPunktowrzutowane.clear();
    }
    public void tablicaWierzcholkow(int indeks) {
        for(int i=0; i<3;i++) {
            tablicaWierzcholkow[i][0] = (int) (listaPunktowWczytane.get(sciany[indeks][i]-1).getX());
            tablicaWierzcholkow[i][1] = (int) (listaPunktowWczytane.get(sciany[indeks][i]-1).getY());
            tablicaWierzcholkow[i][2] = (int) (listaPunktowWczytane.get(sciany[indeks][i]-1).getZ());
        }
    }
    public boolean sprawdzCzyWidoczna(){
        int x1 = tablicaWierzcholkow[1][0] - tablicaWierzcholkow[0][0];
        int x2 = tablicaWierzcholkow[1][1] - tablicaWierzcholkow[0][1];
        int x3 = tablicaWierzcholkow[1][2] - tablicaWierzcholkow[0][2];
        int y1 = tablicaWierzcholkow[2][0] - tablicaWierzcholkow[1][0];
        int y2 = tablicaWierzcholkow[2][1] - tablicaWierzcholkow[1][1];
        int y3 = tablicaWierzcholkow[2][2] - tablicaWierzcholkow[1][2];
        int N1 = x2 * y3 - x3 * y2;
        int N2 = x3 * y1 - x1 * y3;
        int N3 = x1 * y2 - x2 * y1;
        int ev1 = (int) obserwator.getX() - tablicaWierzcholkow[0][0];
        int ev2 = (int) obserwator.getY() - tablicaWierzcholkow[0][1];
        int ev3 = (int) obserwator.getZ() - tablicaWierzcholkow[0][2];
        int wynik = N1 * ev1 + N2 * ev2 + N3 * ev3;
        //System.out.println(wynik);

        if (wynik< 0)
            return true;
        else
            return false;
    }
}




