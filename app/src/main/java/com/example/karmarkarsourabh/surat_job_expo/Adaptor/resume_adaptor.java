package com.example.karmarkarsourabh.surat_job_expo.Adaptor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karmarkarsourabh.surat_job_expo.Modal.resume.resume_md;
import com.example.karmarkarsourabh.surat_job_expo.R;
import com.example.karmarkarsourabh.surat_job_expo.Resume_form;
import com.squareup.picasso.Picasso;

public class resume_adaptor extends RecyclerView.Adapter<resume_adaptor.ViewHolder> implements View.OnClickListener {
    resume_md rm_modal;
    Context context;
    public resume_adaptor(resume_md rm_modal, Context context) {
        this.rm_modal = rm_modal;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_resume, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.thm.setText(Html.fromHtml(rm_modal.getData().get(i).getTheme_name()));
        viewHolder.cat.setText(Html.fromHtml(rm_modal.getData().get(i).getTheme_category()));
        Picasso.get().load(context.getResources().getString(R.string.ResumeImage_uri)+rm_modal.getData().get(i).getTheme_prev()).into(viewHolder.cv);
        viewHolder.layout.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return rm_modal.getData().size();
    }

    @Override
    public void onClick(View v) {
        context.startActivity(new Intent(context, Resume_form.class));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView thm, cat;
        ImageView arrow, cv;
        ConstraintLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            thm = (TextView) itemView.findViewById(R.id.theme_tv);
            cat = (TextView) itemView.findViewById(R.id.cat_tv);
            arrow = (ImageView) itemView.findViewById(R.id.arrow);
            cv = (ImageView) itemView.findViewById(R.id.resume_previwe);
            layout = (ConstraintLayout) itemView.findViewById(R.id.resumeID);
        }
    }
}

//https://drive.google.com/viewerng/viewer?url=