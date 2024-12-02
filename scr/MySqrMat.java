import java.util.Arrays;

public class MySqrMat{
    /*
     * ベクトル計算を行うメソッドをもつ。
     * 正方行列を保持し、行列計算を行うメソッドをもつ。
     * 
     * MySqrMatクラスにより回転行列や同次変換行列を扱えるようになる。
     */
    protected double[][] mat;

    public MySqrMat(){}
    public MySqrMat(int dim){
        //this.matをdim*dim単位行列で初期化
        this.mat = new double[dim][dim];
        for (int i=0; i<dim; i++){
            for (int j=0; j<dim; j++){
                if (i==j){
                    this.mat[i][j] = 1;
                }
                else{
                    this.mat[i][j] = 0;
                }
            }
        }
    }

    /*==================
    ベクトル計算のメソッド
    ==================*/
    public static double[] mul(double a, double[] v1){
        //ベクトルの定数倍
        double[] result = new double[v1.length];

        for (int i=0; i<v1.length; i++){
            result[i] = a * v1[i];
        }
        
        return result;
    }

    public static double[] add(double[] v1, double[] v2){
        //ベクトルの和
        if (v1.length != v2.length){
            //２つの引数の次元が一致しない
            throw new IllegalArgumentException();
        }

        double[] result = new double[v1.length];

        for (int i=0; i<v1.length; i++){
            result[i] = v1[i] + v2[i];
        }

        return result;
    }

    public static double iProd(double[] v1, double[] v2){
        //ベクトルの内積
        if (v1.length != v2.length){
            //２つの引数の次元が一致しない
            throw new IllegalArgumentException();
        }

        double result = 0.0;

        for (int i=0; i<v1.length; i++){
            result += v1[i] * v2[i];
        }

        return result;
    }

    public static double norm(double[] v1){
        //ベクトルのノルム（絶対値）
        double result = 0.0;

        result = Math.sqrt( MySqrMat.iProd(v1, v1) );

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
        //あらかじめ行列を初期化しておく必要あり。非検査例外ArrayIndexOutOfBoundsExceptionが生じうる
        this.mat[i][j] = v;
    }

    public void setMat(double[][] m){
        //二次元配列で行列を初期化（ディープコピー）
        //引数が正方行列でない場合に非検査例外をthrow
        this.mat = new double[m.length][m.length];
        for (int i=0; i<m.length; i++){
            if (m[i].length != m.length){
                throw new IllegalArgumentException();
            }
            for(int j=0; j<m[i].length; j++){
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
        this.mat = new double[][]{
            {m00, m01, m02},
            {m10, m11, m12},
            {m20, m21, m22}
        };
    }

    public void setMat(
        double m00, double m01, double m02, double m03,
        double m10, double m11, double m12, double m13,
        double m20, double m21, double m22, double m23,
        double m30, double m31, double m32, double m33
    ){
        //引数16つで行列を初期化
        this.mat = new double[][]{
            {m00, m01, m02, m03},
            {m10, m11, m12, m13},
            {m20, m21, m22, m23},
            {m30, m31, m32, m33}
        };
    }

    public double getData(int i, int j){
        //i行j列目の要素を返す
        return this.mat[i][j];
    }

    public double[] getLine(int ln){
        //行列の第ln行目を取得
        double[] result = this.mat[ln].clone();

        return result;
    }

    public double[] getCol(int cl){
        //行列の第cl列目を取得
        double[] result = new double[this.mat.length];

        for (int i=0; i<this.mat.length; i++){
            result[i] = this.mat[i][cl];
        }

        return result;
    }

    public MySqrMat copy(){
        //行列を複製
        MySqrMat cp = new MySqrMat();

        cp.setMat(this.mat);

        return cp;
    }

    public void T(){
        //行列を転置
        double[][] tMat = new double[this.mat.length][this.mat.length];

        for (int i=0; i<this.mat.length; i++){
            for (int j=0; j<this.mat.length; j++){
                tMat[i][j] = this.mat[j][i];
            }
        }
        this.setMat(tMat);
    }

    public void mulMat(MySqrMat tg){
        //行列にtgを左からかける
        if (tg.mat.length != this.mat.length){
            throw new IllegalArgumentException();
        }

        double[][] result = new double[this.mat.length][this.mat.length];

        for (int i=0; i<this.mat.length; i++){
            if ( tg.mat[i].length != this.mat.length){
                throw new IllegalArgumentException();
            }
            for (int j=0; j<this.mat.length; j++){
                result[i][j] = MySqrMat.iProd( tg.getLine(i), this.getCol(j) );
            }
        }
        this.setMat(result);
    }

    public double[] mulVec(double[] vec){
        //行列に右から列ベクトルをかける
        //行ベクトル表記のvecを列ベクトルとして扱う
        if (vec.length != this.mat.length){
            throw new IllegalArgumentException();
        }

        double[] result = new double[this.mat.length];

        for (int i=0; i<this.mat.length; i++){
            result[i] = MySqrMat.iProd(this.getLine(i), vec);
        }
        return result;
    }
}