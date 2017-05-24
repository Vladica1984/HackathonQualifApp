package com.vpopovic.hackathonqualifapp.database.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Alen on 23-May-17.
 */

@DatabaseTable(tableName = Article.TABLE_NAME_ARTICLE)
public class Article {

    public static final String TABLE_NAME_ARTICLE = "article";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_ARTICLE_NAME = "articleName";
    public static final String FIELD_ARTICLE_DESCRIPTION = "description";
    public static final String FIELD_ARTICLE_PRICE ="price";
    public static final String FIELD_ARTICLE_DATE = "date";

    public static final String FIELD_NAME_USER = "user";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int aId;
    @DatabaseField(columnName = FIELD_ARTICLE_NAME)
    private String aName;
    @DatabaseField(columnName = FIELD_ARTICLE_DESCRIPTION)
    private String aDescription;
    @DatabaseField(columnName = FIELD_ARTICLE_PRICE)
    private String aPrice;
    @DatabaseField(columnName = FIELD_ARTICLE_DATE)
    private Date aDate;

    @DatabaseField(columnName = FIELD_NAME_USER, foreign = true, foreignAutoRefresh = true)
    private User aUser;


    public Article(){}

    public int getaId() {
        return aId;
    }

    public void setaId(int aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaDescription() {
        return aDescription;
    }

    public void setaDescription(String aDescription) {
        this.aDescription = aDescription;
    }

    public String getaPrice() {
        return aPrice;
    }

    public void setaPrice(String aPrice) {
        this.aPrice = aPrice;
    }

    public Date getaDate() {
        return aDate;
    }

    public void setaDate(Date aDate) {
        this.aDate = aDate;
    }

    public User getaUser() {
        return aUser;
    }

    public void setaUser(User aUser) {
        this.aUser = aUser;
    }


    @Override
    public String toString() {
        return  aName;
    }
}
