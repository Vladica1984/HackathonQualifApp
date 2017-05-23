package com.vpopovic.hackathonqualifapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.vpopovic.hackathonqualifapp.R;
import com.vpopovic.hackathonqualifapp.database.DatabaseHelper;
import com.vpopovic.hackathonqualifapp.database.model.Article;

import java.sql.SQLException;

public class ArticleActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Article a;

    private EditText aName;
    private EditText aDesc;
    private EditText aPrice;
    private EditText aDate;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);


        int articleconnect = getIntent().getExtras().getInt(DetailActivity.ARTICLE_KEY);

        try {
            a = getDatabaseHelper().getmArticleDao().queryForId(articleconnect);
            aName = (EditText) findViewById(R.id.article_name);
            aDesc = (EditText) findViewById(R.id.article_description);
            aPrice = (EditText) findViewById(R.id.article_price);
            aDate = (EditText) findViewById(R.id.article_date);

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

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
