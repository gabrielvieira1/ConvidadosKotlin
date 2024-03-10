package com.convidadoskotlin.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.convidadoskotlin.R
import com.convidadoskotlin.databinding.RowGuestBinding
import com.convidadoskotlin.service.model.GuestModel
import com.convidadoskotlin.view.listener.GuestListener

class GuestViewHolder(private val item: RowGuestBinding, private val listener: GuestListener) :
    RecyclerView.ViewHolder(item.root) {

    fun bind(guest: GuestModel) {

        // Atribui valores
        item.textName.text = guest.name

        // Atribui eventos
        item.textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        // Atribui eventos
        item.textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }
    }
}