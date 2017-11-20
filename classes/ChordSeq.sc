// Класс должен иметь методы для возврата 3х
// Pdefn - для нот, амплитуд и длительностей.

// Pdefn(\notes, Pseq(~q.getNotes, inf)); - для нот,
// Pdefn переопределяются по вызовам коллбэков

ChordSeq {
	var <bars, gui, noteStream, ampStream;

	*new {
		|length|
		^super.new.init(length);
	}

	init {
		|length|
		var bars_gui, change_note_callback, change_amp_callback, change_dur_callback;

				
		Pdefn(\csn, Pseq([0], inf));
		Pdefn(\csa, Pseq([0], inf));
		Pdefn(\csd, Pseq([1], inf));

		change_note_callback = {			
			Pdefn(\csn, Pseq(this.getNotes.value, inf));
			"chord changed".postln;
		};

		change_amp_callback = {
			Pdefn(\csa, Pseq(this.getAmps.value, inf));
			"amps changed".postln;
		};
		
		change_dur_callback = {
			Pdefn(\csd, Pseq(this.getDurs.value, inf));
			"durs changed".postln;
		};

		bars = { ChordFactory(change_note_callback, change_amp_callback, change_dur_callback) }! length;
	}

	getNotes {
		var notes;

		notes = bars.collect({
			|item|
			if(item.state > 0 , {item.getChord;});
		});

		^notes.takeThese({|i| i.isNil});
	}

	getAmps {
		var amps;

		amps = bars.collect({
			|item|
			if(item.state > 0 , {item.gain;});
		});

		^amps.takeThese({|i| i.isNil});
	}

	getDurs {
		var durs;

		durs = bars.collect({
			|item|
			if(item.state > 0 , {item.dur;});
		});

		^durs.takeThese({|i| i.isNil});
	}

	getChordStream {
		^Pdefn(\csn);
	}
	
	getAmpStream {
		^Pdefn(\csa);
	}

	getDurStream {
		^Pdefn(\csd);
	}

	gui {
		var g, bars_gui;
		bars_gui = bars.collect({|bar| bar.gui });

		g = HLayout(*bars_gui);
		^g;
	}
}