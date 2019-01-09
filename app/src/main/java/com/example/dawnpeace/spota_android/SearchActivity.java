package com.example.dawnpeace.spota_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.Draft;
import com.example.dawnpeace.spota_android.Classes.Preoutline;
import com.example.dawnpeace.spota_android.Interfaces.SearchInterface;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private EditText et_search;
    private ImageButton ib_search;
    private SharedPrefHelper sharedpref;
    private RecyclerView rv_preoutlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

    }

    private void initView(){
        et_search = findViewById(R.id.et_search);
        ib_search = findViewById(R.id.ib_search);
        sharedpref = SharedPrefHelper.getInstance(this);

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    public void search(){
        String query = et_search.getText().toString();
        if(query.isEmpty()){
            Toast.makeText(this, "Kata Kunci Kosong . .", Toast.LENGTH_SHORT).show();
        } else {
            OkHttpClient client = sharedpref.getInterceptor();
            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(APIUrl.BASE_URL).client(client).build();
            SearchInterface search = retrofit.create(SearchInterface.class);
            Call<List<Preoutline>> call  = search.studentSearcDraft(query);
            call.enqueue(new Callback<List<Preoutline>>() {
                @Override
                public void onResponse(Call<List<Preoutline>> call, Response<List<Preoutline>> response) {
                    if(response.isSuccessful()){
                        rv_preoutlines = (RecyclerView) findViewById(R.id.rv_search);
                        SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(response.body(),SearchActivity.this);
                        rv_preoutlines.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        rv_preoutlines.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<Preoutline>> call, Throwable t) {

                }
            });
        }


    }

    public void search(View view){
        search();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
