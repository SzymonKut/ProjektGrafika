package com.company;

import javax.swing.*;
import java.awt.event.*;

public class OknoPanel extends JFrame  {

    PanelGraficzny panelGraficzny;
    //JScrollPane scrollPane;
    int szerokoscOkna = 800;
    int wysokoscOkna = 800;
    int polozenieOknaX =510;
    int polozenieOknaY = 10;

    public OknoPanel(){
        panelGraficzny=new PanelGraficzny();
        //scrollPane=new JScrollPane(panelGraficzny,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //add(scrollPane);
        add(panelGraficzny);
        setTitle("Panel do rysowania");
        setBounds(polozenieOknaX, polozenieOknaY, szerokoscOkna, wysokoscOkna);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
    }
}
