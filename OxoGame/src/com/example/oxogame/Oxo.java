package com.example.oxogame;

import com.vaadin.ui.GridLayout;

public class Oxo extends GridLayout {

    public Oxo(Side side){
        super(3,3);
        setHeight("300px");
        setWidth("300px");
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                addComponent(new Cell(side), i, j);
    }
   
}
