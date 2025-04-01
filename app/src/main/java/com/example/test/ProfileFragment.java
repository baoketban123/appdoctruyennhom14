package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class ProfileFragment extends Fragment {
    TextView txtUsername;
    EditText edtNickname, edtEmail;
    ImageView img_Profile;
    Button btnSave, btnEdit, btnLogout, btn_Upload;
    RadioButton rbMale, rbFemale;
    RadioGroup rgGender;
    private boolean isEditing = false;
    SharedPreferences prefs;
    static final int PICK_IMAGE_REQUEST = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewPro=LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile,null);
        txtUsername=viewPro.findViewById(R.id.txtUsername);
        edtNickname=viewPro.findViewById(R.id.edtNickname);
        edtEmail=viewPro.findViewById(R.id.edtEmail);
        img_Profile=viewPro.findViewById(R.id.img_Profile);
        btnEdit=viewPro.findViewById(R.id.btnEdit);
        btn_Upload=viewPro.findViewById(R.id.btn_Upload);
        btnSave=viewPro.findViewById(R.id.btnSave);
        btnLogout=viewPro.findViewById(R.id.btnLogout);
        rbFemale=viewPro.findViewById(R.id.rbFemale);
        rbMale=viewPro.findViewById(R.id.rbMale);
        rgGender=viewPro.findViewById(R.id.rgGender);


        // Lấy SharedPreferences
        prefs = requireActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "");
        txtUsername.setText("Username: " + username);// Lấy username từ SharedPreferences, không chỉnh sửa được

        edtNickname.setText("");
        edtEmail.setText("");
        rbMale.setChecked(true);

        //Sửa
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing = true;
                edtNickname.setEnabled(true);
                edtEmail.setEnabled(true);
                btnSave.setEnabled(true);
                Toast.makeText(getActivity(), "Nhấn Lưu để cập nhật", Toast.LENGTH_SHORT).show();
            }
        });

        //Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    isEditing = false;
                    edtNickname.setEnabled(false);
                    edtEmail.setEnabled(false);
                    btnSave.setEnabled(false);
                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    // Lưu nickname, email, và gender vào SharedPreferences
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nickname", edtNickname.getText().toString());
                    editor.putString("email", edtEmail.getText().toString());
                    editor.putString("gender", rbMale.isChecked() ? "Nam" : "Nữ");
                    editor.apply();
                }
            }
        });

        //chọn hình
        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        //đăng xuất
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa trạng thái đăng nhập
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();

                // Chuyển hướng về MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish(); // Đóng activity hiện tại
            }
        });

        return viewPro;
    }
}
