package dam.aguadulce.aal.practicapokemon;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
                        // Añadir pokemon a la base de datos y a la lista


                        //Mostrar notificacion
                        TextView messageTextView = new TextView(requireContext());
                        messageTextView.setText("\n" + nombrePokemon +  " added to your list");
                        messageTextView.setGravity(Gravity.CENTER); // Center the text
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                        builder.setTitle("Add pokemon")
                                .setView(messageTextView)
                                .setPositiveButton(R.string.close_dialog, (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                }
            }
        });
        // Retornar la raíz del binding
        return binding.getRoot();

    }
}