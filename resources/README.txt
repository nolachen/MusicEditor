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
Note has several constructors, including one to construct notes from the data in the text files,
and a convenience constructor that initializes the instrument and volume to a constant.

To implement our views, we made a ViewModel to give data to the View without giving it direct
access to the Model. The ViewModel has several helpful methods to transfer data to the View,
including getNotesAtBeat, length (in beats), and getNoteRange (a list of strings of the range of
pitches).

We also made sure that any method in the model interface that would return lists of notes would
return an unmodifiable list and also, we created a wrapper class for Note called ImmutableNote so
 that it could not be changed.

For the text view, we initialized an arraylist of arraylists of empty strings, and placed the
existing notes accordingly.

For the GUI view, we created a custom JPanel for the notes grid, and a JPanel for the pitches on
the left side. We put these both into another JPanel and added it into a scrollpane. The notes
grid is drawn by placing rectangles where each note belongs, and then drawing the entire measure
grid on top.

For the midi view, we used a synchronizer with a receiver and sent out messages directly to the
receiver without using a sequencer. For each note in the viewModel, we wrote it in a start and
stop message format to give to the synchronizer to play. The view contains a hashmap of
instrument to channel so we can easily assign and keep track of the instruments at each channel.