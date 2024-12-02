import java.awt.*;
/*
 * Graphics2Dを用いてワイヤーフレームを描画するクラス
 * 
 * javax.swing.JFrameはjava.awt.Frameを継承している
 * JFrame.paint()の引数から得たgをフィールドに設定している。
 * Graphics2Dを使えばFrameにComponentをaddできる？
 */

public class VrsCamera{
    private Graphics2D g;
    private double f = 1.0; //ピンホールカメラの焦点距離
    private double near = 0.5; // near clip

    //コンストラクタは省略できる？

    public void setGraphics(Graphics gc){
        this.g = (Graphics2D) gc;
    }
    public void setF(double inF){
        this.f = inF;
    }

    public void shotPers(MyWire m){
        /*
         * ピンホールカメラを模した
         * 透視投影(perspective projeection)によって
         * ワイヤーフレームを描画するメソッド
         * 
         * this.f: ピンホール（グローバル座標の原点）と撮像素子（正規化画像座標系の原点）との距離
         * this.near: near clip。far clipは実装していない。
         */
        //色の設定
        this.g.setColor(m.getColor());

        //頂点の描画
        for (int i=0; i<m.vSize(); i++){
            double[] pos = m.getVPos(i);
            if(pos[2]>near){
                //グローバル座標系を正規化画像座標系に変換
                double imgX = pos[0] * this.f / pos[2];
                double imgY = pos[1] * this.f / pos[2];
                //点を描画
                this.fillPoint(imgX, imgY);
            }
        }

        //辺の描画
        for (int i=0; i<m.eSize(); i++){
            int[] id = m.getEdgeID(i);
            double[] posST = m.getVPos(id[0]);
            double[] posED = m.getVPos(id[1]);

            if (posST[2]>near && posED[2]>near){
                double imgSTX = posST[0] * this.f / posST[2];
                double imgSTY = posST[1] * this.f / posST[2];
                double imgEDX = posED[0] * this.f / posED[2];
                double imgEDY = posED[1] * this.f / posED[2];

                this.drawLine(imgSTX, imgSTY, imgEDX, imgEDY);
            }
        }
    }
    public void shotPara(MyWire m){
        /*
         * 平行投影によって
         * ワイヤーフレームを描画するメソッド
         */

         //色の設定
        this.g.setColor(m.getColor());

        //頂点の描画
        for (int i=0; i<m.vSize(); i++){
            double[] pos = m.getVPos(i);
            this.fillPoint(pos[0], pos[1]);
        }

        //辺の描画
        for (int i=0; i<m.eSize(); i++){
            int[] id = m.getEdgeID(i);
            double[] posST = m.getVPos(id[0]);
            double[] posED = m.getVPos(id[1]);
            this.drawLine(posST[0], posST[1], posED[0], posED[1]);
        }
    }

    public int[] img2screen(double x, double y){
        /*
         * 正規化画像座標系をスクリーン座標系に変換するメソッド
         */
        int[] pos = {-1,-1};
        pos[0] = (int)(400+100*x);
        pos[1] = (int)(300-100*y);
        return pos;
    }

    public void drawLine(double sx,double sy, double ex,double ey){
        /*
         * 線分を描画するメソッド
         * 
         * 引数：　開始点、終止点の座標（正規化画像座標系）
         */
        // スクリーン座標系に変換
        int[] st = img2screen(sx,sy);
        int[] ed = img2screen(ex,ey);

        //線分を描画
        this.g.drawLine(st[0],st[1],  ed[0],ed[1]);
    }
    public void fillPoint(double x,double y){
        /*
         * 点（小さな円）を描画するメソッド
         * 
         * 引数：　中心座標（正規化画像座標系）
         */
        // 半径（スクリーン座標系）
        int r = 2; 
        //中心座標（スクリーン座標系）
        int[] pt = img2screen(x,y);

        //長方形を指定し、内接する楕円を塗りつぶす
        this.g.fillOval(pt[0]-r, pt[1]-r, 2*r,2*r);
    }
}
