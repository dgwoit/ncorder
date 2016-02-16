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

import java.util.List;


public class IOIOConnectionInfo {
    public final static String BUS_TYPE_A2D = "A/D";
    public final static String BUS_TYPE_SPI = "SPI";
    public final static String BUS_TYPE_UART = "UART";

    protected String mName;
    protected String[] mTypes;
    protected int mPin;

    public IOIOConnectionInfo(String name, String[] types, int pin) {
        mName = name;
        mTypes = types;
        mPin = pin;
    }

    public String getName() {
        return mName;
    }

    public String[] getTypes() {
        return mTypes;
    }

    public int getPin() {
        return mPin;
    }
}
