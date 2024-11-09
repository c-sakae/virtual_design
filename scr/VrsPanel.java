import javax.swing.*;
import java.awt.*;

public abstract class VrsPanel extends JPanel implements Runnable{
    /*
     * JPanelを拡張した抽象クラス
     * 抽象メソッドを実装し、コンストラクタを呼ぶと
     * フレームが表示されアニメーションが描画される。
     */
    
    private Thread timer;//Runnableを実装するthisから作成したスレッド
    private int time;//アニメーション表示用カウンタ
    private VrsCamera cam;//描画用クラス
    private JFrame f;//このフレームにthis（パネル）をaddする
    public VrsPanel(){
        // 描画用カメラを初期化
        this.cam = new VrsCamera();
        // スレッド作成後にexecute()するとNullPointerExceptionが出たので移動
        this.execute();
        //パネル(this)の設定
        this.setPreferredSize(new Dimension(800,600));//コンポーネントサイズの設定
        this.setBackground(Color.white);//コンポーネントの背景色設定
        //フレームを作成しパネルをadd
        this.f = new JFrame();
        this.f.add(this);
        this.f.pack();//フレームサイズを自動設定
        this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.f.setVisible(true);
        this.f.setTitle("title");

        // スレッドを作成し実行
        //コンストラクタ実行後に処理は発生しないが、 execute()を上に書けば、別スレッドを作成する必要はないのでは？
        // while(true)を別プロセスにするとちょっと安心できる理由がある？
        this.timer = new Thread(this);//標準ライブラリjava.langのクラス
        this.timer.start();//別プロセスを立ち上げてrun()（無限ループ）を実行するイメージ？
        //ワイヤーフレームクラス等の初期化
        //あえてスレッドの実行後に記述する理由があるのか？
        //this.execute();
    }

    abstract public void execute();
    
    public void run(){
        while(true){
            // アニメーションのスピードは1秒間に30回程度
            // sleep は 1000/30 ms
            try{
                this.timer.sleep(1000/30);//timerスレッドのスリープ
            }
            catch(InterruptedException e){
                //スレッドの中断命令を無視してる？
                //mainスレッドの処理が終わると中断命令が出る説
            }
        
            this.time++;
            //シーンの変更
            this.update();
            //レンダリング
            this.repaint();//コンポーネントのpaint()かupdate()を呼び出す

        }
    }

    //super.update()はpaint()を呼び出すだけ。@Overrideする。
    abstract public void update();

    @Override
    public void paint(Graphics g){
        super.paint(g);

        //カメラでワイヤーフレームを描画
        this.cam.setGraphics(g);
        this.draw(g,this.cam);

        //黒色でカウンタを描画
        g.setColor(Color.black);
        g.drawString(""+getTime(),10,10);
    }

    abstract public void draw(Graphics g,VrsCamera cam);

    public int getTime(){
        return this.time;
    }

    public void setTime(int t){
        this.time = t;
    }
    public VrsCamera getCam(){
        return cam;
    }
}
