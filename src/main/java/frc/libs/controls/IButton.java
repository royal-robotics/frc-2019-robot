package frc.libs.controls;

public interface IButton {
    enum ButtonType {
        /***
         * isPressed function returns true while the button is down
         */
        Hold,

        /***
         * isPressed function returns true a single time until button is toggled up and back down
         */
        Toggle, //isPressed function returns true a single time until button is toggled up and back down
    }

    boolean isPressed();
}