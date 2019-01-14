package night.legacy.org.chatterandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lukx.jchatter.lib.models.User;

import java.util.ArrayList;

import static night.legacy.org.chatterandroid.App.getInstance;

public class ProgramActivity extends AppCompatActivity {

    ImageView profilePic;
    RecyclerView recyView;
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

        recyView = (RecyclerView)findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(App.getInstance().Connector.getUsers(),this);

        recyView.setAdapter(adapter);
        recyView.setLayoutManager(new LinearLayoutManager(this));

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

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
        private static final String TAG = "RecyclerViewAdapter";

        public User[] Users;
        public Context context;

        public RecyclerViewAdapter(User[] users, Context mContext)
        {
            Users = users;
            context = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem,viewGroup,false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.fullname.setText(Users[i].firstName + " " + Users[i].secondName);
            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            viewHolder.imagePic.setImageResource(R.drawable.profilepic_placeholder);
        }

        @Override
        public int getItemCount() {
            return Users.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imagePic;
            TextView fullname;
            RelativeLayout layout;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imagePic = itemView.findViewById(R.id.imageView_listitem_pic);
                fullname = itemView.findViewById(R.id.textView_listitem_fullname);
                layout = itemView.findViewById(R.id.relativeLayout_listitem);
            }
        }
    }

}
