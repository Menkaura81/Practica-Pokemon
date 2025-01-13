package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import dam.aguadulce.aal.practicapokemon.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Navegacion
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
            //NavigationUI.setupActionBarWithNavController(this, navController);
        }

        // Listener para los clicks en el menu inferior
        binding.bottomNavigation.setOnItemSelectedListener(this::onMenuSelected);

        setContentView(binding.getRoot());
    }


    /**
     * Método que implementa la logica de navegación entre las pestañas del menu inferior
     * @param menuItem Item del menú que se ha pulsado
     * @return
     */
    private boolean onMenuSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.mis_pokemons_menu){
            navController.navigate(R.id.misPokemonsFragment);
        } else if (menuItem.getItemId() == R.id.pokedex_menu){
            navController.navigate(R.id.pokedexFragment);
        } else if (menuItem.getItemId() == R.id.ajustes_menu){
            navController.navigate(R.id.ajustesFragment);
        }
        return true;
    }
}