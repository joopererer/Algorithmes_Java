/**
 * O1-背包问题
 * -- 每种物品的数量只有1个，只能选择放于不放。
 * 优化函数:
 *   Bk(y) = max(B[k-1](y-Wk)+Vk, B[k-1](y))
 */
public class Knapsack_01 {

    /**
     * 蛮力算法 O(2^k)
     * @param V
     * @param W
     * @param k
     * @param y
     * @return
     */
    public int calcul_value_rec(int[] V, int[] W, int k, int y) {
        if(k<=0){
            return 0;
        }
        int val1 = 0;
        if(y-W[k-1]>=0){
            val1 = calcul_value_rec(V, W, k-1, y-W[k-1]) + V[k-1];
        }
        int val2 = calcul_value_rec(V, W, k-1, y);
        return Math.max(val1, val2);
    }

    /**
     * 动态规划算法
     * 算法复杂度: O(ky)
     * 空间复杂度：O(ky)
     *  - 优点：可完整追溯物品的选择
     * @param V
     * @param W
     * @param k
     * @param y
     * @return
     */
    public int calcul_value_dp(int[] V, int[] W, int k, int y){
        int[][] B = new int[k+1][y+1];
        for(int i=0; i<=k; i++){
            B[i][0] = 0;
        }
        for(int i=0; i<=y; i++){
            B[0][i] = 0;
        }
        for(int i=1; i<=k; i++){
            for(int j=1; j<=y; j++){
                if(j-W[i-1]>=0){
                    B[i][j] = Math.max(B[i-1][j-W[i-1]]+V[i-1], B[i-1][j]);
                }else{
                    B[i][j] = B[i-1][j];
                }
            }
        }

        for(int i=0; i<=k; i++){
            for(int j=0; j<=y; j++){
                System.out.print(B[i][j]+", ");
            }
            System.out.println();
        }

        trace(B, W, k, y-1);

        return B[k][y];
    }

    private void trace(int[][] B, int[] W, int k, int y){
        while(k>0 && y>=0){
            if(B[k-1][y]!=B[k][y]){ // 至少取了一次该物品
                System.out.print((k-1)+",");
                y -= W[k-1];
            }
            k -= 1;          // 需要注意，01背包问题，无论如何都要-1，因为每种物品只能取一次
        }
        System.out.println();
    }

    /**
     * 动态规划算法(优化)
     * 算法复杂度: O(ky)
     * 空间复杂度：O(y)
     *  - 缺点：无法完整追溯物品的选择
     * @param V
     * @param W
     * @param k
     * @param y
     * @return
     */
    public int calcul_value_dp_op(int[] V, int[] W, int k, int y){
        int[] B = new int[y+1];
        for(int i=0; i<=y; i++){
            B[i] = 0;
        }
        for(int i=0; i<k; i++){
            for(int j=y; j>=W[i]; j--){  // 这里使用倒序，正好利用了初始化值为0的便利；j-W[i]是往前取的值，倒序确保前面的值最后才被计算(所以还是上一次的值)
                B[j] = Math.max(B[j-W[i]]+V[i], B[j]);
            }
        }

        return B[y];
    }

    public static void main(String[] args){
        Knapsack_01 kns = new Knapsack_01();
        int[] V = {24, 2, 9, 10, 9};  // 物品价格
        int[] W = {10, 3, 4, 5, 4};   // 物品体积
        int y = 25;                   // 背包容量
        int k = V.length;           // 最大物品标号

        int max_val = kns.calcul_value_rec(V, W, k, y);
        System.out.println("max_val: "+max_val);

        max_val = kns.calcul_value_dp(V, W, k, y);
        System.out.println("max_val: "+max_val);

        max_val = kns.calcul_value_dp_op(V, W, k, y);
        System.out.println("max_val: "+max_val);
    }

}
