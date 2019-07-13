package jp.oumimaiko.wordmemo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import io.realm.Realm;

public class EditDialog extends DialogFragment {

        public EditDialog() {
        }

        private String mTitle;
        private String mDescription;
        private WordsModel mWordsModel;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // Get the layout inflater
            final LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.edit_dialog,null);

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.edit_dialog, null))
                    // Add action buttons
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            EditText titleEditText = view.findViewById(R.id.title);
                            EditText descriptionEditText = view.findViewById(R.id.description);

                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();

                            if (mWordsModel == null) {
                            // 新規作成の場合
                            mWordsModel = new WordsModel();

                            Calendar calendar = Calendar.getInstance();
                            mWordsModel.setDate(calendar.getTime());
                            mWordsModel.setTitle(titleEditText.getText().toString());
                            mWordsModel.setDescription(descriptionEditText.getText().toString());

                            realm.copyToRealmOrUpdate(mWordsModel);
                            realm.commitTransaction();
                            realm.close();
                            }

                            realm.copyToRealmOrUpdate(mWordsModel);
                            realm.commitTransaction();

                            realm.close();

                        }
                    })
                    .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditDialog.this.getDialog().cancel();
                        }
                    });


            return builder.create();
        }
    }