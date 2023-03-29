package com.example.roomdatabasepractice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {

    Context context;
    List<User> arrayList;

    public UserListAdapter(Context context, List<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(arrayList.get(position).getUserName());
        holder.tvMobileNo.setText(arrayList.get(position).getMobileNo());

        byte[] imageByte = arrayList.get(position).getImageUrl();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        holder.imgUser.setImageBitmap(bitmap);

        holder.imgDelete.setOnClickListener(view ->
        {
            AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "User_Details_Database").allowMainThreadQueries().build();
            UserDao userDao = db.userDao();
            userDao.deleteByUserName(arrayList.get(position).getUserName());
            arrayList.remove(position);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser, imgDelete;
        TextView tvName, tvMobileNo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobileNo = itemView.findViewById(R.id.tvMobileNo);
        }
    }

}
