package com.rookieandroid.admin.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.rookieandroid.admin.Admin
import com.rookieandroid.admin.App.Companion.TYPE_HEADER
import com.rookieandroid.admin.R
import com.rookieandroid.admin.adapters.AdminAdapter
import com.rookieandroid.admin.adapters.EmptyDataObserver
import com.rookieandroid.admin.adapters.SearchAdminAdapter
import com.rookieandroid.admin.architecture.AdminViewModel

class AdminFragment : Fragment(R.layout.fragment_admin)
{
    private lateinit var search : SearchView
    private lateinit var cancel : TextView
    private lateinit var noResults : TextView
    private lateinit var adminShimmer : ShimmerFrameLayout
    private lateinit var group : Group
    private lateinit var emptyGroup : Group
    private lateinit var searchGroup : Group

    private lateinit var adminRecyclerView : RecyclerView
    private lateinit var searchAdminRecyclerView: RecyclerView
    private lateinit var moderatorRecyclerView : RecyclerView
    private lateinit var searchModeratorRecyclerView : RecyclerView
    private lateinit var coachRecyclerView : RecyclerView
    private lateinit var searchCoachRecyclerView : RecyclerView

    private lateinit var emptyAdminView : View
    private lateinit var emptyAdminHeader : TextView
    private lateinit var emptyAdminMessage : TextView
    private lateinit var emptyModeratorView : View
    private lateinit var emptyModeratorHeader : TextView
    private lateinit var emptyModeratorMessage : TextView
    private lateinit var emptyCoachView : View
    private lateinit var emptyCoachHeader : TextView
    private lateinit var emptyCoachMessage : TextView

    private val admins : ArrayList<Admin> = ArrayList()
    private val searchAdmins : ArrayList<Admin> = ArrayList()
    private val moderators : ArrayList<Admin> = ArrayList()
    private val searchModerators : ArrayList<Admin> = ArrayList()
    private val coaches : ArrayList<Admin> = ArrayList()
    private val searchCoaches : ArrayList<Admin> = ArrayList()

    private val adminViewModel : AdminViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        search = view.findViewById(R.id.search)
        search.setOnQueryTextFocusChangeListener { v, hasFocus ->
            cancel.isVisible = true
            group.isVisible = false
            emptyGroup.isVisible = false
            searchGroup.isVisible = true
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                submitQuery(query)
                if(searchAdmins.isEmpty() && searchModerators.isEmpty() && searchCoaches.isEmpty())
                    noResults.isVisible = true
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdmins.clear()
                searchModerators.clear()
                searchCoaches.clear()
                noResults.isVisible = false
                return false
            }
        })

        cancel = view.findViewById(R.id.cancel_search)
        cancel.setOnClickListener {
            search.setQuery("", false)
            search.clearFocus()
            it.isVisible = false
            group.isVisible = true
            emptyAdminView.isVisible = admins.isEmpty()
            emptyModeratorView.isVisible = moderators.isEmpty()
            emptyCoachView.isVisible = coaches.isEmpty()
            searchGroup.isVisible = false
        }

        noResults = view.findViewById(R.id.search_no_results)
        adminShimmer = view.findViewById(R.id.admin_shimmer)
        group = view.findViewById(R.id.admin_lists)
        emptyGroup = view.findViewById(R.id.empty_lists)
        searchGroup = view.findViewById(R.id.search_lists)

        emptyAdminView = view.findViewById(R.id.admin_empty)
        emptyAdminHeader = emptyAdminView.findViewById(R.id.title_header)
        emptyAdminHeader.text = getString(R.string.admin_header)
        emptyAdminMessage = emptyAdminView.findViewById(R.id.empty_list)
        emptyAdminMessage.text = getString(R.string.no_admins)

        emptyModeratorView = view.findViewById(R.id.moderator_empty)
        emptyModeratorHeader = emptyModeratorView.findViewById(R.id.title_header)
        emptyModeratorHeader.text = getString(R.string.moderator_header)
        emptyModeratorMessage = emptyModeratorView.findViewById(R.id.empty_list)
        emptyModeratorMessage.text = getString(R.string.no_moderators)

        emptyCoachView = view.findViewById(R.id.coach_empty)
        emptyCoachHeader = emptyCoachView.findViewById(R.id.title_header)
        emptyCoachHeader.text = getString(R.string.coach_header)
        emptyCoachMessage = emptyCoachView.findViewById(R.id.empty_list)
        emptyCoachMessage.text = getString(R.string.no_coaches)

        adminRecyclerView = view.findViewById(R.id.admin_list)
        adminRecyclerView.layoutManager = LinearLayoutManager(context)
        adminRecyclerView.adapter = AdminAdapter(admins)
        //(adminRecyclerView.adapter as AdminAdapter).registerAdapterDataObserver(EmptyDataObserver(adminRecyclerView, emptyAdminView))

        searchAdminRecyclerView = view.findViewById(R.id.search_admin_list)
        searchAdminRecyclerView.layoutManager = LinearLayoutManager(context)
        searchAdminRecyclerView.adapter = SearchAdminAdapter(searchAdmins)

        moderatorRecyclerView = view.findViewById(R.id.moderator_list)
        moderatorRecyclerView.layoutManager = LinearLayoutManager(context)
        moderatorRecyclerView.adapter = AdminAdapter(moderators)
        //(moderatorRecyclerView.adapter as AdminAdapter).registerAdapterDataObserver(EmptyDataObserver(moderatorRecyclerView, emptyModeratorView))

        searchModeratorRecyclerView = view.findViewById(R.id.search_moderator_list)
        searchModeratorRecyclerView.layoutManager = LinearLayoutManager(context)
        searchModeratorRecyclerView.adapter = SearchAdminAdapter(searchModerators)

        coachRecyclerView = view.findViewById(R.id.coach_list)
        coachRecyclerView.layoutManager = LinearLayoutManager(context)
        coachRecyclerView.adapter = AdminAdapter(coaches)
        //(coachRecyclerView.adapter as AdminAdapter).registerAdapterDataObserver(EmptyDataObserver(coachRecyclerView, emptyCoachView))

        searchCoachRecyclerView = view.findViewById(R.id.search_coach_list)
        searchCoachRecyclerView.layoutManager = LinearLayoutManager(context)
        searchCoachRecyclerView.adapter = SearchAdminAdapter(searchCoaches)

        adminViewModel.getAdmins().observe(viewLifecycleOwner){
            adminShimmer.stopShimmer()
            adminShimmer.isVisible = false
            emptyAdminView.isVisible = it.isEmpty()
            admins.clear()
            admins.addAll(it)
            (adminRecyclerView.adapter as AdminAdapter).notifyDataSetChanged()
        }

        adminViewModel.getModerators().observe(viewLifecycleOwner){
            adminShimmer.stopShimmer()
            adminShimmer.isVisible = false
            emptyModeratorView.isVisible = it.isEmpty()
            moderators.clear()
            moderators.addAll(it)
            (moderatorRecyclerView.adapter as AdminAdapter).notifyDataSetChanged()
        }

        adminViewModel.getCoaches().observe(viewLifecycleOwner){
            adminShimmer.stopShimmer()
            adminShimmer.isVisible = false
            emptyCoachView.isVisible = it.isEmpty()
            coaches.clear()
            coaches.addAll(it)
            (coachRecyclerView.adapter as AdminAdapter).notifyDataSetChanged()
        }
    }

    private fun submitQuery(query : String?)
    {
        searchAdmins.clear()
        admins.forEach {
            if(it.name.uppercase().contains(query?.uppercase() as CharSequence))
            {
                searchAdmins.add(it)
            }
        }

        if(searchAdmins.size > 0)
        {
            searchAdmins.add(0, Admin("Admins", "", TYPE_HEADER))
            (searchAdminRecyclerView.adapter as SearchAdminAdapter).notifyDataSetChanged()
        }

        searchModerators.clear()
        moderators.forEach {
            if(it.name.uppercase().contains(query?.uppercase() as CharSequence))
            {
                searchModerators.add(it)
            }
        }

        if(searchModerators.size > 0)
        {
            searchModerators.add(0, Admin("Moderators", "", TYPE_HEADER))
            (searchModeratorRecyclerView.adapter as SearchAdminAdapter).notifyDataSetChanged()
        }

        searchCoaches.clear()
        coaches.forEach {
            if(it.name.uppercase().contains(query?.uppercase() as CharSequence))
            {
                searchCoaches.add(it)
            }
        }

        if(searchCoaches.size > 0)
        {
            searchCoaches.add(0, Admin("Coaches", "", TYPE_HEADER))
            (searchCoachRecyclerView.adapter as SearchAdminAdapter).notifyDataSetChanged()
        }
    }
}