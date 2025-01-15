package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        Log.d("TAG", "hasta aqui");
        if (pokemonDetallesLista != null){
            adapter = new PokemonRecyclerViewAdapter(pokemonDetallesLista, this);
            binding.misPokemonsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            binding.misPokemonsRecyclerView.setAdapter(adapter);
        }




        return binding.getRoot();
    }
}