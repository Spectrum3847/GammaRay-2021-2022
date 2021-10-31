//Created by Spectrum3847
package frc.lib.gamepads;

import edu.wpi.first.wpilibj2.command.button.Button;
import frc.lib.drivers.SpectrumDigitalInput;

public class DigitalInputButton extends Button {

	SpectrumDigitalInput input;

	public DigitalInputButton(SpectrumDigitalInput i) {
		input = i;
	}

	public boolean get(){
		return !input.get();
	}
}
