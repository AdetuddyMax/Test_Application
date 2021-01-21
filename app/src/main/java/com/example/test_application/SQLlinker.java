package com.example.test_application;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLlinker{

    private static SQLiteDatabase db;

    public  SQLlinker(){

    }

    public static void Start(){
        System.out.println("test");
        db = SQLiteDatabase.openOrCreateDatabase("/data/data/Test_Application/com.example.db/rougelike.db",null);
        if(!tabbleIsExist()){
            createTable();
        }
    }

    public static boolean tabbleIsExist(){
        boolean result = false;
        Cursor cursor = null;
        try {
            //这里表名可以是Sqlite_master
            String sql = "select count(*) as c from rougelike where type ='table' and name ='' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        return result;
    }

    public static void createTable(){
        String stu_table1="create table Operators ( to_id int primary key autoincrement," +
                "o_name char(30)," +
                "o_level int check(o_level>=1 and o_level <=6)," +
                "o_profession int check(o_profession>=1 and o_profession<=8)," +
                "o_skill_1 char(30)," +
                "o_skill_2 char(30)," +
                "o_skill_3 char(30)" +
                ")",//干员基本数据，1先锋，2狙击，3近卫，4术士，5重装，6医疗，7特种，8辅助
        stu_table2="create table self_Operators(" +
                "s_id int primary key autoincrement," +
                "s_level int," +
                "s_excellentize int check(s_excellentize>=0 and s_excellentize<=2)," +
                "s_potency int check(s_potency>=1 and s_potency<=6)," +
                "s_skill_1 int check(s_skill_1>=0 and s_skill_1<=10)," +
                "s_skill_2 int check(s_skill_2>=0 and s_skill_2<=10)," +
                "s_skill_3 int check(s_skill_3>=0 and s_skill_3<=10)," +
                "foreign key(s_id) references Operators(o_id)" +
                ")",//自拥有干员
        stu_table3="create table rougelike_level(" +
                "rl_id int primary key autoincrement," +
                "rl_level char(10)," +
                "rl_hope int," +
                "rl_specialpoint int" +
                ")",//rougelilke关卡设置
        stu_table4="create table rougelike_file(" +
                "rf_id int primary key autoincrement," +
                "rf_level_id int," +
                "rf_hope int," +
                "rf_time datetime," +
                "foreign key(rf_level_id) references rougelike_level(rl_id)" +
                ")",//存档
        stu_table5="create table file_to_self_Operators(" +
                "ftso_rf_id int," +
                "ftso_s_id int," +
                "foreign key(ftso_s_id) references self_Operators(s_id)," +
                "foreign key(ftso_rf_id) references rougelike_file(rf_id)" +
                ")";//存档与自干员一对多关联表
        db.execSQL(stu_table1);
        db.execSQL(stu_table2);
        db.execSQL(stu_table3);
        db.execSQL(stu_table4);
        db.execSQL(stu_table5);
    }

}
