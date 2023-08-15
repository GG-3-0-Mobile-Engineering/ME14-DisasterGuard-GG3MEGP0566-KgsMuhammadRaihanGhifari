<h1 align="center">Final Project(DisasterGuard)</h1>

<p align="center">
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
</p>

<p align="center">  
DisasterGuard demonstrates modern Android development with Retrofit, Koin, Coroutines, Flow, Jetpack (Room, ViewModel), and Material Design based on MVVM architecture.
</p>
</br>

## App Screenshots

| Mode  | Screenshots                                                                                | 
|-------|----------------------------------------------------------------------------------------|
| <b>Light</b> | <img src="readphoto/disasterlight.png" alt="disasterlight">|
| <b>Dark</b>  | <img src="readphoto/disasterdark.png" alt="disasterdark">  |

## Purpose

Contained within this repository is the source code for the Disaster App, representing the final project submission for the Generasi GIGIH 3.0 initiative. This application is crafted with the intention of furnishing data concerning disasters and their respective positions on a map. Moreover, it incorporates a range of functionalities that enable users to refine and visualize the disaster-related information.

## Features

* Display disasters according to a specific time period.
* Indicate the positions of disasters on the map.
* Enumerate disasters with filtering choices (flood, earthquake, fire, haze, wind, volcano).
* Filter disasters based on the area.
* Get notification alerts contingent on water level conditions.
* Provide compatibility for both light and dark visual themes.
* Incorporate a loading animation capability.

## Open API

DisasterGuard using the [PetaBencana]([https://docs.petabencana.id/](https://docs.petabencana.id/)) for constructing RESTful API.<br>
DisasterGuard provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Disaster.

## Architecture
**DisasterGuard** project is built upon the foundational principles of the MVVM (Model-View-ViewModel) architecture and incorporates the Repository pattern as a structural framework. This architectural design aligns closely with Google's official recommendations for building robust and maintainable Android applications, which follows the [Google's official architecture guidance](https://developer.android.com/topic/architecture).

![architecture](readphoto/figure0.png)

The overall architecture of **DisasterGuard** is composed of two layers; the UI layer and the data layer. Each layer has dedicated components and they have each different responsibilities, as defined below:

**DisasterGuard** was built with [Guide to app architecture](https://developer.android.com/topic/architecture), so it would be a great sample to show how the architecture works in real-world projects.


### UI Layer

![architecture](readphoto/figure2.png)

The UI layer consists of UI elements to configure screens that could interact with users and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) that holds app states and restores data when configuration changes.
- UI elements observe the data flow via [DataBinding](https://developer.android.com/topic/libraries/data-binding), which is the most essential part of the MVVM architecture.

### Data Layer

![architecture](readphoto/figure3.png)

The Data Layer encompasses repositories that encapsulate essential business logic. This logic involves tasks such as retrieving data from the local database and fetching remote data from the network. This layer is constructed with an "offline-first" approach, prioritizing operations that can be performed without an active network connection. Furthermore, the Data Layer adheres to the principle of maintaining a [single source of truth](https://en.wikipedia.org/wiki/Single_source_of_truth) principle.<br>

**DisasterGuard** An offline-first application is designed to execute either all or a critical subset of its core operations without requiring an active internet connection. This functionality ensures that users can access essential features even when offline, eliminating the need for constant network connectivity. Consequently, users are not obligated to stay continuously updated with network resources, leading to a reduction in their data consumption. For more in-depth insights, For further information, you can check out [Build an offline-first app](https://developer.android.com/topic/architecture/data-layer/offline-first).

## Modularization

![architecture](readphoto/modular.png)

**DisasterGuard** adopted modularization strategies below:

- **Reusability**: Modulizing reusable codes properly enable opportunities for code sharing and limits code accessibility in other modules at the same time.
- **Parallel Building**: Each module can be run in parallel and it reduces the build time.
- **Strict visibility control**: Modules restrict to expose dedicated components and access to other layers, so it prevents they're being used outside the module
- **Decentralized focusing**: Each developer team can assign their dedicated module and they can focus on their own modules.

For more information, check out the [Guide to Android app modularization](https://developer.android.com/topic/modularization).

### Encryption

Implement encryption on Room Database with SQLCipher on DisasterGuard.

![architecture](readphoto/encription.png)


## Download DisasterGuard App

You can access the application by downloading it through the provided link:
<a href="https://drive.google.com/file/d/1QAo4xRmdhcSeBdtH9oXp5Y2KScHMveYb/view?usp=sharing">Click here.</a>

