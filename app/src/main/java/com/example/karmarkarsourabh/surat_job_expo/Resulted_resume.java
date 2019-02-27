package com.example.karmarkarsourabh.surat_job_expo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Resulted_resume extends AppCompatActivity {
    private WebView resumePreview;
    private FloatingActionButton downloadButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resulted_resume);

        //initialization
        resumePreview = (WebView) findViewById(R.id.pdf_preview);
        downloadButton = (FloatingActionButton) findViewById(R.id.FB_download_button);

        resumePreview.loadUrl("http://docs.google.com/gview?embedded=true&url="+getResources().getString(R.string.ResumeImage_uri)+"simpleCV/resume.pdf");
    }
}
