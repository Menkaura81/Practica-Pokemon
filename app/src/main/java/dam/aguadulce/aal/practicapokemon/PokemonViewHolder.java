package dam.aguadulce.aal.practicapokemon;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import dam.aguadulce.aal.practicapokemon.databinding.PokemonCardviewBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    private final PokemonCardviewBinding binding;

    public PokemonViewHolder(PokemonCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind (PokemonDetalles pokemon) {
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
