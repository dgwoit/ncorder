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
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewTypeActivity extends SingleFragmentActivity implements ViewTypeListFragment.OnListFragmentInteractionListener {
    String mStreamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(null != savedInstanceState) {
            mStreamName = savedInstanceState.getString(ParamNames.STREAM_NAME);
        } else {
            Intent intent = getIntent();
            mStreamName = intent.getStringExtra(ParamNames.STREAM_NAME);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(ParamNames.STREAM_NAME, mStreamName);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected Fragment createFragment() {
        return ViewTypeListFragment.newInstance();
    }

    @Override
    public void onListFragmentInteraction(Object object) {
        String viewType = (String)object;
        Intent intent = new Intent(this, DataViewActivity.class);
        intent.putExtra(ParamNames.STREAM_NAME, mStreamName);
        intent.putExtra(ParamNames.DISPLAY_PARAMS, viewType);
        startActivity(intent);
    }
}
