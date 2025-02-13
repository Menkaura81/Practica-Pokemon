package dam.aguadulce.aal.practicapokemon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;


/**
 * Clase que implementa el login
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * Metodo onCreate
     * @param savedInstanceState Bundle con el estado de la actividad
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    /**
     * Metodo que inicia el login
     */
    private void startSignIn() {
        FirebaseAuth.getInstance().setLanguageCode("es"); // Idioma de FirebaseAuth

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.pokeball)      // Set logo drawable
                .setTheme(R.style.Theme_PracticaPokemon)      // Set theme
                .build();

        signInLauncher.launch(signInIntent);
    }


    /**
     * Metodo auxiliar para el login
     */
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );


    /**
     * Metodo que comprueba el resultado del login
     * @param result Resultado del login
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            // Lanzamos la mainActivity
            goToMainActivity();
        } else {
            Toast.makeText(this, getString(R.string.login_error_msg), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Metodo que comprueba si hay sesion abierta cada vez que se muestra la pantalla
     */
    @Override
    protected void onStart() {
        super.onStart();

        // Obtenemos el usuario de Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Si no es nullo, vamos a MainActivity, si no vamos a login
        if (user != null){
            goToMainActivity();
        } else {
            startSignIn();
        }

    }


    /**
     * Metodo para lanzar la actividad principal cuando sea necesario
     */
    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
