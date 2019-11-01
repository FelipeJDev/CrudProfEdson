package com.example.atividadesqlite;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

public class EmpresasActivity extends AppCompatActivity {
    private ListView listView;
    private EmpresaDAO dao;
    private List<Empresa> empresas;
    private List<Empresa> empresasFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        listView = findViewById(R.id.lista_empresas);
        dao = new EmpresaDAO(this);
        empresas = dao.obterTodos();
        empresasFiltrados.addAll(empresas);
        ArrayAdapter<Empresa> adptador = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_1, empresasFiltrados);
        listView.setAdapter(adptador);
        registerForContextMenu(listView);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal, menu);
        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String Query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String Query) {
                procuraEmpresa(Query);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public  void procuraEmpresa(String nome){
        empresasFiltrados.clear();
        for(Empresa a: empresas){
            if(a.getNome().toLowerCase().contains(nome.toLowerCase())){
                empresasFiltrados.add(a);
            }
        }
        listView.invalidateViews();
    }

    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Empresa empresaExcluir = empresasFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!!!")
                .setMessage("Confirmar Exclusão?")
                .setNegativeButton("NAO", null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        empresasFiltrados.remove(empresaExcluir);
                        empresas.remove(empresaExcluir);
                        dao.excluir(empresaExcluir);
                        listView.invalidateViews();
                    }
                }).create();
        dialog.show();
    }

    ///Verificar
    public void cadastrar(MenuItem  item){
        Intent it = new Intent(this, EmpresasMainActivity.class);
        startActivity(it);
    }
    ///Verificar
    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Empresa empresaAtualizar = empresasFiltrados.get(menuInfo.position);
        Intent it = new Intent(this, EmpresasMainActivity.class);
        it.putExtra("empresa", empresaAtualizar);
        startActivity(it);
    }

    @Override
    public void onResume(){
        super.onResume();
        empresas = dao.obterTodos();
        empresasFiltrados.clear();
        empresasFiltrados.addAll(empresas);
        listView.invalidateViews();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Concluido", Toast.LENGTH_SHORT).show();
        finishAndRemoveTask();
    }
}