package com.javarush.games.ticktacktoe;

import com.javarush.engine.cell.*;

public class TicTacToeGame extends Game {
    private int[][] model = new int[3][3];
    private int currentPlayer;
    private boolean isGameStopped;
    public void initialize(){
        setScreenSize(3,3);
        startGame();
        updateView();
    }
    public void startGame(){
        isGameStopped=false;
        for(int i=0; i<model.length;i++){
            for(int j=0; j<model[i].length;j++){
                model[i][j]=0;
            }
        }
        currentPlayer=1;
    }
    public void updateCellView(int x, int y, int value) {
        if (value == 1)
            setCellValueEx(x, y, Color.WHITE, "X", Color.RED);
        else if (value == 2)
            setCellValueEx(x, y, Color.WHITE, "O", Color.BLUE);
        else
            setCellValueEx(x, y, Color.WHITE, " ", Color.WHITE);
    }

    public void updateView(){
        for(int i=0; i<model.length;i++){
            for(int j=0; j<model[i].length;j++){
                updateCellView(i,j,model[i][j]);
            }
        }
    }
    public void onMouseLeftClick(int x, int y){
        if(isGameStopped){
            return;
        }
        if(model[x][y]!=0){
            return;
        }
        setSignAndCheck(x, y);
        currentPlayer=(currentPlayer==1)? 2:1;
        computerTurn();
        currentPlayer=(currentPlayer==1)? 2:1;
    }
    public boolean checkWin(int x, int y, int n){
        boolean win=false;
        for (int i = 0; i < model[x].length; i++) {
            if (model[x][i]!=n){
                break;
            }
            if (i==model[x].length-1){
                win=true;
            }
        }
        for (int i = 0; i < model[y].length; i++) {
            if (model[i][y]!=n){
                break;
            }
            if (i==model.length-1){
                win=true;
            }
        }
        if(x==y){
            for (int i = 0; i < model.length; i++) {
            if (model[i][i]!=n){
                break;
            }
            if (i==model.length-1){
                win=true;
            }
        }
        }
        
        if(x+y==model.length-1){
            for (int i = 0; i < model.length; i++) {
            if (model[i][model.length-1-i]!=n){
                break;
            }
            if (i==model.length-1){
                win=true;
            }
            
        }
        }
        return win;
    }    
    public void setSignAndCheck(int x, int y) {
        model[x][y] = currentPlayer;
        updateView();

        if (checkWin(x, y, currentPlayer)) {
            isGameStopped = true;
            if (currentPlayer == 1)
                showMessageDialog(Color.NONE, "You Win!", Color.GREEN, 75);
            if (currentPlayer == 2)
                showMessageDialog(Color.NONE, "Game Over", Color.RED, 75);
            return;
        }

        if (!hasEmptyCell()) {
            isGameStopped = true;
            showMessageDialog(Color.NONE, " Draw!", Color.BLUE, 75);
            return;
        }
    }
    public boolean hasEmptyCell(){
        boolean has = false;
        for(int i=0;i<model.length;i++){
            for(int j=0;j<model[i].length;j++){
                if(model[i][j]==0){
                    return has=true;
                }
            }
        }
        return has;
    }   
    public void onKeyPress(Key key){
        if (key == Key.SPACE && isGameStopped) { 
            startGame();
            updateView();
        }
        if (key == Key.ESCAPE) { 
            startGame();
            updateView();
        }
        return;
    }
    public void computerTurn(){
        if(model[1][1]==0){
            setSignAndCheck(1,1);
            return;
        }
             for(int i=0;i<model.length;i++){
                 for(int j=0;j<model[i].length;j++){
                       if(checkFutureWin(i,j,currentPlayer)){
                        setSignAndCheck(i,j);
                        return;
                }
            }
          }
            
            for(int i=0;i<model.length;i++){
                 for(int j=0;j<model[i].length;j++){
                       if(checkFutureWin(i,j,3-currentPlayer)){
                        setSignAndCheck(i,j);
                        return;
                }
            }
          }
         
             for(int i=0;i<model.length;i++){
                 for(int j=0;j<model[i].length;j++){
                       if(model[i][j]==0){
                        setSignAndCheck(i,j);
                        return;
                }
            }
          }
        
    }
    public boolean checkFutureWin(int x, int y, int n){
        if(model[x][y]!=0){
            return false;
        }
        int originalValue=model[x][y];
        model[x][y] = n;
        if(checkWin(x,y,n)){
            model[x][y]=originalValue;
            return true;
        }
        model[x][y]=originalValue;
        return false;
    }
}



