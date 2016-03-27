/*
* THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.example.drock.n_corder;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drock.n_corder.units.UnitFormatter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumericViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumericViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumericViewFragment extends DataViewFragment implements IMeasurementSink {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "streamName";


    private String mStreamName;

    private TextView mTV;
    //private OnFragmentInteractionListener mListener;
    private UnitFormatter mFormatter;
    private boolean mChanged = true;

    public NumericViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param streamName the name of the sensor stream to attach to
     * @return A new instance of fragment NumericViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumericViewFragment newInstance(String streamName) {
        NumericViewFragment fragment = new NumericViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, streamName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStreamName = getArguments().getString(ARG_PARAM1);
            //SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SensorStreamBroker.getInstance().DetachFromStream(this, mStreamName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_numeric_view, container, false);
        mTV = (TextView) view.findViewById(R.id.fragmentSensorValue);
        SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // from MeasurementSink
    @Override
    public boolean update(Measurement m) {
        if(null == mFormatter) {
            mFormatter = new UnitFormatter(m.getUnit());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String title = String.format("%s (%s)", mFormatter.getUnitSystemName(), mFormatter.getUnitName());
                    ((DataViewActivity)getActivity()).setTitle(title);
                }
            });
        }
        return setValue(m.getValue());
    }

    float mValue = 0;
    private synchronized boolean setValue(float value) {
        try {
            if(value != mValue || mChanged ) {
                mValue = value;
                mChanged = false;

                final String str = mFormatter.format(value);

                //this would be better done as a view
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTV.setText(str);
                    }
                });
            }
            return true;
        } catch (Exception e){
            Log.e("NumericViewFragment", "error setting value");
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SensorStreamBroker.getInstance().AttachToStream(this, mStreamName);
    }

    @Override
    public void onPause() {
        super.onPause();
        SensorStreamBroker.getInstance().DetachFromStream(this, mStreamName);
    }
}
