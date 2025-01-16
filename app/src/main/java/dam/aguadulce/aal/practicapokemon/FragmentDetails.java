package dam.aguadulce.aal.practicapokemon;

import static dam.aguadulce.aal.practicapokemon.PreferencesHelper.KEY_DELETE_POKEMONS;
import static dam.aguadulce.aal.practicapokemon.PreferencesHelper.PREFS_NAME;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import dam.aguadulce.aal.practicapokemon.databinding.FragmentPokemonDetailsBinding;


public class FragmentDetails extends Fragment {

    private FragmentPokemonDetailsBinding binding;

    public FragmentDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar binding
        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        PokemonDetails pokemonDetails;

        super.onViewCreated(view, savedInstanceState);

        // Obtener los datos pasados en el Bundle
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

        // Obtener la preferencia de SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        boolean canDelete = sharedPreferences.getBoolean(KEY_DELETE_POKEMONS, false);

        // Mostrar u ocultar el botón de eliminación basado en el valor de la preferencia
        if (canDelete) {
            binding.deleteButton.setVisibility(View.VISIBLE); // Mostrar el botón
        } else {
            binding.deleteButton.setVisibility(View.GONE); // Ocultar el botón
        }

        // Configurar el botón de eliminación
        binding.deleteButton.setOnClickListener(v -> {
            if (pokemonDetails != null) {
                Toast.makeText(requireContext(), pokemonDetails.getName() + " eliminado", Toast.LENGTH_SHORT).show();
                Log.d("PokemonList", "antes del borrado" );
                // Intentar encontrar el fragmento que contiene la lista de Pokémon y eliminarlo
                Fragment fragment = getParentFragment();
                if (fragment instanceof MyPokemonsFragment) {
                    MyPokemonsFragment myPokemonsFragment = (MyPokemonsFragment) fragment;
                    myPokemonsFragment.deletePokemon(pokemonDetails);
                }



                // Volver al fragmento MyPokemonsFragment (atras)
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }


    private void updateUI(PokemonDetails pokemonDetails) {
        // Usar Picasso para cargar la imagen del Pokémon
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