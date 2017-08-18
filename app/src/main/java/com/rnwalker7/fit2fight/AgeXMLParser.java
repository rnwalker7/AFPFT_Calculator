package com.rnwalker7.fit2fight;

/**
 * Created by rnwalker7 on 12/30/16.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class AgeXMLParser {
    List<AgeGroups> ageGroupsList;
    private AgeGroups ageGroup;
    private String text;

    public AgeXMLParser() {
        ageGroupsList = new ArrayList<>();
    }

    public List<AgeGroups> getAgeGroupsList() {
        return ageGroupsList;
    }

    public List<AgeGroups> parse(InputStream is) {
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
                        if (tagname.equalsIgnoreCase("AgeGroup")) {
                            // create a new instance of employee
                            ageGroup = new AgeGroups();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("AgeGroup")) {
                            // add employee object to list
                            ageGroupsList.add(ageGroup);
                        } else if (tagname.equalsIgnoreCase("Size")) {
                            ageGroup.setAbCircString(text);
                        } else if (tagname.equalsIgnoreCase("AbPts")) {
                            ageGroup.setAbCircPts(Double.parseDouble(text));
                        } else if (tagname.equalsIgnoreCase("PUNum")) {
                            ageGroup.setPushupString(text);
                        } else if (tagname.equalsIgnoreCase("PUPts")) {
                            ageGroup.setPushupPts(Double.parseDouble(text));
                        } else if (tagname.equalsIgnoreCase("CHNum")) {
                            ageGroup.setCrunchString(text);
                        } else if (tagname.equalsIgnoreCase("CHPts")) {
                            ageGroup.setCrunchPts(Double.parseDouble(text));
                        } else if (tagname.equalsIgnoreCase("MaxTime")) {
                            ageGroup.setMaxTime(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("RunPts")) {
                            ageGroup.setRunPts(Double.parseDouble(text));
                        } else if (tagname.equalsIgnoreCase("Walk")) {
                            ageGroup.setWalkTime(Integer.parseInt(text));
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

        return ageGroupsList;
    }
}
