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
        /// Secondary controller.
        /// </summary>
        XboxController secondary;

        /// <summary>
        /// Grabs speed for primary controller drive.
        /// </summary>
        public double GetSpeed => primary.GetLeftYAxis();
        /// <summary>
        /// Grabs turn for primary controller drive.
        /// </summary>
        public double GetTurn => primary.GetRightXAxis();
        /// <summary>
        /// Grabs state of A button to spin shooter wheel (can change if we choose to, which we probably will)
        /// </summary>
        public bool GetSpinButton => primary.GetA();
        /// <summary>
        /// Grabs state of B button to active shooting mechanism (can change if we choose to, which we probably will)
        /// </summary>
        public bool GetShootButton => primary.GetB();

        /// <summary>
        /// Grabs intake arm value for manual control
        /// </summary>
        public double GetIntakeArmManual => secondary.GetLeftYAxis();

        public Controllers()
        {
            XboxController primary = new XboxController(0);
        }

    }
}
