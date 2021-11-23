package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityProfileBinding;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<student> mListUsert;

    public UserAdapter(List<student> mListUsert) {
        this.mListUsert = mListUsert;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ActivityProfileBinding activityProfileBinding = ActivityProfileBinding.inflate(
            LayoutInflater.from(parent.getContext()),parent,false
        );
        return new UserViewHolder(activityProfileBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.SetData(mListUsert.get(position));


    }

    @Override
    public int getItemCount() {
            return mListUsert.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ActivityProfileBinding binding;

        UserViewHolder(ActivityProfileBinding activityProfileBinding){
            super(activityProfileBinding.getRoot());
            binding = activityProfileBinding;
        }

        void SetData(student student){
            binding.tvTen.setText(student.getName());
            binding.tvMaSV.setText(student.getUser());

        }



    }
}
