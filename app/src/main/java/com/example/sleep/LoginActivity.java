package com.example.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sleep.db.User;

import org.litepal.LitePal;

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
        pref = PreferenceManager.getDefaultSharedPreferences(this);
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
}
