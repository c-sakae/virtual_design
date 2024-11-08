import java.awt.*;

public class VrsCamera{
	private Graphics2D g;
	private double f = 1.0;
	private double near = 0.5;

	public void setF(double inF){
		this.f = inF;
	}
	public void shotPers(MyWire m){
		//色の設定
		this.g.setColor(m.getColor());

		//点の描画
		for (int i=0; i<m.vSize(); i++){
			double[] pos = m.getVPos(i);
			if(pos[2]>near){
				double imgX = pos[0] * this.f / pos[2];
				double imgY = pos[1] * this.f / pos[2];
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
        this.g.setColor(m.getColor());
        for (int i=0; i<m.vSize(); i++){
            double[] pos = m.getVPos(i);
            this.fillPoint(pos[0], pos[1]);
        }

        for (int i=0; i<m.eSize(); i++){
            int[] id = m.getEdgeID(i);
            double[] posST = m.getVPos(id[0]);
            double[] posED = m.getVPos(id[1]);
            this.drawLine(posST[0], posST[1], posED[0], posED[1]);
        }

	}

	public int[] img2screen(double x, double y){
		int[] pos = {-1,-1};
		pos[0] = (int)(400+100*x);
		pos[1] = (int)(300-100*y);
		return pos;
	}
	public void setGraphics(Graphics gc){
		g=(Graphics2D)gc;
	}

	// 他はg.drawLineとほぼどうようだが，型はdouble．
	public void drawLine(double sx,double sy, double ex,double ey){
		// (1) スタート地点をスクリーン座標に変換
		int[] st = img2screen(sx,sy);

		// (2) エンド地点をスクリーン座標に変換
		int[] ed = img2screen(ex,ey);

		// (3) stからedへ線を引く
		g.drawLine(st[0],st[1],  ed[0],ed[1]);
	}
	public void fillPoint(double x,double y){
		int r = 2; // 半径

		// (1) x,yを座標変換
		int[] pt = img2screen(x,y);

		// (2) 中心が pt 半径が3になるようにfillOvalを実行
		// ヒント：円を囲む四角の左上の座標は x-r,y-r，横幅は 2r になる
		g.fillOval(pt[0]-r, pt[1]-r, 2*r,2*r);
	}
}


