package Slime_Game;

import java.util.ArrayList;

/**
 * @author Atibrewa
 * @author bean-b
 * @author Mkleit
 * 
 * Creates a set number of Waves and adds them to a list, from where they are fed into Gamehandler
 */
public class WaveHandler {
    private static ArrayList<Wave> waveList;
    private static int waveNum;

    /**
     * Creates different waves of enemies for different rounds
     * and adds them to WaveList
     * and then returns them one by one
     */
    WaveHandler() {
        waveNum = 0;
        waveList = new ArrayList<Wave>();
        setWaves();
    }

    /**
     * increments wave number and returns a new wave if there are any left
     * @return Wave (The next wave)
     */
    public Wave getNextWave() {
        if(waveList.size() >= waveNum) {
            waveNum++;
            return waveList.get(waveNum - 1);
        }
        return null;
    }

    private void setWaves() {
        //  spaced waves of white 
        ArrayList<Enemy> enemyList1 = new ArrayList<>();
        for (int i=0; i < 9; i++) {
            enemyList1.add(EnemyIndex.getWhiteChicken());
        }
        waveList.add(new Wave(enemyList1, 6));

        //spaced waves of white with brown in middle
        ArrayList<Enemy> enemyList2 = new ArrayList<>();
        for (int i=0; i <3; i++) {
            enemyList2.add(EnemyIndex.getWhiteChicken());
        }
        for (int i=0; i <3; i++) {
            enemyList2.add(EnemyIndex.getBrownChicken());
        }
        for (int i=0; i <2; i++) {
            enemyList2.add(EnemyIndex.getWhiteChicken());
        }
        waveList.add(new Wave(enemyList2, 6));

        //clumped wave of whites with one brown
        ArrayList<Enemy> enemyList3 = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            enemyList3.add(EnemyIndex.getWhiteChicken());
        }
        enemyList3.add(EnemyIndex.getBrownChicken());
        waveList.add(new Wave(enemyList3, 1));

        //spaced browns with blues at end
        ArrayList<Enemy> enemyList4 = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            enemyList4.add(EnemyIndex.getBrownChicken());
        }
        for (int i=0; i < 2; i++) {
            enemyList4.add(EnemyIndex.getBlueChicken());
        }
        waveList.add(new Wave(enemyList4, 5));

        //somewhat spaced blues with one yellow at the end
        ArrayList<Enemy> enemyList5 = new ArrayList<>();
        for (int i=0; i <6; i++) {
            enemyList5.add(EnemyIndex.getBlueChicken());
        }
        enemyList5.add(EnemyIndex.getYellowChicken());
        waveList.add(new Wave(enemyList5, 3));

        //spaced blacks with whites in between
        ArrayList<Enemy> enemyList6 = new ArrayList<>();
        enemyList6.add(EnemyIndex.getBlackChicken());
        for (int i = 0; i < 8; i++) {
            enemyList6.add(EnemyIndex.getWhiteChicken());
        }
        for (int i = 0; i < 2; i++) {
            enemyList6.add(EnemyIndex.getBlackChicken());
        }
        waveList.add(new Wave(enemyList6, 4));

        //somehwat spaced blues then yellows
        ArrayList<Enemy> enemyList7 = new ArrayList<>();
        for (int i = 0; i<5; i++) {
            enemyList7.add(EnemyIndex.getBlueChicken());
        }
        for (int i = 0; i<4; i++) {
            enemyList7.add(EnemyIndex.getYellowChicken());
        }
        waveList.add(new Wave(enemyList7, 2));

        //clumped browns with blacks mixed in
        ArrayList<Enemy> enemyList8 = new ArrayList<>();
        for (int i = 0; i <6; i++) {
            enemyList8.add(EnemyIndex.getBrownChicken());
            if(i % 3 == 0){
                enemyList8.add(EnemyIndex.getBlackChicken());
            }
        }
        waveList.add(new Wave(enemyList8, 1));

        //clumped black and blues
        ArrayList<Enemy> enemyList9 = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            enemyList9.add(EnemyIndex.getBlackChicken());
        }
        for (int i=0; i <3; i++) {
            enemyList9.add(EnemyIndex.getBlueChicken());
        }
        waveList.add(new Wave(enemyList9, 1));

        //large waves of spaced black and browns, with a mega at the end
        ArrayList<Enemy> enemyList10 = new ArrayList<>();
        for (int i = 0; i<3; i++) {
            enemyList10.add(EnemyIndex.getBlackChicken());
            enemyList10.add(EnemyIndex.getBlueChicken());
            enemyList10.add(EnemyIndex.getYellowChicken());
        }
        enemyList10.add(EnemyIndex.getMegaChicken());
        waveList.add(new Wave(enemyList10, 3));
    }

    /**
     * returns the number of waves/rounds in the game
     * @return int - number of rounds
     */
    public static int getWaveListSize() {
        return waveList.size();
    }

}

