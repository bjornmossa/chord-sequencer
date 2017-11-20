ChordFactory {
	classvar notes, alters, shapes;
	var <state, gui, <gain, <dur, note, octave, alter, shape;
	// gui elements
	var on_button,
	    sel_base,
		sel_alter,
		sel_shape,
		octave_knob,
		gain_knob,
	    sel_dur,
	    oct_num,
	    gain_num;

	*new {
		|n_callback, a_callback, d_callback|
		^super.new.init(n_callback, a_callback, d_callback, notes, alters, shapes);
	}

	*initClass {
		notes = [\A, \B, \C, \D, \E, \F, \G];
		alters = [nil, \s, \b];
		shapes = [
			\major,
            \minor,
            \major7,
            \dom7,
            \minor7,
            \aug,
            \dim,
            \dim7,
            '1',
            '5',
            \plus,
            \sharp5,
            \msharp5,
            \sus2,
            \sus4,
            '6',
            \m6,
            '7sus2',
            '7sus4',
            '7flat5',
            \m7flat5,
            '7sharp5',
            \m7sharp5,
            '9',
            \m9,
            \m7sharp9,
            \maj9,
            '9sus4',
            '6by9',
            \m6by9,
            '7flat9',
            \m7flat9,
            '7flat10',
            '9sharp5',
            \m9sharp5,
            '7sharp5flat9',
            \m7sharp5flat9,
            '11',
            \m11,
            \maj11,
            '11sharp',
            \m11sharp,
            '13',
            \m13
		];
	}

	init {
		|n_callback, a_callback, d_callback, notes, alters, shapes|

		state = 0;
		note = notes[0];
		octave = 1;
		shape = shapes[0];

		on_button = Button().states_([
			["off", Color.blue, Color.white],
			["on", Color.white, Color.blue],
		]).action_({
			|btn|
			state = btn.value;
			n_callback.value;
			a_callback.value;
			d_callback.value;
		});

		sel_base = PopUpMenu().items_(notes).action_({
			|sel|
			note = notes[sel.value];
			state.postln;
			if (state > 0, {n_callback.value});
		});
		
		sel_alter = PopUpMenu().items_(alters).action_({
			|sel|
			alter = alters[sel.value];
			if (state > 0, {n_callback.value});
		});

		sel_shape = PopUpMenu().items_(shapes).action_({
			|sel|
			shape = shapes[sel.value];
			if (state > 0, {n_callback.value});
		});

		oct_num = NumberBox();

		octave_knob = Knob().keystep_(0.1).action_({
			|knob|
			octave = ((knob.value).linlin(0.0, 1.0, 1, 5)).round;
			oct_num.value = octave;
			if (state > 0, {n_callback.value});
		});

		gain_num = NumberBox();

		gain_knob = Knob().action_({
			|knob|
			gain = knob.value;
			gain_num.value = gain;
			if (state > 0, {a_callback.value});
		});

		sel_dur = PopUpMenu().items_(((4..2)++(1..32).collect({|i| 1 / i }))).action_({
			|sel|
			dur = sel.item;
			if (state > 0, {d_callback.value});
		});

		^this;
	}

	getChord {
		if (alter.isNil, {
			^(note++octave++shape).asSymbol.asNotes;
		}, {
			^(note++alter++octave++shape).asSymbol.asNotes;
		})
	}

	gui {
		var g;
		g = VLayout(
			on_button,
			StaticText().string_("note:"),
			sel_base,
			StaticText().string_("alteration:"),
			sel_alter,
			StaticText().string_("shape:"),
			sel_shape,
			StaticText().string_("octave:"),
			octave_knob,
			oct_num,
			StaticText().string_("gain:"),
			gain_knob,
			gain_num,
			sel_dur
		);
		^g;
	}
}