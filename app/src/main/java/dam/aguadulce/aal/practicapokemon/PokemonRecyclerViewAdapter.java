package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import dam.aguadulce.aal.practicapokemon.databinding.PokemonCardviewBinding;
import java.util.ArrayList;


/**
 * Clase que implementa el adaptador del RecyclerView
 */
public class PokemonRecyclerViewAdapter extends Adapter<PokemonViewHolder> {

    private final ArrayList<PokemonDetails> pokemons;
    private final Fragment fragment;


    /**
     * Constructor
     * @param pokemons
     * @param fragment
     */
    public PokemonRecyclerViewAdapter (ArrayList<PokemonDetails> pokemons, Fragment fragment){
        this.pokemons = pokemons;
        this.fragment = fragment;
    }


    /**
     * Método que infla el layout del ViewHolder
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @NonNull
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        PokemonCardviewBinding binding = PokemonCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new PokemonViewHolder(binding);
    }


    /**
     * Método que actualiza el contenido del ViewHolder, también añade el listener para el click en la tarjeta
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position){
        PokemonDetails currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        holder.itemView.setOnClickListener(view -> {
            Bundle result = new Bundle();
            result.putString("nombrePokemon", currentPokemon.getName());

            // Enviamos el resultado al FragmentManager
            fragment.requireActivity()
                    .getSupportFragmentManager()
                    .setFragmentResult("cardClick", result);

        });
    }


    /**
     * Método que cuenta la cantidad de personajes que hay en la lista
     * @return int Numero de personajes
     */
    @Override
    public int getItemCount(){
        return pokemons.size();
    }
}


