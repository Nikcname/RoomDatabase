package com.forevermore.nikcname.roomdatabase.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.forevermore.nikcname.roomdatabase.R;
import com.forevermore.nikcname.roomdatabase.dataholders.ContactData;

public class CreateContact extends DialogFragment {

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextPhone;
    private EditText[] editTexts;
    private Button buttonSave;
    private Button buttonClear;
    private CallbackCreateContact callbackCreateContact;
    private ContactData contactData;

    public CreateContact() {}

    public static CreateContact newInstance(ContactData contactData) {

        CreateContact fragment = new CreateContact();
        Bundle args = new Bundle();
        args.putSerializable("contact", contactData);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View viewNewContact = inflater.inflate(R.layout.fragment_contact, container, false);

        editTextName = viewNewContact.findViewById(R.id.edit_text_name);
        editTextSurname = viewNewContact.findViewById(R.id.edit_text_surname);
        editTextPhone = viewNewContact.findViewById(R.id.edit_text_phone);

        if (getArguments() != null){
            ContactData contactData = (ContactData) getArguments().getSerializable("contact");
            editTextName.setText(contactData.getName());
            editTextSurname.setText(contactData.getSurname());
            editTextPhone.setText(contactData.getPhone());
        }

        editTexts = new EditText[]{editTextName, editTextSurname, editTextPhone};
        buttonSave = viewNewContact.findViewById(R.id.button_save);
        buttonClear = viewNewContact.findViewById(R.id.button_clear);

        return viewNewContact;
    }

    @Override
    public void onStart() {
        super.onStart();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean allGood = true;
                for (EditText editText : editTexts){
                    if (editText.getText().toString().equals("")){
                        editText.setHint(Html.fromHtml("<font color='#A9A9A9'>" + editText.getHint() + "</font>"));
                        allGood = false;
                    }
                }
                if (allGood){
                    callbackCreateContact.newContact(new ContactData(
                            editTextName.getText().toString(),
                            editTextSurname.getText().toString(),
                            editTextPhone.getText().toString()
                    ));
                    clearAll();
                    dismiss();
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
            }
        });
    }

    public interface CallbackCreateContact{
        void newContact(ContactData newContactData);
    }

    public void setListener(CallbackCreateContact callbackCreateContact){
        this.callbackCreateContact = callbackCreateContact;
    }

    private void clearAll(){
        for (EditText editText : editTexts){
            editText.setText("");
            editText.setHint(Html.fromHtml("<font color='#A9A9A9'>" + editText.getHint() + "</font>"));
        }
        editTextName.requestFocus();
    }
}
