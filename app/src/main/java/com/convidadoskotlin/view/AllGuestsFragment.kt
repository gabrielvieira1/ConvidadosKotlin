package com.convidadoskotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.convidadoskotlin.databinding.FragmentAllBinding
import com.convidadoskotlin.service.constants.GuestConstants
import com.convidadoskotlin.view.adapter.GuestAdapter
import com.convidadoskotlin.view.listener.GuestListener
import com.convidadoskotlin.viewmodel.GuestsViewModel
import com.convidadoskotlin.view.GuestFormActivity

class AllGuestsFragment : Fragment() {

    private lateinit var viewModel: GuestsViewModel
    private val adapter: GuestAdapter = GuestAdapter()
    private lateinit var listener: GuestListener
    private var _binding: FragmentAllBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View? {

        viewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Elemento de interface - RecyclerView
        // Não é possível deixar o Kotlin fazer o mapeamento, pois a fragment ainda não está totalmente criada
        // Assim, precisamos buscar o elemento através de findViewById
        val recycler = binding.recyclerAllGuests

        // Atribui um layout que diz como a RecyclerView se comporta
        recycler.layoutManager = LinearLayoutManager(context)

        // Defini um adapater - Faz a ligação da RecyclerView com a listagem de itens
        recycler.adapter = adapter

        listener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUEST.ID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.load(GuestConstants.FILTER.EMPTY)
            }
        }

        // Cria os observadores
        observe()

        adapter.attachListener(listener)
        return root
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(GuestConstants.FILTER.EMPTY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Cria os observadores
     */
    private fun observe() {
        viewModel.guestList.observe(viewLifecycleOwner) {
            adapter.updateGuests(it)
        }
    }
}