package com.dawnofneo.everdo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {

    RecyclerView reminderRecyclerView;
    ReminderRecyclerAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ReminderDatabaseAdapter databaseAdapter;

    private OnFragmentInteractionListener mListener;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReminderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(databaseAdapter.getAllReminderDataList().isEmpty()==false)
            adapter = new ReminderRecyclerAdapter(getActivity(),databaseAdapter.getAllReminderDataList());
        else
            adapter = new ReminderRecyclerAdapter(getActivity(),getElements());
        reminderRecyclerView.setAdapter(adapter);
        Toast.makeText(getActivity(), "Resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_reminder, container, false);
        reminderRecyclerView = (RecyclerView) layout.findViewById(R.id.reminder_recyclerView);

        databaseAdapter = new ReminderDatabaseAdapter(getContext());
        if(databaseAdapter.getAllReminderDataList().isEmpty()==false)
            adapter = new ReminderRecyclerAdapter(getActivity(),databaseAdapter.getAllReminderDataList());
        else
            adapter = new ReminderRecyclerAdapter(getActivity(),getElements());
        reminderRecyclerView.setAdapter(adapter);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.add_reminder_icon);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getActivity(),CreateReminder.class);
                startActivity(intent);
            }
        });

        return layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public static List<ReminderElements> getElements(){
        List<ReminderElements> elementsList = new ArrayList<>();
        String taskOverview[] = {"task 1","task 2","task 3","task 4","task 5","task 6","task 7","task 8"};
        String subtasks[]= {"subtask 1","subtask 2","subhello neo hello neo hello hello heool hello ehelllo task 3","subtask 4","subtask 5","subtask 6","subtask 7","subtask 8"};
        String notifyAt[]={"notify 1","notify 2","notify 3","notify 4","notify 5","notify 6","notify 7","notify 8"};
        String taskLocation[]= {"Location 1","Location 2","Location 3","Location 4","Location 5","Location 6","Location 7","Location 8"};
        String deleteTask[]= {"del 1","del 2","del 3","del 4","del 5","del 6","del 7","del 8"};
        String isDone[]= {"isdone 1","isdone 2","isdone 3","isdone 4","isdone 5","isdone 6","isdone 7","isdone 8"};
        String startAt[]= {"start 1","start 2","start 3","start 4","start 5","start 6","start 7","start 8"};
        String endAt[]= {"end 1","end 2","end 3","end 4","end 5","end 6","end 7","end 8"};

        for(int i = 0;i<taskOverview.length&&i<subtasks.length&&i<notifyAt.length&&i<taskLocation.length&&i<deleteTask.length&&i<isDone.length&&i<startAt.length&&i<endAt.length;i++){
            ReminderElements element = new ReminderElements();
            element. taskOverview = taskOverview[i];
            element. subtasks = subtasks[i];
            element. notifyAt = notifyAt[i];
            element. taskLocation = taskLocation[i];
            element. deleteTask = deleteTask[i];
            element. isDone = isDone[i];
            element. startAt = startAt[i];
            element. endAt = endAt[i];
            elementsList.add(i,element);
        }
        return elementsList;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
