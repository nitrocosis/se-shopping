package com.srgiovine.seshopping.splash;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.srgiovine.seshopping.account.AccountManager;
import com.srgiovine.seshopping.model.User;
import com.srgiovine.seshopping.task.BackgroundTask;
import com.srgiovine.seshopping.task.Callback;
import com.srgiovine.seshopping.task.SimpleCallback;

import java.util.Arrays;
import java.util.List;

import srgiovine.com.seshopping.R;

class LoginFormDialog extends FormDialog {

    private EditText email;
    private EditText password;

    private BackgroundTask loginTask;

    private final Callback<User> loginCallback = new SimpleCallback<User>() {
        @Override
        public void onSuccess(User user) {
            LoginFormDialog.this.onConfirmActionSuccess();
        }

        @Override
        public void onFailed() {
            LoginFormDialog.this.onConfirmActionFailed();
        }
    };

    LoginFormDialog(Context context, AccountManager accountManager, Callback<Void> callback) {
        super(context, accountManager, callback);

        email = (EditText) contentView.findViewById(R.id.email);
        password = (EditText) contentView.findViewById(R.id.password);
    }

    @Override
    protected void onTakeConfirmAction() {
        super.onTakeConfirmAction();
        loginTask = accountManager.login(email.getText().toString(), password.getText().toString(), loginCallback);
    }

    @Override
    protected void onConfirmActionFailed() {
        super.onConfirmActionFailed();
        Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean onValidateFormFieldInputs() {
        boolean formFieldsValid = super.onValidateFormFieldInputs();
        formFieldsValid &= onValidateEmailField(email);
        return formFieldsValid;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (loginTask != null) {
            loginTask.cancel();
            loginTask = null;
        }
    }

    @Override
    protected List<EditText> formFields() {
        return Arrays.asList(email, password);
    }

    @Override
    protected int layoutRes() {
        return R.layout.dialog_login;
    }
}
