package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import dam.aguadulce.aal.practicapokemon.databinding.FragmentPokedexBinding;


/**
 * Clase que implementa el fragmento de la pokedex
 */
public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private PokemonRecyclerViewAdapter adapter;
    private ArrayList<PokemonDetails> pokemonDetallesLista;


    /**
     * Constructor vacío requerido
     */
    public PokedexFragment() {
    }


    /**
     * Método estático para crear una instancia del fragmento con argumentos
     * @param listaPokemon
     * @return
     */
    public static PokedexFragment newInstance(ArrayList<PokemonDetails> listaPokemon) {
        PokedexFragment fragment = new PokedexFragment();
        Bundle args = new Bundle();
        args.putSerializable("pokemonDetallesLista", listaPokemon); // Pasar la lista como argumento serializable
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Método onCreate que recupera la lista de pokemons
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recuperamos la lista de Pokémon desde los argumentos
        if (getArguments() != null) {
            pokemonDetallesLista = (ArrayList<PokemonDetails>) getArguments().getSerializable("pokemonDetallesLista");
        }
    }


    /**
     * Método onCreateView que infla el layout del fragmento y configura el RecyclerView
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

        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Configuramos el adaptador con el listener
        adapter = new PokemonRecyclerViewAdapter(pokemonDetallesLista, this);
        binding.pokedexRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.pokedexRecyclerView.setAdapter(adapter);

        // Configuramos el receptor del resultado
        requireActivity().getSupportFragmentManager().setFragmentResultListener("cardClick", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if ("cardClick".equals(requestKey)) {
                    // Recuperamos los datos del Pokémon desde el resultado
                    String nombrePokemon = result.getString("nombrePokemon");

                    // Buscamos el Pokémon en la lista para pasar todos los detalles
                    PokemonDetails selectedPokemon = null;
                    for (PokemonDetails pokemon : pokemonDetallesLista) {
                        if (pokemon.getName().equals(nombrePokemon)) {
                            selectedPokemon = pokemon;
                            break;
                        }
                    }

                    if (selectedPokemon != null) {
                        // Añadimos pokemon a la base de datos y a la lista
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("user").add(selectedPokemon)
                                .addOnSuccessListener(runnable ->
                                        Toast.makeText(getContext(), nombrePokemon + " " + getString(R.string.pokemon_added_msg) , Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(runnable ->
                                        Toast.makeText(getContext(), getString(R.string.pokemon_add_failed_msg), Toast.LENGTH_SHORT).show()
                        );
                    }
                }
            }
        });

        return binding.getRoot();
    }
}