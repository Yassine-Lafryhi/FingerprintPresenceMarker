package me.fingerprint.presence.marker.controllers;

import java.awt.*;
import java.io.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.layout.font.FontProvider;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import jssc.SerialPort;
import jssc.SerialPortException;
import me.fingerprint.presence.marker.Main;
import me.fingerprint.presence.marker.database.Database;
import me.fingerprint.presence.marker.models.Session;
import me.fingerprint.presence.marker.models.Student;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Controller {
    @FXML
    DatePicker datePicker;
    @FXML
    Pane pane;
    @FXML
    ScrollPane scrollPane;
    @FXML
    Button connectButton;
    SerialPort serialPort;
    String receivedData;
    String theDate;

    public void connect() {
        Thread thread = new Thread(() -> {
            try {
                serialPort = new SerialPort("/dev/cu.SPM-DevB");
                serialPort.openPort();
                serialPort.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
            } catch (SerialPortException exception) {
                exception.printStackTrace();
            }
        });
        thread.start();
        connectButton.setText("Connected");
    }

    public void getList() {
        try {
            LocalDate localDate = datePicker.getValue();
            String year = localDate.toString().split("-")[0];
            String month = localDate.toString().split("-")[1];
            String day = localDate.toString().split("-")[2];
            String date = year + "/" + month + "/" + day;
            serialPort.writeBytes(date.getBytes());
            try {
                Thread.sleep(2500);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            try {
                byte[] bytes = serialPort.readBytes(serialPort.getInputBufferBytesCount());
                receivedData = new String(bytes, StandardCharsets.UTF_8);
                serialPort.closePort();
            } catch (SerialPortException exception) {
                exception.printStackTrace();
            }
        } catch (SerialPortException exception) {
            exception.printStackTrace();
        }
        //TODO
        receivedData = "[{\"id\":1,\"date\":\"18/10/2020\",\"from\":\"08:00\",\"to\":\"10:00\",\"numbers\":\"1,2,3,4,5\"},{\"id\":2,\"date\":\"12/12/2020\",\"from\":\"10:00\",\"to\":\"12:00\",\"numbers\":\"1,2,3,4,5\"},{\"id\":3,\"date\":\"12/12/2020\",\"from\":\"14:00\",\"to\":\"16:00\",\"numbers\":\"1,2,3,4,5\"},{\"id\":4,\"date\":\"12/12/2020\",\"from\":\"16:00\",\"to\":\"18:00\",\"numbers\":\"1,2,3,4,5\"}]";
        generateList();
    }

    private void generateList() {
        int session = 1;
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Session>>() {
        }.getType();
        ArrayList<Session> sessions = gson.fromJson(receivedData, type);
        ArrayList<Student> students = new ArrayList<>();
        try {
            String query = "SELECT Id, FirstName, LastName FROM Students";
            PreparedStatement preparedStatement = Database.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        for (int i = 0; i < students.size(); i++) {
            if (!sessions.get(session - 1).getNumbers().contains(String.valueOf(i + 1))) {
                students.get(i).setAbsent(true);
            }
        }

        String date = sessions.get(session - 1).getDate();
        String from = sessions.get(session - 1).getFrom();
        String to = sessions.get(session - 1).getTo();

        scrollPane.setFitToWidth(true);
        Label subjectTextLabel = createLabel(34.0d, 156.0d, 8.0d, 26.0d, 24.0d, "#000000", "#ffffff", "Subject :");
        pane.getChildren().addAll(subjectTextLabel);
        Label dateLabel = createLabel(34.0d, 160.0d, 615.0d, 26.0d, 24.0d, "#000000", "#ffffff", date);
        pane.getChildren().addAll(dateLabel);
        Label timeLabel = createLabel(34.0d, 160.0d, 615.0d, 59.0d, 24.0d, "#000000", "#ffffff", from + " -> " + to);
        pane.getChildren().addAll(timeLabel);
        Label teacherTextLabel = createLabel(34.0d, 156.0d, 8.0d, 59.0d, 24.0d, "#000000", "#ffffff", "Teacher :");
        pane.getChildren().addAll(teacherTextLabel);
        Label teacherLabel = createLabel(34.0d, 453.0d, 163.0d, 59.0d, 24.0d, "#000000", "#ffffff", "R.ROBERT");
        pane.getChildren().addAll(teacherLabel);
        Label subjectLabel = createLabel(34.0d, 453.0d, 163.0d, 26.0d, 24.0d, "#000000", "#ffffff", "OOPs concepts in Java");
        pane.getChildren().addAll(subjectLabel);
        Label numberLabel = createLabel(34.0d, 43.0d, 121.0d, 92.0d, 24.0d, "#000000", "#ffffff", "N");
        pane.getChildren().addAll(numberLabel);
        Label firstNameTextLabel = createLabel(34.0d, 227.0d, 163.0d, 92.0d, 24.0d, "#000000", "#ffffff", "First name");
        pane.getChildren().addAll(firstNameTextLabel);
        Label lastNameTextLabel = createLabel(34.0d, 227.0d, 389.0d, 92.0d, 24.0d, "#000000", "#ffffff", "Last name");
        pane.getChildren().addAll(lastNameTextLabel);
        Label signatureLabel = createLabel(34.0d, 160.0d, 615.0d, 92.0d, 24.0d, "#000000", "#ffffff", "Signature");
        pane.getChildren().addAll(signatureLabel);
        double y = 92.0d;
        for (int i = 0; i < students.size(); i++) {
            y += 33d;
            pane.getChildren().addAll(createLabel(34.0d, 43.0d, 121.0d, y, 24.0d, "#000000", "#FFFFFF", String.valueOf(i + 1)));
            if (students.get(i).isAbsent()) {
                pane.getChildren().addAll(createLabel(34.0d, 227.0d, 163.0d, y, 24.0d, "#000000", "#FFFFFF", students.get(i).getLastName().toUpperCase()));
                pane.getChildren().addAll(createLabel(34.0d, 227.0d, 389.0d, y, 24.0d, "#000000", "#FFFFFF", students.get(i).getFirstName().toUpperCase()));
                pane.getChildren().addAll(createLabel(34.0d, 160.0d, 615.0d, y, 24.0d, "#000000", "#e74c3c", "Absent"));
            } else {
                pane.getChildren().addAll(createLabel(34.0d, 227.0d, 163.0d, y, 24.0d, "#000000", "#FFFFFF", students.get(i).getLastName().toUpperCase()));
                pane.getChildren().addAll(createLabel(34.0d, 227.0d, 389.0d, y, 24.0d, "#000000", "#FFFFFF", students.get(i).getFirstName().toUpperCase()));
                pane.getChildren().addAll(createLabel(34.0d, 160.0d, 615.0d, y, 24.0d, "#000000", "#2ecc71", "Present"));
            }
        }
        Label listTitleLabel = createLabel(114.0, y - 33d, -1284.0d, 1384.5d, 34.0d, "#000000", "#ffffff", "Attendance List");
        listTitleLabel.setRotate(-90d);
        pane.getChildren().addAll(listTitleLabel);
        Label teacherSignatureTextLabel = createLabel(34.0d, 382.0d, 8.0d, y + 33d, 24.0d, "#000000", "#ffffff", "Teacher's signature :");
        pane.getChildren().addAll(teacherSignatureTextLabel);
        Label teacherSignatureLabel = createLabel(34.0d, 386.0d, 389.0d, y + 33d, 24.0d, "#000000", "#ffffff", "R.ROBERT");
        pane.getChildren().addAll(teacherSignatureLabel);

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
        Template template = velocityEngine.getTemplate("templates/list.vm");

        VelocityContext context = new VelocityContext();
        context.put("title", "Attendance List");
        context.put("date", date);
        context.put("time", from + " -> " + to);
        context.put("students", students);

        StringWriter stringWriter = new StringWriter();
        template.merge(context, stringWriter);
        try {
            PrintWriter writer = new PrintWriter(new File("").getAbsolutePath() + "/lists/list.html", "UTF-8");
            writer.println(stringWriter.toString());
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException exception) {
            exception.printStackTrace();
        }
        theDate = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        try {
            createPdf(new File("").getAbsolutePath() + "/lists/list.html", new File("").getAbsolutePath() + "/fonts", new File("").getAbsolutePath() + "/lists/" + theDate + ".pdf");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        File file = new File(new File("").getAbsolutePath() + "/lists/list.html");
        if (file.exists()) {
            file.delete();
        }
    }


    public void createPdf(String src, String font, String dest) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new DefaultFontProvider(false, false, false);
        fontProvider.addDirectory(font);
        properties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new File(src), new File(dest), properties);
    }

    public void Exit() {
        System.exit(0);
    }

    public void Minimize() {
        Main.stage.setIconified(true);
    }

    public Label createLabel(double height, double width, double x, double y, double fontSize, String color, String backgroundColor, String text) {
        Label label = new Label();
        label.setPrefHeight(height);
        label.setPrefWidth(width);
        label.setLayoutX(x);
        label.setStyle("-fx-background-color:" + backgroundColor + ";-fx-border-color: black;");
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Paint.valueOf(color));
        label.setLayoutY(y);
        label.setFont(Font.font("DINRoundPro-Medi", fontSize));
        label.setText(text);
        return label;
    }

    public void openPdfFile() {
        if (Desktop.isDesktopSupported()) {
            try {
                File list = new File(new File("").getAbsolutePath() + "/lists/" + theDate + ".pdf");
                Desktop.getDesktop().open(list);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
