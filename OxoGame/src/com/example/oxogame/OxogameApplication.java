package com.example.oxogame;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class OxogameApplication extends Application implements Button.ClickListener{
    
    
    Button x = new Button("X");
    Button o = new Button("O");
    public static Square[][] terrain = new Square[3][3];
    final VerticalLayout v = new VerticalLayout();
    public static List<Oxo> twoOxo = new ArrayList<Oxo>();
    public Side side;
    @Override
    public void init() {
        
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                   terrain[i][j]=new Square();
        
        setTheme("myTheme");
        x.addListener(this);
        o.addListener(this);
        
        HorizontalLayout h = new HorizontalLayout();
        h.setSpacing(true);
        h.addComponent(x);
        h.addComponent(o);

        Window mainWindow = new Window("Oxogame Application");
        
        v.addComponent(h);
        
        
        

        
        setMainWindow(mainWindow);

        Refresher ref = new Refresher();
        ref.setRefreshInterval(250);
        ref.addListener(new OxoListener());
        
        VerticalLayout main = new VerticalLayout();
        main.addComponent(ref);
        main.addComponent(v);
        
        
        
        mainWindow.setContent(main);
    }
    
    
   synchronized public final void check(Side side, Button button){
            if(side.getSide()==false){
               getMainWindow().showNotification("Sorry already taken");
            }else if(side.getSide()==true){
               
               Oxo grid = new Oxo(side);
               this.twoOxo.add(grid);
               this.v.removeAllComponents();
               this.v.addComponent(grid);
               side.setSide(false);
               
               if (button.equals(x)){
                   this.side = Side.XSIDE;        
               }else if (button.equals(o)){
                   this.side = Side.OSIDE;                 
               }
               
    
            }
        
    }
      
        @Override
        public void buttonClick(ClickEvent event) {
            if(event.getButton().equals(x)){
                check(Side.XSIDE, event.getButton());
            }else if(event.getButton().equals(o)){
                check(Side.OSIDE, event.getButton());
            }

        }

}
