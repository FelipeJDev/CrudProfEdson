package com.example.atividadesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EmpresasMainActivity extends AppCompatActivity {

    private EditText nome;
    private EditText cnpj;
    private EditText telefone;
    private EmpresaDAO dao;
    private Empresa empresasMainActivity = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nome  = findViewById(R.id.InserirNomeEmpresa);
        cnpj = findViewById(R.id.InserirCnpjEmpresa);
        telefone = findViewById(R.id.inserirTelefoneEmpresa);
        dao = new EmpresaDAO(this);


        Intent it = getIntent();
        if(it.hasExtra("empresasMainActivity")){
            empresasMainActivity = (Empresa) it.getSerializableExtra("empresasMainActivity");
            nome.setText(empresasMainActivity.getNome());
            cnpj.setText(empresasMainActivity.getCnpj());
            telefone.setText(empresasMainActivity.getTelefone());
        }
    }

    public void salvar (View view){
        if (empresasMainActivity == null){
            empresasMainActivity = new Empresa();
            empresasMainActivity.setNome (nome.getText().toString());
            empresasMainActivity.setCnpj(cnpj.getText().toString());
            empresasMainActivity.setTelefone (telefone.getText().toString());
            long id = dao.inserir(empresasMainActivity);
            Toast.makeText(this, "Empresa adicionada com Sucesso. ID: " + id, Toast.LENGTH_SHORT).show();
        }else{
            empresasMainActivity.setNome (nome.getText().toString());
            empresasMainActivity.setCnpj(cnpj.getText().toString());
            empresasMainActivity.setTelefone (telefone.getText().toString());
            dao.atualizar(empresasMainActivity);
            Toast.makeText(this, "Empresa editada com sucesso", Toast.LENGTH_SHORT).show();
        }
        Intent it = new Intent(this, EmpresasActivity.class);
        startActivity(it);
    }
}