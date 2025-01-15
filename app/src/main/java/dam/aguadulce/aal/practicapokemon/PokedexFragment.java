package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dam.aguadulce.aal.practicapokemon.databinding.FragmentPokedexBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokedexFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private PokemonRecyclerViewAdapter adapter;
    private ArrayList<PokemonDetails> pokemonDetallesLista;

    public PokedexFragment() {
        // Constructor vacío requerido
    }

    // Método estático para crear una instancia del fragmento con argumentos
    public static PokedexFragment newInstance(ArrayList<PokemonDetails> listaPokemon) {
        PokedexFragment fragment = new PokedexFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configurar el adaptador con el listener
        adapter = new PokemonRecyclerViewAdapter(pokemonDetallesLista, this);
        binding.pokedexRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokedexRecyclerView.setAdapter(adapter);


        // Retornar la raíz del binding
        return binding.getRoot();

    }
}