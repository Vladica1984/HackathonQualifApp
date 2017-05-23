package com.vpopovic.hackathonqualifapp.database.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Alen on 23-May-17.
 */
@DatabaseTable(tableName = User.TABLE_NAME_USER)
public class User {

    public static final String TABLE_NAME_USER = "user";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_USER_NAME =  "name";
    public static final String FIELD_USER_ADRESS = "adress";
    public static final String FIELD_USER_EMAIL = "email";
    public static final String FIELD_USER_PASSWORD = "password";

    public static final  String TABLE_USER_ARTICLES = "articles";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int uId;
    @DatabaseField(columnName = FIELD_USER_NAME)
    private String uName;
    @DatabaseField(columnName = FIELD_USER_ADRESS)
    private String uAdress;
    @DatabaseField(columnName = FIELD_USER_EMAIL)
    private String uEmail;
    @DatabaseField(columnName = FIELD_USER_PASSWORD)
    private String uPassword;

    @ForeignCollectionField(columnName = User.TABLE_USER_ARTICLES, eager = true)
    private ForeignCollection<Article> articles;


    public User(){}

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuAdress() {
        return uAdress;
    }

    public void setuAdress(String uAdress) {
        this.uAdress = uAdress;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public ForeignCollection<Article> getArticles() {
        return articles;
    }

    public void setArticles(ForeignCollection<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "User{" +
                "uName='" + uName + '\'' +
                ", uAdress='" + uAdress + '\'' +
                ", uEmail='" + uEmail + '\'' +
                '}';
    }
}
