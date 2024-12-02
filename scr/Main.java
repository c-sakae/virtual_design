/*
 * VrsPanelを継承し、
 * ワイヤーフレームに関わる抽象メソッドを記述することで、
 * 3DCGアニメーションを表示する。
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main extends VrsPanel{
    private ModelDp modelDp;
    private ModelSlime slime;

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
        this.modelDp = new ModelDp();
        this.modelDp.setPos(0, -3, 10);

        this.slime = new ModelSlime();


        VrsCamera cam = getCam();
        cam.setF(7.0);

        JButton btn01 = new JButton("turn left");
        btn01.addActionListener(
            new BtnListener(this.modelDp, "rotY")
        );
        JButton btn02 = new JButton("turn up");
        btn02.addActionListener(
            new BtnListener(this.modelDp, "rotX")
        );
        JButton btn03 = new JButton("init");
        btn03.addActionListener(
            new BtnListener(this.modelDp, "initMat")
        );
        this.add(btn01);
        this.add(btn02);
        this.add(btn03);
    }

    @Override
    public void update(){
        /*
         * シーンの更新
         */
        this.modelDp.run();
        this.slime.run();
    }

    @Override
    public void draw(Graphics g, VrsCamera cam){
        /*
         * レンダリング
         */
        cam.shotPers(this.slime);
        cam.shotPers(this.modelDp);
    }
}