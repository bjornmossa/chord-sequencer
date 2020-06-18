SChord {
  classvar <notes, <alters, <shapes;
  var <>dur = 1, <>note, <>octave = 2, <>alter, <>shape, <>amp = 1, <>active = false;

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

  *new {
	^super.new.init();
  }

  init {
	note = notes[0];
	alter = alters[0];
	shape = shapes[0];

	^this;
  }

  getNotes {
	if (alter.isNil,
	  {^(note++octave++shape).asSymbol.asNotes;},
	  {^(note++alter++octave++shape).asSymbol.asNotes;}
	)
  }
}
