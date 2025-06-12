package com.aula.desafio_mobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        val btEntrar = view.findViewById<View>(R.id.bt_entrar)

        btEntrar.setOnClickListener() {
            val inputSenha = view.findViewById<EditText>(R.id.input_senha)
            val inputUser = view.findViewById<EditText>(R.id.input_usuario)
            val senha = inputSenha.text.toString()
            val user = inputUser.text.toString()
            lifecycleScope.launch {
                val admin = buscarAdmin(user, senha)
                if (admin) {
                    val navController = Navigation.findNavController(view)
                    Toast.makeText(context, "Senha correta!", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.action_navigation_admin_to_homeAdminFragment)
                } else {
                    Toast.makeText(context, "Senha incorreta!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    suspend fun buscarAdmin(user: String, password: String): Boolean {
        val db = FirebaseFirestore.getInstance()
        val querySnapshot = db.collection("admin")
            .whereEqualTo("user", user)
            .whereEqualTo("password", password)
            .get().await()
        if (!querySnapshot.isEmpty) return true
        return false
    }
}