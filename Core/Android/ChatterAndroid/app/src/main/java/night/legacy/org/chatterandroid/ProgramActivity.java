package night.legacy.org.chatterandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import net.lukx.jchatter.lib.models.User;

public class ProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        TextView name =  (TextView)findViewById(R.id.textView_LoggedLogin);
        User loggedUser = App.getInstance().LoggedUser;
        name.setText(loggedUser.firstName + " " + loggedUser.secondName);
    }
}
