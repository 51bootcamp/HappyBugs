package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import io.happybugs.happybugs.APIInterface.APIInterface;
import io.happybugs.happybugs.R;
import io.happybugs.happybugs.model.UserReportItem;
import io.happybugs.happybugs.model.UserReportList;
import io.happybugs.happybugs.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context currContext = this;
    private Button btnEditReport;
    Call<UserReportList> requestReportList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    //startActivity(new Intent(currContext, MainActivity.class));
                    return true;
                case R.id.nav_create:
                    startActivity(new Intent(currContext, ReportActivity.class));
                    return true;
                case R.id.nav_account:
                    //TODO(minoring): Build after account view is created.
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit rfInstance = RetrofitInstance.getInstance(currContext);
        APIInterface service = rfInstance.create(APIInterface.class);
        requestReportList = service.getReportList();

        navigationSettings();
        showReportList();
    }

    public void showReportList() {
        final ListView reportListView = (ListView) findViewById(R.id.report_listview);
        final ReportListViewAdapter reportListViewAdapter = new ReportListViewAdapter();

        requestReportList.enqueue(new Callback<UserReportList>() {
            @Override
            public void onResponse(Call<UserReportList> call, Response<UserReportList> response) {
                try {
                    List<UserReportItem> userReportList = response.body().getData();
                    if (!(userReportList.isEmpty())) {
                        invisibleHomeIntroContents();
                        reportListViewAdapter.addAll(userReportList);
                        reportListView.setAdapter(reportListViewAdapter);
                    }
                } catch (NullPointerException e) {
                    startActivity(new Intent(currContext, SignInActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserReportList> call, Throwable t) {

            }
        });
    }

    public void invisibleHomeIntroContents() {
        findViewById(R.id.home_title).setVisibility(View.GONE);
        findViewById(R.id.home_content).setVisibility(View.GONE);
        findViewById(R.id.button_startreport).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // TODO(minoring): Handle navigation view item clicks.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigationSettings() {
        Button btnStartReport = (Button) findViewById(R.id.button_startreport);
        btnStartReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(currContext, ReportActivity.class));
            }
        });

        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
