- - -

# `public class AudioTipFragment`

- - -

*`Fragment` -> `AudioTipFragment`*

  

This `Fragment` is design to work with similar sized sub elements (such as navigation and video controls panes). It spawns a media player with basic controls which can play a helpful tip in audio only formats. The playback does not start automatically after the fragment's creation.

  

## Public methods

  

### `public static AudioTipFragment newInstance(int resource)`

This factory method creates a new `AudioTipFragment` using a standard Android resource identifier. See Android [resource types](https://developer.android.com/guide/topics/resources/available-resources) for more information.

#### Parameters

`int resource` : An audio resource identifier (e.g. `R.raw.audio_resource`). If the identifier is a video, then only the audio is played back (todo:verify).

#### Returns

`AudioTipFragment` with an audio resource loaded.

### `void closeAudioTip(Event event)`

This method is called via the `EventBus.Subscribe` annotation. It triggers the `MediaPlayer` to stop the current playback and calls the `mListener.onAudioTipClose()` implementation on the listener dependency. This signals to the spawning activity that the user intends to close and destroy `this`.

#### Parameters

`Event event` : The `Event` object emitted from the `EventBus`. The `event` should be of type `MediaEvent` (a derived class for media events), and `event`'s message should be `MediaEvent.CLOSE` in order the stop and close the fragment.

## Private Classes & Methods

### `void toggleAudioTipPlayback()`

### `void updateAudioTipSeekBar()`

- - -

# `public class InformationFragment`

## Public Methods

##### `public static InformationFragment newInstance()`.

#### returns
Creates a new instance and returns a new `Information Fragment`

## Private Methods and Classes
##### `private String getDisplayDate()`
#### returns
A time string formatted for the current locale.
- - -

# `public class NavigationFragment`

- - -

## Public Classes & Methods

### `public static NavigationFragment newInstance()`

#### returns

A new `NavigationFragment`
- - -
###### `public interface OnFragmentInteractionListener`
###### `public void onNavigateTasks()`
###### `public void onNavigateAudioTip()`
###### `public void onNavigateVideoTutorial()`
###### `public void onNavigate(Event event)`
---

## Private Classes & Methods

  

###### `private void updateNavigation()`

---
# `public class ChildPlayFragment`

This `Fragment` is designed to work with closely with `GuidedActivity` and `ChildPlayDirective`. It spawns a new `LinearLayout` along with "children" layouts.

  
Implements: *`TimedTextLoaderListener`*

- - -
`fragment` -> `ScreeningFragment` -> `ChildPlayFragment`

  

## Public methods

 
##### `public static ChildPlayFragment newInstance(ChildPlayDirective)`

#### parameters

ChildPlayDirective

#### throws
#### returns

new ChildPlayFragment

### `TimedTextLoaderListener` Implementation

  

##### `public void onPrepared(View, TimedTextObject)`

######  `public class ViewModelProvider`

##### `public void onCreate(Bundle savedInstanceState)`
##### `public void onCreateView(LatoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`
######  *Inflates a view
######  *Creates a child element
###### *Create vertical column called `prompt`
  

## Private Classes & Methods

  

##### `class TimedTextLoader [extends Async<InputStream, Void, TimedTextObject>]`

  

##### `TimedTextLoader(View target, TimedTextLoaderListener listener)`

  

##### `protected TimedTextObject doInBackground(InputStream... params)`

  

##### private void onTimedText(Caption, LinearLayout)

  

- - -

# `public abstract class ChildPlayDirective`

*`Serializable`*, *`TimedTask`*

- - -

  

## Public methods

  

###### `public ChildPlayFragment`

  

###### `public void addTeleprompter(String title, InputStream content)`

  
  

### Parameters

`title` : The title of feed. This user facing element is a title for teleprompt stream. If this value is `null`, then no title is created or displayed.

  

`Content` : The content of the feed.

  

- Content follows a popular subtitle format, SRT, for displaying sequences of texts at specified times, intervals.

  

- Content is an `InputStream` from which a BufferedReader can read and parse the SRT data.

  

(Format SRT.java parseStream(Input Stream)

  

##### `public void removeTeleprompter(int index)`

  

### Parameters

  

`index` : The index of teleprompter to remove.

  

### Throws

  

`java.lang.IndexOutOfBoundsException`

This exception is raised when the epression `index >= N || index < 0` where `N` is the number of teleprompts.

  

### `TimedTask` Implementation

  
  

### Parameters


##### `public void onPrepared(View target, TimedTextObject timedText)`

  
## Private Classes
`private static class TimedTextLoader`
Extends `InputStreams`
Parameters `TimedTextObject`

##### `TimedTextLoader(View target , TimedTextLoaderListener listener)`
##### `protected TimedTextObject doInBackground(InputStream... params)`
##### `protected void onPostExecute(TimedTextObject result)`

## Private Methods

  

##### `private void onTimedText(Caption caption, LinearLayou target)`

  

# Models

- - -

# `public abstract class CodeModel`

- - -

*`Serializable`*, *`Preprocessable`*

- - -

This model represents a codable model that would be used in conducting a screening process.

## Public Classes & Methods


#### `public CodeModel(String question, String... options)`
#### `public String getQuestion()`
#### `public String getOption(int index)`

### Parameters

### Throws

`ArrayIndexOutOfBoundsException`

#### `public int getRaw()`
#### `public void setCode(int code)`
- - -
# public class MediaViewModel

- - -

More information on Android's synchronized `LiveData<T>` type can be found [here](https://developer.android.com/topic/libraries/architecture/livedata).

  
## Public Classes & Methods


### Get

#### `public LiveData<String> getPlaybackState()`
#### `public LiveData<Integer> getDuration()`
#### `public LiveData<Boolean> getCapturingState()`
#### `public LiveData<Integer> getSeekToPosition()`
#### `public LiveData<Integer> getVideoPosition()`
#### `public LiveData<Integer> getBufferPercentage()`

### Set
 
#### `public void setPlaybackState(String playbackState)`

#### `public void setDuration(Integer duration)`


#### `public void setSeekToPosition(Integer pos)`

#### `pubilc void setCapturingState(Boolean bool)`

#### `public void setVideoPosition(Integer pos)`

#### `public void setBufferPercentage(Integer precent)`

#### `public void reset()`

Modify all values held in `LiveData<T>` types to `null`.

 
## Private Classes & Methods

# Public Class GuidedActivity
Activity life cycle 
## Extends

`AppCompatActivity`
## parameters

#### `GuidedWorkFlow`

## Implementation

#### `InformationFragment`
#### `SidebarFragment`
#### `NavigationFragment`
#### `AudioTipFragment`
#### `Video TutorialFragment`
#### `VideoControllerFragment`

## Public Classes and Methods
#### `public void onStart`
#### `public void onNavigationAudioTip`
#### `public void onNaviagationVideoTutorial`
#### `public void onSidebarInteraction`
#### `public voidInformationInteraction`


## Private Classes and Methods
####  `protected void onCreate`

