package com.example.oxogame;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.VerticalLayout;

public class Cell extends VerticalLayout{

    public Cell(Side side){
        setHeight("100px");
        setWidth("100px");
        if(side.equals(Side.XSIDE)){
            setStyleName("emtySquareX");
        }else if(side.equals(Side.OSIDE)){
            setStyleName("emtySquareO");
        }

        addListener(new CellListener());


    }

    public class CellListener implements LayoutClickListener {

        @Override
        public void layoutClick(LayoutClickEvent event) {
            
            final Side side = ((OxogameApplication)getApplication()).side;
            final OxogameApplication app = (OxogameApplication)getApplication();

            GridLayout parent = (GridLayout)Cell.this.getParent();
            GridLayout.Area area = parent.getComponentArea(Cell.this);
            int i = area.getColumn1();
            int j = area.getRow1();
            
            if (waitForOtherPlayer(side,app)==true){
                OxogameApplication.terrain[i][j].setHit(true);
                if (side.equals(Side.XSIDE)){
                    Cell.this.setStyleName("xSquare");
                    OxogameApplication.terrain[i][j].setSide(Side.XSIDE);
                    
                }else{
                    Cell.this.setStyleName("oSquare");
                    OxogameApplication.terrain[i][j].setSide(Side.OSIDE);
                }
                
                Cell.this.removeListener(this);
            }

        }

        //display "Wait for another player to join" until there are two players
        private boolean waitForOtherPlayer(Side side, OxogameApplication app){

            boolean wait = false;

            if (side.equals(Side.XSIDE)){
                if (Side.OSIDE.getSide()==true){
                    app.getMainWindow().showNotification("Wait for another player to join");
                    wait = false;
                }else{
                    wait = true;
                }
            }else{
                if (Side.XSIDE.getSide()==true){
                    app.getMainWindow().showNotification("Wait for another player to join");
                    wait = false;
                }else{
                    wait = true;
                }
            }
            return wait;
        }
        
//        private boolean checkIfThereAreTwoPlayers(Side side, OxogameApplication app){
//            if(app.twoOxo.size()==2){
//                return true;
//            }else{
//                app.getMainWindow().showNotification("Wait for another player to join");
//                return false;
//            }
//        }

    }

}
