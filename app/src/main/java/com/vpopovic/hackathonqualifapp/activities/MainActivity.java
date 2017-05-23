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
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.vpopovic.hackathonqualifapp.R;
import com.vpopovic.hackathonqualifapp.database.DatabaseHelper;
import com.vpopovic.hackathonqualifapp.database.model.Article;
import com.vpopovic.hackathonqualifapp.database.model.User;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    public static final String USER_ID = "whichUser";
    public static final String ARTICLE_KEY = "articleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ListView articlesView = (ListView) findViewById(R.id.all_articles_list);
        List<Article> articleList = null;
        try {
            articleList = getDatabaseHelper().getmArticleDao().queryForAll();

            ListAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item, articleList);
            articlesView.setAdapter(adapter);

            articlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Article a = (Article) articlesView.getItemAtPosition(position);

                    Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
                    intent.putExtra(ARTICLE_KEY, a.getaId());
                    startActivity(intent);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId())
        {
            case (R.id.login_dialog_presed):
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.login_dialog);

                Button confirm = (Button) dialog.findViewById(R.id.confirm_login);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText email=(EditText) dialog.findViewById(R.id.user_email);
                        EditText password = (EditText) dialog.findViewById(R.id.user_password_login);

                        List<User> checkList=null;
                        try {

                            checkList = getDatabaseHelper().getmUserDao().queryBuilder()
                                    .where()
                                    .eq(User.FIELD_USER_EMAIL, email.getText())
                                    .query();

                            if(checkList.size()==1){
                                User theUser = checkList.get(0);
                                String pass=theUser.getuPassword().toString();
                                String typedpass=password.getText().toString();

                                if (typedpass.equals(pass)){

                                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                    int userident= checkList.get(0).getuId();
                                    intent.putExtra(USER_ID, userident );
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(MainActivity.this,"Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(MainActivity.this,"There is more than one user with this e-mail!!!", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

                Button cancel = (Button) dialog.findViewById(R.id.cancel_login);
                cancel.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
                break;

            case (R.id.register_dialog_presed):
                final Dialog regDialog = new Dialog(this);
                regDialog.setContentView(R.layout.register_dialog);

                Button create = (Button) regDialog.findViewById(R.id.create_user);
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText userName = (EditText) regDialog.findViewById(R.id.user_name);
                        EditText userAdress = (EditText) regDialog.findViewById(R.id.user_adress);
                        EditText userEmail  = (EditText) regDialog.findViewById(R.id.user_email);
                        EditText userPassword = (EditText) regDialog.findViewById(R.id.user_password_register);

                        User newUser = new User();
                        newUser.setuName(userName.getText().toString());
                        newUser.setuAdress(userAdress.getText().toString());
                        newUser.setuEmail(userEmail.getText().toString());
                        newUser.setuPassword(userPassword.getText().toString());

                        try {
                            getDatabaseHelper().getmUserDao().create(newUser);

                            regDialog.dismiss();

                            Toast.makeText(MainActivity.this, " Registration successful!", Toast.LENGTH_SHORT).show();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                });

                Button cancelCreate = (Button) regDialog.findViewById(R.id.cancel_create);
                cancelCreate.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        regDialog.dismiss();
                    }
                });

                regDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh(){

        ListView listView = (ListView) findViewById(R.id.all_articles_list);

        if(listView !=null){
            ArrayAdapter<Article> adapter= (ArrayAdapter) listView.getAdapter();

            if(adapter !=null){

                try {

                    adapter.clear();
                    List<Article> articleList= null;
                    articleList = getDatabaseHelper().getmArticleDao().queryForAll();
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
