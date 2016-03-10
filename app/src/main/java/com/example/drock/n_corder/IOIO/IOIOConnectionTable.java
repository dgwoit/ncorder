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
package com.example.drock.n_corder.IOIO;

import com.example.drock.n_corder.IOIO.IOIOConnectionInfo;

import java.util.LinkedList;
import java.util.List;

public class IOIOConnectionTable {

    List<IOIOConnectionInfo> mConnectionInfo;


    public IOIOConnectionTable() {
        LoadSettings();
    }

    //we should be able to load this from a file so we can have different connection info for the
    //IOIO OTG board, and for the Seeed Studio IOIO OTG base shield board
    public void LoadSettings() {
        mConnectionInfo = new LinkedList<IOIOConnectionInfo>();

        //add data for Seeed Studio IOIO OTG grove base shield board
        mConnectionInfo.add(new IOIOConnectionInfo("J1 (SPI)", new String[]{IOIOConnectionInfo.BUS_TYPE_SPI, IOIOConnectionInfo.BUS_TYPE_UART}, 4));
        mConnectionInfo.add(new IOIOConnectionInfo("J2 (SPI)", new String[]{IOIOConnectionInfo.BUS_TYPE_SPI, IOIOConnectionInfo.BUS_TYPE_UART}, 1));
        mConnectionInfo.add(new IOIOConnectionInfo("J3 (UART)", new String[]{IOIOConnectionInfo.BUS_TYPE_UART}, 11));
        mConnectionInfo.add(new IOIOConnectionInfo("J6 (UART)", new String[]{IOIOConnectionInfo.BUS_TYPE_UART}, 13));
        mConnectionInfo.add(new IOIOConnectionInfo("J7 (A/D)", new String[]{IOIOConnectionInfo.BUS_TYPE_A2D, IOIOConnectionInfo.BUS_TYPE_UART}, 33));
        mConnectionInfo.add(new IOIOConnectionInfo("J8 (A/D)", new String[]{IOIOConnectionInfo.BUS_TYPE_A2D, IOIOConnectionInfo.BUS_TYPE_UART}, 37));
    }

    public List<IOIOConnectionInfo> getConnectionInfo() {
        return mConnectionInfo;
    }

    public IOIOConnectionInfo getConnectionInfo(String connectionId) {
        assert mConnectionInfo != null;
        assert !connectionId.isEmpty();
        for(IOIOConnectionInfo info: mConnectionInfo) {
            if(info.getName().compareTo(connectionId) == 0)
                return info;
        }

        return null;
    }
}
