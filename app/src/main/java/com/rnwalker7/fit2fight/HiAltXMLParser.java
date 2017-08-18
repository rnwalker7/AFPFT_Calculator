package com.rnwalker7.fit2fight;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnwalker7 on 1/5/17.
 */

public class HiAltXMLParser {

    List<HiAltGroups> hiAltList;
    private HiAltGroups hiAltGroup;
    private String text;

    public HiAltXMLParser() {
        hiAltList = new ArrayList<>();
    }

    public List<HiAltGroups> getHiAltList() {
        return hiAltList;
    }

    public List<HiAltGroups> parse(InputStream is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("AltGroup")) {
                            // create a new instance of employee
                            hiAltGroup = new HiAltGroups();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("AltGroup")) {
                            // add employee object to list
                            hiAltList.add(hiAltGroup);
                        } else if (tagname.equalsIgnoreCase("MaxTime")) {
                            hiAltGroup.setMaxTime(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("Adjustment")) {
                            hiAltGroup.setAltAdj(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("WalkTime")) {
                            hiAltGroup.setWalkTime(Integer.parseInt(text));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hiAltList;
    }
}

