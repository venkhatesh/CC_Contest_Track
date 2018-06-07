package com.example.venkat.dingdong;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FutureContest.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FutureContest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FutureContest extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    List<ContestInfo> contestlist = new ArrayList<>();
    ArrayList<String> ContestName = new ArrayList<>();
    ArrayList<String> StartDate = new ArrayList<>();
    ArrayList<String> EndDate = new ArrayList<>();
    String sh;
    String url = "https://www.codechef.com/contests";
    RecyclerView recyclerView;
    ContestAdapter contestAdapter;

    public FutureContest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FutureContest.
     */
    // TODO: Rename and change types and number of parameters
    public static FutureContest newInstance(String param1, String param2) {
        FutureContest fragment = new FutureContest();
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
        new Contest().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_future_contest, container, false);
    }
    private class Contest extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc =  Jsoup.connect(url).get();
                Element currentContest = doc.select("table.dataTable").get(1);
                for(Element row:currentContest.select("tr")){

                    ContestName.add(row.select("a[href]").text());
                    Log.v("Check",row.select("a[href]").text());
                    StartDate.add(row.select("td.start_date").text());
                    Log.v("Check",row.select("td.start_date").text());
                    EndDate.add(row.select("td.end_date").text());
                    Log.v("Check",row.select("td.end_date").text());

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView = (RecyclerView)getActivity().findViewById(R.id.future_recycler);
            contestAdapter = new ContestAdapter(contestlist,getActivity());
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(contestAdapter);
            Log.v("table 2",ContestName.toString()+StartDate.toString()+EndDate.toString());
            for(int i=1;i<ContestName.size();i++) {
                ContestInfo contestInfo = new ContestInfo(ContestName.get(i),
                        StartDate.get(i), EndDate.get(i));
                contestlist.add(contestInfo);
            }
            contestAdapter.notifyDataSetChanged();
        }
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
