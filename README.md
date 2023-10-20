# FlickrSearch

Flickr Search is an application that searched for tags on the public photos feed.

## Contents

- [Compatibility](#compatibility)
- [Demo](#demo)
- [Features](#features)
- [Setup](#setup)
- [Todo](#todo)

## Compatibility

- **Gradle Version:** 8.0
- **Gradle JDK:** JetBrains Runtime version 17.0.6 (jbr-17)
- **Minimum SDK:** 28
- **Target SDK:** 34

## Demo

![Demo](demo.gif)

## Features

This app includes a handful of fun features, some of which I'll point out here.
- Uses Retrofit to query the end point for a list of public photos from the feed.
- Uses a Lazy view (specifically `LazyVerticalGrid`) to display the contents.
- Includes a collapsable top app bar with the serach bar integrated and always visible. 
- Uses Dagger Hilt for dependency injection.
- Uses a unidirectional MVI style view model pattern to keep UI and buisiness logic seperate.
- Uses compose navigation to navigate between screens.
- Uses Coil to load and display the images in an async manner.
- Utilizes the number of fixed columns in the `LazyVerticalGrid` in landscape mode to make it look better.
- Displays the tags in a nice `FlowRow` view

## Setup

1. Clone the repository:

    ```sh
    $ git clone https://github.com/garrett-livefront/FlickrSearch.git
    ```

### Run the App

1. Open the project in Android Studio.
2. Once gradle is done building, press the play button on the top bar towards the right with the target `app` set an a virtual device.

### Running Tests

1. In Android Studio's toolbar where `app` was selected to run, click the drop down and select `SearchViewModelTests`
	- Alternatively you could find the `com.livefront.flickrsearch (test)` package in the Project tab on the left, right click on that, and click run.

## Todo

If I had more time to work on this project, I would like to add:
- [ ] More themeing, I didn't spend a ton of time designing the UI and it looks a little plain. It may work in dark mode but no time was put towards making sure it looks good.
- [ ] More accessability, I added content descriptions for Talkback but more attention to font sizes and contrast would have been nice.
- [ ] More unit tests, I added unit tests to the main view model which does the majority of the heavy lifting, but adding more tests to bring the test coverage up above 90% would be ideal.
- [ ] More landscape support, I took the time to make sure the search view looked good and worked in landscape but didn't put that samme time towards the detail screen.
- [ ] I would have liked to add a button on the detail screen to share, or at the very least copy the URL for the image.
- [ ] Right now the navigation uses the detault animations, I would have liked to add a swipe up and swipe right animations for navigating to and from screens.
