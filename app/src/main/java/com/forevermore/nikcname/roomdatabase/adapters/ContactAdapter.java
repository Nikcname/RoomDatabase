package com.forevermore.nikcname.roomdatabase.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forevermore.nikcname.roomdatabase.MainActivity;
import com.forevermore.nikcname.roomdatabase.R;
import com.forevermore.nikcname.roomdatabase.dataholders.ContactData;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactData> contactDataList;
    private CallbackContactAdapter callbackContactAdapter;
    private int pos;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public ContactAdapter(List<ContactData> contactDataList) {
        this.contactDataList = contactDataList;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView textViewFio;
        TextView textViewPhone;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewFio = itemView.findViewById(R.id.text_view_fio);
            textViewPhone = itemView.findViewById(R.id.text_view_phone);

            itemView.setClickable(true);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.edit_item, Menu.NONE, "Edit");
            menu.add(Menu.NONE, R.id.delete_item, Menu.NONE, "Delete");
        }
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View viewElement = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_adapter,
                viewGroup, false);

        return new ContactViewHolder(viewElement);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder contactViewHolder, int i) {

        final int final_i = i;
        contactViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                setPos(final_i);
                Log.d("sss", String.valueOf(final_i));

                return false;
            }
        });

        ContactData contactData = contactDataList.get(i);

        contactViewHolder.textViewFio.setText(contactData.getName() + " " + contactData.getSurname());
        contactViewHolder.textViewPhone.setText(contactData.getPhone());
    }

    @Override
    public void onViewRecycled(@NonNull ContactViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return contactDataList.size();
    }

    public interface CallbackContactAdapter{
        void itemPlace(int position);
    }

    public void setListener(CallbackContactAdapter callbackContactAdapter){
        this.callbackContactAdapter = callbackContactAdapter;
    }
}
