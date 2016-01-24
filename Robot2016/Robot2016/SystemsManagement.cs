using Robot2016.Components;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Robot2016
{
    class SystemsManagement
    {
        public ArmState armState { get; private set; }

        public SystemsManagement()
        {
            armState = ArmState.Idle;
            shooterState = ShooterState.Idle;
        }

        public enum ArmState
        {
            Idle,
            Intake,
            Store,
            Load,
            Shoot,
            DefenseLow,
            DefenseHigh,
        
        }

        public ShooterState shooterState { get; private set; }
        public WPILib.Talon armMotor = new WPILib.Talon(1);
        public bool LoadButton { get; set; }
        public bool ChargeButton { get; set; }
        public bool ShootButton { get; set; }
        public bool IntakeButton { get; set; }
        public bool DefenseLowButton { get; set; }
        public bool DefenseHighButton { get; set; }
        public Arm ManipulatorArm = new Arm();
        public Intake ManipulatorIntake = new Intake();
        internal bool IntakeHasBall;
        internal bool ShooterHasBall;
        internal bool ShooterShotBall;


        public enum ShooterState
        {
            Idle,
            Load,
            Charge,
            Shoot,
        }

        public void Update()
        {
            switch (shooterState)
            {
                case ShooterState.Idle:
                    ShooterIdle();
                    if (LoadButton && armState == ArmState.Store )
                    {
                        shooterState = ShooterState.Load;
                    }

                    break;
                case ShooterState.Load:
                    ShooterLoad();
                    if (ChargeButton && ShooterHasBall)
                    {
                        shooterState = ShooterState.Charge;
                    }

                    break;

                    
                case ShooterState.Charge:
                    ShooterCharge();
                    if (ShootButton && ChargeButton)
                    {
                        shooterState = ShooterState.Shoot;
                    }
                    if (!ChargeButton)
                    {
                        shooterState = ShooterState.Load;
                    }

                    break;

                case ShooterState.Shoot:
                    ShooterShoot();
                    if (ShooterShotBall)
                    {
                        shooterState = ShooterState.Idle;
                    }

                    break;

            }

            switch (armState)
            {
                case ArmState.Idle:
                    ArmIdle();
                    if (IntakeButton && !ShooterHasBall)
                    {
                        armState = ArmState.Intake;
                    }
                    if (ShootButton && ShooterHasBall)
                    {
                        armState = ArmState.Shoot;
                    }
                    if (DefenseLowButton)
                    {
                        armState = ArmState.DefenseLow;
                    }
                    if (DefenseHighButton)
                    {
                        armState = ArmState.DefenseHigh;
                    }
                    break;

                case ArmState.Intake:
                    ArmIntake();
                    if (IntakeHasBall)
                    {
                        armState = ArmState.Store;
                    }
                    if (!IntakeButton)
                    {
                        armState = ArmState.Idle;
                    }
                    break;

                case ArmState.Store:
                    ArmStore();
                    if (LoadButton)
                    {
                        armState = ArmState.Load;
                    }
                    if (DefenseLowButton)
                    {
                        armState = ArmState.DefenseLow;
                    }
                    if (DefenseHighButton)
                    {
                        armState = ArmState.DefenseHigh;
                    }
                    break;

                case ArmState.Load:
                    ArmLoad();
                    if (!IntakeHasBall && ShooterHasBall)
                    {
                        armState = ArmState.Idle;
                    } 
                    break;

                case ArmState.Shoot:
                    ArmShoot();
                    if ( ShooterShotBall || !ShootButton)
                    {
                        armState = ArmState.Idle;
                    }
                    
                    break;

               
                case ArmState.DefenseLow:
                    ArmDefenseLow();
                    if (!DefenseLowButton && IntakeHasBall)
                    {
                        armState = ArmState.Store;
                    }
                    if (!DefenseLowButton && !IntakeHasBall)
                    {
                        armState = ArmState.Idle;
                    }

                    break;

                case ArmState.DefenseHigh:
                    ArmDefenseHigh();
                    if (!DefenseHighButton && IntakeHasBall)
                    {
                        armState = ArmState.Store;
                    }
                    if (!DefenseHighButton && !IntakeHasBall)
                    {
                        armState = ArmState.Idle;
                    }
                    break;
            }

        }

        public void ShooterIdle()
        {
            //Wheels stopped and shooter turret down/reset
        }

        public void ShooterLoad()
        {
            //Wheels run reverse and move turret to load position
            //when we get the ball, move turret down
        }

        public void ShooterCharge()
        {
            //Aims turret and spins front shooter wheels forward
        }

        public void ShooterShoot()
        {
            //Spins index wheels so ball goes into shooter wheels and shoots
        }

        public void ArmIdle()
        {
            armState = ArmState.Load;

        }

        public void ArmIntake()
        {
           //Arm moves out and spins intake wheel(s) 
        }

        public void ArmStore()
        {
            //Intake wheels stop spinning. Ready to load ball.
        }

        public void ArmLoad()
        {
            //Moves arm toward shooter, then spins intake wheel to load when shooter is in position
        }

        public void ArmShoot()
        {
            //Moves arm out of way of shooter
        }

        public void ArmDefenseLow()
        {
            //Moves arm down to be able to get past or move certain defenses
        }

        public void ArmDefenseHigh()
        {
            //Moves arm up to be able to get past or move certain defenses
        }

      



    }
}
