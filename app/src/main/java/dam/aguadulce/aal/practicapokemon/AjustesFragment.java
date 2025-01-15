package dam.aguadulce.aal.practicapokemon;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import dam.aguadulce.aal.practicapokemon.databinding.FragmentAjustesBinding;


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

        binding.about.setOnClickListener(v -> onAboutSelected());
        binding.closeSession.setOnClickListener(v -> onCloseSessionSelected());

        return binding.getRoot();
    }

    private void onCloseSessionSelected() {
        // Se cierra sesión y se notifica al usuario
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

        // Volvemos a la pantalla de login
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }


    private void onAboutSelected(){
        TextView messageTextView = new TextView(requireContext());
        messageTextView.setText(R.string.about_msg);
        messageTextView.setGravity(Gravity.CENTER); // Center the text

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.about)
                .setView(messageTextView)
                .setPositiveButton(R.string.close_dialog, (dialog, which) -> dialog.dismiss())
                .show();
    }
}