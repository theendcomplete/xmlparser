package com.xml.parser.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class parser implements EntryPoint {

    //    private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL() + "upload";
    private static final String UPLOAD_ACTION_URL = "/upload";
    private final String extension = ".xml";

    @SuppressWarnings("unused")
    private Logger logger = Logger.getLogger("XMLParserLogger");

    private VerticalPanel mainPanel = new VerticalPanel();
    static private Grid xmlGrid = new Grid(1, 5);
    private FileUpload fileUpload = new FileUpload();
    private FormPanel form = new FormPanel();


    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {


        fileUpload.setName("uploadFormElement");

        form.setAction(UPLOAD_ACTION_URL);
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        form.add(fileUpload);


        //Table for Articles
//        xmlGrid.setText(0, 0, "ID");
//        xmlGrid.setText(0, 1, "Amount");
//        xmlGrid.setText(0, 2, "Start Amount");
//        xmlGrid.setText(0, 3, "PCT");
//        xmlGrid.setText(0, 4, "Price");
//        xmlGrid.setText(0, 5, "Stock Value");
        xmlGrid.setText(0, 0, "Name");
        xmlGrid.setText(0, 1, "Email");
        xmlGrid.setText(0, 2, "Phone");
        xmlGrid.setText(0, 3, "State");
        xmlGrid.setText(0, 4, "Zip");
        xmlGrid.setVisible(false);
        xmlGrid.setBorderWidth(1);


        mainPanel.add(form);
        mainPanel.add(new Button("Submit", new ClickHandler() {
            public void onClick(ClickEvent event) {
                //Get file name
                String filename = fileUpload.getFilename();
                // Check the length of the filename
                if (filename.length() != 0) {
                    // Get the extension
                    String fileExtention = filename.substring(filename.length() - extension.length(), filename.length());
                    // Check if the extension is '.pdf'
                    if (!fileExtention.equals(extension)) {
                        Window.alert("Only .xml files are allowed");
                    } else {
                        form.submit();
                    }
                } else
                    Window.alert("No file choosen");
            }
        }));
        mainPanel.add(xmlGrid);

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {

                String xml = event.getResults();
                logger.info(xml);
                parseMessage(xml);
            }
        });

        RootPanel.get("parser").add(mainPanel);
    }

    private static void parseMessage(String messageXml) {
        try {
            // parse the XML document into a DOM
            Document messageDom = XMLParser.parse(messageXml);
            int recordSize = messageDom.getElementsByTagName("record").getLength();
            //Пересчитываем количество строк в таблице
            xmlGrid.resizeRows(recordSize + 1);
            //Показываем таблицу
            if (recordSize > 0) {
                xmlGrid.setVisible(true);


                for (int i = 0; i < recordSize; i++) {

                    String nameValue = messageDom.getElementsByTagName("Name").item(i)
                            .getFirstChild().getNodeValue();
                    String emailValue = messageDom.getElementsByTagName("Email").item(i)
                            .getFirstChild().getNodeValue();
                    String phoneValue = messageDom.getElementsByTagName("Phone").item(i)
                            .getFirstChild().getNodeValue();
                    String stateValue = messageDom.getElementsByTagName("State").item(i)
                            .getFirstChild().getNodeValue();
                    String zipValue = messageDom.getElementsByTagName("Zip").item(i)
                            .getFirstChild().getNodeValue();

                    //i+1 потому, что нулевая строка таблицы - шапка
                    xmlGrid.setText(i + 1, 0, nameValue);
                    xmlGrid.setText(i + 1, 1, emailValue);
                    xmlGrid.setText(i + 1, 2, phoneValue);
                    xmlGrid.setText(i + 1, 3, stateValue);
                    xmlGrid.setText(i + 1, 4, zipValue);

                }
                Window.alert("Файл успешно загружен и прочитан. Количество записей: " + recordSize);
            }

        } catch (Exception e) {
            Window.alert("Не получилось прочесть XML :(");
        }
    }

}