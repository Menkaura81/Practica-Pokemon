package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private static final String TAG = "MainActivity";
    public List<PokemonDetalles> pokemonDetallesLista = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Peticion a la API de Pokemon
        PokemonApiService apiService = RetrofitClient.getClient().create(PokemonApiService.class);
        Call<PokemonResponse> call = apiService.getPokemons(0, 150);

        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Pokemon> pokemons = response.body().getResults();
                    fetchPokemonDetails(apiService, pokemons);
                } else {
                    Log.e(TAG, "Respuesta fallida: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "Error en la petición: " + t.getMessage());
            }
        });

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


    // Función para realizar consultas individuales por cada Pokémon usando su URL
    private void fetchPokemonDetails(PokemonApiService apiService, List<Pokemon> pokemons) {
        for (Pokemon pokemon : pokemons) {
            Call<PokemonDetalles> detailsCall = apiService.getPokemonDetails(pokemon.getUrl());
            detailsCall.enqueue(new Callback<PokemonDetalles>() {
                @Override
                public void onResponse(Call<PokemonDetalles> call, Response<PokemonDetalles> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        pokemonDetallesLista.add(response.body());
                        Log.d(TAG, "Detalles del Pokémon añadidos: " + response.body().getName());
                    } else {
                        Log.e(TAG, "Fallo al obtener detalles: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<PokemonDetalles> call, Throwable t) {
                    Log.e(TAG, "Error al obtener detalles: " + t.getMessage());
                }
            });
        }
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
            Bundle bundle = new Bundle();
            bundle.putSerializable("pokemonDetallesLista", new ArrayList<>(pokemonDetallesLista));
            navController.navigate(R.id.pokedexFragment, bundle);
        } else if (menuItem.getItemId() == R.id.ajustes_menu){
            navController.navigate(R.id.ajustesFragment);
        }
        return true;
    }



}