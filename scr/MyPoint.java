import java.awt.*;

public class MyPoint {
    protected double accX, accY;
    protected double velX, velY;
    protected double posX, posY;
    protected double accZ, velZ, posZ;

    protected Color color;

    public MyPoint(){
        this.setAcc(0.0, 0.0, 0.0);
        this.setVel(0.0, 0.0, 0.0);
        this.setPos(0.0, 0.0, 0.0);
        this.setColor(Color.black);
    }

    public void setVelZ(double vz){
        this.velZ = vz;
    }
    public void setAccZ(double az){
        this.accZ = az;
    }
    public double getZ(){
        return this.posZ;
    }
    public void setZ(double z) {
        this.posZ = z;
    }
    public void setPos(double px, double py, double pz){
        this.setX(px);
        this.setY(py);
        this.setZ(pz);
    }
    public void setVel(double vx, double vy, double vz){
        this.setVelX(vx);
        this.setVelY(vy);
        this.setVelZ(vz);
    }
    public void setAcc(double ax, double ay, double az){
        this.setAccX(ax);
        this.setAccY(ay);
        this.setAccZ(az);
    }
    public double[] getPos(){
        double[] pos = {this.posX, this.posY, this.posZ};
        return pos;
    }
    public double[] getVel(){
        double[] vel = {this.velX, this.velY, this.velZ};
        return vel;
    }
    public double[] getAcc(){
        double[] acc = {this.accX, this.accY, this.accZ};
        return acc;
    }


    public void setVelX(double vx){
        this.velX = vx;
    }
    public void setVelY(double vy){
        this.velY = vy;
    }

    public void setAccX(double ax){
        this.accX = ax;
    }
    public void setAccY(double ay){
        this.accY = ay;
    }

    public void run(){
        this.setVelX(this.velX + this.accX);
        this.setVelY(this.velY + this.accY);
        this.setVelZ(this.velZ + this.accZ);

        this.setX(this.posX + this.velX);
        this.setY(this.posY + this.velY);
        this.setZ(this.posZ + this.velZ);
    }

    public double getX(){
        return this.posX;
    }

    public void setX(double x){
        this.posX = x;
    }

    public void setY(double y){
        this.posY = y;
    }

    public double getY(){
        return this.posY;
    }

    public void setColor(Color c){
        this.color = c;
    }
    public Color getColor(){
        return this.color;
    }
}