package com.i_am_kern.Class;

import android.provider.ContactsContract;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Post extends BmobObject implements MultiItemEntity{

   private String title="标题";
   private Myuser author;
   private String content="内容";
   private BmobRelation likes;
   private Integer type = 1;
   private List<String> suoluetuUrls;
   private int itemType=0;
   private int sum_comment_num;

    public int getSum_comment_num() {
        return sum_comment_num;
    }

    public void setSum_comment_num(int sum_comment_num) {
        this.sum_comment_num = sum_comment_num;
    }

    public Post() {
    }

    public List<String> getSuoluetuUrls() {
        return suoluetuUrls;
    }

    public void setSuoluetuUrls(List<String> suoluetuUrls) {
        this.suoluetuUrls = suoluetuUrls;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Myuser getAuthor() {
        return author;
    }

    public void setAuthor(Myuser author) {
        this.author = author;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
