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

<img src="/ss4.png" width=25% height=25%>
<img src="/ss1.png" width=25% height=25%>
<img src="/ss2.png" width=25% height=25%>

###Tech Libraries Used:

Kotlin Coroutines<br />
Dagger-Hilt<br />
LiveData<br />
Lifecycle<br />
ViewModel<br />
Retrofit<br />
OkHttp<br />

Room - for local storage<br />
Data Binding - for UI binding<br />
Moshi - for JSON object creation<br />
Picasso - for loading image<br />
Mockito - for unit testing<br />












