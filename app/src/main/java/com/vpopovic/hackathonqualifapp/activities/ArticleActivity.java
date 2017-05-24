package com.vpopovic.hackathonqualifapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.vpopovic.hackathonqualifapp.R;
import com.vpopovic.hackathonqualifapp.database.DatabaseHelper;
import com.vpopovic.hackathonqualifapp.database.model.Article;

import java.sql.SQLException;

public class ArticleActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Article a;

    private TextView aName;
    private TextView aDesc;
    private TextView aPrice;
    private TextView aDate;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        int artId = getIntent().getExtras().getInt(DetailActivity.ARTICLE_KEY_USER);

        try {
            a = getDatabaseHelper().getmArticleDao().queryForId(artId);
            aName = (TextView) findViewById(R.id.article_name);
            aDesc = (TextView) findViewById(R.id.article_description);
            aPrice = (TextView) findViewById(R.id.article_price);
            aDate = (TextView) findViewById(R.id.article_date);

            aName.setText(a.getaName());
            aDesc.setText(a.getaDescription());
            aPrice.setText(a.getaPrice());
            aDate.setText(a.getaDate().toString());

        } catch(
                SQLException e)

        {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.delete_article):

                try {
                    getDatabaseHelper().getmArticleDao().delete(a);
                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
