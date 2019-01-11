package night.legacy.org.chatterandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.lukx.jchatter.lib.models.User;

import static night.legacy.org.chatterandroid.App.getInstance;

public class ProgramActivity extends AppCompatActivity {

    ImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        TextView name =  (TextView)findViewById(R.id.textView_LoggedLogin);
        User loggedUser = App.getInstance().LoggedUser;
        name.setText(loggedUser.firstName + " " + loggedUser.secondName);
        profilePic = (ImageView)findViewById(R.id.imageView_ProfilePic);
        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onProfilePicClick(view);
            }
        });
    }

    private void onProfilePicClick(View v)
    {
        Intent intent = new Intent(this, ActivityShowUser.class);
        Bundle b = new Bundle();
        b.putInt("userID",App.getInstance().LoggedUser.id);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
