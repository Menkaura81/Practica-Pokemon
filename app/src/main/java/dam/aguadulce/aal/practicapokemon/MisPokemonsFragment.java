package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dam.aguadulce.aal.practicapokemon.databinding.FragmentMisPokemonsBinding;


public class MisPokemonsFragment extends Fragment {

    private FragmentMisPokemonsBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMisPokemonsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}