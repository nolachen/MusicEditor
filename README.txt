IMusicEditorModel is the interface with public methods: add, remove, playSimultaneously,
playConsecutively, getAllNotes, getNotesAtBeat, length, and getTempo.

MusicEditorModel is the model class that implements IMusicEditorModel.
It contains a TreeMap from beat number to a list of notes beginning at that beat.
Previously, we stored each note at each beat that it was playing at. We redesigned our model so
that it would only store each note at its starting beat in the TreeMap. This is following the
single point of truth principle, where every piece of information should only be stored once.
Since each note has a duration and start beat, it would be redundant to store it at every beat.

Note represents a musical note with pitch, octave, start beat, duration, instrument, and volume.
The instrument and volume fields were added to support MIDI.
Note can get the next note which is helpful to make the range of all notes for the model. The map representing the music is passed in from the model to Note in order to add this note to the model.

Pitch is an enum with all the possible pitches.