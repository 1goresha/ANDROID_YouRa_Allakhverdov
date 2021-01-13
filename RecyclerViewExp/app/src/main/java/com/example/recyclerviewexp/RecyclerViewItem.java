package com.example.recyclerviewexp;

public class RecyclerViewItem {

    private int viewResource;
    private String text1;
    private String text2;

    public RecyclerViewItem(int viewResource, String text1, String text2) {
        this.viewResource = viewResource;
        this.text1 = text1;
        this.text2 = text2;
    }

    public int getViewResource() {
        return viewResource;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
