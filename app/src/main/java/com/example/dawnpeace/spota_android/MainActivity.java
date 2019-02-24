package com.example.dawnpeace.spota_android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.dawnpeace.spota_android.Classes.User;
import com.example.dawnpeace.spota_android.Interfaces.UserInterface;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNav;
    private DrawerLayout mainDrawer;
    private NavigationView navSideBar;
    private AlertDialog.Builder logoutAlert;
    private User user;
    public TextView head_identity_number;
    public TextView head_username;
    private SharedPrefHelper mSharedPrefs;
    private ImageView iv_nav_header;
    private Fragment homeFragment = new HomeFragment();
    private Fragment scheduleFragment = new ScheduleFragment();
    private Fragment outlineFragment = new OutlineFragment();
    private Fragment consultationFragment = new ConsultationFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefs = SharedPrefHelper.getInstance(getApplicationContext());
        checkTokenAvailability();

        //Change ActionBar to created Toolbar
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        //Instantiate drawer and its behaviour
        mainDrawer = findViewById(R.id.main_drawer);
        ActionBarDrawerToggle toggleMainDrawer = new ActionBarDrawerToggle(this, mainDrawer, mainToolbar, R.string.drawer_toggle_open, R.string.drawer_toggle_close);
        mainDrawer.addDrawerListener(toggleMainDrawer);
        toggleMainDrawer.syncState();


        //Handle drawer item on click
        NavigationView navSideBar = findViewById(R.id.nav_side_bar);
        navSideBar.setNavigationItemSelectedListener(this);
        View header = navSideBar.getHeaderView(0);
        head_identity_number = (TextView) header.findViewById(R.id.nav_header_identity_number);
        head_username = (TextView) header.findViewById(R.id.nav_header_username);
        iv_nav_header = (ImageView) header.findViewById(R.id.iv_nav_header);

        //Get the default menu for bottom_nav
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        initView();

        if(mSharedPrefs.getUser() != null){
            String url = mSharedPrefs.getUser().getPictureUrl() == null ? "" : mSharedPrefs.getUser().getPictureUrl();
            if (!url.isEmpty()) {
                Glide.with(this)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .into(iv_nav_header);
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_menu_change_password:
                Intent intent = new Intent(this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_menu_search:
                Intent intent1 = new Intent(this,SearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.drawer_menu_about:
                Intent intent2 = new Intent(this,AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.drawer_menu_notification:
                Intent intent3 = new Intent(this,NotificationActivity.class);
                startActivity(intent3);
                break;
            case R.id.drawer_menu_logout:
                AlertDialog alert = logoutAlert.create();
                alert.setTitle("Logout");
                alert.show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
            mainDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initView() {
        if (mSharedPrefs.getUser() == null) {
            getAuthenticatedUser();
        } else {
            User user = mSharedPrefs.getUser();
            head_identity_number.setText(user.getIdentity_number());
            head_username.setText(user.getName());
        }

        logoutAlert = new AlertDialog.Builder(this);
        logoutAlert.setMessage("Apakah anda yakin untuk keluar ?")
                .setCancelable(true)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    private void getAuthenticatedUser() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + mSharedPrefs.getToken())
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface auth_user = retrofit.create(UserInterface.class);
        Call<User> call = auth_user.getAuthUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mSharedPrefs.storeUser(response.body());
                    head_identity_number.setText(response.body().getIdentity_number());
                    head_username.setText(response.body().getName());
                    String url = response.body().getPictureUrl() == null ? "" : response.body().getPictureUrl();

                    Glide.with(MainActivity.this)
                            .load(url)
                            .apply(RequestOptions.circleCropTransform())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.ic_person_black_24dp)
                                    .error(R.drawable.ic_vpn_key_black_24dp)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true))
                            .into(iv_nav_header);
                } else {
                    Toast.makeText(MainActivity.this, "Terjadi kesalahan Autentikasi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            if(bundle.getBoolean("hasChanged")){
                AsyncGlideCache asyncGlideCache = new AsyncGlideCache(this);
                asyncGlideCache.execute();
                Glide.get(this).clearMemory();
                Glide.with(MainActivity.this)
                        .load(mSharedPrefs.getUser().getPictureUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.ic_person_black_24dp)
                                .error(R.drawable.ic_vpn_key_black_24dp)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true))
                        .into(iv_nav_header);
                head_identity_number.setText(mSharedPrefs.getUser().getIdentity_number());
                head_username.setText(mSharedPrefs.getUser().getName());
            }
        }
        if(mSharedPrefs.getUser() != null){
            Glide.with(this)
                    .load(mSharedPrefs.getUser().getPictureUrl())
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_person_black_24dp)
                            .skipMemoryCache(true))
                    .into(iv_nav_header);
        }


        Fragment selectedFragment = null;
        switch (bottomNav.getSelectedItemId()) {
            case R.id.nav_home:
                selectedFragment = homeFragment;
                break;
            case R.id.nav_schedule:
                selectedFragment = scheduleFragment;
                break;
            case R.id.nav_outline:
                selectedFragment = outlineFragment;
                break;
            case R.id.nav_profile:
                selectedFragment = consultationFragment;
                break;
            default:
                selectedFragment = new HomeFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = homeFragment;
                    break;
                case R.id.nav_schedule:
                    selectedFragment = scheduleFragment;
                    break;
                case R.id.nav_outline:
                    selectedFragment = outlineFragment;
                    break;
                case R.id.nav_profile:
                    selectedFragment = consultationFragment;
                    break;
                default:
                    selectedFragment = new HomeFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }

    };


    private void logout(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                .client(mSharedPrefs.getInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInterface userInterface = retrofit.create(UserInterface.class);
        Call<Void> call = userInterface.deleteFCMToken();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mSharedPrefs.logout();
                    new LogoutTask().execute();
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    ActivityCompat.finishAffinity(MainActivity.this);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Terjadi Kesalahan !", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void checkTokenAvailability(){
        if(mSharedPrefs.getFirebaseToken() != null && mSharedPrefs.getToken() != null){
            Log.d("FCMTOKEN", "onResponse: "+mSharedPrefs.getFirebaseToken());
            if(!mSharedPrefs.issetFCMToken()){
                Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL)
                        .client(mSharedPrefs.getInterceptor())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserInterface auth = retrofit.create(UserInterface.class);
                Call<Void> call = auth.storeFCMToken(mSharedPrefs.getFirebaseToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            mSharedPrefs.setFcmTokenAvailability(true);
                            Log.d("FCMTOKEN", "onResponse: "+mSharedPrefs.getFirebaseToken());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        }
    }
}
