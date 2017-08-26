package com.bishe.nongcun.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.utils.CONFIG;
import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.JsonLoginBean;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    void initListener() {
        _loginButton.setOnClickListener(this);
        _signupLink.setOnClickListener(this);
    }

    @Override
    void processClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.link_signup:
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            default:
                break;
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();

        //获取输入内容
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //联网，获取数据
        OkGo.get(CONFIG.URL_LOGIN)
                .params("username", username)
                .params("password", password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();

                        JsonLoginBean jsonLoginBean = gson.fromJson(s, JsonLoginBean.class);
                        //如果得到权限>0,则登录成功。
                        if (jsonLoginBean.getPermission() > 0) {
                            Log.e("zwc", "onSuccess: ===");
                            onLoginSuccess();
                            progressDialog.dismiss();
                        } else {
                            onLoginFailed();
                            progressDialog.dismiss();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "登陆失败", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = et_username.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty()) {
            et_username.setError("请输入有效账号");
            valid = false;
        } else {
            et_username.setError(null);
        }

        if (password.isEmpty()) {
            et_password.setError("请输入密码");
            valid = false;
        } else {
            et_password.setError(null);
        }

        return valid;
    }
}
