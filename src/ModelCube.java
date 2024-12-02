import java.awt.*;
public class ModelCube extends MyWire{
    @Override
    public void run(){
        super.run();
        //壁に到達すると逆側に接続される
        if(this.posX > 10){
            this.posX = -10;
        }
        else if (this.posX < -10){
            this.posX = 10;
        }
        //回転
        this.rotEuler(1, 2, 1);
    }

    public ModelCube(){
        super();

        this.setPos(0, 3, 15);
        this.setVelX(0.1);
        this.setColor(Color.blue);
        
        //頂点の登録
        this.addVPos( 1,  1, -1);
        this.addVPos(-1,  1, -1);
        this.addVPos( 1, -1, -1);
        this.addVPos(-1, -1, -1);

        this.addVPos( 1,  1,  1);
        this.addVPos(-1,  1,  1);
        this.addVPos( 1, -1,  1);
        this.addVPos(-1, -1,  1);

        //辺の登録
        this.addEdge(0,1);
        this.addEdge(1,3);
        this.addEdge(3,2);
        this.addEdge(2,0);

        this.addEdge(4,5);
        this.addEdge(5,7);
        this.addEdge(7,6);
        this.addEdge(6,4);

        this.addEdge(0,4);
        this.addEdge(1,5);
        this.addEdge(3,7);
        this.addEdge(2,6);
    }
}