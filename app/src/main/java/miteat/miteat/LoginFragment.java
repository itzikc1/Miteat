package miteat.miteat;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import miteat.miteat.Model.Entities.User;
import miteat.miteat.Model.Model;
import miteat.miteat.R;

/**
 * Created by Itzik on 05/06/2016.
 */
public class LoginFragment extends Fragment   {

    private String username,password;
 //   private Button ok;
    private EditText editTextUsername,editTextPassword;
    private CheckBox saveLoginCheckBox;
    private Boolean saveLogin;
    Delegate delegate;

    interface Delegate{
       public void finishLogin();
        public void goToRegister();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final Delegate delegate = (Delegate) getActivity();


        Button register = (Button) view.findViewById(R.id.buttonRegistar);
        Button login = (Button) view.findViewById(R.id.buttonLogin);
        Button forgotPassword = (Button) view.findViewById(R.id.forgotPassword);

        editTextUsername = (EditText) view.findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        saveLoginCheckBox = (CheckBox) view.findViewById(R.id.saveLoginCheckBox);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                username = editTextUsername.getText().toString();
                password = editTextPassword.getText().toString();
                saveLogin = saveLoginCheckBox.isSaveEnabled();
                Boolean bool = checkIfUserExist(username, password);
                if (bool != true) {
                    Toast.makeText(getActivity().getApplicationContext(), "Wrong User or Password!", Toast.LENGTH_LONG).show();
                } else {
                    delegate.finishLogin();
                }

            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.goToRegister();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builderShare = new AlertDialog.Builder(MainActivity.this);
//                builderShare.setTitle("Add User");
//                final EditText input = new EditText(MainActivity.this);
//                input.setInputType(InputType.TYPE_CLASS_TEXT);
//
//                builderShare.setView(input);
//
//                builderShare.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Users user = new Users(input.getText().toString());
//                        Model.instance().addUser(user);
//                    }
//                });
//                builderShare.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builderShare.show()

            }
        });
    return view;
    }

    private Boolean checkIfUserExist(String user,String password)
    {
        User newUser = new User(user,password);
        Boolean bool = Model.instance().checkIfUserExist(newUser);
        return  bool;
    }

}
