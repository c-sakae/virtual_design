/*
 * x軸正の方向に歩行するスライム――を構成するパーツ（円）
 * ※スライム：　キャラクターの名前
 * 
 * スライムは剛体ではなく、現状ひとつのワイヤーフレームモデルでは変形を表現できない。
 * 
 * スライムは、y軸に対してほぼ線対称。平面y=kで斬ると断面は円になる。
 * 一辺の長さ1.0の立方体にスライムを収め、それを24等分してできた25の平面でスライムを表現する。
 * 
 * 各パーツの原点（MyPoint.posXなどで定まる）は立方体の底面の中心とする。円の中心と原点が異なることに注意。
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

/*
i番目の円の中心のy座標は、(double)i/24.0
i番目の円の直径は、diameter_6[i]/6.0
    一辺の長さが6.0の立方体にスライムを収めたときの断面の直径：
    diameter_6[25] = {
        2.8, 4.2, 4.8, 5.2, 5.6, 5.9, 
        6.0, 6.0, 6.0, 6.0, 6.0, 6.0,
        5.7, 5.5, 5.0, 4.5, 3.8, 3.1,
        2.3, 1.8, 1.2, 0.8, 0.6, 0.3,
        0.0
    };
iは立方体の底面から上面へ数えている。
*/

import java.awt.Color;

public class ModelCircle extends MyWire{
    private double y_org;//原点からのデフォルトの位置
    private double ang; //単振動のための角度（度数法）
    private double ang_plus = 8.0; //１フレーム当たりの回転角（度数法）

    public ModelCircle(double[] center, double radius, double y_org){
        super();

        this.y_org = y_org;
        this.ang = 0.0;
        this.setPos(center[0], center[1], center[2]);
        this.setVelX(0.1);
        this.setColor(Color.blue);
        
        //頂点の登録
        for (int i=0; i<24; i++){
            double rad = 2.0 * Math.PI * i/24.0;
            this.addVPos(radius * Math.cos(rad),  0.0,  radius * Math.sin(rad));
        }

        //辺の登録
        for (int i=0; i<24; i++){
            this.addEdge(i, (i+1)%24);
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

        this.ang = (this.ang + this.ang_plus) % 360.0;
        double state = Math.abs( Math.sin(Math.PI * ang / 180.0) );
        if (state < 0.25){
            //縮んだ状態（接地）⇔元の状態（接地）
            state = state * 2.0 + 0.5; //[0.5, 1.0)
            this.htMat.setData(1, 3,
                state * y_org
            );
        }
        else if (state < 0.5){
            //元の状態（接地）⇔伸びた状態（接地）
            state = (state - 0.25) * 4.0; //[0.0, 1.0)
            this.htMat.setData(1, 3,
                (1.0 + 0.3 * state) * y_org
            );
            this.htMat.setData(0, 0,
                1.0 - 0.35 * state
            );
            this.htMat.setData(2, 2,
                1.0 - 0.35 * state
            );
        }
        else if (state < 0.75){
            //伸びた状態（接地）⇔伸びた状態（最高点）
            state = (state - 0.5) * 2.8; //[0.0, 0.7)
            this.htMat.setData(1, 3,
                state + 1.3 * y_org
            );
            this.htMat.setData(0, 0,
                0.7 + 0.35 * state
            );
            this.htMat.setData(2, 2,
                0.7 + 0.35 * state
            );
        }
        else{ //state: [0.75, 1.0]
            //伸びた状態（最高点）⇔元の状態（最高点）
            state = (state - 0.75) * 4.0; //[0.0, 1.0]
            this.htMat.setData(1, 3,
                (-0.3*state + 1.3) * y_org + 0.3 * state + 0.7
            );
        }
    }

}