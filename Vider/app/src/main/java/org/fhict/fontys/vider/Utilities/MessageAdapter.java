package org.fhict.fontys.vider.Utilities;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.fhict.fontys.vider.Models.ChatMessage;
import org.fhict.fontys.vider.R;

import java.util.ArrayList;
import java.util.List;

// MessageAdapter.java
public class MessageAdapter extends BaseAdapter {

    List<ChatMessage> messages = new ArrayList<ChatMessage>();
    Context context;

    public MessageAdapter(Context context, List<ChatMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    public void add(ChatMessage message) {
        this.messages.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ChatMessage message = messages.get(i);

        if (message.getMessageSender().equals("U")) { // this message was sent by us so let's create a basic chat bubble on the right
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getMessageText());
        } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.name.setText(message.getSenderName());
            holder.messageBody.setText(message.getMessageText());
        }

        return convertView;
    }

}

class MessageViewHolder {
    public TextView name;
    public TextView messageBody;
}
