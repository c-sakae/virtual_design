import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main extends VrsPanel{
    private ModelCube cube;
    private ModelA modelA;
    private ModelSword sword;

    public static void main(String[] argv){
        Main m = new Main();
    }
    public Main(){
        super();
    }

    @Override
    public void update(){
        this.sword.run();
        this.modelA.run();
        this.cube.run();
    }

    @Override
    public void draw(Graphics g, VrsCamera cam){
        cam.shotPers(this.sword);
        cam.shotPers(this.modelA);
        cam.shotPers(this.cube);
    }

    @Override
    public void execute(){
        this.sword = new ModelSword();
        this.modelA = new ModelA();
        this.cube = new ModelCube();
        VrsCamera cam = getCam();
        cam.setF(7.0);
        this.cube.setRX(60);
        this.modelA.setEuler(60, 20, 30);
    }
}