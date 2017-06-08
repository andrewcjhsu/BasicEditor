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

public class NewEditor extends Application {
private final Rectangle textBoundingBox;

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final String MESSAGE_PREFIX ="command";

    public NewEditor(){
        textBoundingBox = new Rectangle(1, 12);
    }
    public class FastLinkedList{
        private Node sentinel;
        private int currentPos=0;
        private Node currentNode;

        public FastLinkedList(){
            sentinel=new Node(null,null,null);
            currentNode=sentinel;
        }
        public void add(char x){
            if(currentPos==0){
                sentinel.next=new Node(x,null,sentinel);
                currentNode=sentinel.next;
                }
            else {
                Node tmp=currentNode.next;
                currentNode.next=new Node(x,tmp,currentNode);
                currentNode=currentNode.next;
                tmp.previous=currentNode;
            }
        }

         public void remove(){
            if (currentPos==0){
                return;
            }
            else{
                Node tmp=currentNode.previous;
                currentNode.next.previous=tmp;
                tmp.next=currentNode.next;
                currentNode=tmp;
            }
        }
    }
     private class Node{
            public Text nodeText;
            public Node next;
            public Node previous; 

        public Node(Text x, Node y, Node z){
                nodeText=x;
                next=y;
                previous=z;      
            }
        }