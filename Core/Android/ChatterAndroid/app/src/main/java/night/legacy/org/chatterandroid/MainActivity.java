package night.legacy.org.chatterandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button button;
    Button register;
    EditText text_Login;
    EditText text_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button_Login);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    onClickLogin(view);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        register = (Button) findViewById(R.id.button_Register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onClickRegister(view);
            }
        });

        text_Login = (EditText)findViewById(R.id.editText_Login);
        text_Password = (EditText)findViewById(R.id.editText_Password);


    }

    //On button click
    private void onClickLogin(View v) throws URISyntaxException, IOException {


        String login = text_Login.getText().toString();
        String password = text_Password.getText().toString();

        App app = App.getInstance();
        app.LoggedUser = new User();
        app.LoggedUser.login = login;

        /*
        Communicator c = new Communicator();

        c.setServerURI(new URI(getString(R.string.server_uri)));

        UserRepo rep = new UserRepo(c);

        rep.getLoginHeader().setLogin(login);
        rep.getLoginHeader().setPassword(password);

        if(rep.validateLogin())
        {
            System.console().printf("Validated");
        }
        else
        {
            System.console().printf("Invalidated");
        }*/


        startActivity(new Intent(MainActivity.this, ProgramActivity.class));
    }

    //On button click
    private void onClickRegister(View v) {
        startActivity(new Intent(MainActivity.this, RegisterUserActivity.class));
    }

}
