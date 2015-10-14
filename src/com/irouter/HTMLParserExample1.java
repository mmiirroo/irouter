package com.irouter;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParserExample1 {
    public static void main(String[] args) {
        System.out.println(HTMLParserExample1.class.getCanonicalName());
        System.out.println(HTMLParserExample1.class.getName());
        System.out.println(HTMLParserExample1.class.getPackage().getName());
/*        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect("http://www.baidu.com").get();

            // get page title
            String title = doc.title();
            System.out.println("title : " + title);

            // get all links
            Elements links = doc.select("a[href]");
            for (Element link : links) {

                // get the value from href attribute
                System.out.println("\nlink : " + link.attr("href"));
                System.out.println("text : " + link.text());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
