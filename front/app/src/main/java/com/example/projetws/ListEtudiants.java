package com.example.projetws;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListEtudiants extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> etudiants = new ArrayList<>();
    private final String fetchUrl = "http://10.0.2.2:8080/php/mobileapi/ws/getEtudiants.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        setContentView(listView);

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, fetchUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                String nom = obj.getString("nom");
                                String prenom = obj.getString("prenom");
                                String ville = obj.getString("ville");
                                String sexe = obj.getString("sexe");
                                etudiants.add(nom + " " + prenom + " - " + ville + " (" + sexe + ")");
                            }
                            listView.setAdapter(new ArrayAdapter<>(ListEtudiants.this, android.R.layout.simple_list_item_1, etudiants));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> error.printStackTrace()
        );

        queue.add(request);
    }
}
