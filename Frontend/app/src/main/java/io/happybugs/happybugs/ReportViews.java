package io.happybugs.happybugs;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ReportViews extends AppCompatActivity {
    View whatView;
    View whereView;
    View whenView;
    View whoView;
    View detailsView;

    public ReportViews(View whatView, View whereView,
                 View whenView, View whoView, View detailsView) {
        this.whatView = whatView;
        this.whereView = whereView;
        this.whenView = whenView;
        this.whoView = whoView;
        this.detailsView = detailsView;
    }

    public View getWhatView() {
        return whatView;
    }

    public View getWhereView() {
        return whereView;
    }

    public View getWhenView() {
        return whenView;
    }

    public View getWhoView() {
        return whoView;
    }

    public View getDetailsView() {
        return detailsView;
    }
}
