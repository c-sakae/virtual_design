import java.util.*;

public class MyWire extends MyPoint{
    protected ArrayList<double[]> vertex;
    protected ArrayList<int[]> edge;
    protected MyRot rot;

    public int eSize(){
        return this.edge.size();
    }

    public void addEdge(int st, int ed){
        int[] id = {st, ed};
        this.edge.add(id);
    }
    public int[] getEdgeID(int i){
        int[] result = edge.get(i);
        return result;
    }

    public MyWire(){
        super();
        this.rot = new MyRot();
        this.vertex = new ArrayList<double[]>();
        this.edge = new ArrayList<int[]>();
    }

    public void rotX (double rx){
        double rad = rx * Math.PI / 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MyRot Mx = new MyRot();
        Mx.setMat(
            1, 0, 0,
            0, cos, -sin,
            0, sin, cos
        );

        this.rot.mulMat(Mx);
    }
    public void setRX(double rx){
        this.rot = new MyRot();
        this.rotX(rx);
    }
    
    public void rotY(double ry){
        double rad = ry * Math.PI / 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MyRot My = new MyRot();
        My.setMat(
            cos, 0, sin,
            0, 1, 0,
            -sin, 0, cos
        );

        this.rot.mulMat(My);
    }
    public void rotZ(double rz){
        double rad = rz * Math.PI / 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MyRot Mz = new MyRot();
        Mz.setMat(
            cos, -sin, 0,
            sin, cos, 0,
            0, 0, 1
        );

        this.rot.mulMat(Mz);
    }
    public void setRY(double ry){
        this.rot = new MyRot();
        this.rotY(ry);
    }
    public void setRZ(double rz){
        this.rot = new MyRot();
        this.rotZ(rz);
    }
    public void rotEuler(double rx, double ry, double rz){
        this.rotX(rx);
        this.rotY(ry);
        this.rotZ(rz);
    }
    public void setEuler(double rx, double ry, double rz){
        this.rot = new MyRot();
        this.rotEuler(rx, ry, rz);
    }
    public int vSize(){
        return this.vertex.size();
    }

    public void addVPos(double x, double y, double z){
        double[] p = {x, y, z};
        this.vertex.add(p);
    }

    public double[] getVPos(int i){
        double[] p = this.vertex.get(i);
        double[] rp = this.rot.mulVec(p);
        double[] result = {rp[0]+this.getX(), rp[1]+this.getY(), rp[2]+this.getZ()};
        return result;
    }
}