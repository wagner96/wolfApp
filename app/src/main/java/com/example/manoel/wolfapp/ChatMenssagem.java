package com.example.manoel.wolfapp;

import java.util.Date;

/**
 * Created by Manoel on 15/10/2017.
 */

public class ChatMenssagem {
    private String menssagemText;
    private String menssagemUser;
    private Long menssagemTime;

    public ChatMenssagem(String menssagemText, String mensagemUser) {
        this.setMenssagemText(menssagemText);
        this.setMenssagemUser(getMenssagemUser());

        setMenssagemTime(new Date().getTime());
    }
    public ChatMenssagem(){

    }

    public String getMenssagemText() {
        return menssagemText;
    }

    public void setMenssagemText(String menssagemText) {
        this.menssagemText = menssagemText;
    }

    public String getMenssagemUser() {
        return menssagemUser;
    }

    public void setMenssagemUser(String menssagemUser) {
        this.menssagemUser = menssagemUser;
    }

    public Long getMenssagemTime() {
        return menssagemTime;
    }

    public void setMenssagemTime(Long menssagemTime) {
        this.menssagemTime = menssagemTime;
    }
}

