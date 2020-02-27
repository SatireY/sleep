package com.example.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sleep.db.User;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private EditText accounEdit;

    private EditText passwordEdit;

    private Button login;

    private Button register;

    private CheckBox remeberPass;

    private boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(LoginActivity.this, permissions, 1);
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.lo_toolbar);
        setSupportActionBar(toolbar);
        accounEdit = (EditText) findViewById(R.id.lo_account);
        passwordEdit = (EditText) findViewById(R.id.lo_password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        remeberPass = (CheckBox) findViewById(R.id.remember_pass);
        boolean isRemember = pref.getBoolean("remember_password", false);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accounEdit.setText(account);
            passwordEdit.setText(password);
            remeberPass.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String account = accounEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                List<User> users = LitePal.findAll(User.class);
                for (User user : users) {
                    if (account.equals(user.getName()) && password.equals(user.getPassword())) {// 账号验证成功
                        isSuccess = true;
                        editor = pref.edit();
                        if (remeberPass.isChecked()) {
                            editor.putBoolean("remember_password", true);
                            editor.putString("account", account);
                            editor.putString("password", password);
                        } else {
                            editor.clear();
                        }
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
                if (!isSuccess){
                    Toast.makeText(LoginActivity.this, "账户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
                default:
                    break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
                default:
        }
    }
}
