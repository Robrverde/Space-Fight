package com.example.spacefight;

import android.content.Context;
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStreamWriter;
import java.io.Serializable;

public class GlobalVariables implements Serializable {

    //Global variables
    public static int aliens_defeated;
    public static int shots_fired;
    public static int games_played;
    public static int games_won;
    public static int game_overs;

    public static double overall_accuracy;
    public static double win_ratio;

    public static void SaveData(Context app)
    {
        try {
            FileOutputStream file = app.openFileOutput("my_data", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(file);
            os.writeObject(new GlobalVariables());
            os.close();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LoadData(Context app)
    {

        try {
            FileInputStream file = app.openFileInput("my_data");
            ObjectInputStream os = new ObjectInputStream(file);
            os.readObject();
            os.close();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*

    public static void SaveDataAsText(Context app)
    {

        try {
            FileOutputStream file = app.openFileOutput("my_text", Context.MODE_PRIVATE);
            BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(file));

            //Custom save file
            //buff.write("" + level_1); buff.newLine();

            buff.close();
            file.close();

            Log.d("Example", "Data Saved as Text");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LoadDataAsText(Context app)
    {
        FileInputStream file = null;
        try {
            file = app.openFileInput("my_text");
            BufferedReader buff = new BufferedReader(new InputStreamReader(file));

            //level_1 = Integer.parseInt(buff.readLine());

            file.close();

            Log.d("Example", "Data loaded as Text");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
    }

    */

}
