package com.thxllez.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thxllez.listadetarefas.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public TarefaDAO(Context context) {
        DBHelper db = new DBHelper(context);
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            escreve.insert(DBHelper.TABELA_TAREFAS, null,cv); //o segundo parametro recebe um valor padrão para gravar caso o usuario n tenha digitado nada para aquele campo, se for null ele nao salva se nao estiver com os dados completos.
        }catch(Exception e){
            Log.i("TarefaDAO", "Erro ao salvar tarefa!");
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());

        try{
            String[] args = {tarefa.getId().toString()};
            //escreve.update(DBHelper.TABELA_TAREFAS, cv, "id = ? AND status = ?", args);  // ? no 3 param é substituido pelo 4 param
            escreve.update(DBHelper.TABELA_TAREFAS, cv, "id = ?", args);  // ? no 3 param é substituido pelo 4 param
        }catch(Exception e){
            Log.i("TarefaDAO", "Erro ao atualizar tarefa!");
            return false;
        }


        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try{
            String[] args = {tarefa.getId().toString()};
            escreve.delete(DBHelper.TABELA_TAREFAS, "id = ?", args);
        }catch(Exception e){
            Log.i("TarefaDAO", "Erro ao atualizar tarefa!");
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " +DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = ler.rawQuery(sql,null); //2 param: filtros que podem ser aplicados ao select

        while(c.moveToNext()){

            Tarefa tarefa = new Tarefa();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }

        return tarefas;
    }
}
