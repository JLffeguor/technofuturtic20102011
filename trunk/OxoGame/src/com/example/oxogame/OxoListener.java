package com.example.oxogame;

import java.util.Iterator;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;


public class OxoListener implements RefreshListener{

    public void refresh(final Refresher source) {




        if (OxogameApplication.twoOxo.size()==2){
            Oxo oOne = OxogameApplication.twoOxo.get(0);
            Oxo oTwo = OxogameApplication.twoOxo.get(1);


            for(int i=0;i<3;i++)
                for(int j=0;j<3;j++){
                    Cell celloOne = (Cell)oOne.getComponent(i, j);
                    Cell celloTwo = (Cell)oTwo.getComponent(i, j);

                    if(OxogameApplication.terrain[i][j]!=null){
                        Side side = OxogameApplication.terrain[i][j].getSide();
                        if(side!=null){
                            if(side.equals(Side.XSIDE)){
                                celloOne.setStyleName("xSquare");
                                celloTwo.setStyleName("xSquare");
                            }
                            if(side.equals(Side.OSIDE)){
                                celloOne.setStyleName("oSquare");
                                celloTwo.setStyleName("oSquare");
                            }
                        }
                    }
                    //                    if(celloOne.getStyleName()!=celloTwo.getStyleName()){
                    //                        update(oOne,oTwo,celloOne,celloTwo);
                    //                        update(oOne,oTwo,celloTwo,celloOne);
                    //                    }


                }


        }


    }

    //    void update(Oxo oOne, Oxo oTwo, Cell celloOne, Cell celloTwo){
    //        if((celloOne.getStyleName().equals("emtySquareX"))||(celloOne.getStyleName().equals("emtySquareO")))
    //            if((!celloTwo.getStyleName().equals("emtySquareX"))||(!celloOne.getStyleName().equals("emtySquareO"))){
    //                celloOne.setStyleName(celloTwo.getStyleName());
    //            }
    //    }
}
