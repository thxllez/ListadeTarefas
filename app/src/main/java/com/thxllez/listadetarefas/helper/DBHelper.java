package com.thxllez.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper{

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";


    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //utilizar para criar a primeira vez o banco de dados, chamado apenas uma vez assim que o usuário instala o app
        String sql = "CREATE TABLE IF NOT EXISTS "+ TABELA_TAREFAS + " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT NOT NULL); "; //tipo TEXT: quantidade GRANDE de dados

        try{
            db.execSQL(sql);
            Log.i("DBHelper", "Sucesso ao criar tabela");
        }catch (Exception e){
            Log.i("DBHelper", "Erro ao criar tabela" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //utilizar para modificar tabelas, criar novas, etc, chamado quando o usuário estiver atualizando seu app
        //quando o VERSION for alterado e o usuário atualizar o app, automaticamente será chamado o método onUpgrade para fazer as atualizaçoes necessárias

        //String sql = "ALTER TABLE " + TABELA_TAREFAS + " ADD COLUMN status VARCHAR(1);";

        /*try{
            db.execSQL(sql);
            Log.i("DBHelper", "Sucesso ao atualizar app");
        }catch (Exception e){
            Log.i("DBHelper", "Erro ao atualizar app" + e.getMessage());
        }*/
    }

}
