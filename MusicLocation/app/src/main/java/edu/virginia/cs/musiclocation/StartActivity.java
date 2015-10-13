package edu.virginia.cs.musiclocation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    static final String NAME_KEY = "name";

    private Button submitButton;
    private EditText editValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        submitButton = (Button) findViewById(R.id.returnInput);
        editValue = (EditText) findViewById(R.id.editText);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editValue.getText().toString();
                SharedPreferences preferences = getSharedPreferences(StartActivity.class.getName
                        (), MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit()
                        .putString(NAME_KEY, userName);
                editor.commit();
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

}
