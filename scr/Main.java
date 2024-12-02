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
    private ModelCircle[] circles;

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

        this.circles = new ModelCircle[25];
        double[] diameter_6 = { //直径の6倍
            2.8, 4.2, 4.8, 5.2, 5.6, 5.9, 
            6.0, 6.0, 6.0, 6.0, 6.0, 6.0,
            5.7, 5.5, 5.0, 4.5, 3.8, 3.1,
            2.3, 1.8, 1.2, 0.8, 0.6, 0.3,
            0.0
        };
        double[] center = {0.0, 0.0, 15.0};
        for (int i=0; i<25; i++){
            this.circles[i] = new ModelCircle(center, diameter_6[i]/12.0, (double)i/24.0);
        }

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
        for (int i=0; i<25; i++){
            this.circles[i].run();
        }
    }

    @Override
    public void draw(Graphics g, VrsCamera cam){
        /*
         * レンダリング
         */
        for (int i=0; i<25; i++){
            cam.shotPers(this.circles[i]);
        }
        cam.shotPers(this.modelDp);
    }
}