package com.vpopovic.hackathonqualifapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.vpopovic.hackathonqualifapp.R;
import com.vpopovic.hackathonqualifapp.database.DatabaseHelper;
import com.vpopovic.hackathonqualifapp.database.model.Article;
import com.vpopovic.hackathonqualifapp.database.model.User;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private User u;
    public static  final String ARTICLE_KEY = "article key";

    private EditText uName;
    private EditText uAdress;
    private EditText uEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int userConnection = getIntent().getExtras().getInt(MainActivity.USER_ID);

        try {
            u = getDatabaseHelper().getmUserDao().queryForId(userConnection);

            uName = (EditText) findViewById(R.id.user_name_detail);
            uAdress = (EditText) findViewById(R.id.user_adress_detail);
            uEmail = (EditText) findViewById(R.id.user_email_detail);

            uName.setText(u.getuName());
            uAdress.setText(u.getuAdress());
            uEmail.setText(u.getuEmail());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        final ListView userArticlesList = (ListView) findViewById(R.id.user_articles_list);


        try {
            List<Article>  ArtList = getDatabaseHelper().getmArticleDao().queryBuilder()
                    .where()
                    .eq(Article.FIELD_NAME_USER, u.getuId())
                    .query();

            ListAdapter artadapter = new ArrayAdapter<>(this,R.layout.list_item, ArtList);
            userArticlesList.setAdapter(artadapter);

            userArticlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Article ar= (Article) userArticlesList.getItemAtPosition(position);
                    Intent intent = new Intent(DetailActivity.this,ArticleActivity.class);
                    intent.putExtra(ARTICLE_KEY, ar.getaId());
                    startActivity(intent);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case (R.id.add_article):

                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.add_article);

                Button add = (Button) dialog.findViewById(R.id.add_article_click);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText newName = (EditText) dialog.findViewById(R.id.add_article_name);
                        EditText newDesc = (EditText) dialog.findViewById(R.id.add_article_description);
                        EditText newPrice = (EditText) dialog.findViewById(R.id.add_article_price);

                        Article newA = new Article();
                        newA.setaName(newName.getText().toString());
                        newA.setaDescription(newDesc.getText().toString());
                        newA.setaPrice(newPrice.getText().toString());
                        newA.setaDate(new Date());
                        newA.setaUser(u);

                        try {
                            getDatabaseHelper().getmArticleDao().create(newA);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                        refresh();
                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancel_input);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        refresh();
                    }
                });
                dialog.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        ListView userArticlesList = (ListView) findViewById(R.id.user_articles_list);

        if (userArticlesList != null) {
            ArrayAdapter<Article> adapter = (ArrayAdapter) userArticlesList.getAdapter();

            if (adapter != null) {

                try {
                    adapter.clear();
                    List<Article> articleList = null;
                    articleList = getDatabaseHelper().getmArticleDao().queryBuilder()
                            .where()
                            .eq(Article.FIELD_NAME_USER, u.getuId())
                            .query();
                    adapter.addAll(articleList);
                    adapter.notifyDataSetChanged();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    public DatabaseHelper getDatabaseHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
