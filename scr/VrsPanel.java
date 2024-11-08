import javax.swing.*;
import java.awt.*;

public abstract class VrsPanel extends JPanel implements Runnable{
	
	private Thread timer;
	private int time;
	private VrsCamera cam;
	private JFrame f;
	public VrsPanel(){
		// 描画用カメラを生成
		cam = new VrsCamera();
		setPreferredSize(new Dimension(800,600));
		setBackground(Color.white);
		f=new JFrame();
			f.add(this);
			f.pack();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);

		timer = new Thread(this);
		timer.start();
		execute();
	}

	public void run(){
		while(true){
			// アニメーションのスピードは1秒間に30回程度
			// sleep は 1000/30 ms
			try{ timer.sleep(1000/30); }
			catch(InterruptedException e){}
		
			time++;
			update();
			repaint();

		}
	}	
	@Override
	public void paint(Graphics g){
		super.paint(g);

		cam.setGraphics(g);
		draw(g,cam);

		g.setColor(Color.black);
		g.drawString(""+getTime(),10,10);
	}

	public void setTime(int t){ time=t; }
	public int getTime(){ return time; }
	public VrsCamera getCam(){
		return cam;
	}

	// 以下オーバーライドされる
	abstract public void draw(Graphics g,VrsCamera cam);
	abstract public void update();
	abstract public void execute();

}
