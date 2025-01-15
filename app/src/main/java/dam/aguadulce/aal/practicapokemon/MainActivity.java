package dam.aguadulce.aal.practicapokemon;

import static dam.aguadulce.aal.practicapokemon.PreferencesHelper.KEY_LANGUAGE;
import static dam.aguadulce.aal.practicapokemon.PreferencesHelper.PREFS_NAME;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import dam.aguadulce.aal.practicapokemon.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private List<PokemonDetails> pokemonDetailsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String languageCode = sharedPreferences.getString(KEY_LANGUAGE, "es");  // Idioma por defecto
        // Cambiar el idioma si es necesario
        changeLanguage(languageCode);

        // Petición a la API de Pokemon
        PokemonApiService apiService = RetrofitClient.getClient().create(PokemonApiService.class);
        Call<PokemonResponse> call = apiService.getPokemons(0, 150);
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemons = response.body().getResults();
                    fetchPokemonDetails(apiService, pokemons);
                } else {
                    Toast.makeText(MainActivity.this, "Respuesta fallida", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error en la petición", Toast.LENGTH_SHORT).show();
            }
        });

        // Navegación
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


    private void changeLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    /**
     * Método que implementa la segunda consulta a la API en la que se obtienen los detalles de cada pokemon. Esta en una función
     * separada porque Retrofit es asíncrono
     * @param apiService api de retrofit
     * @param pokemons Lista de pokemons obtenida en la primera consulta
     */
    private void fetchPokemonDetails(PokemonApiService apiService, List<Pokemon> pokemons) {
        for (Pokemon pokemon : pokemons) {
            Call<PokemonDetails> detailsCall = apiService.getPokemonDetails(pokemon.getUrl());
            detailsCall.enqueue(new Callback<PokemonDetails>() {
                @Override
                public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        pokemonDetailsList.add(response.body());

                    } else {
                        Toast.makeText(MainActivity.this, "Fallo al obtener detalles: ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PokemonDetails> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Fallo al obtener detalles: ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * Método que implementa la logica de navegación entre las pestañas del menu inferior
     * @param menuItem Item del menú que se ha pulsado
     * @return true
     */
    private boolean onMenuSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.mis_pokemons_menu){
            navController.navigate(R.id.misPokemonsFragment);
        } else if (menuItem.getItemId() == R.id.pokedex_menu){
            Bundle bundle = new Bundle();
            bundle.putSerializable("pokemonDetallesLista", new ArrayList<>(pokemonDetailsList));
            navController.navigate(R.id.pokedexFragment, bundle);
        } else if (menuItem.getItemId() == R.id.ajustes_menu){
            navController.navigate(R.id.ajustesFragment);
        }
        return true;
    }
}