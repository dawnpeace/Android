package com.example.dawnpeace.spota_android;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dawnpeace.spota_android.Classes.ViewPreoutline;
import com.example.dawnpeace.spota_android.Interfaces.SearchInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreoutlineActivity extends AppCompatActivity {

    private RecyclerView rv_review;
    private TextView tv_title;
    private TextView tv_status;
    private TextView tv_student_name;
    private TextView tv_content;
    private TextView tv_upvote;
    private TextView tv_downvote;
    private SharedPrefHelper mSharedPref;
    private LinearLayout ll_preoutline;
    private int preoutline_id;
    private String url="";
    private MyTagHandler tagHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preoutline);
        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.preoutline_menu);
        }
        try{
            preoutline_id = getIntent().getExtras().getInt("id");
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        mSharedPref = SharedPrefHelper.getInstance(this);

        initView();
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preoutline_sub_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.preoutline_download_menu:
                if(!url.isEmpty()){
                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(url);
                    Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setTitle("Draft Praoutline");
                    request.setDescription("Downloading");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalFilesDir(this,"Downloads","Draft.pdf");
                    downloadManager.enqueue(request);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void initView(){
        tv_title = findViewById(R.id.tv_preoutline_title);
        tv_status = findViewById(R.id.tv_preoutline_status);
        tv_student_name = findViewById(R.id.tv_preoutline_student_name);
        tv_content = findViewById(R.id.tv_preoutline_content);
        tv_upvote = findViewById(R.id.tv_preoutline_upvote_count);
        tv_downvote = findViewById(R.id.tv_preoutline_downvote_count);
        rv_review = findViewById(R.id.rv_preoutline);
        ll_preoutline = findViewById(R.id.ll_preoutline_draft);
        rv_review.setNestedScrollingEnabled(false);
    }

    public void loadData(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPref.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SearchInterface search = retrofit.create(SearchInterface.class);
        Call<ViewPreoutline> call = search.getPreoutline(preoutline_id);
        call.enqueue(new Callback<ViewPreoutline>() {
            @Override
            public void onResponse(Call<ViewPreoutline> call, Response<ViewPreoutline> response) {
                if (response.isSuccessful()) {
                    ViewPreoutline vp = response.body();
                    tv_title.setText(vp.getPreoutlineDraft().getTitle());
                    tv_student_name.setText(vp.getPreoutlineDraft().getName() + " ( "+vp.getPreoutlineDraft().getIdentity_number()+" )");
                    tv_status.setText(vp.getPreoutlineDraft().getStatus());
                    tv_content.setText(Html.fromHtml(vp.getPreoutlineDraft().getDescription(),null,new MyTagHandler()));
                    tv_upvote.setText(Integer.toString(vp.getPreoutlineDraft().getApproval_count()));
                    tv_downvote.setText(Integer.toString(vp.getPreoutlineDraft().getDisapproval_count()));
                    ll_preoutline.setVisibility(View.VISIBLE);
                    PreoutlineReviewRecyclerAdapter adapter = new PreoutlineReviewRecyclerAdapter(PreoutlineActivity.this, vp.getReviews());
                    rv_review.setLayoutManager(new LinearLayoutManager(PreoutlineActivity.this));
                    rv_review.setAdapter(adapter);
                    url = vp.getPreoutlineDraft().getFile_url();
                }
            }

            @Override
            public void onFailure(Call<ViewPreoutline> call, Throwable t) {

            }
        });
    }
}
