/*
スライムの頂点情報、辺情報を持つファイル"slime.txt"を生成するプログラム
*/

#include <stdio.h>
#include <math.h>

int main(void){
    double diameter_6[25] = {
        2.8, 4.2, 4.8, 5.2, 5.6, 5.9, 
        6.0, 6.0, 6.0, 6.0, 6.0, 6.0,
        5.7, 5.5, 5.0, 4.5, 3.8, 3.1,
        2.3, 1.8, 1.2, 0.8, 0.6, 0.3,
        0.0
    };

    FILE *fp;
    fp = fopen("../obj/slime.txt", "w");

    for(int i=0; i<25; i++){
        double radius = diameter_6[i]/12.0;
        for (int j=0; j<24; j++){
            double rad = j * 2 * M_PI / 24.0;
            fprintf(fp, "v %lf %lf %lf\n", 
                radius * cos(rad),
                (double)i / 24.0,
                radius * sin(rad)
            );
        }
        for (int j=0; j<24; j++){
            fprintf(fp, "f %d// %d//\n",
                24*i+1 + j,
                24*i+1 + (j+1)%24
            );
        }
    }

    fclose(fp);
    return 0;
}