package com.bishe.nongcun.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bishe.nongcun.R;
import com.bishe.nongcun.bean.MyInstallation;
import com.bishe.nongcun.bean.MyUser;
import com.bishe.nongcun.utils.LogUtils;
import com.bishe.nongcun.utils.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import rx.functions.Action1;

public class LoginActivity extends BaseActivity {
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.et_username)
    EditText et_phone;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;
    private String phone;
    private String password;

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }

    public void login() {
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

        // 手机号登录
        BmobUser.loginByAccount(phone, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if (user != null) {
                    LogUtils.e("登录成功:");
                    modifyInstallationUser(user);
                    onLoginSuccess();
                    progressDialog.dismiss();
                } else {
                    LogUtils.e("登录失败");
                    onLoginFailed();
                    progressDialog.dismiss();
                }
            }
        });

       /*  //联网，获取数据
        MyUser myUser = new MyUser();
        myUser.setUsername(username);
        myUser.setPassword(password);

       myUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser bmobUser, BmobException e) {
                if(e==null){
                    LogUtils.e("登录成功:");
                    onLoginSuccess();
                    progressDialog.dismiss();

                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    LogUtils.e("登录失败");
                    onLoginFailed();
                    progressDialog.dismiss();
                }
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        phone = et_phone.getText().toString();
        password = et_password.getText().toString();
        if (!StringUtils.checkPhone(phone)) {
            et_phone.setError("请输入正确手机号");
            valid = false;
        } else {
            et_phone.setError(null);
        }

        if (password.isEmpty()) {
            et_password.setError("请输入密码");
            valid = false;
        } else {
            et_password.setError(null);
        }

        return valid;
    }

    /**
     * 修改设备表的用户信息：先查询设备表中的数据，再修改数据中用户信息
     * @param user
     */
    private void modifyInstallationUser(final MyUser user) {
        BmobQuery<MyInstallation> bmobQuery = new BmobQuery<>();
        final String id = BmobInstallationManager.getInstallationId();
        bmobQuery.addWhereEqualTo("installationId", id);
        bmobQuery.findObjectsObservable(MyInstallation.class)
                .subscribe(new Action1<List<MyInstallation>>() {
                    @Override
                    public void call(List<MyInstallation> installations) {

                        if (installations.size() > 0) {
                            MyInstallation installation = installations.get(0);
                            installation.setMyuser(user);
                            installation.updateObservable()
                                    .subscribe(new Action1<Void>() {
                                        @Override
                                        public void call(Void aVoid) {
                                            LogUtils.e("更新设备用户信息成功！");
                                        }
                                    }, new Action1<Throwable>() {
                                        @Override
                                        public void call(Throwable throwable) {
                                            LogUtils.e("更新设备用户信息失败：" + throwable.getMessage());
                                        }
                                    });

                        } else {
                            LogUtils.e("后台不存在此设备Id的数据，请确认此设备Id是否正确！\n" + id);
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e("查询设备数据失败：" + throwable.getMessage());
                    }
                });
    }


    @OnClick({R.id.btn_login, R.id.link_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.link_signup:
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }
}
