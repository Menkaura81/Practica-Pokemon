package dam.aguadulce.aal.practicapokemon;

import static dam.aguadulce.aal.practicapokemon.PreferencesHelper.KEY_DELETE_POKEMONS;
import static dam.aguadulce.aal.practicapokemon.PreferencesHelper.PREFS_NAME;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import dam.aguadulce.aal.practicapokemon.databinding.FragmentPokemonDetailsBinding;


/**
 * Clase que implementa el fragmento detalles
 */
public class FragmentDetails extends Fragment {

    private FragmentPokemonDetailsBinding binding;

    /**
     * Constructor vacío requerido
     */
    public FragmentDetails() {
    }


    /**
     * Método onCreateView que infla el layout con el binding
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
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    /**
     * Método onViewCreated que actualiza la interfaz con los datos del pokemon
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        PokemonDetails pokemonDetails;

        super.onViewCreated(view, savedInstanceState);

        // Datos pasados en el Bundle
        Bundle args = getArguments();
        if (args != null) {
            pokemonDetails = (PokemonDetails) args.getSerializable("pokemonDetalles");
            if (pokemonDetails != null) {
                updateUI(pokemonDetails); // Actualizar la interfaz con los datos
            } else {
                // Manejo en caso de que el objeto sea nulo
                binding.detailName.setText("Detalles no disponibles");
            }
        } else {
            pokemonDetails = null;
        }

        // Obtenemos la preferencia de SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        boolean canDelete = sharedPreferences.getBoolean(KEY_DELETE_POKEMONS, false);

        // Mostramos u ocultamos el botón de eliminación basado en el valor de la preferencia
        if (canDelete) {
            binding.deleteButton.setVisibility(View.VISIBLE); // Mostrar el botón
        } else {
            binding.deleteButton.setVisibility(View.GONE); // Ocultar el botón
        }

        // Botón de eliminación
        binding.deleteButton.setOnClickListener(v -> {
            // Creamos el bundle con los datos del pokemon
            if (pokemonDetails != null) {
                Bundle result = new Bundle();
                result.putSerializable("pokemonDetails", pokemonDetails);
                requireActivity().getSupportFragmentManager().setFragmentResult("deletePokemon", result);

                // Volvemos al fragmento anterior (mypokemons)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    /**
     * Método que actualiza la interfaz con los datos del pokemon
     * @param pokemonDetails
     */
    private void updateUI(PokemonDetails pokemonDetails) {
        // Usamos Picasso para cargar la imagen del Pokémon
        Picasso.get()
                .load(pokemonDetails.getSprite()) // URL de la imagen
                .placeholder(R.drawable.placeholder) // Imagen de carga
                .error(R.drawable.placeholder) // Imagen de error
                .into(binding.detailImage); // ImageView destino

        binding.detailName.setText(pokemonDetails.getName());
        binding.detailIndex.setText(pokemonDetails.getId());
        binding.detailType.setText(pokemonDetails.getType());
        binding.detailHeight.setText(pokemonDetails.getHeight());
        binding.detailWeight.setText(pokemonDetails.getWeight());
    }
}