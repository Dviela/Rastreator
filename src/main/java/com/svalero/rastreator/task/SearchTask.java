package com.svalero.rastreator.task;

import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SearchTask extends Task<Integer> {

    private String url;
    private List<String> keyboards;

    public SearchTask(String url, List<String> keyboards)throws MalformedURLException {
        this.url = url;
        this.keyboards = keyboards;
    }

    @Override
    protected Integer call() throws Exception {
        Document document = Jsoup.connect(url).get();
        for (String keyboard : this.keyboards) {
            int numProcessed = 0;
            int numFound = 0;
            updateProgress(numProcessed, document.select("p").size());
            for (Element text : document.select("p")) {
                numProcessed++;
                try{
                    Thread.sleep(10);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                if (text.text().toLowerCase().contains(keyboard)){
                    System.out.println("Success " +url + " " + numFound + " " + keyboard);
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    numFound++;
                }
                updateMessage(numFound + "article found whith keyboard " + keyboard);
                updateProgress(numProcessed, document.select("p").size());
            }

        }
        return null;
    }
}
