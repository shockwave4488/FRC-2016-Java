using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WPILib.Extras;
using WPILib;

namespace Robot2016
{
    /// <summary>
    /// A class for robot controllers.
    /// </summary>
    class Controllers
    {
        /// <summary>
        /// Primary controller.
        /// </summary>
        XboxController primary;

        /// <summary>
        /// Grabs speed for primary controller drive.
        /// </summary>
        public double GetSpeed => primary.GetLeftYAxis();
        /// <summary>
        /// Grabs turn for primary controller drive.
        /// </summary>
        public double GetTurn => primary.GetRightXAxis();
    }
}
