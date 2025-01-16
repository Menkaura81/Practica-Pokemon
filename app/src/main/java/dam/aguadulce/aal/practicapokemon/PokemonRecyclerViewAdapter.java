package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import dam.aguadulce.aal.practicapokemon.databinding.PokemonCardviewBinding;
import java.util.ArrayList;

public class PokemonRecyclerViewAdapter extends Adapter<PokemonViewHolder> {

    private final ArrayList<PokemonDetails> pokemons;
    private final Fragment fragment;

    public PokemonRecyclerViewAdapter (ArrayList<PokemonDetails> pokemons, Fragment fragment){
        this.pokemons = pokemons;
        this.fragment = fragment;
    }


    @NonNull
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        PokemonCardviewBinding binding = PokemonCardviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new PokemonViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position){
        PokemonDetails currentPokemon = this.pokemons.get(position);
        holder.bind(currentPokemon);

        holder.itemView.setOnClickListener(view -> {
            Bundle result = new Bundle();
            result.putString("nombrePokemon", currentPokemon.getName());

            // Enviar el resultado al FragmentManager
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


