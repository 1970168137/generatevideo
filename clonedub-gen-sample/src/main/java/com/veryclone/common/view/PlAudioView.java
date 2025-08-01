package com.veryclone.common.view;

import java.util.Date;

public class PlAudioView {
    private int id;
    private String type;
    private String state;
    private int videoId;
    private int odds;
    private String description;
    private Date createdAt;
    private String name;

    // 无参构造函数
    public PlAudioView() {
    }

    // 有参构造函数
    public PlAudioView(int id, String type, String state, int videoId, int odds, String description, Date createdAt, String name) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.videoId = videoId;
        this.odds = odds;
        this.description = description;
        this.createdAt = createdAt;
        this.name = name;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getOdds() {
        return odds;
    }

    public void setOdds(int odds) {
        this.odds = odds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PlVideoView{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", videoId=" + videoId +
                ", odds=" + odds +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                '}';
    }
}    