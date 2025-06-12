package com.aula.desafio_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aula.desafio_mobile.databinding.NavigationHomeAdminBinding

class HomeAdminFragment : Fragment() {
    private var _binding: NavigationHomeAdminBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterAtendimento: AdapterAtendimento
    private val atendimentos = mutableListOf<Atendimento>()
    private val db = Database()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NavigationHomeAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFirestoreListener()
    }

    private fun setupRecyclerView() {
        adapterAtendimento = AdapterAtendimento(atendimentos)

        binding.rvAdmin.apply {
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

    fun addAtendimento(atendimento: Atendimento) {
        db.salvar(atendimento, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}