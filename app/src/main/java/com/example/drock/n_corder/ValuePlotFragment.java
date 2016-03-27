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


import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.drock.n_corder.units.UnitFormatter;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ValuePlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ValuePlotFragment extends DataViewFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STREAM_NAME = "streamName";

    private String mStreamName;
    private DataPlotView mDataView;
    private UnitFormatter mFormatter;

    public ValuePlotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param streamName name of stream to bind to
     * @return A new instance of fragment ValuePlotFragment.
     */
    public static ValuePlotFragment newInstance(String streamName) {
        assert !streamName.isEmpty();
        ValuePlotFragment fragment = new ValuePlotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_STREAM_NAME, streamName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStreamName = getArguments().getString(ARG_STREAM_NAME);
        }
    }

    @Override
    public int getMenuResource() { return R.menu.menu_data_plot; }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_regress) {
            mDataView.startRegressionTool();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_value_plot, container, false);
        mDataView = (DataPlotView)view.findViewById(R.id.data_plot_view);
        if(mDataView != null) {
            List<Measurement> data = MeasurementDataStore.getInstance().getData();
            mDataView.attachDataSource(data);
            Observer observer = new Observer() {
                @Override
                public void update(Observable observable, Object data) {
                    mDataView.onDataChanged();
                    List<Measurement> measurements = (List<Measurement>)data;
                    if(null == mFormatter && measurements.size() > 0) {
                        mFormatter = new UnitFormatter(measurements.get(0).getUnit());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String title = String.format("%s (%s)", mFormatter.getUnitSystemName(), mFormatter.getUnitName());
                                ((DataViewActivity)getActivity()).setTitle(title);
                            }
                        });
                    }
                }
            };
            MeasurementDataStore.getInstance().addObserver(observer);
        }
        return view;
    }
}
