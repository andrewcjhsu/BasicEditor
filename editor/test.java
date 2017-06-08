// package editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.LinkedList;
import java.util.List;



public class test extends Application {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private LinkedList<String> storedCharacters = new LinkedList<String>();
    //Character characterTyped;
    String text;
    /** An EventHandler to handle keys that get pressed. */
    public class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;
     

        private static final int STARTING_FONT_SIZE = 20;
        private static final int STARTING_TEXT_POSITION_X = 10;
        private static final int STARTING_TEXT_POSITION_Y = 10;

        /** The Text to display on the screen. */
        private Text displayText = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
        private int fontSize = STARTING_FONT_SIZE;

        private String fontName = "Verdana";

        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            // textCenterX = windowWidth / 2;
            // textCenterY = windowHeight / 2;
            textCenterX=10;
            textCenterY=10;
            // Initialize some empty text and add it to root so that it will be displayed.
            displayText = new Text(textCenterX, textCenterY, "");
            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simpler!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));

            // All new Nodes need to be added to the root in order to be displayed.
            root.getChildren().add(displayText);
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
            	 	// text = getText();
               //      displayText.setText(text);
                    String characterTyped=keyEvent.getCharacter();
                if (text.length() > 0 && text.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
               		Character x=characterTyped.charAt(0);
               		String y=x.toString();
               		storedCharacters.addLast(y);
             		displayText.setText(displayText.getText()+characterTyped);
                    keyEvent.consume();
                }

                // centerText();
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.UP) {
                    fontSize += 5;
                    displayText.setFont(Font.font(fontName, fontSize));
                    centerText();
                } else if (code == KeyCode.DOWN) {
                    fontSize = Math.max(0, fontSize - 5);
                    displayText.setFont(Font.font(fontName, fontSize));
                    centerText();
                } else if(code==KeyCode.BACK_SPACE){
              		storedCharacters.removeLast();
              		displayText.setText(getText());
                
                	// characterTyped=getCharacter();
                }
            	 // else if (code==KeyCode.ENTER){
                // 	textCenterY+=fontSize;
                // }

            }
        }

        private void centerText() {
            // Figure out the size of the current text.
            double textHeight = displayText.getLayoutBounds().getHeight();
            double textWidth = displayText.getLayoutBounds().getWidth();

            // Calculate the position so that the text will be centered on the screen.
            double textTop = textCenterY + textHeight;
            double textLeft = textCenterX + textWidth;

            // Re-position the text.
            displayText.setX(textLeft);
            displayText.setY(textTop);

            // Make sure the text appears in front of any other objects you might add.
            displayText.toFront();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        // To get information about what keys the user is pressing, create an EventHandler.
        // EventHandler subclasses must override the "handle" function, which will be called
        // by javafx.
        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);

        primaryStage.setTitle("Single Letter Display Simple");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public String getText(){
    	String x="";
    	for (int i=0;i<storedCharacters.size();i++){
    		x+=storedCharacters.get(i);
    	}
    	return x;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
