/**
 * 最大公共子序列
 * 优化函数：
 *  C[i,j]   -- X<0..i>和Y<0..j>的最大子序列长度
 *    = 0                        如果i=0或j=0
 *    = C[i-1, j-1]              如果i,j>0 且X[i]=Y[j]
 *    = max(C[i,j-1], C[i-1,j])  如果i,j>0 且X[i]!=Y[j]
 */
public class SousSequence {

    /**
     * 蛮力算法
     * @param X
     * @param Y
     * @param n
     * @param m
     * @return 最大系序列的长度
     */
    public int LCS_rec(String X, String Y, int n, int m){
        if(n<=0 || m<=0){
            return 0;
        }
        if(X.charAt(n-1)==Y.charAt(m-1)){
            return LCS_rec(X, Y, n-1, m-1) + 1;
        }
        return Math.max(LCS_rec(X,Y,n-1,m), LCS_rec(X,Y,n,m-1));
    }

    /**
     * 动态规划算法
     * @param X
     * @param Y
     * @param n
     * @param m
     * @return 最大系序列的长度
     */
    public int LCS_dp(String X, String Y, int n, int m){
        int[][] C = new int[n+1][m+1];
        for(int i=0; i<=n; i++){
            C[i][0] = 0;
        }
        for(int i=0; i<=m; i++){
            C[0][i] = 0;
        }
        for(int i=1; i<=n; i++){
            for(int j=1; j<=m; j++){
                if(X.charAt(i-1)==Y.charAt(j-1)){  // 此处要注意，对比的字符是要从0开始
                    C[i][j] = C[i-1][j-1] + 1;
                }else{
                    C[i][j] = Math.max(C[i-1][j], C[i][j-1]);
                }
            }
        }

        String lcs = trace(X, Y, n, m, C);
        System.out.println(lcs);

        return C[n][m];
    }

    private String trace(String X, String Y, int n, int m, int[][] C){
        StringBuilder buf = new StringBuilder();
        int val = C[n][m];
        while(C[n][m]>0){
            if(X.charAt(n-1)==Y.charAt(m-1)){
                buf.insert(0, X.charAt(n-1)); // 注意是逆序的，所以这里用插入
                val -= 1;
                n -= 1;
                m -= 1;
            } else {
                if(val==C[n-1][m]){
                    n -= 1;
                }else{
                    m -= 1;
                }
            }
        }
        return buf.toString();
    }

    public static void main(String[] args){
        SousSequence ssq = new SousSequence();
        String X = "ABCBDAB";
        String Y = "BDCABA";

        int lcs = ssq.LCS_rec(X, Y, X.length(), Y.length());
        System.out.println("LCS: "+lcs);

        lcs = ssq.LCS_dp(X, Y, X.length(), Y.length());
        System.out.println("LCS: "+lcs);
    }

}
