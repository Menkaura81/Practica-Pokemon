package dam.aguadulce.aal.practicapokemon;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Locale;
import dam.aguadulce.aal.practicapokemon.databinding.FragmentAjustesBinding;


public class AjustesFragment extends Fragment {

    private FragmentAjustesBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(PreferencesHelper.PREFS_NAME, getContext().MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAjustesBinding.inflate(inflater, container, false);

        // Inicializar el estado del Switch
        binding.deletePokemons.setChecked(sharedPreferences.getBoolean(PreferencesHelper.KEY_DELETE_POKEMONS, false));

        binding.about.setOnClickListener(v -> onAboutSelected());
        binding.closeSession.setOnClickListener(v -> onCloseSessionSelected());
        binding.changeLanguage.setOnClickListener(v -> onChangeLanguageSelected());
        binding.deletePokemons.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Guardar el estado del switch
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PreferencesHelper.KEY_DELETE_POKEMONS, isChecked);  // Guardamos el estado del switch como booleano
            editor.apply();
            boolean savedState = sharedPreferences.getBoolean(PreferencesHelper.KEY_DELETE_POKEMONS, false);
            Log.d("DeletePokemonsSwitch", "Valor leído desde SharedPreferences: " + savedState);
        });
        return binding.getRoot();
    }


    private void onChangeLanguageSelected() {
        // Dependiendo del idioma actual cambiamos en consecuencia
        if ("es".equals(sharedPreferences.getString(PreferencesHelper.KEY_LANGUAGE, "es"))){
            // Almacenamos la preferencia
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PreferencesHelper.KEY_LANGUAGE, "en");
            editor.apply();
            // Cambiamos el idioma
            changeLanguage("en");
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PreferencesHelper.KEY_LANGUAGE, "es");
            editor.apply();
            changeLanguage("es");
        }

    }


    private void onCloseSessionSelected() {
        // Se cierra sesión y se notifica al usuario
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Volvemos a la pantalla de login
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }


    private void onAboutSelected(){
        // Creación del textview para el mensaje
        TextView messageTextView = new TextView(requireContext());
        messageTextView.setText(R.string.about_msg);
        messageTextView.setGravity(Gravity.CENTER); // Center the text

        // Mostrar el mensaje
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.about)
                .setView(messageTextView)
                .setPositiveButton(R.string.close_dialog, (dialog, which) -> dialog.dismiss())
                .show();
    }


    private void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        // Recargar la actividad
        Intent i = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(i);
    }
}