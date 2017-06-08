import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.LinkedList;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;



/**
 * A JavaFX application that displays the letter the user has typed most recently in the center of
 * the window. Pressing the up and down arrows causes the font size to increase and decrease,
 * respectively.
 */
// cursor
// word wrapping
// open and save
// arrow keys
// mouse input
// window resizing
// verticall scrolling
// undo and redo
// printing the current position
public class Editor extends Application {
private final Rectangle textBoundingBox;

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final String MESSAGE_PREFIX ="command";
    private final int MARGIN = 10;
    protected int wHeight= WINDOW_HEIGHT;
    private static final int STARTING_FONT_SIZE = 12;
    private static final int STARTING_TEXT_POSITION_X = 0;
    private static final int STARTING_TEXT_POSITION_Y = 0;
    protected int wrappingWidth=1000;
    protected int wrappingHeight=1000;
    private Text displayText = new Text(STARTING_TEXT_POSITION_X, STARTING_TEXT_POSITION_Y, "");
            int textCenterX;
        int textCenterY;
        double cursorX;
        double cursorY;

    public Editor(){
        textBoundingBox = new Rectangle(1, 12);
    }
     private int getDimensionInsideMargin(int outsideDimension) {
        return outsideDimension - 2 * MARGIN;
    }
    // public class FastLinkedList{
    //     private Node sentinel;
    //     private int currentPos=0;
    //     private Node currentNode;

    //     public FastLinkedList(){
    //         sentinel=new Node(null,null,null);
    //         currentNode=sentinel;
    //     }
    //     public void add(char x){
    //         if(currentPos==0){
    //             sentinel.next=new Node(x,null,sentinel);
    //             currentNode=sentinel.next;
    //             }
    //         else {
    //             Node tmp=currentNode.next;
    //             currentNode.next=new Node(x,tmp,currentNode);
    //             currentNode=currentNode.next;
    //             tmp.previous=currentNode;
    //         }
    //     }

    //      public void remove(){
    //         if (currentPos==0){
    //             return;
    //         }
    //         else{
    //             Node tmp=currentNode.previous;
    //             currentNode.next.previous=tmp;
    //             tmp.next=currentNode.next;
    //             currentNode=tmp;
    //         }
    //     }
    // }
    //  private class Node{
    //         public Text nodeText;
    //         public Node next;
    //         public Node previous; 

    //     public Node(Text x, Node y, Node z){
    //             nodeText=x;
    //             next=y;
    //             previous=z;      
    //         }
    //     }

    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {



  
        private LinkedList <Character> storedCharacters=new LinkedList<Character>();
        private String txt;
        private int cursorPos=0;

     

        /** The Text to display on the screen. */
    
        public int fontSize = STARTING_FONT_SIZE;
        public int line=1;

        private String fontName = "Verdana";

        KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            // textCenterX = windowWidth / 2;
            // textCenterY = windowHeight / 2;
            textCenterX=5;
            textCenterY=5;

            cursorX=0;
            cursorY=(line-1)*1.22*fontSize;
            // Initialize some empty text and add it to root so that it will be displayed.
            // displayText = new Text(textCenterX, textCenterY, "");
            // Always set the text origin to be VPos.TOP! Setting the origin to be VPos.TOP means
            // that when the text is assigned a y-position, that position corresponds to the
            // highest position across all letters (for example, the top of a letter like "I", as
            // opposed to the top of a letter like "e"), which makes calculating positions much
            // simpler!
            displayText.setTextOrigin(VPos.TOP);
            displayText.setFont(Font.font(fontName, fontSize));
            displayText.setWrappingWidth(wrappingWidth);

            // All new Nodes need to be added to the root in order to be displayed.
            root.getChildren().add(displayText);
            textBoundingBox.setX(cursorX);
            textBoundingBox.setY(cursorY);
            root.getChildren().add(textBoundingBox);
            makeRectangleColorChange();

        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.isShortcutDown()) {
                if (keyEvent.getCode() == KeyCode.A) { 
                System.out.println("hi");   
                }
                else if (keyEvent.getCode() == KeyCode.S) {   
                 System.out.println("hi");    
                }
                else if (keyEvent.getCode() == KeyCode.Z) {    
                }
                else if (keyEvent.getCode() == KeyCode.Y) {    
                }
                else if (keyEvent.getCode() == KeyCode.EQUALS) {    
                    fontSize+=4;
                    displayText.setFont(Font.font(fontName, fontSize));

                }
                else if (keyEvent.getCode() == KeyCode.MINUS) { 
                    if (fontSize<=4){
                        fontSize=4;
                    }
                    else{
                        fontSize-=4;
                        displayText.setFont(Font.font(fontName, fontSize));
                    }
                }
            }
            else if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    // Ignore control keys, which have non-zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
                    storedCharacters.add(cursorPos,characterTyped.charAt(0));
                    txt=getText(storedCharacters);
                    cursorPos++;
                    displayText.setText(txt);
                    centerTextAndUpdateBoundingBox();
                    keyEvent.consume();
                }

            }else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
               if (code == KeyCode.UP) {
                    fontSize += 5;
                    displayText.setFont(Font.font(fontName, fontSize));
                    // centerText();
                } else if (code == KeyCode.DOWN) {
                    fontSize = Math.max(0, fontSize - 5);
                    displayText.setFont(Font.font(fontName, fontSize));
                    // centerText();
                } else if(code==KeyCode.RIGHT){
                    cursorPos++;
                }else if (code==KeyCode.LEFT){
                    cursorPos--;
                }else if (code== KeyCode.BACK_SPACE){
                    storedCharacters.removeLast();
                    txt=getText(storedCharacters);
                    displayText.setText(txt);
                    cursorPos--;
                    centerTextAndUpdateBoundingBox();
                }
            }
        }
        private void backspaceCursor(){     
            double textHeight = displayText.getLayoutBounds().getHeight();
            double textWidth = displayText.getLayoutBounds().getWidth();
            cursorX=textWidth;
            textBoundingBox.setX(cursorX);
        }

        private String enterCursor(String t){
            textCenterY+=1.22*fontSize;
            t="";
            return t;
        }
        private void centerTextAndUpdateBoundingBox() {
            // Figure out the size of the current text.
            String txt="";
            line=1;
            Text txtTemp = new Text("");
            txtTemp.setTextOrigin(VPos.TOP);
            txtTemp.setFont(Font.font(fontName, fontSize));
            for(int i=0; i<cursorPos; i++){
                txt = txt + storedCharacters.get(i);
                if(storedCharacters.get(i)==13){
                    txt = enterCursor(txt);
                    line++;
                    cursorY=(line-1)*1.22*fontSize;
                    textBoundingBox.setY(cursorY);
                }
                if(storedCharacters.get(i)==8&&displayText.getLayoutBounds().getWidth()<=0){
                    // if (displayText.getLayoutBounds().getHeight()==0){
                    //     cursorY=0;
                    //     textBoundingBox.setY(cursorY);
                    // }
                    // else {
                    //      line--;
                    //     cursorY=(line-1)*1.22*fontSize;
                    //     textBoundingBox.setY(cursorY);
                    // }
                }
            }

            txtTemp.setText(txt);
            double textHeight = displayText.getLayoutBounds().getHeight();
            double textWidth = txtTemp.getLayoutBounds().getWidth();


            if (textWidth<=WINDOW_WIDTH){
                cursorX=textWidth;
                textBoundingBox.setX(cursorX);
            }
            


            // Make sure the text appears in front of any other objects you might add.
            displayText.toFront();
        }
}
    private class RectangleBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors =
                {Color.BLACK, Color.TRANSPARENT};
            RectangleBlinkEventHandler() {
            // Set the color to be the first color in the list.
            changeColor();
        }
          private void changeColor() {
            textBoundingBox.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }

     public void makeRectangleColorChange() {
        // Create a Timeline that will call the "handle" function of RectangleBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The rectangle should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        RectangleBlinkEventHandler cursorChange = new RectangleBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

        EventHandler<KeyEvent> keyEventHandler =
                new KeyEventHandler(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Register the event handler to be called for all KEY_PRESSED and KEY_TYPED events.

        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);

        int imageWidth = 100;
        int imageStartingHeight = 100;
        int imageMaxHeight = WINDOW_HEIGHT;
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(WINDOW_HEIGHT);
        scrollBar.setMin(imageStartingHeight);
        scrollBar.setMax(imageMaxHeight);
        root.getChildren().add(scrollBar);
        double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);
         scrollBar.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldValue,
                    Number newValue) {
                // newValue describes the value of the new position of the scroll bar. The numerical
                // value of the position is based on the position of the scroll bar, and on the min
                // and max we set above. For example, if the scroll bar is exactly in the middle of
                // the scroll area, the position will be:
                //      scroll minimum + (scroll maximum - scroll minimum) / 2
                // Here, we can directly use the value of the scroll bar to set the height of Josh,
                // because of how we set the minimum and maximum above.
                    cursorY=newValue.doubleValue();
                    textCenterY=(int)cursorY;
                    ((KeyEventHandler)keyEventHandler).centerTextAndUpdateBoundingBox();
            }
        });


         scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenWidth,
                    Number newScreenWidth) {
                // Re-compute Allen's width.
                int newSceneWidth = getDimensionInsideMargin(newScreenWidth.intValue());
                displayText.setWrappingWidth(newSceneWidth);
            }
        });

            scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldScreenHeight,
                    Number newScreenHeight) {
                int newSceneHeight = getDimensionInsideMargin(newScreenHeight.intValue());
                wHeight=newSceneHeight;
            }
        });
        primaryStage.setTitle("Text Editor");

        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     public String getText(LinkedList<Character> storedCharacters){
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


