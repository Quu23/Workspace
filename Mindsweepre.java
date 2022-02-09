import java.util.ArrayList;
import java.util.List;

public class Mindsweepre {
    static final int WALL  = -1;
    static final int MAPS =  0;
    static final int BOM   =  1;
    //6 X 6 mas ; Actually 4 X 4 mas ;
    static int[][] maps = {
        {WALL,WALL,WALL,WALL,WALL,WALL},
        {WALL,MAPS,MAPS,MAPS,MAPS,WALL},
        {WALL,MAPS,MAPS,MAPS,MAPS,WALL},
        {WALL,MAPS,MAPS,MAPS,MAPS,WALL},
        {WALL,MAPS,MAPS,MAPS,MAPS,WALL},
        {WALL,WALL,WALL,WALL,WALL,WALL}};
    static int[][] mapNum = {
        {0,0,0,0,0,0},
        {0,0,0,0,0,0},
        {0,0,0,0,0,0},
        {0,0,0,0,0,0},
        {0,0,0,0,0,0},
        {0,0,0,0,0,0},
    };
    public static void main(String[] args) {
        int bomNum=5;//the Number of Boms
        int mapsX,mapsY;
        List<Integer> whereBom = new ArrayList<>();

        System.out.println("Number of Boms:"+bomNum);
        
        while(whereBom.size()<5){
            mapsX = new java.util.Random().nextInt(4)+1;
            mapsY = new java.util.Random().nextInt(4)+1;
            if(!checkIsBom(mapsX, mapsY)){
                maps[mapsX][mapsY] = BOM;
                whereBom.add((mapsX*1000)+mapsY);
            } 
        }

        for(int i=1;i<maps.length-1;i++){
            for(int j=1;j<maps[0].length-1;j++){
                mapNum[i][j]=CheckSurrudBomNum(i, j);
            }
        }
        
        for(int i=1;i<mapNum.length-1;i++){
            System.out.println("");
            for(int j=1;j<mapNum[0].length-1;j++){
                System.out.print(" "+mapNum[i][j]);
            }
        }
        System.out.println("");
        System.out.println("~Answer~");
        for (Integer Coordinate : whereBom) {
            System.out.println(" "+Coordinate);
        }
    }
    private static int CheckSurrudBomNum(int i,int j){
        int surrudBomNum=0;
        for(int k=-1;k<2;k++){
            for(int p=-1;p<2;p++){
                if(k==0&&p==0)continue;
                if(checkIsBom(i+k, j+p)){
                    surrudBomNum++;
                }
            }
        }
        return surrudBomNum;
    }
    private static boolean checkIsBom(int x,int y){
        if(maps[x][y]==BOM){
            return true;
        }
        return false;
    }
}
//end
