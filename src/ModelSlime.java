/*
 * x軸正の方向に歩行するスライム
 * ※スライム：　キャラクターの名前
 * 
 * 歩行するスライムは５つの状態をとり、各状態の間は線形に補完する。
 * 状態の遷移は単振動で制御する。
 * 状態0.0:     縮んだ状態（接地）
 * 状態0.25:    元の状態（接地）
 * 状態0.5:     伸びた状態（接地）
 * 状態0.75:    伸びた状態（最高点）
 * 状態1.0:     元の状態（最高点）
 * 遷移順：
 *      0.0⇒0.25⇒0.5⇒0.75⇒1.0⇒0.75⇒0.5⇒0.25⇒（戻る）
 */

import java.awt.Color;
import java.io.IOException;

public class ModelSlime extends MyWire{
    private int ang; //単振動のための角度（度数法）
    private int del_ang = 8; //１フレーム当たりの回転角（度数法）

    public ModelSlime(){
        super();

        this.ang = 0;
        this.setPos(0.0, 0.0, 15.0);
        this.setVelX(0.1);
        this.setColor(Color.blue);
        
        try{
            this.loadOBJ("obj/slime.txt");
        }
        catch(IOException e){
            System.out.println("objファイルの読み込みに失敗しました。");
        }
    }

    @Override
    public void run(){
        super.run();
        if(this.posX > 10){
            this.posX = -10;
        }
        else if (this.posX < -10){
            this.posX = 10;
        }

        //this.htMatを操作
        this.ang = (this.ang + this.del_ang) % 360;
        double state = Math.abs( Math.sin(Math.PI * this.ang / 180.0) );
        if (state < 0.25){
            //縮んだ状態（接地）⇔元の状態（接地）
            state = state * 2.0 + 0.5; //[0.5, 1.0)
            this.initMat();
            this.scale(1.0, state, 1.0);
        }
        else if (state < 0.5){
            //元の状態（接地）⇔伸びた状態（接地）
            state = (state - 0.25) * 4.0; //[0.0, 1.0)
            this.initMat();
            this.scale(
                1.0 - 0.3 * state,
                1.0 + 0.3 * state,
                1.0 - 0.3 * state
            );
        }
        else if (state < 0.75){
            //伸びた状態（接地）⇔伸びた状態（最高点）
            state = (state - 0.5) * 2.8; //[0.0, 0.7)
            this.replace(0.0, state, 0.0);
        }
        else{ //state: [0.75, 1.0]
            //伸びた状態（最高点）⇔元の状態（最高点）
            state = (state - 0.75) * 4.0; //[0.0, 1.0]
            this.initMat();
            this.scale(
                0.7 + 0.3 * state,
                1.3 - 0.3 * state,
                0.7 + 0.3 * state
            );
            this.replace(0.0, 0.7 + 0.3 * state, 0.0);
        }
    }

}