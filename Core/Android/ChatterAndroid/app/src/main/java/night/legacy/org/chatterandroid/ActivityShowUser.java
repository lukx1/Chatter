package night.legacy.org.chatterandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import net.lukx.jchatter.lib.models.User;

import org.w3c.dom.Text;

public class ActivityShowUser extends AppCompatActivity {

    private User ShowedUser;
    private ImageView ProfilePic;
    private TextView LoginString;
    private TextView NameString;
    private TextView EmailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        ProfilePic = (ImageView)findViewById(R.id.imageView_UserProfile_Pic);
        LoginString = (TextView)findViewById(R.id.textView_UserProfile_Name);
        NameString = (TextView)findViewById(R.id.textView_UserProfile_FullName);
        EmailString = (TextView)findViewById(R.id.textView_UserProfile_Email);

        Bundle b = getIntent().getExtras();
        int value = -1;
        if(b != null)
        {
            value = b.getInt("userID");
        }
        ShowedUser = App.getInstance().Connector.getUser(value);

        LoginString.setText(ShowedUser.login);
        NameString.setText(ShowedUser.firstName + " " + ShowedUser.secondName);
        EmailString.setText(ShowedUser.email);
    }
}
