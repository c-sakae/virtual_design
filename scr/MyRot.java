public class MyRot{
    /*
     * ベクトル計算を行うメソッドをもつ。
     * 3*3行列を保持し、行列計算を行うメソッドをもつ。
     * 
     * MyRotクラスにより回転行列を扱えるようになる。
     */
    //単位行列I_3で初期化
    protected double[][] mat = {
        {1.0, 0.0, 0.0},
        {0.0, 1.0, 0.0},
        {0.0, 0.0, 1.0}
    };

    public MyRot(){}

    /*==================
    ベクトル計算のメソッド
    ==================*/
    public static double[] mul(double a, double[] v1){
        //ベクトルの定数倍
        int len = v1.length;
        double[] result = new double[len];

        for (int i=0; i<len; i++){
            result[i] = a * v1[i];
        }
        
        return result;
    }

    public static double[] add(double[] v1, double[] v2){
        //ベクトルの和
        int len = v1.length;
        double[] result = new double[len];

        for (int i=0; i<len; i++){
            result[i] = v1[i] + v2[i];
        }

        return result;
    }

    public static double iProd(double[] v1, double[] v2){
        //ベクトルの内積
        double result = 0.0;

        for (int i=0; i<v1.length; i++){
            result += v1[i] * v2[i];
        }

        return result;
    }

    public static double norm(double[] v1){
        //ベクトルのノルム（絶対値）
        double result = 0.0;

        result = Math.sqrt( MyRot.iProd(v1, v1) );

        return result;
    }

    /*==================
    行列に係るメソッド
    ==================*/
    public void print(){
        //行列をprint
        for (int i=0; i<this.mat.length; i++){
            for (int j=0; j<this.mat[i].length; j++){
                System.out.printf("%.3f ", this.mat[i][j]);
            }
            System.out.printf("%n");
        }
        System.out.printf("%n");
    }

    public void setData(int i, int j, double v){
        //行列の要素に値を設定
        this.mat[i][j] = v;
    }

    public void setMat(double[][] m){
        //二次元配列で行列を初期化
        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                this.setData(i, j, m[i][j]);
            }
        }
    }

    public void setMat(
        double m00, double m01, double m02,
        double m10, double m11, double m12,
        double m20, double m21, double m22
    ){
        //引数９つで行列を初期化
        double[][] m = {
            {m00, m01, m02},
            {m10, m11, m12},
            {m20, m21, m22}
        };
        this.setMat(m);
    }

    public double[] getLine(int ln){
        //行列の第ln行目を取得
        double[] result = this.mat[ln].clone();

        return result;
    }

    public double[] getCol(int cl){
        //行列の第cl列目を取得
        double[] result = new double[3];

        for (int i=0; i<3; i++){
            result[i] = this.mat[i][cl];
        }

        return result;
    }

    public MyRot copy(){
        //行列を複製
        MyRot cp = new MyRot();

        cp.setMat(this.mat);

        return cp;
    }

    public void T(){
        //行列を転置
        double[][] tMat = new double[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                tMat[i][j] = this.mat[j][i];
            }
        }
        this.setMat(tMat);
    }

    public void mulMat(MyRot tg){
        //行列にtgを左からかける
        double[][] result = new double[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                result[i][j] = MyRot.iProd( tg.getLine(i), this.getCol(j) );
            }
        }
        this.setMat(result);
    }

    public double[] mulVec(double[] vec){
        //行列に右から列ベクトルをかける
        //テキストには左からとあるが、無理では？
        double[] result = new double[3];

        for (int i=0; i<3; i++){
            result[i] = MyRot.iProd(this.getLine(i), vec);
        }
        return result;
    }
}