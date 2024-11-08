public class MyRot{
    protected double[][] mat = {
        {1.0, 0.0, 0.0},
        {0.0, 1.0, 0.0},
        {0.0, 0.0, 1.0}
    };

    public MyRot(){}

    public void print(){
        for (int i=0; i<this.mat.length; i++){
            for (int j=0; j<this.mat[i].length; j++){
                System.out.printf("%.3f ", this.mat[i][j]);
            }
            System.out.printf("%n");
        }
        System.out.printf("%n");
    }

    public static double[] mul(double a, double[] v1){
        int len = v1.length;
        double[] result = new double[len];

        for (int i=0; i<len; i++){
            result[i] = a * v1[i];
        }
        
        return result;
    }

    public static double[] add(double[] v1, double[] v2){
        int len = v1.length;
        double[] result = new double[len];

        for (int i=0; i<len; i++){
            result[i] = v1[i] + v2[i];
        }

        return result;
    }

    public static double iProd(double[] v1, double[] v2){
        int len = v1.length;
        double result = 0;

        for (int i=0; i<len; i++){
            result += v1[i] * v2[i];
        }

        return result;
    }

    public static double norm(double[] v1){
        double result = 0;

        result = Math.sqrt( iProd(v1, v1) );

        return result;
    }

    public void setData(int i, int j, double v){
        mat[i][j] = v;
    }

    public void setMat(double[][] m){
        for (int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                setData(i, j, m[i][j]);
            }
        }
    }

    public void setMat(
        double m00, double m01, double m02,
        double m10, double m11, double m12,
        double m20, double m21, double m22
    ){
        double[][] m = {
            {m00, m01, m02},
            {m10, m11, m12},
            {m20, m21, m22}
        };
        this.setMat(m);
    }

    public double[] getLine(int ln){
        double[] result = new double[3];

        result = this.mat[ln].clone();

        return result;
    }

    public double[] getCol(int cl){
        double[] result = new double[3];

        for (int i=0; i<3; i++){
            result[i] = this.mat[i][cl];
        }

        return result;
    }

    public MyRot copy(){
        MyRot cp = new MyRot();

        cp.setMat(this.mat);

        return cp;
    }

    public void T(){
        double[][] tMat = new double[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                tMat[i][j] = this.mat[j][i];
            }
        }
        this.setMat(tMat);
    }

    public void mulMat(MyRot tg){
        double[][] result = new double[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                result[i][j] = this.iProd( tg.getLine(i), this.getCol(j) );
            }
        }
        this.setMat(result);
    }

    public double[] mulVec(double[] vec){
        double[] result = new double[3];

        for (int i=0; i<3; i++){
            result[i] = this.iProd(this.getLine(i), vec);
        }
        return result;
    }
}