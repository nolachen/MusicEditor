IMusicEditorModel is the interface with public methods.

MusicEditorModel is the model class which has a TreeMap to represent the music data. The TreeMap maps the integer beats to a list of notes that are played at each beat. 

Note represents a musical note with pitch, octave, start beat, and duration.
Note can get the next note which is helpful to make the range of all notes for the model. The map representing the music is passed in from the model to Note in order to add this note to the model.

Pitch is an enum with all the possible pitches.