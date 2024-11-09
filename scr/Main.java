/*
 * VrsPanelを継承し、
 * ワイヤーフレームに関わる抽象メソッドを記述することで、
 * 3DCGアニメーションを表示する。
 */

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
    public void execute(){
        /*
         * 初期化
         */
        this.sword = new ModelSword();

        this.modelA = new ModelA();
        this.modelA.setEuler(60, 20, 30);

        this.cube = new ModelCube();
        this.cube.setRX(60);

        VrsCamera cam = getCam();
        cam.setF(7.0);

        JButton btn01 = new JButton("button01");
        btn01.addActionListener(
            new BtnListener(this.sword, "TurnBlack")
        );
        JButton btn02 = new JButton("button02");
        btn02.addActionListener(
            new BtnListener(this.sword, "TurnGreen")
        );
        this.add(btn01);
        this.add(btn02);
    }

    @Override
    public void update(){
        /*
         * シーンの更新
         */
        this.sword.run();
        this.modelA.run();
        this.cube.run();
    }

    @Override
    public void draw(Graphics g, VrsCamera cam){
        /*
         * レンダリング
         */
        cam.shotPers(this.sword);
        cam.shotPers(this.modelA);
        cam.shotPers(this.cube);
    }
}