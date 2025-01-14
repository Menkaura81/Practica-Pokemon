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
    private static final String TAG = "PokeViewHolder";

    public PokemonViewHolder(PokemonCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }


    public void bind (Pokemon pokemon) {
        binding.nombrePokemon.setText(pokemon.getName());
        // Hay que hacerlo así para no bloquear la interfaz de usuario ya que retrofit es asincrono
        fetchPokemonImage(pokemon.getUrl());
        binding.executePendingBindings();
    }


    private void fetchPokemonImage(String url) {
        // Crear instancia de Retrofit (usa tu cliente existente)
        PokemonApiService apiService = RetrofitClient.getClient().create(PokemonApiService.class);
        Call<PokemonDetails> call = apiService.getPokemonDetails(url);

        call.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(@NonNull Call<PokemonDetails> call, @NonNull Response<PokemonDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener la URL de la imagen desde el JSON
                    String imageUrl = response.body()
                            .getSprites()
                            .getVersions()
                            .getGeneration_iv()
                            .getPlatinum()
                            .getFront_default();

                    // Cargar la imagen usando Picasso
                    if (imageUrl != null) {
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.placeholder) // Imagen mientras carga
                                .error(R.drawable.placeholder)       // Imagen si hay error
                                .into(binding.imagenPokemon);
                    } else {
                        Log.e(TAG, "URL de imagen es null");
                    }
                } else {
                    Log.e(TAG, "Error al obtener los detalles del Pokémon: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PokemonDetails> call, @NonNull Throwable t) {
                Log.e(TAG, "Fallo en la petición: " + t.getMessage());
            }
        });
    }
}
