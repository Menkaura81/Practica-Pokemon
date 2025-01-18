package dam.aguadulce.aal.practicapokemon;

import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import dam.aguadulce.aal.practicapokemon.databinding.PokemonCardviewBinding;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    private final PokemonCardviewBinding binding;


    /**
     * Constructor
     * @param binding Instancia de la clase PokemonCardviewBinding
     */
    public PokemonViewHolder(PokemonCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    /**
     * Método que bindea los datos del pokemon al cardview
     * @param pokemon Objeto del tipo PokemonDetails que incluye los detalles del Pokemon
     */
    public void bind (PokemonDetails pokemon) {
        binding.nombrePokemon.setText(pokemon.getName());
        // Usamos Picasso para descargar la imagen y bindearla al cardview
        Picasso.get()
                .load(pokemon.getSprite()) // URL de la imagen
                .placeholder(R.drawable.placeholder) // Imagen de carga
                .error(R.drawable.placeholder) // Imagen en caso de error
                .into(binding.imagenPokemon);
        binding.executePendingBindings();
    }
}
