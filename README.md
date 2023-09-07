# Rick and Morty Multiverse

### Overview
This project is about the character list of Rick and Morty series which also shows their details.
This app loads information from [The Rick and Morty API](https://rickandmortyapi.com/api/)

### Architecture

Here we have tried to ensure Clean architecture

- **data**  This module is responsible for local database, networkpojo, repository and restapi service

- **di** This module is used for dependency injection. In this app we have used Hilt

- **presenter** This module holds the view and viewModel classes
  We have used MVVM pattern

**Unit testing**
Unit testing is done for ViewModel and Repository

![My Image](/ss4.png)
![My Image](/ss1.png)

###Tech Libraries Used:

Kotlin Coroutines
Dagger-Hilt
LiveData
Lifecycle
ViewModel
Retrofit
OkHttp

Room - for local storage
Data Binding - for UI binding
Moshi - for JSON object creation
Picasso - for loading image
Mockito - for unit testing












