
# Rick and Morty App

For the implementation of this coding test, I have done a MVVM architecture app with three different layers, the UI layer (Activities and ViewModel), the domain layer (use cases), and the data layer (Repositories and data sources).

I have chosen Room as the data cache environment and done an 'offline first' strategy, where the data flows from de local data source and can be refreshed from de remote source.

As a reactive pattern, I have chosen Flow. Data flows from de data sources to the viewmodel with this mechanism, and it's transformed to a StateFlow at the viewmodel for UI consumption.

I have also incorporated Hilt as a dependency injector and created all the necessary modules.

Regarding the UI, I implemented a simple list with some animations, and a detail.

To conclude, I have written some unit tests for the repository and data sources layer.
