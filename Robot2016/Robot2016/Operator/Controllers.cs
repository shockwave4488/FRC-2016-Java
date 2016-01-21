using WPILib.Extras;

namespace Robot2016.Operator
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
        /// <summary>
        /// Grabs state of A button to spin shooter wheel (can change if we choose to, which we probably will)
        /// </summary>
        public bool GetSpinButton => primary.GetA();
        /// <summary>
        /// Grabs state of B button to active shooting mechanism (can change if we choose to, which we probably will)
        /// </summary>
        public bool GetShootButton => primary.GetB();

        public Controllers()
        {
            XboxController primary = new XboxController(0);
        }

    }
}
