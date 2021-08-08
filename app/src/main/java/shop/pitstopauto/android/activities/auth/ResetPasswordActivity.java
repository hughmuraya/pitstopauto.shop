package shop.pitstopauto.android.activities.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import shop.pitstopauto.android.R;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reset Password");

        hideSoftKeyboard();

        (findViewById(R.id.tv_signin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mint = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mint);
            }
        });

        (findViewById(R.id.btn_reset_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mint = new Intent(ResetPasswordActivity.this, SignInActivity.class);
                Toast.makeText(ResetPasswordActivity.this, "Password reset successfully. Check your email.", Toast.LENGTH_LONG).show();
                mint.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mint);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

}