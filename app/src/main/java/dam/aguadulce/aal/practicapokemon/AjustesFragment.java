package dam.aguadulce.aal.practicapokemon;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dam.aguadulce.aal.practicapokemon.databinding.FragmentAjustesBinding;
import dam.aguadulce.aal.practicapokemon.databinding.FragmentMisPokemonsBinding;


public class AjustesFragment extends Fragment {

    private FragmentAjustesBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAjustesBinding.inflate(inflater, container, false);

        binding.about.setOnClickListener(this::onAjustesSelected);


        return binding.getRoot();
    }


    private boolean onAjustesSelected(){
        return true;
    }
}