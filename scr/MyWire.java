/*
 * 点群モデル・ワイヤーフレームモデル
 */

import java.util.*;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;


public class MyWire extends MyPoint{
    protected ArrayList<double[]> vertex; //頂点
    protected ArrayList<int[]> edge; //辺
    //同次変換行列（homogeneous transformation matrix） = { {RotMat(3dim) zeroVec(3dim)}, {placeVec(3dim) 1} }
    protected MySqrMat htMat;

    public MyWire(){
        super();
        this.initMat();
        this.vertex = new ArrayList<double[]>();
        this.edge = new ArrayList<int[]>();
    }

    /*
     * this.vertexに係るメソッド
     */

    public int vSize(){
        //頂点数を返す
        return this.vertex.size();
    }
    public void addVPos(double x, double y, double z){
        //頂点を追加する（モデル中心点からの相対位置）。
        double[] p = {x, y, z};
        this.vertex.add(p);
    }
    public double[] getVPos(int i){
        //i番目の頂点の座標を返す（グローバル座標系）。
        double[] p = this.vertex.get(i);
        double[] rp = this.htMat.mulVec(new double[]{p[0], p[1], p[2], 1});
        double[] result = {rp[0]+this.getX(), rp[1]+this.getY(), rp[2]+this.getZ()};
        return result;
    }

    /*
     * this.edgeに係るメソッド
     */

    public int eSize(){
        //辺の数を取得するメソッド
        return this.edge.size();
    }
    public void addEdge(int st, int ed){
        //頂点番号を用いて、辺を追加するメソッド
        int[] id = {st, ed};
        this.edge.add(id);
    }
    public int[] getEdgeID(int i){
        //辺番号を用いて、辺を構成する頂点番号を返すメソッド
        int[] result = edge.get(i);
        return result;
    }

    /*
     * OBJファイルを読み込み、
     * this.vertex, this.edgeに登録する
     */

    public void loadOBJ(String fileName)throws IOException{
        BufferedReader reader = null;
        Path path = Paths.get(fileName); //非チェック例外のみ
        Charset cs = StandardCharsets.UTF_8;

        //ファイルオープン
        try{
            reader = Files.newBufferedReader(path, cs); //throws IOE
        }
        catch(IOException e){
            //java.langはimport不要らしい。
            System.out.println("ファイルオープンに失敗");
        }
        //ファイルリード
        while(true){
            String line;
            try{
                line = reader.readLine(); //throws IOE
            }
            catch(IOException e){
                System.out.println("ファイルリードに失敗");
                break;
            }
            if(line == null)break;
            if(line.substring(0, 2).equals("v ")){
                /*
                this.vertexに登録する
                line:
                    v (double)vx (double)vy (double)vz\n
                */
                String strs[] = line.split("\s");
                this.addVPos(
                    Double.parseDouble(strs[1]), //vx
                    Double.parseDouble(strs[2]), //vy
                    Double.parseDouble(strs[3])  //vz
                );
            }
            else if(line.substring(0, 2).equals("f ")){
                /*
                this.edgeに登録する
                line:
                    f (int)v1/(int)??/(int)?? (int)v2/(int)??/(int)?? ... \n
                頂点番号viは1から始まる点に注意。
                頂点情報を格納する配列の要素番号は当然0から。
                */
                String strs[] = line.split("[\s/]");
                List<Integer> face = new ArrayList<Integer>(); //ジェネリクスというらしい
                //面を形成する点をリストに
                for (int i=1; i<strs.length; i+=3){
                    face.add(Integer.decode(strs[i]));
                }
                //面を形成する辺を登録
                for (int i=0; i<face.size(); i++){
                    int st = i;
                    int ed = (i+1) % face.size();
                    this.addEdge(
                        face.get(st).intValue() - 1,
                        face.get(ed).intValue() - 1
                    );
                }
            }
        }
        //ファイルクローズ
        try{
            reader.close(); //throws IOE
        }
        catch(IOException e){
            System.out.println("ファイルクローズに失敗");
        }
}

    /*
     * this.htMatに係るメソッド
     */

    public void initMat(){
        //同次変換行列を初期化
        this.htMat = new MySqrMat(4);
    }

    public void rotX (double rx){
        //グローバル座標x軸周りにモデルを回転
        double rad = rx * Math.PI / 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MySqrMat Mx = new MySqrMat(4);
        Mx.setMat(
            1.0, 0.0,  0.0, 0.0,
            0.0, cos, -sin, 0.0,
            0.0, sin,  cos, 0.0,
            0.0, 0.0,  0.0, 1.0
        );

        this.htMat.mulMat(Mx);
    }
    public void rotY(double ry){
        double rad = ry * Math.PI / 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MySqrMat My = new MySqrMat(4);
        My.setMat(
             cos, 0.0, sin, 0.0,
             0.0, 1.0, 0.0, 0.0,
            -sin, 0.0, cos, 0.0,
             0.0, 0.0, 0.0, 1.0
        );

        this.htMat.mulMat(My);
    }
    public void rotZ(double rz){
        double rad = rz * Math.PI / 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MySqrMat Mz = new MySqrMat(4);
        Mz.setMat(
            cos, -sin, 0.0, 0.0,
            sin,  cos, 0.0, 0.0,
            0.0,  0.0, 1.0, 0.0,
            0.0,  0.0, 0.0, 1.0
        );

        this.htMat.mulMat(Mz);
    }
    public void setRX(double rx){
        //x軸周りに回転した状態に初期化
        this.htMat = new MySqrMat(4);
        this.rotX(rx);
    }
    public void setRY(double ry){
        this.htMat = new MySqrMat(4);
        this.rotY(ry);
    }
    public void setRZ(double rz){
        this.htMat = new MySqrMat(4);
        this.rotZ(rz);
    }
    public void rotEuler(double rx, double ry, double rz){
        //x軸周り、y軸、z軸の順に回転
        this.rotX(rx);
        this.rotY(ry);
        this.rotZ(rz);
    }
    public void setEuler(double rx, double ry, double rz){
        //x, y, zの順に回転させた状態に初期化
        this.htMat = new MySqrMat(4);
        this.rotEuler(rx, ry, rz);
    }
    public void rotAxisAngle(double nx, double ny, double nz, double ang){
        //単位ベクトルn=(nx, ny, nz)を軸にang°回転
        double rad = ang * Math.PI/ 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MySqrMat R = new MySqrMat(4);
        //ロドリゲスの回転公式
        R.setMat(
            nx*nx*(1-cos)   +cos, nx*ny*(1-cos)-nz*sin, nz*nx*(1-cos)+ny*sin, 0.0,
            nx*ny*(1-cos)+nz*sin, ny*ny*(1-cos)   +cos, ny*nz*(1-cos)-nx*sin, 0.0,
            nz*nx*(1-cos)-ny*sin, ny*nz*(1-cos)+nx*sin, nz*nz*(1-cos)   +cos, 0.0,
                             0.0,                  0.0,                  0.0, 1.0
        );

        this.htMat.mulMat(R);
    }

    public void translate(double dx, double dy, double dz){
        //delta=(dx, dy, dz)だけ平行移動
        MySqrMat Tr = new MySqrMat(4);
        Tr.setMat(
            1.0, 0.0, 0.0,  dx,
            0.0, 1.0, 0.0,  dy,
            0.0, 0.0, 1.0,  dz,
            0.0, 0.0, 0.0, 1.0
        );

        this.htMat.mulMat(Tr);
    }

    public void replace(double x, double y, double z){
        //モデルの原点に戻った後、(x, y, z)だけ平行移動
        //回転や拡大縮小はそのまま
        this.htMat.setData(0, 3, x);
        this.htMat.setData(1, 3, y);
        this.htMat.setData(2, 3, z);
    }

    public void scale(double kx, double ky, double kz){
        //モデルの原点を中心にk=(kx, ky, kz)だけ拡大
        MySqrMat Sc = new MySqrMat(4);
        Sc.setMat(
             kx, 0.0, 0.0, 0.0,
            0.0,  ky, 0.0, 0.0,
            0.0, 0.0,  kz, 0.0,
            0.0, 0.0, 0.0, 1.0
        );

        this.htMat.mulMat(Sc);
    }
    public void scaleHere(double kx, double ky, double kz){
        //this.htMatのplaceを中心にk=(kx, ky, kz)だけ拡大
        MySqrMat Sc = new MySqrMat(4);
        Sc.setMat(
             kx, 0.0, 0.0, this.htMat.getData(0, 3)*(1.0-kx),
            0.0,  ky, 0.0, this.htMat.getData(1, 3)*(1.0-ky),
            0.0, 0.0,  kz, this.htMat.getData(2, 3)*(1.0-kz),
            0.0, 0.0, 0.0,                       1.0
        );

        this.htMat.mulMat(Sc);
    }
}