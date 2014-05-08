package com.example.shopping_list;


public class ItemsModel {

    private long id;
    private long group_id;
    private String name;
    private String translation;
    private String transcript;
    private String audio;

    public ItemsModel(){

    }
    public ItemsModel(String name, String translation, String transcript, String audio)
    {
        this.name = name;
        this.translation = translation;
        this.transcript  = transcript;
        this.audio = audio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }
}
