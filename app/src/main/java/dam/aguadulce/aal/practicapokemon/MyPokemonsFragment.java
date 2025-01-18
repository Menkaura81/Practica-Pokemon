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
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;

import dam.aguadulce.aal.practicapokemon.databinding.FragmentMisPokemonsBinding;


public class MyPokemonsFragment extends Fragment {

    private FragmentMisPokemonsBinding binding;
    private PokemonRecyclerViewAdapter adapter;
    private ArrayList<PokemonDetails> pokemonDetallesLista;


    /**
     * Constructor vacío requerido
     */
    public MyPokemonsFragment() {
    }


    /**
     * Método estático para crear una instancia del fragmento con argumentos
     * @param listaPokemon Lista de pokemons que el usuario tiene capturados
     * @return
     */
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

        // Listner para el borrado de pokemos desde los detalles
        requireActivity().getSupportFragmentManager().setFragmentResultListener("deletePokemon", this, (requestKey, result) -> {
            PokemonDetails pokemonDetails = (PokemonDetails) result.getSerializable("pokemonDetails");
            if (pokemonDetails != null) {
                deletePokemon(pokemonDetails);
            }
        });


        // Recuperamos la lista de Pokémon desde los argumentos
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


    /**
     * Método para eliminar un Pokémon de la lista y actualizar la vista
     * @param pokemonDetails Objeto del tipo PokemonDetails que queremos eliminar
     */
    public void deletePokemon(PokemonDetails pokemonDetails) {
        // Primero lo borramos de la base de datos
        if (pokemonDetallesLista != null && pokemonDetails != null) {
            // Hay que buscar en firestore el pokemon con ese id y luego obtener el id de firestore para poder eliminarlo
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("user")
                    .whereEqualTo("id", pokemonDetails.getId())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // Recorrer los documentos encontrados
                            for (DocumentSnapshot document : queryDocumentSnapshots) {
                                // Eliminar el documento usando su ID de Firestore
                                db.collection("user")
                                        .document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(unused ->
                                                Toast.makeText(getContext(), pokemonDetails.getName()+ " " + getString(R.string.delete_msg), Toast.LENGTH_SHORT).show()
                                        )
                                        .addOnFailureListener(e ->
                                                Toast.makeText(getContext(), "Delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                        );
                            }
                        } else {
                            // Si no se encontró el Pokémon
                            Toast.makeText(getContext(), "No Pokémon found with the specified ID", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Error fetching Pokémon: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
            // Luego lo borramos de la lista local
            pokemonDetallesLista.remove(pokemonDetails);
            // Notificamos que la lista ha cambiado
            adapter.notifyDataSetChanged();
        }
    }
}