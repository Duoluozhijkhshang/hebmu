package com.i_am_kern.Class;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Comment extends BmobObject {
    private String content;
    private Myuser user;
    private Post post;
    private List<BmobFile> images;
    private Myuser tragetuser;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Myuser getUser() {
        return user;
    }

    public void setUser(Myuser user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<BmobFile> getImages() {
        return images;
    }

    public void setImages(List<BmobFile> images) {
        this.images = images;
    }
}
