import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class BtnListener implements ActionListener {
    private MyWire wf;
    private String em;

    public BtnListener (MyWire wireFrame, String eventMode){
        this.wf = wireFrame;
        switch(eventMode){
            case "TurnBlack":
            case "TurnGreen":
            case "rotY":
            case "rotX":
            case "initMat":
                this.em = eventMode;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
    public void actionPerformed (ActionEvent e){
        switch(this.em){
            case "TurnBlack":
                this.turnBlack();
                break;
            case "TurnGreen":
                this.turnGreen();
                break;
            case "rotY":
                this.rotY();
                break;
            case "rotX":
                this.rotX();
                break;
            case "initMat":
                this.initMat();
                break;
        }
    }
    private void turnBlack(){
        this.wf.setColor(Color.black);
    }
    private void turnGreen(){
        this.wf.setColor(Color.green);
    }
    private void rotY(){
        this.wf.rotY(10);
    }
    private void rotX(){
        this.wf.rotX(10);
    }
    private void initMat(){
        this.wf.initMat();
    }
}