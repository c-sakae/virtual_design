import java.awt.*;
public class ModelA extends MyWire{
    @Override
    public void run(){
        super.run();
        if (this.posY<0){
            this.setVelY(-1 * this.velY);
            this.setY(0);
        }
    }

    public ModelA(){
        super();

        this.setPos(0, 3, 15);
        this.setAcc(0.0, -0.1, 0.0);

        this.setColor(Color.red);
        this.setAcc(0.00, -0.01, 0.00);
        
        this.addVPos( 1,  1, -1);
        this.addVPos(-1,  1, -1);
        this.addVPos( 1, -1, -1);
        this.addVPos(-1, -1, -1);

        this.addVPos( 1,  1,  1);
        this.addVPos(-1,  1,  1);
        this.addVPos( 1, -1,  1);
        this.addVPos(-1, -1,  1);

        this.addVPos(0, 1.5, 0);

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

        this.addEdge(0,8);
        this.addEdge(1,8);
        this.addEdge(5,8);
        this.addEdge(4,8);
    }
}