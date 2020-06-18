ChordSeq2 {
  classvar chords, player;

  *initClass {
	// load synthdef here
	chords = List();
	chords.add(SChord());

	player = Task({
	  loop {
		chords.select(_.active).do({
		  | chord |
		  chord.getNotes.collect({
			| note |
			Synth(\singrain, [freq: note.midicps, amp: chord.amp / chord.getNotes.size, sustain: chord.dur]);
		  });	
		  chord.dur.wait;
		})
	  }
	});
  }

  *play {
	player.play;
  }

  *stop {
	player.stop;
  }
}