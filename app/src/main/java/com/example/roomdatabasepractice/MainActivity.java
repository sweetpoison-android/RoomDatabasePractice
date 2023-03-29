package com.example.roomdatabasepractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roomdatabasepractice.databinding.ActivityMainBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String name, mobileNo;
    ActivityMainBinding binding;
    int REQUEST_CODE = 200;
    Bitmap bitmap = null;
    ArrayList<Object> bitmaps;
    byte[] imgByte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.imgCard.setOnClickListener(view ->
        {
            Intent in = new Intent();
            in.setAction(Intent.ACTION_GET_CONTENT);
            in.setType("image/*");
            startActivityForResult(Intent.createChooser(in, "select picture"), REQUEST_CODE);
        });

        binding.btnInsert.setOnClickListener(view ->
        {
            name = binding.etName.getText().toString();
            mobileNo = binding.etMobile.getText().toString();

            int randomNo = new Random().nextInt(100);

            if (!(name.isEmpty() && mobileNo.isEmpty())) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_Details_Database").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();

                boolean checkMobileNo = userDao.isMobileNoExists(mobileNo);

               if (checkMobileNo)
               {
                   new AlertDialog.Builder(MainActivity.this).setMessage("Mobile No Already Exists").show();
               }
               else
               {
                   ByteArrayOutputStream stream = new ByteArrayOutputStream();
                   if (bitmap != null)
                   {
                       bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                       imgByte = stream.toByteArray();
                       userDao.insertData(new User(randomNo, name, mobileNo, imgByte));
                       Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                       binding.etName.setText("");
                       binding.etMobile.setText("");
                   }
                   else
                  {
                      new AlertDialog.Builder(MainActivity.this).setMessage("Please select user Image").show();
                  }

               }
            } else {
                new AlertDialog.Builder(MainActivity.this).setMessage("enter details").show();
            }

        });

        binding.btnFetch.setOnClickListener(view -> {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "User_Details_Database").allowMainThreadQueries().build();
            UserDao userDao = db.userDao();
            List<User> userList = userDao.getAllUser();
//            String strUser = "";
//            for (User user : userList)
//            {
//                strUser = strUser+"\n"+user.getUid()+"\tName : "+user.getUserName()+"\n\t\t\t\tMobile No : "+user.getMobileNo()+"\n";
//
//            }
//            binding.tvUser.setText(strUser);

            UserListAdapter adapter = new UserListAdapter(MainActivity.this, userList);
            binding.recyclerview.setAdapter(adapter);
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data!= null)
        {
            Uri selectImgUrl = data.getData();
            binding.imgUser.setImageURI(selectImgUrl);
            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImgUrl);
                binding.imgUser.setImageBitmap(bitmap);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

