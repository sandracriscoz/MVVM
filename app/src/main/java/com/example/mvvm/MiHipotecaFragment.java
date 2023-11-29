package com.example.mvvm;

import android.app.Application;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.mvvm.databinding.FragmentMiHipotecaBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MiHipotecaFragment extends Fragment {
    private FragmentMiHipotecaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiHipotecaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MiHipotecaViewModel miHipotecaViewModel = new ViewModelProvider(this).get(MiHipotecaViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double capital = Double.parseDouble(binding.capital.getText().toString());
                int plazo = Integer.parseInt(binding.plazo.getText().toString());

                miHipotecaViewModel.calcular(capital, plazo);
            }
        });

        miHipotecaViewModel.cuota.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double cuota) {
                binding.cuota.setText(String.format("%.2f",cuota));
            }
        });
    }
    public class MiHipotecaViewModel extends AndroidViewModel {

        Executor executor;

        SimuladorHipoteca simulador;

        MutableLiveData<Double> cuota = new MutableLiveData<>();

        public MiHipotecaViewModel(@NonNull Application application) {
            super(application);

            executor = Executors.newSingleThreadExecutor();
            simulador = new SimuladorHipoteca();
        }

        public void calcular(double capital, int plazo) {

            final SimuladorHipoteca.Solicitud solicitud = new SimuladorHipoteca.Solicitud(capital, plazo);

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    double cuotaResultante = simulador.calcular(solicitud);
                    cuota.postValue(cuotaResultante);
                }
            });
        }
    }
}
