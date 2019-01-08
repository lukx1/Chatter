package night.legacy.org.chatterandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.models.User;
import net.lukx.jchatter.lib.repos.UserRepo;

import java.io.IOException;
import java.net.URISyntaxException;


public class RegisterUserActivity extends AppCompatActivity {

    Button registerButton;
    EditText login;
    EditText password;
    EditText firstname;
    EditText surname;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        registerButton = (Button)findViewById(R.id.button_rgRegister);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    onRegisterClick(view);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        login = (EditText)findViewById(R.id.editText_regLogin);
        firstname= (EditText)findViewById(R.id.editText_rgFirstname);
        surname = (EditText)findViewById(R.id.editText_rgSurname);
        password = (EditText)findViewById(R.id.editText_rgPassw);
        email = (EditText)findViewById(R.id.editText_rgEmail);

    }

    private void onRegisterClick(View v) throws IOException, URISyntaxException {
        User user = new User();
        user.login = login.getText().toString();
        user.firstName = firstname.getText().toString();
        user.secondName = surname.getText().toString();
        user.password = password.getText().toString();
        user.email = email.getText().toString();
        user.dateRegistered = new java.util.Date();
        user.dateLastLogin = new java.util.Date();

        UserRepo rep = App.getInstance().Connector.userRepo;
        rep.registerUser(user);
        startActivity(new Intent(RegisterUserActivity.this, MainActivity.class));
    }
}
