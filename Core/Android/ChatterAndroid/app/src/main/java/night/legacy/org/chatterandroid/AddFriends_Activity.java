package night.legacy.org.chatterandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddFriends_Activity extends AppCompatActivity {

    ImageView profilePic;
    RecyclerView recyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
        TextView name =  (TextView)findViewById(R.id.textView_LoggedLogin);
        AndroidUser loggedUser = App.getInstance().LoggedUser;
        name.setText(loggedUser.firstName + " " + loggedUser.secondName);
        profilePic = (ImageView)findViewById(R.id.imageView_ProfilePic);
        profilePic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onProfilePicClick(view);
            }
        });

        //recyView = (RecyclerView)findViewById(R.id.recyclerView);
        //ProgramActivity.RecyclerViewAdapter adapter = new ProgramActivity.RecyclerViewAdapter(loggedUser.Rooms.toArray(new AndroidRoom[loggedUser.Rooms.size()]),this);

        //recyView.setAdapter(adapter);
        //recyView.setLayoutManager(new LinearLayoutManager(this));

        // App.getInstance().PopupHandler.ShowDialog(this,"Succesfully logged as " + loggedUser.login);

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
