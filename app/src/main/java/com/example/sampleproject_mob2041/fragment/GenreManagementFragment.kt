package com.example.sampleproject_mob2041.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleproject_mob2041.R
import com.example.sampleproject_mob2041.adapter.GenreAdapter
import com.example.sampleproject_mob2041.database.Database
import com.example.sampleproject_mob2041.database.GenreDB
import com.example.sampleproject_mob2041.databinding.FragmentGenreBinding
import com.example.sampleproject_mob2041.model.Genre
import com.example.sampleproject_mob2041.viewhandler.SetupRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class GenreManagementFragment : Fragment() {
    private lateinit var binding: FragmentGenreBinding
    private lateinit var adapter:GenreAdapter
    private lateinit var genreList:ArrayList<Genre>
    private lateinit var genreDB:GenreDB


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genreDB = GenreDB(Database(requireContext()))
        genreList = genreDB.getAllGenres()


        adapter = GenreAdapter(requireContext(), genreList)

        setupRecyclerView(binding.genreList)

        setupFAB(binding.fabAddGenre)

    }

    private fun setupRecyclerView(view: RecyclerView) {
        SetupRecyclerView().setupRecyclerView(
                requireContext(),
                binding.genreList,
                adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
            )
    }

    private fun setupFAB(view:FloatingActionButton) {
        view.setOnClickListener {
            // inflate view
            val view =
                LayoutInflater.from(requireContext()).inflate(R.layout.new_genre_dialog, null)
            val edtLayoutNewGenre: TextInputLayout =
                view.findViewById(R.id.edt_layout_new_genre)
            val edtNewGenre: TextInputEditText = view.findViewById(R.id.edt_new_genre)

            // setup view and button for dialog
            val dialog: AlertDialog = AlertDialog.Builder(requireContext())
                .setTitle(requireContext().getText(R.string.add_new_genre))
                .setView(view)
                .setNegativeButton(requireContext().getText(R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(requireContext().getText(R.string.add), null)
            .show()


            // get positive button and set on click for it
            val positiveButton: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener {
                if (edtNewGenre.text.isNullOrBlank()) {
                    edtLayoutNewGenre.error = requireContext().getString(R.string.you_must_enter_all_information)
                } else {
                    val genre = Genre(name = edtNewGenre.text.toString())
                    addIntoDatabase(genre)

                    dialog.dismiss()

                    adapter.mList.clear()
                    adapter.mList = genreDB.getAllGenres()
                    adapter.notifyItemInserted(genreDB.getAllGenres().size)
                }
            }
        }


    }

    private fun addIntoDatabase(genre: Genre) {

        if (genreDB.addGenre(name = genre.name)) {
            Toast.makeText(requireContext(), requireContext().getText(R.string.add_successful), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(),requireContext().getText(R.string.add_failed), Toast.LENGTH_SHORT).show()
        }
    }
}

