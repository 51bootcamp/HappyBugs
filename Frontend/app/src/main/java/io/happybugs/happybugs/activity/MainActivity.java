package io.happybugs.happybugs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import io.happybugs.happybugs.models.ReportData;
import io.happybugs.happybugs.models.ReportDataList;
import io.happybugs.happybugs.network.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context curContext = this;
    private Button btnStartReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartReport = (Button) findViewById(R.id.button_startreport);
        btnStartReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(curContext,ReportActivity.class));
            }
        });

        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) findViewById(R.id.bottom_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (userHasReport()) {
            invisibleHomeIntroContents();
            showReportList();
        }
    }

    public boolean userHasReport() {
        //TODO(minoring): Check if the user has reports using REST API.
        Retrofit rfInstance = RetrofitInstance.getInstance();
        APIInterface service = rfInstance.create(APIInterface.class);

        Call<ReportDataList> request = service.showReportList();
        request.enqueue(new Callback<ReportDataList>() {
            @Override
            public void onResponse(Call<ReportDataList> call, Response<ReportDataList> response) {
                ReportDataList responseBody = response.body();
                System.out.println(responseBody.getReportList());
            }

            @Override
            public void onFailure(Call<ReportDataList> call, Throwable t) {

            }
        });
        return false;
    }

    public void invisibleHomeIntroContents() {
        findViewById(R.id.home_title).setVisibility(View.GONE);
        findViewById(R.id.home_content).setVisibility(View.GONE);
        findViewById(R.id.button_startreport).setVisibility(View.GONE);
    }

    public void showReportList() {
        //TODO(minoring): Change hard-coded report list to REST API json response.
        ListView reportListView = (ListView) findViewById(R.id.report_listview);
        ReportListViewAdapter reportListViewAdapter = new ReportListViewAdapter();

        reportListViewAdapter.addItem("Greyhound divisively hello coldly");
        reportListViewAdapter.addItem("Greyhound divisively hello coldly");
        reportListViewAdapter.addItem("Greyhound divisively hello coldly");

        reportListView.setAdapter(reportListViewAdapter);
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
}
