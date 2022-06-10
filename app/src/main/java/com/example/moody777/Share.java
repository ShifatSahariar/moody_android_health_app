package com.example.moody777;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.moody777.SelfTest.*;


public class Share extends AppCompatActivity {
    sqlite_layer mdb;
    //ArrayList<String> id, total_hour, date ,sleep_type ,awake_night_time,sleep_awake_type,exercise_time_was,exercise_type_was;

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private File pdfFile;
    ImageView imgdownload;
    ImageView imgdownload1;
    ImageView imgdownload2;

    ArrayList<Sleep> MyList1;
    ArrayList<Question> MyListq;

    CheckBox sleepcheck, testcheck;


    Question  Question;
    Question questionID;
    Question question;

    Sleep Sleep ;
    Context context;
    Sleep id;
    Sleep total_hour;
    Sleep date;
    Sleep sleep_type;
    Sleep awake_night_time;
    Sleep exercise_time_was;
    Sleep exercise_type_was;
    Sleep mood_yesterday_was;
    Sleep sleep_awake_type;
    private Button send , sleep, test;
    int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        mdb = new sqlite_layer(this);

        context = this;
        Sleep = new Sleep();
        Question = new Question();
        StoreDataInArray();
        StoreDataInArrayQ ();
        send = (Button) findViewById(R.id.button);
        sleep = (Button) findViewById(R.id.button1);
        test = (Button) findViewById(R.id.button2);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdfWrapperQ();
                    previewPdf ();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }
        });
        sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                previewPdf ();
            }
        });


        }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?

       sleepcheck=(CheckBox)findViewById(R.id.checkBox);
       testcheck=(CheckBox)findViewById(R.id.checkBox1);



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sleepcheck.isChecked()&&testcheck.isChecked()) {
                    try {
                        createPdfWrapper();
                        createPdfWrapperQ();
                        //multiemailNote ();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }}if(testcheck.isChecked()){
                        try {
                            createPdfWrapperQ();
                            emailNote ();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }if(sleepcheck.isChecked()){
                    try {
                        createPdfWrapper();
                        emailNote ();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }}

            }
        });
    }
    private void createPdfWrapperQ () throws FileNotFoundException, DocumentException {
        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdfQ();
            System.out.println("data created");
        }
    }
    private void showMessageOKCancelQ (String message, DialogInterface.OnClickListener okListener)
    {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void createPdfQ () throws FileNotFoundException, DocumentException {
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Document");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");

        }

        String pdfname = "questionItem.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[]{3, 3});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setFixedHeight(50);
        table.setTotalWidth(PageSize.A4.getWidth());
        table.setWidthPercentage(100);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell("number");
        table.addCell("Question");


        table.setHeaderRows(1);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i = 0; i < MyList1.size(); i++) {
            questionID = MyListq.get(i);
            question = MyListq.get(i);
            question = MyListq.get(i);
            question = MyListq.get(i);
            question = MyListq.get(i);


            int qid = questionID.getQuestionID();
            String q = question.getQuestion();

            table.addCell(String.valueOf(qid));
            table.addCell(String.valueOf(q));
        }


        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.DARK_GRAY);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
        document.add(new Paragraph(" Moody application  \n\n", f));
        document.add(table);

        document.close();
    }
        private void createPdfWrapper () throws FileNotFoundException, DocumentException {
            int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                        showMessageOKCancel("You need to allow access to Storage",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    REQUEST_CODE_ASK_PERMISSIONS);
                                        }
                                    }
                                });
                        return;
                    }
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                }
                return;
            } else {
                createPdf();
                System.out.println("data created");
            }
        }
        private void showMessageOKCancel (String message, DialogInterface.OnClickListener okListener)
        {
            new AlertDialog.Builder(context)
                    .setMessage(message)
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
        private void createPdf () throws FileNotFoundException, DocumentException {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Document");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.i(TAG, "Created a new directory for PDF");

            }

            String pdfname = "SleepItem.pdf";
            pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
            OutputStream output = new FileOutputStream(pdfFile);
            Document document = new Document(PageSize.A4);
            PdfPTable table = new PdfPTable(new float[]{3, 3, 3, 3, 3, 3, 3, 3});
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setFixedHeight(50);
            table.setTotalWidth(PageSize.A4.getWidth());
            table.setWidthPercentage(100);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell("date");
            table.addCell("Total hour");
            table.addCell("sleep type");
            table.addCell("awake night time");
            table.addCell("sleep awake type");
            table.addCell("mood");
            table.addCell("exercise time");
            table.addCell("exercise type");


            table.setHeaderRows(1);
            PdfPCell[] cells = table.getRow(0).getCells();
            for (int j = 0; j < cells.length; j++) {
                cells[j].setBackgroundColor(BaseColor.GRAY);
            }
            for (int i = 0; i < MyList1.size(); i++) {
                id = MyList1.get(i);
                date = MyList1.get(i);
                total_hour = MyList1.get(i);
                sleep_type = MyList1.get(i);
                awake_night_time = MyList1.get(i);
                sleep_awake_type = MyList1.get(i);
                mood_yesterday_was = MyList1.get(i);
                exercise_time_was = MyList1.get(i);
                exercise_type_was = MyList1.get(i);

                String pricen = total_hour.getTotal_hour();
                String daten = date.getDate();
                String typen = sleep_type.getSleep_type();
                String awake = awake_night_time.getAwake_night_time();
                String sleepawake = sleep_awake_type.getAwake_night_time();
                String moodtype = mood_yesterday_was.getMood_yesterday_was();
                String exe = exercise_time_was.getExercise_time_was();
                String exetype = exercise_type_was.getExercise_type_was();

                table.addCell(String.valueOf(daten.substring(0, 10)));
                table.addCell(String.valueOf(pricen));
                table.addCell(String.valueOf(typen));
                table.addCell(String.valueOf(awake));
                table.addCell(String.valueOf(sleepawake));
                table.addCell(String.valueOf(moodtype));
                table.addCell(String.valueOf(exe));
                table.addCell(String.valueOf(exetype));

            }
            PdfWriter.getInstance(document, output);
            document.open();
            Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.YELLOW);
            Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
            document.add(new Paragraph(" Moody application  \n\n", f));
            document.add(table);

            document.close();

        }
        private void previewPdf () {
            Intent testIntent = new Intent(Intent.ACTION_VIEW);
            testIntent.setType("application/pdf");

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            context.startActivity(intent);

        }
        private void emailNote ()
        {

            Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), BuildConfig.APPLICATION_ID + ".provider", pdfFile);

            Intent email = new Intent(Intent.ACTION_SEND);
            email.setType("application/pdf");

            email.putExtra(Intent.EXTRA_STREAM, uri);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_SUBJECT, "Report");

            context.startActivity(email);

        }
    /*private void multiemailNote ()
    {


        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("application/pdf");
        email.setAction(Intent.ACTION_SEND_MULTIPLE);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_SUBJECT, "Report");

       File folder = new File("/Document");
        File[] listOfFiles = folder.listFiles();

      *//*  for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }*//*
        ArrayList<Uri> files = new ArrayList<Uri>();
      *//*  try (Stream<Path> walk = Files.walk(Paths.get("C:\\projects"))) {

            List<String> result = walk.map(x -> x.toString())
                    .filter(f -> f.contains("HeaderAnalyzer.java"))
                    .collect(Collectors.toList());

            result.forEach(System.out.println);

        } catch (IOException e) {
            e.printStackTrace();
        }*//*

        for(File path : listOfFiles *//* List of the files you want to send *//*) {
            Uri uri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), BuildConfig.APPLICATION_ID + ".provider", path);
            files.add(uri);
        }
        email.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
        startActivity(email);
    }*/

        void StoreDataInArray () {
            Cursor cursor = mdb.readAllDataS();
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
            } else {
                MyList1 = new ArrayList<Sleep>();
                while (cursor.moveToNext()) {

                    Sleep.setId(cursor.getString(0));
                    Sleep.setDate(cursor.getString(1));
                    Sleep.setTotal_hour(cursor.getString(2));
                    Sleep.setSleep_type(cursor.getString(3));
                    Sleep.setAwake_night_time(cursor.getString(4));
                    Sleep.setSleep_awake_type(cursor.getString(5));
                    Sleep.setMood_yesterday_was(cursor.getString(6));
                    Sleep.setExercise_time_was(cursor.getString(7));
                    Sleep.setExercise_type_was(cursor.getString(8));


                    MyList1.add(Sleep);

                    Sleep = new Sleep();
                }
            }

        }
    void StoreDataInArrayQ () {
        Cursor cursor = mdb.readAllDataQ();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        } else {
            MyListq = new ArrayList<Question>();
            while (cursor.moveToNext()) {

                Question.setQuestionID(cursor.getInt(0));
                Question.setQuestion(cursor.getString(1));
                Question.setQuestion(cursor.getString(2));
                Question.setQuestion(cursor.getString(3));
                Question.setQuestion(cursor.getString(4));
                Question.setQuestion(cursor.getString(5));


                MyListq.add(Question);

                Question = new Question();
            }
        }

    }

    }