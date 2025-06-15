package com.aula.desafio_mobile.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aula.desafio_mobile.AdapterAtendimento
import com.aula.desafio_mobile.Atendimento
import com.aula.desafio_mobile.Database
import com.aula.desafio_mobile.databinding.FragmentHomeBinding
import com.google.firebase.FirebaseApp

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterAtendimento: AdapterAtendimento
    private val atendimentos = mutableListOf<Atendimento>()
    private val db = Database()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFirestoreListener()
    }

    private fun setupRecyclerView() {
        adapterAtendimento = AdapterAtendimento(atendimentos, false)

        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterAtendimento
            addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun setupFirestoreListener() {
        db.listar(atendimentos, adapterAtendimento, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}