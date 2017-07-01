package com.bishe.nongcun.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.CONFIG;
import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.JsonSignupBean;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_mobile)
    EditText _mobileText;
    @Bind(R.id.et_password)
    EditText _passwordText;
    @Bind(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;

    @Override
    int getLayoutId() {
        return R.layout.activity_signup;
    }

    @Override
    void initView() {
        ButterKnife.bind(this);
    }

    @Override
    void initListener() {
        _signupButton.setOnClickListener(this);
        _loginLink.setOnClickListener(this);
    }

    @Override
    void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                signup();
                break;
            case R.id.link_login:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            default:
                break;
        }
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("创建账号...");
        progressDialog.show();

        String username = _nameText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
//
//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // depending on success
//                        // TODO: 2017/5/22 注册成功
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
        OkGo.get(CONFIG.URL_SIGNUP)
                .params("username", username)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        JsonSignupBean jsonSignupBean = gson.fromJson(s, JsonSignupBean.class);
                        //如果得到返回消息为ok,则注册成功。
                        if (jsonSignupBean.getMsg().equals("ok")) {
                            Log.e("zwc", "onSuccess: 注册成功");
                            onSignupSuccess();
                            //对话框消失
                            progressDialog.dismiss();

                        } else {
                            onSignupFailed(1);
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    /**
     * 注册失败，按钮置为可用
     * 依据传参不同，进行不同吐司
     */
    public void onSignupFailed(int i) {
        if (i == 1) {
            Toast.makeText(getBaseContext(), "该用户名已经被注册", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();
        }
        _signupButton.setEnabled(true);
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        Toast.makeText(SignupActivity.this, "注册成功，已自动登录", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignupActivity.this, TestBottomTabBaseActivity.class);
        startActivity(intent);

        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "注册失败", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty()) {
            _nameText.setError("请输入用户名");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (mobile.isEmpty()) {
            _mobileText.setError("请输入有效的手机号");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("请输入有效的密码");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("两次密码输入不一致");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}