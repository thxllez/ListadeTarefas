package com.thxllez.listadetarefas.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.thxllez.listadetarefas.R;
import com.thxllez.listadetarefas.helper.TarefaDAO;
import com.thxllez.listadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText textTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        textTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa, caso seja ediçao
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            textTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemSalvar:

                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if(tarefaAtual != null){
                    String nomeTarefa = textTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty()) {

                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no banco de dados
                        if(tarefaDAO.atualizar(tarefa)){
                            finish(); //encerra a activity
                            Toast.makeText(AdicionarTarefaActivity.this, "Tarefa atualizada", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(AdicionarTarefaActivity.this, "Erro ao atualizar tarefa", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(AdicionarTarefaActivity.this, "Digite uma tarefa!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    String nomeTarefa = textTarefa.getText().toString();
                    if (!nomeTarefa.isEmpty()) {
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);

                        if(tarefaDAO.salvar(tarefa)){
                            finish(); //encerra a activity
                            Toast.makeText(AdicionarTarefaActivity.this, "Tarefa Salva com sucesso!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AdicionarTarefaActivity.this, "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AdicionarTarefaActivity.this, "Digite uma tarefa!", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
