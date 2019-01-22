package night.legacy.org.chatterandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.lukx.jchatter.lib.models.Message;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity {

    AndroidRoom room;
    RecyclerView recyView;
    ImageButton sendButton;
    EditText chatText;
    TextView chatName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle b = getIntent().getExtras();
        int value = -1;
        if(b != null)
        {
            value = b.getInt("roomID");
        }

        room = App.getInstance().LoggedUser.getRoomByID(value);

        recyView = (RecyclerView)findViewById(R.id.recyclerView_chat);
        ChatActivity.RecyclerViewAdapter adapter = new ChatActivity.RecyclerViewAdapter(this);

        recyView.setAdapter(adapter);
        recyView.setLayoutManager(new LinearLayoutManager(this));

        sendButton = (ImageButton)findViewById(R.id.imageButton_chat_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendClick(v);
            }
        });
        chatText = (EditText)findViewById(R.id.editText_chat_chatbox);
        chatName = (TextView)findViewById(R.id.textView_Chat_ChatName);
        if(room.oneOnOne)
            chatName.setText(room.getOtherUser(App.getInstance().LoggedUser).firstName);
        else
            chatName.setText(room.name);
    }

    public void onSendClick(View v)
    {
        String s = chatText.getText().toString();
        if(s.length() != 0)
        {
            Message msg = new Message();
            msg.idsender = App.getInstance().LoggedUser.id;
            msg.content = s;
            msg.dateSent = new java.util.Date();
            msg.idroomReceiver = room.id;
            try {
                App.getInstance().Connector.messageRepo.addMessage(msg);
            } catch (IOException e) {
                App.getInstance().PopupHandler.ShowDialog(this,e.getMessage());
            } catch (URISyntaxException e) {
                App.getInstance().PopupHandler.ShowDialog(this,e.getMessage());
            }
        }
        chatText.setText("");
        try {
            App.getInstance().Connector.getRoomMessages(room);
        } catch (IOException e) {
            App.getInstance().PopupHandler.ShowDialog(this,e.getMessage());
        } catch (URISyntaxException e) {
            App.getInstance().PopupHandler.ShowDialog(this,e.getMessage());
        }
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<ChatActivity.RecyclerViewAdapter.ViewHolder>{
        private static final String TAG = "RecyclerViewAdapter";

        public Context context;

        public RecyclerViewAdapter( Context mContext)
        {
            context = mContext;
        }

        @NonNull
        @Override
        public ChatActivity.RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = null;


                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chatitem_left,viewGroup,false);
            ChatActivity.RecyclerViewAdapter.ViewHolder holder = new ChatActivity.RecyclerViewAdapter.ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChatActivity.RecyclerViewAdapter.ViewHolder viewHolder, int i) {
            User logged = App.getInstance().LoggedUser;
            User sender = App.getInstance().Connector.getUser(room.Messages[i].idsender);
            viewHolder.fullname.setText(sender.firstName + " " + sender.secondName);
            viewHolder.message.setText(room.Messages[i].content);
            if(sender.id == logged.id)
                viewHolder.bg.setImageResource(R.color.colorAccent);
            else
                viewHolder.bg.setImageResource(R.color.colorLightBlue);
            viewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            viewHolder.imagePic.setImageResource(R.drawable.profilepic_placeholder);
        }

        @Override
        public int getItemCount() {
            return room.Messages.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imagePic;
            TextView fullname;
            TextView message;
            RelativeLayout layout;
            ImageView bg;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imagePic = itemView.findViewById(R.id.imageView_chatItemLeft_pic);
                fullname = itemView.findViewById(R.id.textView_chatitemleft_name);
                message = itemView.findViewById(R.id.textView_chatitemleft_message);
                layout = itemView.findViewById(R.id.relativeLayout_chatitemleft);
                bg = itemView.findViewById(R.id.imageView6);
            }
        }
    }
}
