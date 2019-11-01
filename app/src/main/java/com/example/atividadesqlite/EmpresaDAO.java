package com.example.atividadesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {


    private Conexao conexao;
    private SQLiteDatabase banco;

    public EmpresaDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

        //
    public long inserir(Empresa empresa){
        ContentValues values = new ContentValues();
        values.put("nome", empresa.getNome());
        values.put("cnpj", empresa.getCnpj());
        values.put("telefone", empresa.getTelefone());
        return banco.insert("empresa", null, values
        );
    }


    public List<Empresa> obterTodos(){
        List<Empresa> empresas = new ArrayList<>();
        Cursor cursor = banco.query("cliente", new String []{"id", "nome", "cnpj", "telefone"},
               null, null, null , null , null);

        while(cursor.moveToNext()){

            Empresa a = new Empresa();

            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCnpj(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            empresas.add(a);
        }
            return empresas;

    }

    public void excluir(Empresa a){
        banco.delete("cliente","id = ?", new String[]{a.getId().toString()});
    }

    public void atualizar(Empresa empresa){
        ContentValues values = new ContentValues();
        values.put("nome", empresa.getNome());
        values.put("cnpj", empresa.getCnpj());
        values.put("telefone", empresa.getTelefone());
        banco.update("empresa", values, "id = ?", new String[]{empresa.getId().toString()});


    }

}