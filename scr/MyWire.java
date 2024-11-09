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
    protected MyRot rot; //姿勢

    public MyWire(){
        super();
        this.rot = new MyRot();
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
        double[] rp = this.rot.mulVec(p);
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
     * this.rotに係るメソッド
     */

    public void rotX (double rx){
        //グローバル座標x軸周りにモデルを回転
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
    public void setRX(double rx){
        //x軸周りに回転した状態に初期化
        this.rot = new MyRot();
        this.rotX(rx);
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
        //x軸周り、y軸、z軸の順に回転
        this.rotX(rx);
        this.rotY(ry);
        this.rotZ(rz);
    }
    public void setEuler(double rx, double ry, double rz){
        //x, y, zの順に回転させた状態に初期化
        this.rot = new MyRot();
        this.rotEuler(rx, ry, rz);
    }
    public void rotAxisAngle(double nx, double ny, double nz, double ang){
        //単位ベクトルn=(nx, ny, nz)を軸にang°回転
        double rad = ang * Math.PI/ 180.0;
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        MyRot R = new MyRot();
        //ロドリゲスの回転公式
        R.setMat(
            nx*nx*(1-cos)   +cos, nx*ny*(1-cos)-nz*sin, nz*nx*(1-cos)+ny*sin,
            nx*ny*(1-cos)+nz*sin, ny*ny*(1-cos)   +cos, ny*nz*(1-cos)-nx*sin,
            nz*nx*(1-cos)-ny*sin, ny*nz*(1-cos)+nx*sin, nz*nz*(1-cos)   +cos
        );

        this.rot.mulMat(R);
    }
}