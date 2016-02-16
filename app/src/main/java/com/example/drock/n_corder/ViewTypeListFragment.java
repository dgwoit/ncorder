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

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewTypeListFragment extends ListFragmentBase {
    public ViewTypeListFragment() {}

    public static ViewTypeListFragment newInstance() {
        ViewTypeListFragment fragment = new ViewTypeListFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    protected void createListItems() {
        IOIOConnectionTable connections = new IOIOConnectionTable();
        List<IOIOConnectionInfo> connectionInfos = connections.getConnectionInfo();
        ArrayList<ListItem> listItems = new ArrayList<ListItem>();
        listItems.add(new ListItem("Numeric Display", DataViewFactory.ViewType.NumericDisplay));
        listItems.add(new ListItem("Data Plot", DataViewFactory.ViewType.DataPlot));
        setListItems(listItems);
    }
}
