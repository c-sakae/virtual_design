import java.awt.*;
import java.io.IOException;

public class ModelDp extends MyWire {

    public void run(){
        ;
    }
    public ModelDp(){
        super();
        this.setColor(Color.green);

        try{
            this.loadOBJ("obj/dp.obj");
        }
        catch(IOException e){
            System.out.println("objファイルの読み込みに失敗しました。");
        }
    }
}