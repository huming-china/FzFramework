package com.hn.zfz.xml;

import android.util.Xml;

import com.hn.zfz.bean.FenLei;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huming on 2015/12/9.
 */
public class PullXmlUtil {
    public static ArrayList<FenLei> pullKeChengFeiLei(String xmlStr)
            throws XmlPullParserException, IOException {
        System.out.println(xmlStr);
        ArrayList<FenLei> list = new ArrayList<FenLei>();
        FenLei fenlei = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(new StringReader(xmlStr));
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if ("Table".equalsIgnoreCase(parser.getName())) {
                        fenlei = new FenLei();
                    }
                    else if (parser.getName().equalsIgnoreCase("id")) {

                        fenlei.setId(parser.nextText());
                    } else if (parser.getName().equalsIgnoreCase("name")) {
                        String next=parser.nextText();
                        fenlei.setName(next.replace("2010年国家", "").replace("2010年","").replace("2010", ""));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equalsIgnoreCase("Table")
                            && fenlei != null) {
                        list.add(fenlei);
                        fenlei = null;
                    }
                    break;
            }
            event = parser.next();
        }
        return list;
    }
}
