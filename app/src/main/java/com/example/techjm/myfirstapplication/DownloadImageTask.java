package com.example.techjm.myfirstapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static android.R.drawable.ic_menu_camera;

/**
 * Created by techjm on 6/12/17.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    private Boolean download;
    private int a;
    private String BSpecies;
    private String BGeneral;
    private int rating;
    private Boolean up;
    private TextView textView;
    private String finalString;

    public DownloadImageTask(ImageView bmImage, TextView textView, boolean download, int a, String BSpecies, String BGeneral, int rating, boolean up) {
        this.bmImage = bmImage;
        this.download = download;
        this.a = a;
        this.BGeneral = BGeneral;
        this.BSpecies = BSpecies;
        this.rating = rating;
        this.up = up;
        this.textView = textView;
        if (!download) {
            bmImage.setImageResource(ic_menu_camera);
        }
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = "test";
        Document doc = null;
        boolean fail = true;
        int picRating;
        while (fail) {
            fail = false;
            System.out.println(a);
            try {
                String nextURL = "http://e621.net/post/show/";
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(nextURL);
                stringBuilder2.append(a);
                stringBuilder2.append("/");
                finalString = stringBuilder2.toString();
                doc = Jsoup.connect(finalString).get();
                System.out.println(a);

                    Element link1 = doc.getElementById("content");
                    Element link2 = link1.getElementById("post-view");
                    Elements deleted2 = link2.getElementsByClass("status-notice status-red");
                    for (Element link : deleted2) {
                        System.out.println("1");
                        String linkText = link.text();
                        System.out.println(linkText);
                        if (!(linkText.isEmpty())) {
                            fail = true;
                        }
                    }
                if (!fail) {
                    Element BS1 = link2.select("div").first(); //sidebar
                    Element SF1 = BS1.getElementById("stats");
                    Element SF2 = SF1.select("ul").first();
                    Element SF3 = SF2.select("li").get(2);
                    Element SF4 = SF3.select("span").first();
                    if (!(SF4.text().contains("Safe")) && MainActivity.Safe)
                    {
                        fail = true;
                    }
                    if (!fail) {
                    Element SC1 = SF2.select("li").get(3);
                    Element SC2 = SC1.select("span").first();
                        try{
                         picRating = Integer.parseInt(SC2.text());
                        }
                        catch (NullPointerException e)
                        {
                            picRating = 0;
                        }
                    if (rating != 0 && picRating < rating)
                    {
                        fail = true;
                    }
                        if (!fail) {
                    Element BS2 = BS1.select("div").get(1); //margin-bottom(2nd)
                    Elements BS = BS2.getElementsByClass("TAG-TYPE-SPECIES");
                    for (Element link : BS) {
                        String linkText = link.text();
                        System.out.println(linkText);
                        if (BSpecies != null && !BSpecies.isEmpty()) {
                            if (linkText.contains(BSpecies)) fail = true;
                        }
                    }
                            if (!fail) {
                    Elements BG = BS2.getElementsByClass("TAG-TYPE-GENERAL");
                    for (Element link : BG) {
                        String linkText = link.text();
                        System.out.println(linkText);
                        if (BGeneral != null && !BGeneral.isEmpty()) {
                            if (linkText.contains(BGeneral)) fail = true;
                        }
                        if (linkText.contains("animated")) fail = true;
                        if (linkText.contains("flash")) fail = true;
                    }
                                if (!fail) {
                                    Element link3 = link2.getElementById("right-col");
                                    Element link4 = link3.select("div").first();
                                    Element link5 = link4.select("h4").first();
                                    Element link6 = link5.select("a").first();
                                    urldisplay = link6.attr("abs:href");
                                    if (urldisplay.equals("test")) {
                                        fail = true;
                                    }
                                }}}}
                }

                //}
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (urldisplay.equals("test")) {
                fail = true;
            }
            if (fail) { System.out.println("Blacklist"); if (up) {a++;} else {a--;} }

        }
        Bitmap mIcon11 = null;
        try {
            System.out.println(urldisplay);
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        File input = new File(Environment.getExternalStorageDirectory().getPath(), "input.html");
        try{
            FileWriter fw=new FileWriter(input);
            fw.write(urldisplay);
            fw.close();
        }catch(Exception e){System.out.println(e);}
        if (download) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("E621/");
            stringBuilder2.append(a);
            stringBuilder2.append(".png");
            String finalString = stringBuilder2.toString();

            File output = new File(Environment.getExternalStorageDirectory().getPath(), finalString);
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(output);
                mIcon11.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(urldisplay);
        System.out.println("Success...");
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        textView.setText(finalString);
        textView.setLinksClickable(true);
        DisplayMessageActivity.a = a;
    }
}
