package com.example.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sleep.db.User;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button register;

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accountEdit = (EditText) findViewById(R.id.re_account);
        passwordEdit = (EditText) findViewById(R.id.re_password);
        register = (Button) findViewById(R.id.register1);
        back = (Button) findViewById(R.id.back_login);
        register.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register1:
                User user = new User();
                user.setName(accountEdit.getText().toString());
                user.setPassword(passwordEdit.getText().toString());
                user.save();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.back_login:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
                default:
                    break;
        }
    }
}
