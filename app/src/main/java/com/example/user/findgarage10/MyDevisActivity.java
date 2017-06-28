package com.example.user.findgarage10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyDevisActivity extends AppCompatActivity {

    //region declaration
    private ListView listMyDevis;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_devis);

        initView();
    }

    private void initView() {
        listMyDevis = (ListView) findViewById(R.id.my_devis_lstView);
        initList();

    }

    private void initList(){
        String[] listDevis = new String[]{"Ixelles","Etterbeek","Ganshoren","Uccle","Laeken","Woluwe Saint Lambert",
                "Neder-over-hembeek","Jette"};
        listMyDevis.setAdapter(new ArrayAdapter<>(this,0, listDevis));
    }
}
