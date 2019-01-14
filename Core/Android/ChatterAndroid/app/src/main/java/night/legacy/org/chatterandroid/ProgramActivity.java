package night.legacy.org.chatterandroid;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.lukx.jchatter.lib.models.User;

import java.util.ArrayList;

import static night.legacy.org.chatterandroid.App.getInstance;

public class ProgramActivity extends AppCompatActivity {

    ImageView profilePic;
    ListView listView;
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

        listView = (ListView)findViewById(R.id.ListView_main);
        App.getInstance().PopupHandler.ShowDialog(this,"Succesfully logged as " + loggedUser.login);
        //CustomAdapter customAdapter = new CustomAdapter();
        //listView.setAdapter(customAdapter);
    }

    private void onProfilePicClick(View v)
    {
        Intent intent = new Intent(this, ActivityShowUser.class);
        Bundle b = new Bundle();
        b.putInt("userID",App.getInstance().LoggedUser.id);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    class CustomAdapter extends BaseAdapter{

        User[] rels;

        public CustomAdapter()
        {
            rels = App.getInstance().Connector.getUsers();
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //convertView = getLayoutInflater().inflate(R.layout.friend_show_chat_cell,null);
                //
                    //ImageView imageView = (ImageView)findViewById(R.id.imageView_chatcell_pic);
                        //TextView textView = (TextView)findViewById(R.id.textView_chatcell_name);
                            //
                                //textView.setText(rels.get(position).firstName + " " + rels.get(position).secondName);

            return null;
        }
    }

}
