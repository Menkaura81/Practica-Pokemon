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

/**
 * Clase que implementa el fragmento de ajustes
 */
public class AjustesFragment extends Fragment {

    private FragmentAjustesBinding binding;
    private SharedPreferences sharedPreferences;


    /**
     * Método onCreate en el que se incluyen las sharedPreferences
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(PreferencesHelper.PREFS_NAME, getContext().MODE_PRIVATE);
    }


    /**
     * Método onCreateView en el que se incluyen los listeners de los botones.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAjustesBinding.inflate(inflater, container, false);

        // Inicializamos el estado del Switch
        binding.deletePokemons.setChecked(sharedPreferences.getBoolean(PreferencesHelper.KEY_DELETE_POKEMONS, false));

        // Listeners
        binding.about.setOnClickListener(v -> onAboutSelected());
        binding.closeSession.setOnClickListener(v -> onCloseSessionSelected());
        binding.changeLanguage.setOnClickListener(v -> onChangeLanguageSelected());

        // Listener del switch
        binding.deletePokemons.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Guardamos el estado del switch
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(PreferencesHelper.KEY_DELETE_POKEMONS, isChecked);  // Guardamos el estado del switch como booleano
            editor.apply();
            boolean savedState = sharedPreferences.getBoolean(PreferencesHelper.KEY_DELETE_POKEMONS, false);
            Log.d("DeletePokemonsSwitch", "Valor leído desde SharedPreferences: " + savedState);
        });
        return binding.getRoot();
    }


    /**
     * Método que implementa el cambio de idioma
     */
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


    /**
     * Método que implementa el cierre de sesión
     */
    private void onCloseSessionSelected() {
        // Cerramos sesion y notificamos
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Toast.makeText(requireContext(), getString(R.string.close_session_msg), Toast.LENGTH_SHORT).show();

        // Volvemos a la pantalla de login
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();
    }


    /**
     * Método que implementa el mensaje acerda de...
     */
    private void onAboutSelected(){
        // Creamos del textview para el mensaje
        TextView messageTextView = new TextView(requireContext());
        messageTextView.setText(R.string.about_msg);
        messageTextView.setGravity(Gravity.CENTER); // Texto centrado

        // Mostramos el mensaje
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.about)
                .setView(messageTextView)
                .setPositiveButton(R.string.close_dialog, (dialog, which) -> dialog.dismiss())
                .show();
    }


    /**
     * Método que implementa el cambio de idioma
     * @param languageCode Código de leguaje al que se desea cambiar
     */
    private void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        // Recargamos la actividad
        Intent i = requireActivity().getIntent();
        requireActivity().finish();
        startActivity(i);
    }
}