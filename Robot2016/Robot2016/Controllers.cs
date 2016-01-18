using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib.Extras;
using WPILib;

namespace Robot2016
{
    class Controllers
    {
        XboxController primary;

        public double GetSpeed => primary.GetLeftYAxis();
        public double GetTurn => primary.GetRightXAxis();
    }
}
