package dam.aguadulce.aal.practicapokemon;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.Serializable;
import java.util.ArrayList;

import dam.aguadulce.aal.practicapokemon.databinding.FragmentMisPokemonsBinding;


public class MyPokemonsFragment extends Fragment {

    private FragmentMisPokemonsBinding binding;
    private PokemonRecyclerViewAdapter adapter;
    private ArrayList<PokemonDetails> pokemonDetallesLista;

    public MyPokemonsFragment() {
        // Constructor vacío requerido
    }


    // Método estático para crear una instancia del fragmento con argumentos
    public static MyPokemonsFragment newInstance(ArrayList<PokemonDetails> listaPokemon) {
        MyPokemonsFragment fragment = new MyPokemonsFragment();
        Bundle args = new Bundle();
        args.putSerializable("pokemonDetallesLista", listaPokemon); // Pasar la lista como argumento serializable
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperar la lista de Pokémon desde los argumentos
        if (getArguments() != null) {
            pokemonDetallesLista = (ArrayList<PokemonDetails>) getArguments().getSerializable("pokemonDetallesLista");
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMisPokemonsBinding.inflate(inflater, container, false);

        if (pokemonDetallesLista != null) {
            // Configurar el adaptador
            adapter = new PokemonRecyclerViewAdapter(pokemonDetallesLista, this);
            binding.misPokemonsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.misPokemonsRecyclerView.setAdapter(adapter);
        }

        // Configurar el receptor del resultado
        requireActivity().getSupportFragmentManager().setFragmentResultListener("cardClick", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if ("cardClick".equals(requestKey)) {
                    // Recuperar los datos del Pokémon desde el resultado
                    String nombrePokemon = result.getString("nombrePokemon");

                    // Buscar el Pokémon en la lista para pasar todos los detalles
                    PokemonDetails selectedPokemon = null;
                    for (PokemonDetails pokemon : pokemonDetallesLista) {
                        if (pokemon.getName().equals(nombrePokemon)) {
                            selectedPokemon = pokemon;
                            break;
                        }
                    }

                    if (selectedPokemon != null) {
                        // Navegar al fragmento de detalles pasando los datos
                        NavController navController = NavHostFragment.findNavController(MyPokemonsFragment.this);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("pokemonDetalles", (Serializable) selectedPokemon);
                        navController.navigate(R.id.pokemon_details, bundle);
                    }
                }
            }
        });

        return binding.getRoot();
    }


    // Método para eliminar un Pokémon de la lista y actualizar la vista
    public void deletePokemon(PokemonDetails pokemonDetails) {

        if (pokemonDetallesLista != null && pokemonDetails != null) {
            pokemonDetallesLista.remove(pokemonDetails);
            adapter.notifyDataSetChanged();
            for (PokemonDetails pokemon : pokemonDetallesLista) {
                Log.d("PokemonList", "Pokemon: " + pokemon.getName());
            }// Notificar al adaptador que los datos han cambiado
        }
    }
}