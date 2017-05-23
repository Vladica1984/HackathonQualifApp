package com.vpopovic.hackathonqualifapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.vpopovic.hackathonqualifapp.database.model.Article;
import com.vpopovic.hackathonqualifapp.database.model.User;

import java.sql.SQLException;

/**
 * Created by Alen on 23-May-17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "userarticles.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<User, Integer> mUserDao = null;
    private Dao<Article, Integer> mArticleDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource,Article.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Article.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<User, Integer> getmUserDao() throws SQLException {
        if (mUserDao == null) {
            mUserDao = getDao(User.class);
        }

        return mUserDao;
    }

    public Dao<Article,Integer> getmArticleDao() throws SQLException {
        if (mArticleDao == null){
            mArticleDao=getDao(Article.class);
        }
        return mArticleDao;
    }
    @Override
    public void close() {

        mUserDao = null;
        mArticleDao=null;

        super.close();
    }
}
