// 
// Source code recreated from a .class file by IntelliJ IDEA 
// (powered by Fernflower decompiler) 
// 

package net.drakkcore.loader;

import com.badlogic.gdx.Gdx;

import net.drakkcore.States.GameStateManager;

public class LevelsLoader {
    public static int selectLvl = 1;
    private static int maxLvl;
    // [0]number of level; [1]count of balls in row and colomn; [2]count of colors;[3]score to win;[4]mode;[5]count of steps/sec;[6]Open or Close lvl
    // mode: s - step; t - time 
    public static String[] levels = new String[]{"1;7;6;1;s;20;open", "2;6;5;100;t;30;close", "3;7;5;100;t;30;close", "4;8;6;100;s;30;close"};



    public LevelsLoader() {

    }

    public static void setLevelMode(GameStateManager gsm, String level)
    {
        switch(LevelsLoader.levels[0].split(";")[4].toCharArray()[0]){
            case 's':
                gsm.setGameMode(GameStateManager.Mode.STEP);
                break;
            case 't':
                gsm.setGameMode(GameStateManager.Mode.TIME);
                break;
            default:
                gsm.setGameMode(GameStateManager.Mode.UNLIM);
                break;
        }

    }
    public static void LoadingLevels()
    {
        maxLvl = ResourseLoader.getMaxLvl();

        for(int i=0;i < maxLvl; i++)
        {

            levels[i] = levels[i].replaceAll("close","open");

        }
    }

    public static int getSelectLvl() {
        return selectLvl;
    }

    public static void setSelectLvl(int selectLvl) {
      //  Gdx.app.log("LevelsLoader", " selecting LVL " + selectLvl);
        for(int i=0;i < levels.length; i++)
        {
            if (levels[i].split(";")[0].toString().equals(String.valueOf(selectLvl)))
            {
                levels[i] = levels[i].replaceAll("close","open");
            }
        }
        LevelsLoader.selectLvl = selectLvl;
    }

    public static int countOfLvl() {
        return levels.length;
    }
} 
 