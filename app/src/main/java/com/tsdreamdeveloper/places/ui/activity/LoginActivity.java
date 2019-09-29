package com.tsdreamdeveloper.places.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tsdreamdeveloper.places.R;
import com.tsdreamdeveloper.places.base.BaseActivity;
import com.tsdreamdeveloper.places.mvp.presenter.LoginPresenter;
import com.tsdreamdeveloper.places.mvp.view.LoginView;

import static com.tsdreamdeveloper.places.mvp.presenter.MainPresenter.CODE_EXTRA_KEY;

public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText mEtLogin;
    private EditText mEtPassword;
    private String code;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEtLogin = findViewById(R.id.et_login);
        mEtPassword = findViewById(R.id.et_password);
        presenter = new LoginPresenter(this);
    }

    public void onSignInClick(View view) {
        String username = mEtLogin.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        if (username.length() > 1 && password.length() > 1) {
            presenter.auth(username, password);
        }
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void nextStep() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(CODE_EXTRA_KEY, code);
        startActivity(intent);
        finish();
    }
}
