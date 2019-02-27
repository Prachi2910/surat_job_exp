package com.example.karmarkarsourabh.surat_job_expo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.karmarkarsourabh.surat_job_expo.Utill.UrlCall;
import com.example.karmarkarsourabh.surat_job_expo.Utill.utill;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;

public class Resume_form extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout name, profile, experience, skill, education, email, mobile_number;
    private Button save;
    private long enqueID;
    private DownloadManager manager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_form);

        getSupportActionBar().setTitle(R.string.resumeFormTitle);

        //initialization text input layout
        name = (TextInputLayout) findViewById(R.id.CV_PersonName);
        email = (TextInputLayout) findViewById(R.id.CV_Email);
        mobile_number = (TextInputLayout) findViewById(R.id.CV_MobileNumber);
        profile = (TextInputLayout) findViewById(R.id.CV_PersonalProfile);
        experience = (TextInputLayout) findViewById(R.id.CV_WorkExprience);
        education = (TextInputLayout) findViewById(R.id.CV_Education);
        skill = (TextInputLayout) findViewById(R.id.CV_Skills);

        save = (Button) findViewById(R.id.cv_save_button);

//        BroadcastReceiver receiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                String action = intent.getAction();
//                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
//                    DownloadManager.Query query = new DownloadManager.Query();
//                    query.setFilterById(enqueID);
//                    Cursor cursor = manager.query(query);
//
//                    if (cursor.moveToFirst()) {
//                        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
//                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
//                            showPdf(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
//                            Log.e("path", "onReceive: " + cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
//                        }
//                    }
//                }
//            }
//        };
//        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        //onClick call

        save.setOnClickListener(this);

        //ignore file exposure
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cv_save_button:
                if (isNonEmptyValid()) {
                    new BuildResume().execute();
                }
                break;
            default:
                Snackbar.make(v, "you have selected wrong button", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean isNonEmptyValid() {
        boolean isEmpty = false;

//        name = (TextInputLayout) findViewById(R.id.CV_PersonName);
//        email = (TextInputLayout) findViewById(R.id.CV_Email);
//        mobile_number = (TextInputLayout) findViewById(R.id.CV_MobileNumber);
//        profile = (TextInputLayout) findViewById(R.id.CV_PersonalProfile);
//        experience = (TextInputLayout) findViewById(R.id.CV_WorkExprience);
//        education = (TextInputLayout) findViewById(R.id.CV_Education);
//        skill = (TextInputLayout) findViewById(R.id.CV_Skills);

        if (name.getEditText().getText().toString().isEmpty()){
            isEmpty = true;
        }
        return isEmpty;
    }

    private boolean isValid() {
        boolean isvalid = false;
//        if ()
        return isvalid;
    }

    private class BuildResume extends AsyncTask<String, String, String> {
        private Dialog msgDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            msgDialog = new Dialog(Resume_form.this);
            msgDialog.setTitle(getString(R.string.resumeBuildingMsg));
            msgDialog.setCancelable(false);
            msgDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            msgDialog.dismiss();
//            manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(getResources().getString(R.string.ResumeImage_uri) + "simpleCV/" + name.getEditText().getText().toString() + ".pdf"));
//            enqueID = manager.enqueue(request);
            Log.e("downloaded", "onPostExecute: ");
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        protected String doInBackground(String... strings) {

            HttpUrl.Builder builder = HttpUrl.parse(getResources().getString(R.string.ResumeImage_uri) + "simpleCV/simpleCV.php").newBuilder();
            builder.addQueryParameter("name", name.getEditText().getText().toString());
            builder.addQueryParameter("number", mobile_number.getEditText().getText().toString());
            builder.addQueryParameter("profile", profile.getEditText().getText().toString());
            builder.addQueryParameter("email", email.getEditText().getText().toString());
            builder.addQueryParameter("education", education.getEditText().getText().toString());
            builder.addQueryParameter("experience", experience.getEditText().getText().toString());
            builder.addQueryParameter("skills", skill.getEditText().getText().toString());

            UrlCall.callUrl(builder);

            String extStorageDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
            File folder = new File(extStorageDirectory, "SuratJobExpoPDF");
            if (!folder.exists()) {
                Log.e("mkdir", "doInBackground: " + folder.getAbsolutePath() + " " + folder.mkdir());
            }
            File pdfFile = new File(folder, name.getEditText().getText().toString() + ".pdf");

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                Log.e("permission", "onCreate: not granted");
            } else {
                utill.DownloadFile(getResources().getString(R.string.ResumeImage_uri) + "simpleCV/" + name.getEditText().getText().toString() + ".pdf", pdfFile);
                showPdf(pdfFile.getAbsolutePath());
            }
            return "ohk";
        }
    }

    public void showPdf(String path) {
        File file = new File(path);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
