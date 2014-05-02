package com.myperfectgame.states;

/**
 * Date: 08/09/13
 * Time: 16:33
 */
import com.myperfectgame.Config;
import com.myperfectgame.states.transitions.CountdownTransitionOut;
import java.awt.Font;


import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

    private int state;
    private int inputDelta;
    private UnicodeFont gameFont;
    private Image image;

    private enum Selection { NEWGAME, RESUMEGAME, HIGHSCORES, QUIT };
    private Selection selected;


    public Menu(int state){
        this.state = state;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
        selected = Selection.NEWGAME;

        image = new Image("resources/back.png");

        inputDelta = 250;
        try {

            Font font = new Font("Verdana", Font.BOLD, 20);
            gameFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());

        } catch (Exception e) {};



    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
        g.drawImage(image, 0, 0);
        g.setBackground(Color.black);
        //g.drawString("Menu", 280, 245);
        //g.setFont(gameFont);                  // not working

        if(Config.newGame == false) {
            drawSelection(Selection.RESUMEGAME, g, "Resume Game", 250f, 220f);
        }
        drawSelection(Selection.NEWGAME, g, "New Game",250,300);
        drawSelection(Selection.HIGHSCORES, g, "High Scores",250,400);
        drawSelection(Selection.QUIT, g, "Quit",250,500);

        /*
         * MENU:
         * Resume Game (if exist)
         * New Game
         * High Scores
         * Quit

         */

    }

    private void drawSelection(Selection s, Graphics g, String name, float x, float y) {
        if(selected == s) {
            g.setColor(Color.red);
            g.drawString(name, x, y);
        }
        else {
            g.setColor(Color.gray);
            g.drawString(name, x, y);
        }
    }

    public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException{
        inputDelta -= delta;
        Input input = container.getInput();

        if (inputDelta < 0) {
            if(input.isKeyDown(Input.KEY_PAUSE) || input.isKeyDown(Input.KEY_P)) {
                inputDelta = 250;
                sbg.enterState(com.myperfectgame.Game.play, new CountdownTransitionOut(), null);
            }
            else if(input.isKeyDown(Input.KEY_UP)) {
                switch(selected) {
                    case RESUMEGAME:
                        break;
                    case NEWGAME:
                        if(Config.newGame == false) {
                            selected = Selection.RESUMEGAME;
                        }
                        break;
                    case HIGHSCORES:
                        selected = Selection.NEWGAME;
                        break;
                    case QUIT:
                        selected = Selection.HIGHSCORES;
                        break;
                }
                inputDelta = 250;

            }
            else if(input.isKeyDown(Input.KEY_DOWN)) {
                switch(selected) {
                    case RESUMEGAME:
                        selected = Selection.NEWGAME;
                        break;
                    case NEWGAME:
                        selected = Selection.HIGHSCORES;
                        break;
                    case HIGHSCORES:
                        selected = Selection.QUIT;
                        break;
                    case QUIT:
                        break;
                }
                inputDelta = 250;
            }
            else if(input.isKeyDown(Input.KEY_ENTER)) {
                Config.newGame = true;
                sbg.enterState(com.myperfectgame.Game.play, new CountdownTransitionOut(), null);
            }
        }


    }

    public int getID(){
        return state;
    }
}